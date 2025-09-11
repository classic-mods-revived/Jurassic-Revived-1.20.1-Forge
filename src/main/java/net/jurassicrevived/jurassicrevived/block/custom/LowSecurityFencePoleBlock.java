package net.jurassicrevived.jurassicrevived.block.custom;

import net.jurassicrevived.jurassicrevived.util.FenceUpdateGuard;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LowSecurityFencePoleBlock extends Block {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST  = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST  = BooleanProperty.create("west");
    // Diagonals
    public static final BooleanProperty NE = BooleanProperty.create("ne");
    public static final BooleanProperty SE = BooleanProperty.create("se");
    public static final BooleanProperty SW = BooleanProperty.create("sw");
    public static final BooleanProperty NW = BooleanProperty.create("nw");

    public LowSecurityFencePoleBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST,  false)
                .setValue(SOUTH, false)
                .setValue(WEST,  false)
                .setValue(NE, false)
                .setValue(SE, false)
                .setValue(SW, false)
                .setValue(NW, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, NE, SE, SW, NW);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        return this.defaultBlockState()
                .setValue(NORTH, connectsTo(level, pos, Direction.NORTH))
                .setValue(EAST,  connectsTo(level, pos, Direction.EAST))
                .setValue(SOUTH, connectsTo(level, pos, Direction.SOUTH))
                .setValue(WEST,  connectsTo(level, pos, Direction.WEST))
                .setValue(NE,    LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.NORTH, Direction.EAST))
                .setValue(SE,    LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.SOUTH, Direction.EAST))
                .setValue(SW,    LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.SOUTH, Direction.WEST))
                .setValue(NW,    LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.NORTH, Direction.WEST));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (dir.getAxis().isHorizontal()) {
            boolean connect = connectsTo(level, pos, dir);
            state = state.setValue(propertyFor(dir), connect);
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        BlockState updated = state
                .setValue(NE, LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.NORTH, Direction.EAST))
                .setValue(SE, LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.SOUTH, Direction.EAST))
                .setValue(SW, LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.SOUTH, Direction.WEST))
                .setValue(NW, LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.NORTH, Direction.WEST));
        if (updated != state) {
            // Diagonal-only change: client update only to prevent ping-pong
            level.setBlock(pos, updated, Block.UPDATE_CLIENTS);
        }

        // Propagate diagonal recomputation outward (avoid notifying neighbors)
        if (beginGuard()) {
            try {
                updateDiagonalsAround(level, pos);
            } finally {
                endGuard();
            }
        }

        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    // Reentrancy guard like the wire’s (separate guard per class to keep concerns local)
    // private static final ThreadLocal<Boolean> DIAGONAL_UPDATE_GUARD = ThreadLocal.withInitial(() -> false);

    private static boolean beginGuard() {
        // if (Boolean.TRUE.equals(DIAGONAL_UPDATE_GUARD.get())) return false;
        // DIAGONAL_UPDATE_GUARD.set(true);
        // return true;
        return FenceUpdateGuard.begin();
    }

    private static void endGuard() {
        // DIAGONAL_UPDATE_GUARD.set(false);
        FenceUpdateGuard.end();
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!beginGuard()) return;
        try {
            recomputeSelfDiagonals(level, pos, state);
            updateDiagonalsAround(level, pos);
        } finally {
            endGuard();
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            if (beginGuard()) {
                try {
                    updateDiagonalsAround(level, pos);
                } finally {
                    endGuard();
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    private void recomputeSelfDiagonals(Level level, BlockPos pos, BlockState state) {
        BlockState updated = state
                .setValue(NE, LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.NORTH, Direction.EAST))
                .setValue(SE, LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.SOUTH, Direction.EAST))
                .setValue(SW, LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.SOUTH, Direction.WEST))
                .setValue(NW, LowSecurityFenceWireBlock.canConnectDiagonally(level, pos, Direction.NORTH, Direction.WEST));
        if (updated != state) {
            // Diagonal-only change: client update only
            level.setBlock(pos, updated, Block.UPDATE_CLIENTS);
        }
    }

    private void updateDiagonalsAround(Level level, BlockPos pos) {
        BlockPos[] diags = new BlockPos[] {
                pos.north().east(), pos.south().east(), pos.south().west(), pos.north().west()
        };
        for (BlockPos p : diags) {
            BlockState bs = level.getBlockState(p);
            Block b = bs.getBlock();
            if (b instanceof LowSecurityFencePoleBlock) {
                // Recompute the neighbor pole's diagonal flags using the same diagonal rule as wire
                boolean ne = LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.EAST);
                boolean se = LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.EAST);
                boolean sw = LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.WEST);
                boolean nw = LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.WEST);
                BlockState updated = bs
                        .setValue(LowSecurityFencePoleBlock.NE, ne)
                        .setValue(LowSecurityFencePoleBlock.SE, se)
                        .setValue(LowSecurityFencePoleBlock.SW, sw)
                        .setValue(LowSecurityFencePoleBlock.NW, nw);
                if (updated != bs) {
                    // Avoid neighbor notifications here
                    level.setBlock(p, updated, Block.UPDATE_CLIENTS);
                }
            } else if (b instanceof LowSecurityFenceWireBlock) {
                boolean ne = LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.EAST);
                boolean se = LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.EAST);
                boolean sw = LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.SOUTH, Direction.WEST);
                boolean nw = LowSecurityFenceWireBlock.canConnectDiagonally(level, p, Direction.NORTH, Direction.WEST);
                BlockState updated = bs
                        .setValue(LowSecurityFenceWireBlock.NE, ne)
                        .setValue(LowSecurityFenceWireBlock.SE, se)
                        .setValue(LowSecurityFenceWireBlock.SW, sw)
                        .setValue(LowSecurityFenceWireBlock.NW, nw);
                if (updated != bs) {
                    // Avoid neighbor notifications here
                    level.setBlock(p, updated, Block.UPDATE_CLIENTS);
                }
            }
        }
    }

    // Connect to wires, poles, or any solid block with a sturdy face toward this pole.
    private boolean connectsTo(LevelAccessor level, BlockPos pos, Direction dir) {
        BlockPos neighborPos = pos.relative(dir);
        BlockState neighbor = level.getBlockState(neighborPos);
        Block nb = neighbor.getBlock();

        if (nb instanceof LowSecurityFenceWireBlock || nb instanceof LowSecurityFencePoleBlock) {
            return true;
        }

        // Treat solid blocks as connectable if their face toward us is sturdy
        return neighbor.isFaceSturdy(level, neighborPos, dir.getOpposite());
    }

    private static BooleanProperty propertyFor(Direction dir) {
        return switch (dir) {
            case NORTH -> NORTH;
            case EAST  -> EAST;
            case SOUTH -> SOUTH;
            case WEST  -> WEST;
            default -> throw new IllegalArgumentException("Only horizontal");
        };
    }

    // --------- Light/shadow behavior ----------
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0; // don't darken ground beneath
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F; // avoid shadow tinting
    }

    // --------- Shapes (single, bridged per component) ----------
    // Center (bridges the two nubs)
    private static final VoxelShape POST = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

    // Arms: one tall slab per side, bridging low/high rails
    private static final VoxelShape ARM_NORTH = Block.box(7.5, 3.5, 0.0, 8.5, 12.5, 8.0);
    private static final VoxelShape ARM_SOUTH = Block.box(7.5, 3.5, 8.0, 8.5, 12.5, 16.0);
    private static final VoxelShape ARM_WEST  = Block.box(0.0, 3.5, 7.5, 8.0, 12.5, 8.5);
    private static final VoxelShape ARM_EAST  = Block.box(8.0, 3.5, 7.5, 16.0, 12.5, 8.5);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        VoxelShape shape = POST;

        if (state.getValue(NORTH)) shape = Shapes.or(shape, ARM_NORTH);
        if (state.getValue(SOUTH)) shape = Shapes.or(shape, ARM_SOUTH);
        if (state.getValue(WEST))  shape = Shapes.or(shape, ARM_WEST);
        if (state.getValue(EAST))  shape = Shapes.or(shape, ARM_EAST);

        return shape;
    }
}