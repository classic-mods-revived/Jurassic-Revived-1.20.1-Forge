package net.cmr.jurassicrevived.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class EggBlock extends Block {

    private final Supplier<? extends EntityType<? extends Mob>> toSpawn;

    public EggBlock(Properties pProperties, Supplier<? extends EntityType<? extends Mob>> toSpawn) {
        super(pProperties);
        this.toSpawn = toSpawn;
    }

    // Define your custom voxel shape (values are in 1/16ths of a block)
    // Example: a centered egg 8px tall (0.5 block), from (4,0,4) to (12,8,12)
    private static final VoxelShape EGG_SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);

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
            // 20 ticks ≈ 1 second
            level.scheduleTick(pos, this, 20);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
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
}
