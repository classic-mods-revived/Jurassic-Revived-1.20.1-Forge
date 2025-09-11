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
import net.jurassicrevived.jurassicrevived.util.FenceUpdateGuard;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LowSecurityFenceWireBlock extends Block {
    // Reentrancy guard to prevent infinite update loops caused by setBlock callbacks
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
                    // Keep full notifications for power changes
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
                    // Diagonal-only changes: client update only to prevent ping-pong
                    level.setBlock(pos, updated, Block.UPDATE_CLIENTS);
                }
                // Also push recomputation to our 4 diagonals (uses UPDATE_CLIENTS inside)
                updateDiagonalsAround(level, pos);
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
            // Diagonal-only change: client update only
            level.setBlock(pos, updated, Block.UPDATE_CLIENTS);
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

                if (p.equals(nePos)) {
                    backFlag = LowSecurityFencePoleBlock.SW;
                    backA = Direction.SOUTH;
                    backB = Direction.WEST;
                } else if (p.equals(sePos)) {
                    backFlag = LowSecurityFencePoleBlock.NW;
                    backA = Direction.NORTH;
                    backB = Direction.WEST;
                } else if (p.equals(swPos)) {
                    backFlag = LowSecurityFencePoleBlock.NE;
                    backA = Direction.NORTH;
                    backB = Direction.EAST;
                } else { // p == nwPos
                    backFlag = LowSecurityFencePoleBlock.SE;
                    backA = Direction.SOUTH;
                    backB = Direction.EAST;
                }

                boolean allow = LowSecurityFenceWireBlock.canConnectDiagonally(level, p, backA, backB);

                BlockState updated = bs.setValue(backFlag, allow);
                if (updated != bs) {
                    // Avoid neighbor notifications here
                    level.setBlock(p, updated, Block.UPDATE_CLIENTS);
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
                    // Avoid neighbor notifications here
                    level.setBlock(p, updated, Block.UPDATE_CLIENTS);
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

        // Treat solid blocks as potential diagonal endpoints if at least one face toward the corner is sturdy
        boolean diagIsSolidCorner =
                diagState.isFaceSturdy(level, diag, a.getOpposite())
                        || diagState.isFaceSturdy(level, diag, b.getOpposite());

        // Only form diagonal if the target is a wire, a pole, or a solid corner
        if (!diagIsWire && !diagIsPole && !diagIsSolidCorner) return false;

        // If either endpoint is a pole, allow the diagonal (pole↔wire, pole↔pole, pole↔solid-corner)
        if (srcIsPole || diagIsPole) {
            return true;
        }

        // From here we are wire↔(wire or solid-corner)
        // Intermediate cardinal positions along the L-path
        BlockPos stepA = pos.relative(a);
        BlockPos stepB = pos.relative(b);

        BlockState stateA = level.getBlockState(stepA);
        BlockState stateB = level.getBlockState(stepB);

        // Consider any sturdy-faced block in the path as blocking the diagonal turn
        boolean stepABlocks =
                stateA.getBlock() instanceof LowSecurityFenceWireBlock
                        || stateA.getBlock() instanceof LowSecurityFencePoleBlock
                        || stateA.isFaceSturdy(level, stepA, a.getOpposite());

        boolean stepBBlocks =
                stateB.getBlock() instanceof LowSecurityFenceWireBlock
                        || stateB.getBlock() instanceof LowSecurityFencePoleBlock
                        || stateB.isFaceSturdy(level, stepB, b.getOpposite());

        // Block diagonals that cut across an existing leg or sturdy solid at the turn
        if (stepABlocks || stepBBlocks) return false;

        // Note: Removed the "outside-corner" blocking entirely so diagonals are allowed
        // even if a pole or solid sits at the outside corner.

        return true;
    }
    // Only consider diagonals that end at another fence when suppressing wire↔wire cardinals
    private static boolean canConnectDiagonallyToFence(LevelAccessor level, BlockPos pos, Direction a, Direction b) {
        BlockPos diag = pos.relative(a).relative(b);
        BlockState diagState = level.getBlockState(diag);
        Block db = diagState.getBlock();
        if (!(db instanceof LowSecurityFenceWireBlock) && !(db instanceof LowSecurityFencePoleBlock)) {
            return false; // solids should NOT cause wire↔wire cardinal suppression
        }
        return canConnectDiagonally(level, pos, a, b);
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
    // - If neighbor is a wire, suppress the cardinal connection when a corner diagonal to a FENCE (wire or pole)
    //   is allowed from either this block or the neighbor (on either of the two corners adjacent to that side).
    // - Otherwise, connect to any solid block with a sturdy face toward us.
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

            // Only count fence-ending diagonals for suppression
            boolean thisDiagRight = canConnectDiagonallyToFence(level, pos, dir, right);
            boolean thisDiagLeft  = canConnectDiagonallyToFence(level, pos, dir, left);

            Direction back = dir.getOpposite();
            boolean neighDiagRight = canConnectDiagonallyToFence(level, neighborPos, back, back.getClockWise());
            boolean neighDiagLeft  = canConnectDiagonallyToFence(level, neighborPos, back, back.getCounterClockWise());

            if (thisDiagRight || thisDiagLeft || neighDiagRight || neighDiagLeft) {
                return false;
            }

            return true;
        }

        // Connect to solid blocks: a sturdy face toward us is enough
        if (neighbor.isFaceSturdy(level, neighborPos, dir.getOpposite())) {
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