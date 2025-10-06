package net.cmr.jurassicrevived.util;

import net.cmr.jurassicrevived.block.custom.PipeBlock;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public final class PipeBlockPropertyUtil {
    private PipeBlockPropertyUtil() {}

    public static EnumProperty<PipeBlock.ConnectionType> getProp(Direction dir) {
        return switch (dir) {
            case DOWN -> PipeBlock.DOWN;
            case UP -> PipeBlock.UP;
            case NORTH -> PipeBlock.NORTH;
            case SOUTH -> PipeBlock.SOUTH;
            case WEST -> PipeBlock.WEST;
            case EAST -> PipeBlock.EAST;
        };
    }
}
