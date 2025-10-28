package net.cmr.jurassicrevived.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.cmr.jurassicrevived.entity.ModEntities;
import net.cmr.jurassicrevived.entity.SeatEntity;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
// ... existing code ...

public class BenchBlock extends HorizontalDirectionalBlock {

    public static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 19.0D, 19.0D);

    public BenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.SOUTH)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
    }

    // Ensure rotation of FACING property when the block is rotated (e.g., structure placement, commands, etc.)
    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // Ensure we always use a horizontal facing (N/E/S/W), even when looking up or down.
        return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        // The SHAPE is defined for NORTH. Rotate it for other facings.
        return switch (facing) {
            case NORTH -> SHAPE;
            case EAST -> rotateShape90Y(SHAPE);
            case SOUTH -> rotateShape90Y(rotateShape90Y(SHAPE));
            case WEST -> rotateShape90Y(rotateShape90Y(rotateShape90Y(SHAPE)));
            default -> SHAPE;
        };
    }

    // Utility: rotate a VoxelShape 90 degrees around Y (from NORTH to EAST)
    private static VoxelShape rotateShape90Y(VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};
        // Iterate all boxes in the shape and rotate their coordinates
        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            // Convert to block-space rotation around Y: (x,z) -> (1 - z, x)
            double rMinX = 1.0D - maxZ;
            double rMinZ = minX;
            double rMaxX = 1.0D - minZ;
            double rMaxZ = maxX;
            VoxelShape rotated = Block.box(rMinX * 16.0D, minY * 16.0D, rMinZ * 16.0D, rMaxX * 16.0D, maxY * 16.0D, rMaxZ * 16.0D);
            buffer[1] = Shapes.or(buffer[1], rotated);
        });
        return buffer[1].optimize();
    }

    // --- Sitting logic ---

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.isShiftKeyDown()) return InteractionResult.PASS;
        if (level.isClientSide) return InteractionResult.SUCCESS;

        // Reuse existing seat if present
        AABB check = new AABB(pos).inflate(0.2);
        for (SeatEntity seat : level.getEntitiesOfClass(SeatEntity.class, check)) {
            if (seat.getPassengers().isEmpty()) {
                Direction facing = state.getValue(FACING);
                Vec3 base = Vec3.atBottomCenterOf(pos).add(0, 0.45, 0);
                double forward = -0.2;
                Vec3 offset = switch (facing) {
                    case NORTH -> new Vec3(0, 0, forward);
                    case SOUTH -> new Vec3(0, 0, -forward);
                    case WEST  -> new Vec3(forward, 0, 0);
                    case EAST  -> new Vec3(-forward, 0, 0);
                    default    -> Vec3.ZERO;
                };
                Vec3 seatPos = base.add(offset);

                seat.moveTo(seatPos.x, seatPos.y, seatPos.z, facing.toYRot(), 0.0F);
                level.sendBlockUpdated(pos, state, state, Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS);

                seat.scheduleMount(player.getUUID());
                return InteractionResult.CONSUME;
            }
            return InteractionResult.CONSUME;
        }

        Direction facing = state.getValue(FACING);
        Vec3 base = Vec3.atBottomCenterOf(pos).add(0, 0.45, 0);
        double forward = -0.2;
        Vec3 offset = switch (facing) {
            case NORTH -> new Vec3(0, 0, forward);
            case SOUTH -> new Vec3(0, 0, -forward);
            case WEST  -> new Vec3(forward, 0, 0);
            case EAST  -> new Vec3(-forward, 0, 0);
            default    -> Vec3.ZERO;
        };
        Vec3 seatPos = base.add(offset);

        SeatEntity seat = ModEntities.SEAT.get().create(level);
        if (seat == null) return InteractionResult.PASS;

        seat.moveTo(seatPos.x, seatPos.y, seatPos.z, facing.toYRot(), 0.0F);
        level.addFreshEntity(seat);
        seat.moveTo(seatPos.x, seatPos.y, seatPos.z, facing.toYRot(), 0.0F);

        level.sendBlockUpdated(pos, state, state, Block.UPDATE_NEIGHBORS | Block.UPDATE_CLIENTS);

        seat.scheduleMount(player.getUUID());
        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            // Clean up seat if bench is broken/replaced
            for (SeatEntity seat : level.getEntitiesOfClass(SeatEntity.class, new AABB(pos).inflate(0.5))) {
                seat.discard();
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
