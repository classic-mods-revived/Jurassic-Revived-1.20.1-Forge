package net.cmr.jurassicrevived.block.entity.custom;

import net.cmr.jurassicrevived.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// Simple BE to track placement time and provide in-world tooltip via client-side query
public class EggBlockEntity extends BlockEntity {
    private long placedAt = -1L;
    private int totalSeconds = 5; // default

    public EggBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EGG_BE.get(), pos, state);
    }

    public void setPlacedAt(long gameTime) {
        this.placedAt = gameTime;
        setChanged();
    }

    // Make sure we can explicitly reset this BE when placed
    public void resetForNewPlacement(Level level, int totalSeconds) {
        this.placedAt = level.getGameTime();
        this.totalSeconds = Math.max(1, totalSeconds);
        setChanged();
    }

    public void setTotalSeconds(int secs) {
        this.totalSeconds = Math.max(1, secs);
        setChanged();
    }

    public int getTotalSeconds() {
        return totalSeconds;
    }

    public int getSecondsRemaining(Level level) {
        if (placedAt < 0) return totalSeconds;
        long elapsed = level.getGameTime() - placedAt;
        long remainingTicks = Math.max(0, (20L * totalSeconds) - elapsed);
        return (int) Math.ceil(remainingTicks / 20.0);
    }

    // Persist fields so values survive world reloads
    @Override
    public void saveAdditional(net.minecraft.nbt.CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putLong("egg.placedAt", placedAt);
        tag.putInt("egg.totalSeconds", totalSeconds);
    }

    @Override
    public void load(net.minecraft.nbt.CompoundTag tag) {
        super.load(tag);
        if (tag.contains("egg.placedAt")) {
            placedAt = tag.getLong("egg.placedAt");
        }
        if (tag.contains("egg.totalSeconds")) {
            totalSeconds = Math.max(1, tag.getInt("egg.totalSeconds"));
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level instanceof net.minecraft.server.level.ServerLevel server) {
            // After load, ensure the block gets its hatch tick at the correct time
            int secsRemaining = getSecondsRemaining(server);
            if (secsRemaining > 0) {
                server.scheduleTick(getBlockPos(), getBlockState().getBlock(), secsRemaining * 20);
            } else {
                // If already due, schedule a near-immediate tick
                server.scheduleTick(getBlockPos(), getBlockState().getBlock(), 1);
            }
        }
    }

    public void invalidateTimer() {
        this.placedAt = -1L;
        setChanged();
    }

    public Component getHatchTooltip(Level level, Player player) {
        int secs = getSecondsRemaining(level);
        return Component.translatable("tooltip.jurassicrevived.egg.hatches_in_seconds", secs);
    }
}
