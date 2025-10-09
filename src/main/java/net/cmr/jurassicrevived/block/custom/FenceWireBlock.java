package net.cmr.jurassicrevived.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.cmr.jurassicrevived.util.FenceUpdateGuard;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;

public class FenceWireBlock extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static boolean beginGuard() {
        return FenceUpdateGuard.begin();
    }

    private static void endGuard() {
        FenceUpdateGuard.end();
    }

    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty NE = BooleanProperty.create("ne");
    public static final BooleanProperty SE = BooleanProperty.create("se");
    public static final BooleanProperty SW = BooleanProperty.create("sw");
    public static final BooleanProperty NW = BooleanProperty.create("nw");
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 0, 2);

    public enum Tier {
        LOW(0), MEDIUM(1), HIGH(2);
        public final int id;

        Tier(int id) {
            this.id = id;
        }
    }

    private final Tier tierConfig;

    public FenceWireBlock(BlockBehaviour.Properties properties, Tier tier) {
        super(properties);
        this.tierConfig = tier;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(POWERED, false)
                .setValue(NE, false)
                .setValue(SE, false)
                .setValue(SW, false)
                .setValue(NW, false)
                .setValue(TIER, tier.id)
                .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, POWERED, NE, SE, SW, NW, TIER, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        boolean powered = level.hasNeighborSignal(pos);
        boolean waterlogged = level.getFluidState(pos).getType() == Fluids.WATER;

        return this.defaultBlockState()
                .setValue(NORTH, connectsCardinalTo(level, pos, Direction.NORTH))
                .setValue(EAST, connectsCardinalTo(level, pos, Direction.EAST))
                .setValue(SOUTH, connectsCardinalTo(level, pos, Direction.SOUTH))
                .setValue(WEST, connectsCardinalTo(level, pos, Direction.WEST))
                .setValue(NE, canConnectDiagonally(level, pos, Direction.NORTH, Direction.EAST))
                .setValue(SE, canConnectDiagonally(level, pos, Direction.SOUTH, Direction.EAST))
                .setValue(SW, canConnectDiagonally(level, pos, Direction.SOUTH, Direction.WEST))
                .setValue(NW, canConnectDiagonally(level, pos, Direction.NORTH, Direction.WEST))
                .setValue(POWERED, powered)
                .setValue(TIER, tierConfig.id)
                .setValue(WATERLOGGED, waterlogged);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
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
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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
                    level.setBlock(pos, updated, Block.UPDATE_CLIENTS);
                }
                updateDiagonalsAround(level, pos);
            } finally {
                endGuard();
            }
        }
    }

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
        BlockPos nePos = pos.north().east();
        BlockPos sePos = pos.south().east();
        BlockPos swPos = pos.south().west();
        BlockPos nwPos = pos.north().west();

        BlockPos[] diags = new BlockPos[]{nePos, sePos, swPos, nwPos};

        for (BlockPos p : diags) {
            BlockState bs = level.getBlockState(p);
            Block b = bs.getBlock();

            if (b instanceof FencePoleBlock) {
                BooleanProperty backFlag;
                Direction backA;
                Direction backB;

                if (p.equals(nePos)) {
                    backFlag = FencePoleBlock.SW;
                    backA = Direction.SOUTH;
                    backB = Direction.WEST;
                } else if (p.equals(sePos)) {
                    backFlag = FencePoleBlock.NW;
                    backA = Direction.NORTH;
                    backB = Direction.WEST;
                } else if (p.equals(swPos)) {
                    backFlag = FencePoleBlock.NE;
                    backA = Direction.NORTH;
                    backB = Direction.EAST;
                } else {
                    backFlag = FencePoleBlock.SE;
                    backA = Direction.SOUTH;
                    backB = Direction.EAST;
                }

                boolean allow = FenceWireBlock.canConnectDiagonally(level, p, backA, backB);

                BlockState updated = bs.setValue(backFlag, allow);
                if (updated != bs) {
                    level.setBlock(p, updated, Block.UPDATE_CLIENTS);
                }

            } else if (b instanceof FenceWireBlock) {
                boolean ne = canConnectDiagonally(level, p, Direction.NORTH, Direction.EAST);
                boolean se = canConnectDiagonally(level, p, Direction.SOUTH, Direction.EAST);
                boolean sw = canConnectDiagonally(level, p, Direction.SOUTH, Direction.WEST);
                boolean nw = canConnectDiagonally(level, p, Direction.NORTH, Direction.WEST);
                BlockState updated = bs
                        .setValue(FenceWireBlock.NE, ne)
                        .setValue(FenceWireBlock.SE, se)
                        .setValue(FenceWireBlock.SW, sw)
                        .setValue(FenceWireBlock.NW, nw);
                if (updated != bs) {
                    level.setBlock(p, updated, Block.UPDATE_CLIENTS);
                }
            }
        }
    }

    public static boolean canConnectDiagonally(LevelAccessor level, BlockPos pos, Direction a, Direction b) {
        BlockState srcState = level.getBlockState(pos);
        Block srcBlock = srcState.getBlock();

        BlockPos diag = pos.relative(a).relative(b);
        BlockState diagState = level.getBlockState(diag);
        Block diagBlock = diagState.getBlock();

        boolean srcIsPole = srcBlock instanceof FencePoleBlock;
        boolean diagIsWire = diagBlock instanceof FenceWireBlock;
        boolean diagIsPole = diagBlock instanceof FencePoleBlock;

        boolean diagIsSolidCorner =
                diagState.isFaceSturdy(level, diag, a.getOpposite())
                        || diagState.isFaceSturdy(level, diag, b.getOpposite());

        if (!diagIsWire && !diagIsPole && !diagIsSolidCorner) return false;

        if (srcIsPole || diagIsPole) {
            return true;
        }

        BlockPos stepA = pos.relative(a);
        BlockPos stepB = pos.relative(b);

        BlockState stateA = level.getBlockState(stepA);
        BlockState stateB = level.getBlockState(stepB);

        boolean stepABlocks =
                stateA.getBlock() instanceof FenceWireBlock
                        || stateA.getBlock() instanceof FencePoleBlock
                        || stateA.isFaceSturdy(level, stepA, a.getOpposite());

        boolean stepBBlocks =
                stateB.getBlock() instanceof FenceWireBlock
                        || stateB.getBlock() instanceof FencePoleBlock
                        || stateB.isFaceSturdy(level, stepB, b.getOpposite());

        if (stepABlocks || stepBBlocks) return false;

        return true;
    }

    private static boolean canConnectDiagonallyToFence(LevelAccessor level, BlockPos pos, Direction a, Direction b) {
        BlockPos diag = pos.relative(a).relative(b);
        BlockState diagState = level.getBlockState(diag);
        Block db = diagState.getBlock();
        if (!(db instanceof FenceWireBlock) && !(db instanceof FencePoleBlock)) {
            return false;
        }
        return canConnectDiagonally(level, pos, a, b);
    }

    private boolean connectsTo(BlockState neighbor) {
        Block b = neighbor.getBlock();
        return (b instanceof FenceWireBlock) || (b instanceof FencePoleBlock);
    }

    private static BooleanProperty propertyFor(Direction dir) {
        return switch (dir) {
            case NORTH -> NORTH;
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> throw new IllegalArgumentException("Only horizontal");
        };
    }

    private boolean connectsCardinalTo(LevelAccessor level, BlockPos pos, Direction dir) {
        BlockPos neighborPos = pos.relative(dir);
        BlockState neighbor = level.getBlockState(neighborPos);
        Block nb = neighbor.getBlock();

        if (nb instanceof FencePoleBlock) {
            return true;
        }

        if (nb instanceof FenceWireBlock) {
            Direction right = dir.getClockWise();
            Direction left = dir.getCounterClockWise();

            boolean thisDiagRight = canConnectDiagonallyToFence(level, pos, dir, right);
            boolean thisDiagLeft = canConnectDiagonallyToFence(level, pos, dir, left);

            Direction back = dir.getOpposite();
            boolean neighDiagRight = canConnectDiagonallyToFence(level, neighborPos, back, back.getClockWise());
            boolean neighDiagLeft = canConnectDiagonallyToFence(level, neighborPos, back, back.getCounterClockWise());

            if (thisDiagRight || thisDiagLeft || neighDiagRight || neighDiagLeft) {
                return false;
            }

            return true;
        }

        if (neighbor.isFaceSturdy(level, neighborPos, dir.getOpposite())) {
            return true;
        }

        return false;
    }

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

    private static final VoxelShape CENTER = Block.box(7.5, 3.5, 7.5, 8.5, 12.5, 8.5);

    private static final VoxelShape ARM_NORTH = Block.box(7.5, 3.5, 0.0, 8.5, 12.5, 8.0);
    private static final VoxelShape ARM_SOUTH = Block.box(7.5, 3.5, 8.0, 8.5, 12.5, 16.0);
    private static final VoxelShape ARM_WEST = Block.box(0.0, 3.5, 7.5, 8.0, 12.5, 8.5);
    private static final VoxelShape ARM_EAST = Block.box(8.0, 3.5, 7.5, 16.0, 12.5, 8.5);

    private static VoxelShape buildDiagonal(boolean east, boolean south) {
        VoxelShape shape = Shapes.empty();
        double y1 = 3.5, y2 = 12.5;
        for (int i = 0; i < 8; i++) {
            double off = i; // 0..7
            double x1 = east ? 16 - (off + 1) : 0 + off;
            double x2 = x1 + 1;
            double z1 = south ? 16 - (off + 1) : 0 + off;
            double z2 = z1 + 1;
            shape = Shapes.or(shape, Block.box(x1, y1, z1, x2, y2, z2));
        }
        return shape;
    }

    private static final VoxelShape DIAG_NE = buildDiagonal(true, false);
    private static final VoxelShape DIAG_SE = buildDiagonal(true, true);
    private static final VoxelShape DIAG_SW = buildDiagonal(false, true);
    private static final VoxelShape DIAG_NW = buildDiagonal(false, false);

    // Precomputed shapes for all 256 combinations:
    // bit 0..3: NORTH, EAST, SOUTH, WEST; bit 4..7: NE, SE, SW, NW
    private static final VoxelShape[] SHAPES = new VoxelShape[256];

    static {
        for (int mask = 0; mask < SHAPES.length; mask++) {
            VoxelShape s = CENTER;

            if ((mask & (1 << 0)) != 0) s = Shapes.or(s, ARM_NORTH);
            if ((mask & (1 << 1)) != 0) s = Shapes.or(s, ARM_EAST);
            if ((mask & (1 << 2)) != 0) s = Shapes.or(s, ARM_SOUTH);
            if ((mask & (1 << 3)) != 0) s = Shapes.or(s, ARM_WEST);

            if ((mask & (1 << 4)) != 0) s = Shapes.or(s, DIAG_NE);
            if ((mask & (1 << 5)) != 0) s = Shapes.or(s, DIAG_SE);
            if ((mask & (1 << 6)) != 0) s = Shapes.or(s, DIAG_SW);
            if ((mask & (1 << 7)) != 0) s = Shapes.or(s, DIAG_NW);

            SHAPES[mask] = s;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        int mask = 0;
        if (state.getValue(NORTH)) mask |= (1 << 0);
        if (state.getValue(EAST))  mask |= (1 << 1);
        if (state.getValue(SOUTH)) mask |= (1 << 2);
        if (state.getValue(WEST))  mask |= (1 << 3);

        if (state.getValue(NE)) mask |= (1 << 4);
        if (state.getValue(SE)) mask |= (1 << 5);
        if (state.getValue(SW)) mask |= (1 << 6);
        if (state.getValue(NW)) mask |= (1 << 7);

        return SHAPES[mask];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
        return getShape(state, level, pos, ctx);
    }
}