package net.jurassicrevived.jurassicrevived.util;

import net.jurassicrevived.jurassicrevived.block.custom.FencePoleBlock;
import net.jurassicrevived.jurassicrevived.block.custom.FenceWireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;

public final class FenceClimbClientHandler {
    public static void register() {
        MinecraftForge.EVENT_BUS.addListener(FenceClimbClientHandler::onPlayerTickClient);
    }

    private static void onPlayerTickClient(TickEvent.PlayerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        var player = e.player;
        var level = player.level();
        if (!level.isClientSide || !player.isAlive()) return;

        var bb = player.getBoundingBox().inflate(0.05, 0.05, 0.05);
        int minX = Mth.floor(bb.minX), maxX = Mth.floor(bb.maxX);
        int minY = Mth.floor(bb.minY), maxY = Mth.floor(Math.min(bb.minY + 1.0, bb.maxY));
        int minZ = Mth.floor(bb.minZ), maxZ = Mth.floor(bb.maxZ);

        boolean touching = false;
        outer:
        for (int x=minX;x<=maxX;x++) for (int y=minY;y<=maxY;y++) for (int z=minZ;z<=maxZ;z++) {
            var pos = new BlockPos(x,y,z);
            var state = level.getBlockState(pos);

            boolean isWire = state.getBlock() instanceof FenceWireBlock;
            boolean isPole = state.getBlock() instanceof FencePoleBlock;
            if (!isWire && !isPole) continue;

            var shape = state.getCollisionShape(level, pos, CollisionContext.of(player));
            if (shape.isEmpty()) continue;

            if (isPole) {
                var postAabbWorld = new net.minecraft.world.phys.AABB(
                    pos.getX() + 6 / 16.0, pos.getY() + 0.0,       pos.getZ() + 6 / 16.0,
                    pos.getX() + 10 / 16.0, pos.getY() + 16 / 16.0, pos.getZ() + 10 / 16.0
                );
                for (var aabb : shape.toAabbs()) {
                    var moved = aabb.move(pos.getX(), pos.getY(), pos.getZ());
                    if (approximatelySame(moved, postAabbWorld)) continue;
                    if (bb.intersects(moved) && notStandingOnTop(bb, moved)) { touching = true; break outer; }
                }
            } else {
                for (var aabb : shape.toAabbs()) {
                    var moved = aabb.move(pos.getX(), pos.getY(), pos.getZ());
                    if (bb.intersects(moved) && notStandingOnTop(bb, moved)) { touching = true; break outer; }
                }
            }
        }
        if (!touching) return;

        var v = player.getDeltaMovement();
        boolean sneaking = player.isShiftKeyDown();

        double maxHoriz = 0.15;
        double vx = Mth.clamp(v.x, -maxHoriz, maxHoriz);
        double vz = Mth.clamp(v.z, -maxHoriz, maxHoriz);

        boolean forward = Minecraft.getInstance().options.keyUp.isDown();

        double vy = v.y;
        if (sneaking) {
            vy = 0.0;
        } else if (forward) {
            vy = 0.2;
        } else {
            vy = Math.max(vy, -0.15);
        }

        player.fallDistance = 0.0F;
        player.setDeltaMovement(vx, vy, vz);
        player.hasImpulse = true;
    }

    private static boolean approximatelySame(AABB a, AABB b) {
        double eps = 1e-6;
        return Math.abs(a.minX - b.minX) < eps &&
               Math.abs(a.minY - b.minY) < eps &&
               Math.abs(a.minZ - b.minZ) < eps &&
               Math.abs(a.maxX - b.maxX) < eps &&
               Math.abs(a.maxY - b.maxY) < eps &&
               Math.abs(a.maxZ - b.maxZ) < eps;
    }

    private static boolean notStandingOnTop(AABB player, AABB block) {
        double eps = 0.05;
        return !(player.minY >= block.maxY - eps && player.minY <= block.maxY + eps);
    }
}
