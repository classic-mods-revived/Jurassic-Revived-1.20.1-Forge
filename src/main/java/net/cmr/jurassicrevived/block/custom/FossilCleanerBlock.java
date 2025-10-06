package net.cmr.jurassicrevived.block.custom;

import net.cmr.jurassicrevived.block.entity.custom.FossilCleanerBlockEntity;
import net.cmr.jurassicrevived.block.entity.custom.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class FossilCleanerBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public FossilCleanerBlock(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE_NORTH = Shapes.box(
            1.0 / 16.0, 0.0 / 16.0, 1.0 / 16.0,
            15.0 / 16.0, 13.0 / 16.0, 15.0 / 16.0
    );

    private static final VoxelShape SHAPE_SOUTH = rotateShapeY(SHAPE_NORTH, 180);
    private static final VoxelShape SHAPE_WEST  = rotateShapeY(SHAPE_NORTH, 90);
    private static final VoxelShape SHAPE_EAST  = rotateShapeY(SHAPE_NORTH, -90);

    private static VoxelShape rotateShapeY(VoxelShape shape, int degrees) {
        double rad = Math.toRadians(((degrees % 360) + 360) % 360);
        int turns = (int) Math.round(rad / (Math.PI / 2)); // multiples of 90 only
        turns = ((turns % 4) + 4) % 4;

        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};
        for (int i = 0; i < turns; i++) {
            buffer[1] = Shapes.empty();
            shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                double nMinX = 1.0 - maxZ;
                double nMinZ = minX;
                double nMaxX = 1.0 - minZ;
                double nMaxZ = maxX;
                buffer[1] = Shapes.or(buffer[1], Shapes.box(nMinX, minY, nMinZ, nMaxX, maxY, nMaxZ));
            });
            shape = buffer[1];
        }
        return shape;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction dir = state.getValue(FACING);
        return switch (dir) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case WEST  -> SHAPE_WEST;
            case EAST  -> SHAPE_EAST;
            default    -> SHAPE_NORTH;
        };
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FossilCleanerBlockEntity(blockPos, blockState);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof FossilCleanerBlockEntity fbe) {
                // If Creative, just remove without dropping the item
                if (player != null && player.getAbilities().instabuild) {
                    level.removeBlockEntity(pos);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                    return;
                }

                // Survival/Adventure: drop item with BE data if meaningful
                ItemStack stack = new ItemStack(this.asItem());
                if (!fbe.isEmptyForDrop()) {
                    CompoundTag tag = fbe.saveWithoutMetadata();
                    BlockItem.setBlockEntityData(stack, fbe.getType(), tag);
                }

                popResource(level, pos, stack);
                level.removeBlockEntity(pos);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                return;
            }
        }
        // Fall back to default behavior only if it wasn't our BE
        super.playerWillDestroy(level, pos, state, player);
    }

    // Keep inventory-spill disabled; the item now carries contents.
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            // Intentionally do nothing here to avoid duplicate/empty drops.
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack held = pPlayer.getItemInHand(pHand);

        // Custom fluid interaction:
        // Only allow filling the machine from the held item; NEVER remove water from the machine.
        if (!pLevel.isClientSide && held.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof net.cmr.jurassicrevived.block.entity.custom.FossilCleanerBlockEntity fbe) {
                var tankCapOpt = fbe.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.FLUID_HANDLER, null);
                boolean moved = false;

                if (tankCapOpt.isPresent()) {
                    var tankHandler = tankCapOpt.orElse(null);

                    // Always treat as item -> tank transfer (water only). Ignore shift to prevent draining.
                    var itemCapOpt = held.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.FLUID_HANDLER_ITEM);
                    if (itemCapOpt.isPresent()) {
                        var itemHandler = itemCapOpt.orElse(null);

                        // Simulate drain of WATER from the item
                        net.minecraftforge.fluids.FluidStack simDrain = itemHandler.drain(
                                new net.minecraftforge.fluids.FluidStack(net.minecraft.world.level.material.Fluids.WATER, Integer.MAX_VALUE),
                                net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.SIMULATE
                        );
                        if (!simDrain.isEmpty()) {
                            // Simulate tank acceptance
                            int acceptable = tankHandler.fill(simDrain, net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.SIMULATE);
                            if (acceptable > 0) {
                                // Execute transfer
                                net.minecraftforge.fluids.FluidStack drained = itemHandler.drain(
                                        new net.minecraftforge.fluids.FluidStack(net.minecraft.world.level.material.Fluids.WATER, acceptable),
                                        net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE
                                );
                                if (!drained.isEmpty()) {
                                    int filled = tankHandler.fill(drained, net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE);
                                    if (filled > 0) {
                                        // Update held stack to the handler's container (e.g., empty bucket)
                                        ItemStack container = itemHandler.getContainer();
                                        pPlayer.setItemInHand(pHand, container);
                                        moved = true;
                                    }
                                }
                            }
                        }
                    }
                }

                if (moved) {
                    return InteractionResult.sidedSuccess(pLevel.isClientSide());
                }
            }
        }

        // Open GUI otherwise
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof net.cmr.jurassicrevived.block.entity.custom.FossilCleanerBlockEntity fossilCleanerBlockEntity) {
                net.minecraftforge.network.NetworkHooks.openScreen(((net.minecraft.server.level.ServerPlayer)pPlayer), fossilCleanerBlockEntity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return null;
        }

        return createTickerHelper(blockEntityType, ModBlockEntities.FOSSIL_CLEANER_BE.get(),
                (level1, blockPos, blockState, fossilCleanerBlockEntity) -> fossilCleanerBlockEntity.tick(level1, blockPos, blockState));
    }
}
