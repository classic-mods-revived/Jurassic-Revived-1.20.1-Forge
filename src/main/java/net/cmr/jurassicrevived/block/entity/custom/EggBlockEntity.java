package net.cmr.jurassicrevived.block.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

// Simple BE to track placement time and provide in-world tooltip via client-side query
public class EggBlockEntity extends BlockEntity {
    private long placedAt = -1L;

    public EggBlockEntity(BlockPos pos, BlockState state) {
        // Replace with your registered BlockEntityType if you have one; otherwise keep a simple constructor
        super(ModBlockEntities.EGG_BE.get(), pos, state);
    }

    public void setPlacedAt(long gameTime) {
        this.placedAt = gameTime;
        setChanged();
    }

    public int getSecondsRemaining(Level level) {
        if (placedAt < 0) return 5;
        long elapsed = level.getGameTime() - placedAt;
        long remainingTicks = Math.max(0, (20L * 5) - elapsed);
        return (int)Math.ceil(remainingTicks / 20.0);
    }

    // Optional: call this from your client-side block selection tooltip hook if you use one (e.g., Jade/WTHIT).
    public Component getHatchTooltip(Level level, Player player) {
        int secs = getSecondsRemaining(level);
        return Component.translatable("tooltip.jurassicrevived.egg.hatches_in_seconds", secs);
    }
}
