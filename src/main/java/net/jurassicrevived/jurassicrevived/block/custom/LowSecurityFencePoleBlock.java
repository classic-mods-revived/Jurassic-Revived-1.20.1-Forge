package net.jurassicrevived.jurassicrevived.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
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

    public LowSecurityFencePoleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST,  false)
                .setValue(SOUTH, false)
                .setValue(WEST,  false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level level = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        return this.defaultBlockState()
                .setValue(NORTH, connectsTo(level.getBlockState(pos.north())))
                .setValue(EAST,  connectsTo(level.getBlockState(pos.east())))
                .setValue(SOUTH, connectsTo(level.getBlockState(pos.south())))
                .setValue(WEST,  connectsTo(level.getBlockState(pos.west())));
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState updateShape(BlockState state, Direction dir, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (dir.getAxis().isHorizontal()) {
            boolean connect = connectsTo(neighborState);
            return state.setValue(propertyFor(dir), connect);
        }
        return state;
    }

    private boolean connectsTo(BlockState neighbor) {
        Block b = neighbor.getBlock();
        return (b instanceof LowSecurityFencePoleBlock) || (b instanceof LowSecurityFenceWireBlock);
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