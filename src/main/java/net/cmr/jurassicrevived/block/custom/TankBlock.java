package net.cmr.jurassicrevived.block.custom;

import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.cmr.jurassicrevived.block.entity.custom.TankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class TankBlock extends BaseEntityBlock {
    public TankBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            if (player.getAbilities().instabuild) {
                level.removeBlockEntity(pos);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                return;
            }

            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof TankBlockEntity fbe) {
                ItemStack stack = new ItemStack(this.asItem());

                if (!fbe.isEmptyForDrop()) {
                    CompoundTag tag = new CompoundTag();
                    fbe.saveAdditional(tag); // write your BE data
                    // Put into BlockEntityTag so BlockItem places it back
                    CompoundTag blockEntityTag = new CompoundTag();
                    blockEntityTag.merge(tag);
                    stack.getOrCreateTag().put("BlockEntityTag", blockEntityTag);
                }

                popResource(level, pos, stack);
                level.removeBlockEntity(pos);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                return;
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            // Do not spill inventory; item carries contents
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof TankBlockEntity tankBlockEntity) {
                // Use the BE itself as MenuProvider; Forge will send the BlockPos to client
                pPlayer.openMenu(tankBlockEntity);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TankBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.TANK_BE.get(),
                (level, blockPos, blockState, tankBlockEntity) -> tankBlockEntity.tick(level, blockPos, blockState));
    }
}