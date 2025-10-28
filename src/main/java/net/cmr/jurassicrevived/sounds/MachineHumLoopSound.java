package net.cmr.jurassicrevived.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

// Java
public class MachineHumLoopSound extends AbstractTickableSoundInstance {
    private final Level level;
    private final BlockPos pos;

    public MachineHumLoopSound(Level level, BlockPos pos) {
        super(ModSounds.MACHINE_HUM_SOUND.get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
        this.level = level;
        this.pos = pos.immutable();
        this.x = pos.getX() + 0.5;
        this.y = pos.getY() + 0.5;
        this.z = pos.getZ() + 0.5;
        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.looping = true;      // loop
        this.delay = 0;           // no gap
        this.relative = false;    // world-positioned
    }

    @Override
    public void tick() {
        if (this.isStopped()) return;
        var state = level.getBlockState(pos);
        // Stop if block is gone or not lit
        if (state.isAir() || !(state.hasProperty(BlockStateProperties.LIT) && state.getValue(BlockStateProperties.LIT))) {
            this.stop();
        }
    }

    // Public wrapper so callers can stop the sound
    public void stopPlaying() {
        this.stop();
    }
}
