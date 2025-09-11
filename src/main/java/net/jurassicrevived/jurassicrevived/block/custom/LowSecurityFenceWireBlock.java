package net.jurassicrevived.jurassicrevived.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LowSecurityFenceWireBlock extends Block {
    // Reentrancy guard to prevent infinite update loops caused by setBlock callbacks
    private static final ThreadLocal<Boolean> DIAGONAL_UPDATE_GUARD = ThreadLocal.withInitial(() -> false);

    private static boolean beginGuard() {
        if (Boolean.TRUE.equals(DIAGONAL_UPDATE_GUARD.get())) return false;
        DIAGONAL_UPDATE_GUARD.set(true);
        return true;
    }

    private static void endGuard() {
        DIAGONAL_UPDATE_GUARD.set(false);
    }

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST  = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST  = BlockStateProperties.WEST;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    // Diagonals
    public static final BooleanProperty NE = BooleanProperty.create("ne");
    public static final BooleanProperty SE = BooleanProperty.create("se");
    public static final BooleanProperty SW = BooleanProperty.create("sw");
    public static final BooleanProperty NW = BooleanProperty.create("nw");

    public LowSecurityFenceWireBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST,  false)
                .setValue(SOUTH, false)
                .setValue(WEST,  false)
                .setValue(POWERED, false)
                .setValue(NE, false)
                .setValue(SE, false)
                .setValue(SW, false)
                .setValue(NW, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, POWERED, NE, SE, SW, NW);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        boolean powered = level.hasNeighborSignal(pos);
        return this.defaultBlockState()
                .setValue(NORTH, connectsCardinalTo(level, pos, Direction.NORTH))
                .setValue(EAST,  connectsCardinalTo(level, pos, Direction.EAST))
                .setValue(SOUTH, connectsCardinalTo(level, pos, Direction.SOUTH))
                .setValue(WEST,  connectsCardinalTo(level, pos, Direction.WEST))
                .setValue(NE,    canConnectDiagonally(level, pos, Direction.NORTH, Direction.EAST))
                .setValue(SE,    canConnectDiagonally(level, pos, Direction.SOUTH, Direction.EAST))
                .setValue(SW,    canConnectDiagonally(level, pos, Direction.SOUTH, Direction.WEST))
                .setValue(NW,    canConnectDiagonally(level, pos, Direction.NORTH, Direction.WEST))
                .setValue(POWERED, powered);
    }

//    @Override
//    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
//        level.scheduleTick(pos, this, 2);
//    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (dir.getAxis().isHorizontal()) {
            boolean connect = connectsCardinalTo(level, pos, dir);
            state = state.setValue(propertyFor(dir), connect);
        }
        state = state
                .setValue(NE, canConnectDiagonally(level, pos, Direction.NORTH, Direction.EAST))
                .setValue(SE, canConnectDiagonally(level, pos, Direction.SOUTH, Direction.EAST))
                .setValue(SW, canConnectDiagonally(level, pos, Direction.SOUTH, Direction.WEST))
                .setValue(NW, canConnectDiagonally(level, pos, Direction.NORTH, Direction.WEST));
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        boolean poweredNow = level.hasNeighborSignal(pos);
        if (poweredNow != state.getValue(POWERED)) {
            if (beginGuard()) {
                try {
                    level.setBlock(pos, state.setValue(POWERED, poweredNow), Block.UPDATE_ALL);
                } finally {
                    endGuard();
                }
            } else {
                level.setBlock(pos, state.setValue(POWERED, poweredNow), Block.UPDATE_ALL);
            }
        }

        if (beginGuard()) {
            try {
                BlockState updated = state
                        .setValue(NE, canConnectDiagonally(level, pos, Direction.NORTH, Direction.EAST))
                        .setValue(SE, canConnectDiagonally(level, pos, Direction.SOUTH, Direction.EAST))
                        .setValue(SW, canConnectDiagonally(level, pos, Direction.SOUTH, Direction.WEST))
                        .setValue(NW, canConnectDiagonally(level, pos, Direction.NORTH, Direction.WEST));
                if (updated != state) {
                    level.setBlock(pos, updated, Block.UPDATE_ALL);
                }
            } finally {
                endGuard();
            }
        }
    }

