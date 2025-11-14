package net.cmr.jurassicrevived.block.custom;

import net.cmr.jurassicrevived.block.entity.custom.CrateBlockEntity;
import net.cmr.jurassicrevived.block.entity.custom.DNAAnalyzerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
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
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class CrateBlock extends BaseEntityBlock {
    private final int slots;

    public CrateBlock(Properties properties, int slots) {
        super(properties);
        this.slots = slots;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrateBlockEntity(pos, state, this.slots);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type
    ) {
        return null; // no ticking logic
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState();
    }

    /**
     * When broken, drop a single item that carries the contents in its NBT,
     * *instead* of spilling the items in the world.
     */
    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            // Creative: break without drops
            if (player.getAbilities().instabuild) {
                level.removeBlockEntity(pos);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                return;
            }

            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof CrateBlockEntity crateBE) {
                ItemStack stack = new ItemStack(this.asItem());

                if (!crateBE.isEmptyForDrop()) {
                    CompoundTag tag = crateBE.saveWithoutMetadata();
                    // In 1.20.1 Forge, best practice is to use BlockItem.setBlockEntityData
                    // so the block item restores its BE data when placed.
                    BlockItem.setBlockEntityData(stack, crateBE.getType(), tag);
                }

                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
                level.removeBlockEntity(pos);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
                return;
            }
        }

        super.playerWillDestroy(level, pos, state, player);;
    }

    // Keep inventory-spill disabled; the item now carries contents.
    @Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos,
                         BlockState newState, boolean isMoving) {
        if (oldState.getBlock() != newState.getBlock()) {
            // Intentionally do nothing here to avoid duplicate/empty drops.
        }
        super.onRemove(oldState, level, pos, newState, isMoving);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof CrateBlockEntity crate) {
            return crate.redstoneSignal();
        }
        return 0;
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        super.triggerEvent(state, level, pos, id, param);
        BlockEntity be = level.getBlockEntity(pos);
        return be != null && be.triggerEvent(id, param);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof CrateBlockEntity crateBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), crateBlockEntity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
}
