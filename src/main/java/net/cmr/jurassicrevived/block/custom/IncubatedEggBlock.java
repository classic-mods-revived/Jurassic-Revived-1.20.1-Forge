package net.cmr.jurassicrevived.block.custom;

import net.cmr.jurassicrevived.block.entity.custom.EggBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class IncubatedEggBlock extends Block implements EntityBlock {

    private final Supplier<? extends EntityType<? extends Mob>> toSpawn;
    private final int hatchSeconds = 60; // keep Egg as 5s, or make constructor param if desired

    public IncubatedEggBlock(Properties pProperties, Supplier<? extends EntityType<? extends Mob>> toSpawn) {
        super(pProperties);
        this.toSpawn = toSpawn;
    }

    // Define your custom voxel shape (values are in 1/16ths of a block)
    // Example: a centered egg 8px tall (0.5 block), from (4,0,4) to (12,8,12)
    private static final VoxelShape EGG_SHAPE = Block.box(6.5D, 0.0D, 6.5D, 9.5D, 4.0D, 9.5D);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return EGG_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return EGG_SHAPE;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof EggBlockEntity eggBE) {
                // Hard reset for a fresh countdown
                eggBE.resetForNewPlacement(level, hatchSeconds);
            }
            level.scheduleTick(pos, this, hatchSeconds * 20);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof EggBlockEntity eggBE)) return;
        if (eggBE.getSecondsRemaining(level) <= 0) {
            super.tick(state, level, pos, random);
        } else {
            // Not due (could be a stray old tick), reschedule correctly
            level.scheduleTick(pos, this, eggBE.getSecondsRemaining(level) * 20);
            return;
        }
        EntityType<? extends Mob> type = toSpawn.get();
        if (type != null) {
            Mob mob = type.create(level);
            if (mob != null) {
                Vec3 spawn = Vec3.atCenterOf(pos);
                mob.moveTo(spawn.x, spawn.y + 0.1, spawn.z, level.random.nextFloat() * 360F, 0.0F);

                // Run entity initialization (e.g., variants) then make it a baby if applicable
                mob.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.TRIGGERED, null, null);
                if (mob instanceof AgeableMob ageable) {
                    ageable.setBaby(true);
                }

                level.addFreshEntity(mob);
            }
        }
        // Play block break particles + sound for this block state
        level.levelEvent(2001, pos, Block.getId(state));

        level.removeBlock(pos, false);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.isClientSide && state.getBlock() != newState.getBlock()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof EggBlockEntity eggBE) {
                eggBE.invalidateTimer();
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EggBlockEntity(pos, state);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        // Item tooltip (when in inventory). No timing info available here; show static hint.
        tooltip.add(Component.translatable("tooltip.jurassicrevived.egg.hatches_in", hatchSeconds));
    }
}