//    @Override
//    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
//        recomputeSelfDiagonals(level, pos, state);
//        updateDiagonalsAround(level, pos);
//    }

    private void recomputeSelfDiagonals(Level level, BlockPos pos, BlockState state) {
        BlockState updated = state
                .setValue(NE, canConnectDiagonally(level, pos, Direction.NORTH, Direction.EAST))
                .setValue(SE, canConnectDiagonally(level, pos, Direction.SOUTH, Direction.EAST))
                .setValue(SW, canConnectDiagonally(level, pos, Direction.SOUTH, Direction.WEST))
                .setValue(NW, canConnectDiagonally(level, pos, Direction.NORTH, Direction.WEST));
        if (updated != state) {
            level.setBlock(pos, updated, Block.UPDATE_ALL);
        }
    }

    private void updateDiagonalsAround(Level level, BlockPos pos) {
        // Precompute the 4 diagonal positions relative to 'pos'
        BlockPos nePos = pos.north().east();
        BlockPos sePos = pos.south().east();
        BlockPos swPos = pos.south().west();
        BlockPos nwPos = pos.north().west();

        BlockPos[] diags = new BlockPos[] { nePos, sePos, swPos, nwPos };

        for (BlockPos p : diags) {
            BlockState bs = level.getBlockState(p);
            Block b = bs.getBlock();

            if (b instanceof LowSecurityFencePoleBlock) {
                // Determine which diagonal from 'p' points back to 'pos' and set just that flag.
                // Mapping:
                // - If p == pos.north().east(), pole at p should set SW toward 'pos'
                // - If p == pos.south().east(), pole at p should set NW toward 'pos'
                // - If p == pos.south().west(), pole at p should set NE toward 'pos'
                // - If p == pos.north().west(), pole at p should set SE toward 'pos'
                BooleanProperty backFlag;
                Direction backA;
                Direction backB;

                if (p.equals(nePos)) { backFlag = LowSecurityFencePoleBlock.SW; backA = Direction.SOUTH; backB = Direction.WEST; }
                else if (p.equals(sePos)) { backFlag = LowSecurityFencePoleBlock.NW; backA = Direction.NORTH; backB = Direction.WEST; }
                else if (p.equals(swPos)) { backFlag = LowSecurityFencePoleBlock.NE; backA = Direction.NORTH; backB = Direction.EAST; }
                else { /* p == nwPos */   backFlag = LowSecurityFencePoleBlock.SE; backA = Direction.SOUTH; backB = Direction.EAST; }

                boolean allow = LowSecurityFenceWireBlock.canConnectDiagonally(level, p, backA, backB);

                BlockState updated = bs.setValue(backFlag, allow);
                if (updated != bs) {
                    level.setBlock(p, updated, Block.UPDATE_ALL);
                }

            } else if (b instanceof LowSecurityFenceWireBlock) {
                boolean ne = canConnectDiagonally(level, p, Direction.NORTH, Direction.EAST);
                boolean se = canConnectDiagonally(level, p, Direction.SOUTH, Direction.EAST);
                boolean sw = canConnectDiagonally(level, p, Direction.SOUTH, Direction.WEST);
                boolean nw = canConnectDiagonally(level, p, Direction.NORTH, Direction.WEST);
                BlockState updated = bs
                        .setValue(LowSecurityFenceWireBlock.NE, ne)
                        .setValue(LowSecurityFenceWireBlock.SE, se)
                        .setValue(LowSecurityFenceWireBlock.SW, sw)
                        .setValue(LowSecurityFenceWireBlock.NW, nw);
                if (updated != bs) {
                    level.setBlock(p, updated, Block.UPDATE_ALL);
                }
            }
        }
    }

    // Public static helper so other blocks (like the pole) can use the same diagonal rule
    public static boolean canConnectDiagonally(LevelAccessor level, BlockPos pos, Direction a, Direction b) {
        // Source and target
        BlockState srcState = level.getBlockState(pos);
        Block srcBlock = srcState.getBlock();

        BlockPos diag = pos.relative(a).relative(b);
        BlockState diagState = level.getBlockState(diag);
        Block diagBlock = diagState.getBlock();

        boolean srcIsPole  = srcBlock instanceof LowSecurityFencePoleBlock;
        boolean diagIsWire = diagBlock instanceof LowSecurityFenceWireBlock;
        boolean diagIsPole = diagBlock instanceof LowSecurityFencePoleBlock;

        // Only form diagonal if the target is either a wire or a pole
        if (!diagIsWire && !diagIsPole) return false;

        // If either endpoint is a pole, allow the diagonal (pole↔wire or pole↔pole)
        if (srcIsPole || diagIsPole) {
            return true;
        }

        // From here we are wire↔wire only
        // Intermediate cardinal positions along the L-path
        BlockPos stepA = pos.relative(a);
        BlockPos stepB = pos.relative(b);

        BlockState stateA = level.getBlockState(stepA);
        BlockState stateB = level.getBlockState(stepB);

        // 1) Block diagonals that cut across an existing leg:
        if (stateA.getBlock() instanceof LowSecurityFenceWireBlock) return false;
        if (stateB.getBlock() instanceof LowSecurityFenceWireBlock) return false;

        // 2) Block when the corner leg touches a pole
        if (stateA.getBlock() instanceof LowSecurityFencePoleBlock) return false;
        if (stateB.getBlock() instanceof LowSecurityFencePoleBlock) return false;

        // 3) Outside-corner guard: forbid if a pole is adjacent just outside the L-corner
        BlockPos outsideA = stepA.relative(b.getOpposite());
        BlockPos outsideB = stepB.relative(a.getOpposite());
        if (level.getBlockState(outsideA).getBlock() instanceof LowSecurityFencePoleBlock) return false;
        if (level.getBlockState(outsideB).getBlock() instanceof LowSecurityFencePoleBlock) return false;

        return true;
    }

    private boolean connectsTo(BlockState neighbor) {
        Block b = neighbor.getBlock();
        return (b instanceof LowSecurityFenceWireBlock) || (b instanceof LowSecurityFencePoleBlock);
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

    // Cardinal rule:
    // - Connect to a pole in the cardinal direction.
    // - If neighbor is a wire, suppress the cardinal connection when a corner diagonal to a pole is allowed
    //   from either this block or the neighbor (on either of the two corners adjacent to that side).
    private boolean connectsCardinalTo(LevelAccessor level, BlockPos pos, Direction dir) {
        BlockPos neighborPos = pos.relative(dir);
        BlockState neighbor = level.getBlockState(neighborPos);
        Block nb = neighbor.getBlock();

        if (nb instanceof LowSecurityFencePoleBlock) {
            return true;
        }

        if (nb instanceof LowSecurityFenceWireBlock) {
            Direction right = dir.getClockWise();
            Direction left  = dir.getCounterClockWise();

            // Corners adjacent to this edge (from 'pos')
            boolean thisDiagRight = canConnectDiagonally(level, pos, dir, right);
            boolean thisDiagLeft  = canConnectDiagonally(level, pos, dir, left);

            // Equivalent corners from neighbor back toward us
            // If we are at 'pos' and neighbor is at 'pos + dir',
            // then neighbor’s "back toward us" is dir.getOpposite()
            Direction back = dir.getOpposite();
            boolean neighDiagRight = canConnectDiagonally(level, neighborPos, back, back.getClockWise());
            boolean neighDiagLeft  = canConnectDiagonally(level, neighborPos, back, back.getCounterClockWise());

            // If any corner diagonal is valid (either side), prefer the diagonal and suppress wire-wire cardinal
            if (thisDiagRight || thisDiagLeft || neighDiagRight || neighDiagLeft) {
                return false;
            }

            return true;
        }

        return false;
    }

    // --------- Light/shadow behavior ----------
    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter level, BlockPos pos) {
        return 1.0F;
    }

    // --------- Shapes ----------
    private static final VoxelShape CENTER = Block.box(7.5, 3.5, 7.5, 8.5, 12.5, 8.5);

    // Arms: one tall slab per side (bridging low/high)
    private static final VoxelShape ARM_NORTH = Block.box(7.5, 3.5, 0.0, 8.5, 12.5, 8.0);
    private static final VoxelShape ARM_SOUTH = Block.box(7.5, 3.5, 8.0, 8.5, 12.5, 16.0);
    private static final VoxelShape ARM_WEST  = Block.box(0.0, 3.5, 7.5, 8.0, 12.5, 8.5);
    private static final VoxelShape ARM_EAST  = Block.box(8.0, 3.5, 7.5, 16.0, 12.5, 8.5);

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        VoxelShape shape = CENTER;

        if (state.getValue(NORTH)) shape = Shapes.or(shape, ARM_NORTH);
        if (state.getValue(SOUTH)) shape = Shapes.or(shape, ARM_SOUTH);
        if (state.getValue(WEST))  shape = Shapes.or(shape, ARM_WEST);
        if (state.getValue(EAST))  shape = Shapes.or(shape, ARM_EAST);

        return shape;
    }

    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!beginGuard()) return;
        try {
            // Recompute our diagonals and push the “back” diagonal flag onto diagonal poles/wires
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
                    // Tell diagonal neighbors to drop their back-diagonal link to this wire
                    updateDiagonalsAround(level, pos);
                } finally {
                    endGuard();
                }
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return getShape(state, level, pos, ctx);
    }
}