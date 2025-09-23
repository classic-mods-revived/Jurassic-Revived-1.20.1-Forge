package net.jurassicrevived.jurassicrevived.event;

import net.jurassicrevived.jurassicrevived.block.custom.FencePoleBlock;
import net.jurassicrevived.jurassicrevived.block.custom.FenceWireBlock;
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

            if (bs.getBlock() instanceof FenceWireBlock) {
                BlockState updated = bs
                        .setValue(FenceWireBlock.NE, FenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.EAST))
                        .setValue(FenceWireBlock.SE, FenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.EAST))
                        .setValue(FenceWireBlock.SW, FenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.WEST))
                        .setValue(FenceWireBlock.NW, FenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.WEST));
                if (updated != bs) {
                    // Client-only update to avoid neighbor notification ping-pong
                    level.setBlock(p, updated, Block.UPDATE_CLIENTS);
                }
            } else if (bs.getBlock() instanceof FencePoleBlock) {
                BlockState updated = bs
                        .setValue(FencePoleBlock.NE, FenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.EAST))
                        .setValue(FencePoleBlock.SE, FenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.EAST))
                        .setValue(FencePoleBlock.SW, FenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.WEST))
                        .setValue(FencePoleBlock.NW, FenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.WEST));
                if (updated != bs) {
                    // Client-only update to avoid neighbor notification ping-pong
                    level.setBlock(p, updated, Block.UPDATE_CLIENTS);
                }
            }
        }
    }
}