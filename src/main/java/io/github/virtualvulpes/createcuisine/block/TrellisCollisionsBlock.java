package io.github.virtualvulpes.createcuisine.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class TrellisCollisionsBlock extends Block implements SimpleWaterloggedBlock {
	public static final BooleanProperty NORTH;
	public static final BooleanProperty EAST;
	public static final BooleanProperty SOUTH;
	public static final BooleanProperty WEST;
	public static final BooleanProperty UP;
	public static final BooleanProperty DOWN;
	public static final BooleanProperty WATERLOGGED;

	protected static final Map PROPERTY_BY_DIRECTION;
	protected final VoxelShape[] collisionShapeByIndex;
	protected final VoxelShape[] shapeByIndex;
	private final Object2IntMap<BlockState> stateToIndex = new Object2IntOpenHashMap<>();


	public TrellisCollisionsBlock(Properties properties) {
		super(properties);
		this.collisionShapeByIndex = this.makeShapes();
		this.shapeByIndex = this.makeShapes();

		for (BlockState blockState : this.stateDefinition.getPossibleStates()) {
			this.getAABBIndex(blockState);
		}
	}

	protected VoxelShape[] makeShapes() {

		VoxelShape base = Block.box(6, 6, 6, 10, 10, 10);
		VoxelShape N4 = Block.box(6, 6, 0, 10, 10, 6);
		VoxelShape S8 = Block.box(6, 6, 10, 10, 10, 16);
		VoxelShape W16 = Block.box(0, 6, 6, 6, 10, 10);
		VoxelShape E32 = Block.box(10, 6, 6, 16, 10, 10);
		VoxelShape U2 = Block.box(6, 10, 6, 10, 16, 10);
		VoxelShape D1 = Block.box(6, 0, 6, 10, 6, 10);

		VoxelShape SW24 = Shapes.or(S8, W16);
		VoxelShape DU3 = Shapes.or(D1, U2);
		VoxelShape NS12 = Shapes.or(N4, S8);
		VoxelShape DN5 = Shapes.or(N4, D1);
		VoxelShape UN6 = Shapes.or(U2, N4);
		VoxelShape WN20 = Shapes.or(W16, N4);
		VoxelShape DUN7 = Shapes.or(DU3, N4);
		VoxelShape DS9 = Shapes.or(D1, S8);
		VoxelShape ES40 = Shapes.or(E32, S8);
		VoxelShape WE48 = Shapes.or(W16, E32);
		VoxelShape US10 = Shapes.or(U2, S8);
		VoxelShape WES56 = Shapes.or(WE48, S8);

		VoxelShape[] voxelShapes = new VoxelShape[]{
				Shapes.empty(), D1, U2, DU3, N4, Shapes.or(D1, N4), Shapes.or(U2, N4), Shapes.or(DU3, N4), S8, Shapes.or(D1, S8), Shapes.or(U2, S8), Shapes.or(DU3, S8), NS12, Shapes.or(DN5, S8), Shapes.or(NS12, U2), Shapes.or(DU3, NS12),
				W16, Shapes.or(W16, D1), Shapes.or(W16, U2), Shapes.or(W16, DU3), Shapes.or(W16, N4), Shapes.or(W16, DN5), Shapes.or(W16, UN6), Shapes.or(WN20, DU3), Shapes.or(W16, S8), Shapes.or(SW24, D1), Shapes.or(SW24, U2),
				Shapes.or(SW24, DU3), Shapes.or(SW24, N4), Shapes.or(SW24, DN5), Shapes.or(SW24, UN6), Shapes.or(SW24, DUN7), E32, Shapes.or(E32, D1), Shapes.or(E32, U2), Shapes.or(E32, DU3), Shapes.or(E32, N4), Shapes.or(E32, DN5),
				Shapes.or(E32, UN6), Shapes.or(E32, DUN7), Shapes.or(E32, S8), Shapes.or(E32, DS9), Shapes.or(ES40, U2), Shapes.or(ES40, DU3), Shapes.or(ES40, N4), Shapes.or(ES40, DN5), Shapes.or(ES40, UN6), Shapes.or(ES40, DUN7),
				WE48, Shapes.or(WE48, D1), Shapes.or(WE48, U2), Shapes.or(WE48, DU3), Shapes.or(WE48, N4), Shapes.or(WE48, DN5), Shapes.or(WE48, UN6), Shapes.or(WE48, DUN7), Shapes.or(WE48, S8), Shapes.or(WE48, DS9), Shapes.or(WE48, US10),
				Shapes.or(WES56, DU3), Shapes.or(WES56, N4), Shapes.or(WES56, DN5), Shapes.or(WES56, UN6), Shapes.or(WES56, DUN7)
		};

		for(int j = 0; j < 64; ++j) {
			voxelShapes[j] = Shapes.or(base, voxelShapes[j]);
		}

		return voxelShapes;
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
		return !(Boolean)state.getValue(WATERLOGGED);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.shapeByIndex[this.getAABBIndex(state)];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.collisionShapeByIndex[this.getAABBIndex(state)];
	}

	private static int indexFor(Direction facing) {
		return 1 << facing.get3DDataValue();
	}

	protected int getAABBIndex(BlockState state) {
		return this.stateToIndex.computeIntIfAbsent(state, (statex) -> {
			int i = 0;

			if (statex.getValue(NORTH)) {
				i |= indexFor(Direction.NORTH);
			}

			if (statex.getValue(EAST)) {
				i |= indexFor(Direction.EAST);
			}

			if (statex.getValue(SOUTH)) {
				i |= indexFor(Direction.SOUTH);
			}

			if (statex.getValue(WEST)) {
				i |= indexFor(Direction.WEST);
			}

			if (statex.getValue(UP)) {
				i |= indexFor(Direction.UP);
			}

			if (statex.getValue(DOWN)) {
				i |= indexFor(Direction.DOWN);
			}

			return i;
		});
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return switch (rotation) {
			case CLOCKWISE_180 ->
					state.setValue(NORTH, state.getValue(SOUTH)).setValue(EAST, state.getValue(WEST)).setValue(SOUTH, state.getValue(NORTH)).setValue(WEST, state.getValue(EAST));
			case COUNTERCLOCKWISE_90 ->
					state.setValue(NORTH, state.getValue(EAST)).setValue(EAST, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(WEST)).setValue(WEST, state.getValue(NORTH));
			case CLOCKWISE_90 ->
					state.setValue(NORTH, state.getValue(WEST)).setValue(EAST, state.getValue(NORTH)).setValue(SOUTH, state.getValue(EAST)).setValue(WEST, state.getValue(SOUTH));
			default -> state;
		};
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		switch (mirror) {
			case LEFT_RIGHT:
				return state.setValue(NORTH, state.getValue(SOUTH)).setValue(SOUTH, state.getValue(NORTH));
			case FRONT_BACK:
				return state.setValue(EAST, state.getValue(WEST)).setValue(WEST, state.getValue(EAST));
			default:
				return super.mirror(state, mirror);
		}
	}

	static {
		NORTH = PipeBlock.NORTH;
		EAST = PipeBlock.EAST;
		SOUTH = PipeBlock.SOUTH;
		WEST = PipeBlock.WEST;
		UP = PipeBlock.UP;
		DOWN = PipeBlock.DOWN;
		WATERLOGGED = BlockStateProperties.WATERLOGGED;
		PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;
	}
}
