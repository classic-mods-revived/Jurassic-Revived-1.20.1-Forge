package net.jurassicrevived.jurassicrevived.event;

import net.jurassicrevived.jurassicrevived.block.custom.LowSecurityFencePoleBlock;
import net.jurassicrevived.jurassicrevived.block.custom.LowSecurityFenceWireBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;

public class FenceDiagonalUpdateHandler {

    // Register these static methods with NeoForge.EVENT_BUS.addListener in your mod constructor.
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent e) {
        if (!(e.getLevel() instanceof Level level)) return;
        notifyDiagonalFences(level, e.getPos());
    }

    public static void onEntityPlace(BlockEvent.EntityPlaceEvent e) {
        if (!(e.getLevel() instanceof Level level)) return;
        notifyDiagonalFences(level, e.getPos());
    }

    public static void onBreak(BlockEvent.BreakEvent e) {
        if (!(e.getLevel() instanceof Level level)) return;
        notifyDiagonalFences(level, e.getPos());
    }

    private static void notifyDiagonalFences(Level level, BlockPos changedPos) {
        BlockPos[] diagonals = new BlockPos[] {
                changedPos.north().east(),
                changedPos.south().east(),
                changedPos.south().west(),
                changedPos.north().west()
        };

        for (BlockPos p : diagonals) {
            BlockState bs = level.getBlockState(p);

            if (bs.getBlock() instanceof LowSecurityFenceWireBlock) {
                BlockState updated = bs
                        .setValue(LowSecurityFenceWireBlock.NE, LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.EAST))
                        .setValue(LowSecurityFenceWireBlock.SE, LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.EAST))
                        .setValue(LowSecurityFenceWireBlock.SW, LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.WEST))
                        .setValue(LowSecurityFenceWireBlock.NW, LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.WEST));
                if (updated != bs) {
                    // Client-only update to avoid neighbor notification ping-pong
                    level.setBlock(p, updated, Block.UPDATE_CLIENTS);
                }
            } else if (bs.getBlock() instanceof LowSecurityFencePoleBlock) {
                BlockState updated = bs
                        .setValue(LowSecurityFencePoleBlock.NE, LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.EAST))
                        .setValue(LowSecurityFencePoleBlock.SE, LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.EAST))
                        .setValue(LowSecurityFencePoleBlock.SW, LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.WEST))
                        .setValue(LowSecurityFencePoleBlock.NW, LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.WEST));
                if (updated != bs) {
                    // Client-only update to avoid neighbor notification ping-pong
                    level.setBlock(p, updated, Block.UPDATE_CLIENTS);
                }
            }
        }
    }
}