package net.cmr.jurassicrevived.sounds;

import net.cmr.jurassicrevived.JRMod;
import net.cmr.jurassicrevived.block.custom.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Client-only manager for looping machine hum sounds.
 */
@Mod.EventBusSubscriber(modid = JRMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class MachineHumSoundHandler {

    private static final Map<BlockPos, MachineHumLoopSound> ACTIVE_SOUNDS = new HashMap<>();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        ClientLevel level = mc.level;
        if (level == null) {
            // World not loaded; stop all sounds
            stopAllSounds();
            return;
        }

        // Clean up sounds whose blocks are gone or no longer lit
        Iterator<Map.Entry<BlockPos, MachineHumLoopSound>> it = ACTIVE_SOUNDS.entrySet().iterator();
        while (it.hasNext()) {
            var entry = it.next();
            BlockPos pos = entry.getKey();
            MachineHumLoopSound sound = entry.getValue();

            BlockState state = level.getBlockState(pos);
            if (shouldStopHum(state) || sound.isStopped()) {
                sound.stopPlaying();
                it.remove();
            }
        }

        // Start sounds where needed near the player
        if (mc.player != null) {
            int radius = 16; // scan radius around the player
            BlockPos center = mc.player.blockPosition();

            for (BlockPos pos : BlockPos.betweenClosed(
                    center.offset(-radius, -radius, -radius),
                    center.offset(radius, radius, radius))) {

                // betweenClosed returns a mutable BlockPos; make an immutable copy for the map key
                BlockPos immutablePos = pos.immutable();

                if (ACTIVE_SOUNDS.containsKey(immutablePos)) {
                    continue;
                }

                BlockState state = level.getBlockState(immutablePos);
                if (shouldStartHum(state)) {
                    MachineHumLoopSound sound = new MachineHumLoopSound(level, immutablePos);
                    ACTIVE_SOUNDS.put(immutablePos, sound);
                    mc.getSoundManager().play(sound);
                }
            }
        }
    }

    private static boolean shouldStartHum(BlockState state) {
        Block block = state.getBlock();
        // Include all machine blocks that should hum:
        if (!(block instanceof DNAAnalyzerBlock
                || block instanceof DNAExtractorBlock
                || block instanceof DNAHybridizerBlock
                || block instanceof EmbryoCalcificationMachineBlock
                || block instanceof EmbryonicMachineBlock
                || block instanceof FossilCleanerBlock
                || block instanceof FossilGrinderBlock
                || block instanceof GeneratorBlock
                || block instanceof IncubatorBlock)) {
            return false;
        }

        if (state.hasProperty(BlockStateProperties.LIT)) {
            return state.getValue(BlockStateProperties.LIT);
        }
        return false;
    }

    private static boolean shouldStopHum(BlockState state) {
        if (state.isAir()) return true;
        if (state.hasProperty(BlockStateProperties.LIT)) {
            return !state.getValue(BlockStateProperties.LIT);
        }
        return true;
    }

    private static void stopAllSounds() {
        for (MachineHumLoopSound sound : ACTIVE_SOUNDS.values()) {
            sound.stopPlaying();
        }
        ACTIVE_SOUNDS.clear();
    }
}