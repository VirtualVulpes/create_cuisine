package io.github.virtualvulpes.createcuisine.block;

import com.simibubi.create.AllShapes;
import com.simibubi.create.Create;
import com.simibubi.create.content.fluids.transfer.GenericItemEmptying;
import com.simibubi.create.content.fluids.transfer.GenericItemFilling;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.fluid.FluidHelper;

import com.simibubi.create.foundation.item.ItemHelper;

import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.virtualvulpes.createcuisine.block.entity.AllBlockEntities;
import io.github.virtualvulpes.createcuisine.block.entity.FermentationVatBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class FermentationVatBlock extends Block implements IBE<FermentationVatBlockEntity> {
	public static final DirectionProperty FACING = BlockStateProperties.FACING_HOPPER;

	public FermentationVatBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.DOWN));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
		super.createBlockStateDefinition(p_206840_1_.add(FACING));
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
								 BlockHitResult hit) {
		ItemStack heldItem = player.getItemInHand(handIn);

		return onBlockEntityUse(worldIn, pos, be -> {
			if (!heldItem.isEmpty()) {
				Direction direction = hit.getDirection();
				if (FluidHelper.tryEmptyItemIntoBE(worldIn, player, handIn, heldItem, be, direction))
					return InteractionResult.SUCCESS;
				if (FluidHelper.tryFillItemFromBE(worldIn, player, handIn, heldItem, be, direction))
					return InteractionResult.SUCCESS;

				if (GenericItemEmptying.canItemBeEmptied(worldIn, heldItem)
						|| GenericItemFilling.canItemBeFilled(worldIn, heldItem))
					return InteractionResult.SUCCESS;
				if (heldItem.getItem()
						.equals(Items.SPONGE)) {
					Storage<FluidVariant> storage = be.getFluidStorage(direction);
					if (storage != null && !TransferUtil.extractAnyFluid(storage, Long.MAX_VALUE).isEmpty()) {
						return InteractionResult.SUCCESS;
					}
				}
				return InteractionResult.PASS;
			}

			Storage<ItemVariant> inv = be.itemCapability;
			if (inv == null) return InteractionResult.PASS;
			List<ItemStack> extracted = TransferUtil.extractAllAsStacks(inv);
			if (extracted.size() > 0) {
				extracted.forEach(s -> player.getInventory().placeItemBackInInventory(s));
				worldIn.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f,
						1f + Create.RANDOM.nextFloat());
			}
			return InteractionResult.SUCCESS;
		});
	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
		super.updateEntityAfterFallOn(worldIn, entityIn);
		if (!AllBlocks.FERMENTATION_VAT.has(worldIn.getBlockState(entityIn.blockPosition())))
			return;
		if (!(entityIn instanceof ItemEntity))
			return;
		if (!entityIn.isAlive())
			return;
		ItemEntity itemEntity = (ItemEntity) entityIn;
		withBlockEntityDo(worldIn, entityIn.blockPosition(), be -> {

			// Tossed items bypass the quarter-stack limit
			be.inputInventory.withMaxStackSize(64);
			ItemStack stack = itemEntity.getItem().copy();
			try (Transaction t = TransferUtil.getTransaction()) {
				long inserted = be.inputInventory.insert(ItemVariant.of(stack), stack.getCount(), t);
				be.inputInventory.withMaxStackSize(16);
				t.commit();

				if (inserted == stack.getCount()) {
					itemEntity.discard();

					return;
				}

				stack.setCount((int) (stack.getCount() - inserted));
				itemEntity.setItem(stack);
			}
		});
	}

	@Override
	public VoxelShape getInteractionShape(BlockState p_199600_1_, BlockGetter p_199600_2_, BlockPos p_199600_3_) {
		return AllShapes.BASIN_RAYTRACE_SHAPE;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return AllShapes.BASIN_BLOCK_SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext ctx) {
		if (ctx instanceof EntityCollisionContext && ((EntityCollisionContext) ctx).getEntity() instanceof ItemEntity)
			return AllShapes.BASIN_COLLISION_SHAPE;
		return getShape(state, reader, pos, ctx);
	}

	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		IBE.onRemove(state, worldIn, pos, newState);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
		return getBlockEntityOptional(worldIn, pos).map(FermentationVatBlockEntity::getInputInventory)
				.filter(fermentationVat -> !Transaction.isOpen()) // fabric: hack fix for comparators updating when they shouldn't
				.map(ItemHelper::calcRedstoneFromInventory)
				.orElse(0);
	}

	@Override
	public Class<FermentationVatBlockEntity> getBlockEntityClass() {
		return FermentationVatBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends FermentationVatBlockEntity> getBlockEntityType() {
		return AllBlockEntities.FERMENTATION_VAT.get();
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
		return false;
	}
}
