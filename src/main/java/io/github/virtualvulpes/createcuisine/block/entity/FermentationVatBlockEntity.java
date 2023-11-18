package io.github.virtualvulpes.createcuisine.block.entity;

import com.simibubi.create.AllParticleTypes;
import com.simibubi.create.content.fluids.FluidFX;
import com.simibubi.create.content.fluids.particle.FluidParticleData;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.mixer.MechanicalMixerBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.CombinedTankWrapper;

import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Couple;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.LongAttached;
import com.simibubi.create.foundation.utility.NBTHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;

import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import io.github.fabricators_of_create.porting_lib.util.NBTSerializer;
import io.github.virtualvulpes.createcuisine.block.FermentationVatBlock;
import io.github.virtualvulpes.createcuisine.block.inventory.FermentationVatInventory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FermentationVatBlockEntity extends SmartBlockEntity implements SidedStorageBlockEntity {
	private boolean needsUpdate; // fabric: need to delay to avoid doing stuff mid-transaction, causing a crash
	private boolean areFluidsMoving;
	LerpedFloat ingredientRotationSpeed;
	public LerpedFloat ingredientRotation;

	public FermentationVatInventory inputInventory;
	public SmartFluidTankBehaviour inputTank;
	protected SmartInventory outputInventory;
	protected SmartFluidTankBehaviour outputTank;
	private boolean contentsChanged;

	private Couple<SmartInventory> invs;
	private Couple<SmartFluidTankBehaviour> tanks;

	public Storage<ItemVariant> itemCapability;
	protected Storage<FluidVariant> fluidCapability;

	int recipeBackupCheck;

	public static final int OUTPUT_ANIMATION_TIME = 10;
	public List<LongAttached<ItemStack>> visualizedOutputItems;
	List<LongAttached<FluidStack>> visualizedOutputFluids;

	public FermentationVatBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		inputInventory = new FermentationVatInventory(9, this);
		inputInventory.whenContentsChanged(() -> contentsChanged = true);
		outputInventory = new FermentationVatInventory(9, this).forbidInsertion()
				.withMaxStackSize(64);
		areFluidsMoving = false;
		itemCapability = new CombinedStorage<>(List.of(inputInventory, outputInventory));
		contentsChanged = true;
		ingredientRotation = LerpedFloat.angular()
				.startWithValue(0);
		ingredientRotationSpeed = LerpedFloat.linear()
				.startWithValue(0);

		invs = Couple.create(inputInventory, outputInventory);
		tanks = Couple.create(inputTank, outputTank);
		visualizedOutputItems = Collections.synchronizedList(new ArrayList<>());
		visualizedOutputFluids = Collections.synchronizedList(new ArrayList<>());
		recipeBackupCheck = 20;
	}
	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		behaviours.add(new DirectBeltInputBehaviour(this));

		inputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 2, FluidConstants.BUCKET, true)
				.whenFluidUpdates(() -> contentsChanged = true);
		outputTank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.OUTPUT, this, 2, FluidConstants.BUCKET, true)
				.whenFluidUpdates(() -> contentsChanged = true)
				.forbidInsertion();
		behaviours.add(inputTank);
		behaviours.add(outputTank);

		fluidCapability = new CombinedTankWrapper(inputTank.getCapability(), outputTank.getCapability());
	}

	@Override
	protected void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);
		inputInventory.deserializeNBT(compound.getCompound("InputItems"));
		outputInventory.deserializeNBT(compound.getCompound("OutputItems"));

		if (!clientPacket)
			return;

		NBTHelper.iterateCompoundList(compound.getList("VisualizedItems", Tag.TAG_COMPOUND),
				c -> visualizedOutputItems.add(LongAttached.with(OUTPUT_ANIMATION_TIME, ItemStack.of(c))));
		NBTHelper.iterateCompoundList(compound.getList("VisualizedFluids", Tag.TAG_COMPOUND),
				c -> visualizedOutputFluids
						.add(LongAttached.with(OUTPUT_ANIMATION_TIME, FluidStack.loadFluidStackFromNBT(c))));
	}

	@Override
	public void write(CompoundTag compound, boolean clientPacket) {
		super.write(compound, clientPacket);
		compound.put("InputItems", inputInventory.serializeNBT());
		compound.put("OutputItems", outputInventory.serializeNBT());

		if (!clientPacket)
			return;

		compound.put("VisualizedItems", NBTHelper.writeCompoundList(visualizedOutputItems, ia -> NBTSerializer.serializeNBTCompound(ia.getValue())));
		compound.put("VisualizedFluids", NBTHelper.writeCompoundList(visualizedOutputFluids, ia -> ia.getValue()
				.writeToNBT(new CompoundTag())));
		visualizedOutputItems.clear();
		visualizedOutputFluids.clear();
	}

	@Override
	public void destroy() {
		super.destroy();
		ItemHelper.dropContents(level, worldPosition, inputInventory);
		ItemHelper.dropContents(level, worldPosition, outputInventory);
	}

	@Override
	public void remove() {
		super.remove();
	}

	@Override
	public void invalidate() {
		super.invalidate();
	}

	@Override
	public void notifyUpdate() {
		this.needsUpdate = true;
	}

	@Override
	public void lazyTick() {
		super.lazyTick();

		if (!level.isClientSide) {
			if (recipeBackupCheck-- > 0)
				return;
			recipeBackupCheck = 20;
			if (isEmpty())
				return;
			notifyChangeOfContents();
			return;
		}

		BlockEntity blockEntity = level.getBlockEntity(worldPosition.above(2));
		if (!(blockEntity instanceof MechanicalMixerBlockEntity)) {
			setAreFluidsMoving(false);
			return;
		}

		MechanicalMixerBlockEntity mixer = (MechanicalMixerBlockEntity) blockEntity;
		setAreFluidsMoving(mixer.running && mixer.runningTicks <= 20);
	}

	public boolean isEmpty() {
		return inputInventory.isEmpty() && outputInventory.isEmpty() && inputTank.isEmpty() && outputTank.isEmpty();
	}

	@Override
	public void tick() {
		super.tick();
		if (needsUpdate) {
			needsUpdate = false;
			super.notifyUpdate();
		}
		if (level.isClientSide) {
			createFluidParticles();
			tickVisualizedOutputs();
			ingredientRotationSpeed.tickChaser();
			ingredientRotation.setValue(ingredientRotation.getValue() + ingredientRotationSpeed.getValue());
		}

		if (!contentsChanged)
			return;

		contentsChanged = false;
		sendData();

		for (Direction offset : Iterate.horizontalDirections) {
			BlockPos toUpdate = worldPosition.above()
					.relative(offset);
			BlockState stateToUpdate = level.getBlockState(toUpdate);
			if (stateToUpdate.getBlock() instanceof FermentationVatBlock
					&& stateToUpdate.getValue(FermentationVatBlock.FACING) == offset.getOpposite()) {
				BlockEntity be = level.getBlockEntity(toUpdate);
				if (be instanceof FermentationVatBlockEntity)
					((FermentationVatBlockEntity) be).contentsChanged = true;
			}
		}
	}

	public float getTotalFluidUnits(float partialTicks) {
		int renderedFluids = 0;
		float totalUnits = 0;

		for (SmartFluidTankBehaviour behaviour : getTanks()) {
			if (behaviour == null)
				continue;
			for (SmartFluidTankBehaviour.TankSegment tankSegment : behaviour.getTanks()) {
				if (tankSegment.getRenderedFluid()
						.isEmpty())
					continue;
				float units = tankSegment.getTotalUnits(partialTicks);
				if (units < 1)
					continue;
				totalUnits += units;
				renderedFluids++;
			}
		}

		if (renderedFluids == 0)
			return 0;
		if (totalUnits < 1)
			return 0;
		return totalUnits;
	}

	public void notifyChangeOfContents() {
		contentsChanged = true;
	}

	public SmartInventory getInputInventory() {
		return inputInventory;
	}

	public SmartInventory getOutputInventory() {
		return outputInventory;
	}

	private boolean acceptFluidOutputsIntoFermentationVat(List<FluidStack> outputFluids, TransactionContext ctx,
												Storage<FluidVariant> targetTank) {
		for (FluidStack fluidStack : outputFluids) {
			long fill = targetTank instanceof SmartFluidTankBehaviour.InternalFluidHandler
					? ((SmartFluidTankBehaviour.InternalFluidHandler) targetTank).forceFill(fluidStack.copy(), ctx)
					: targetTank.insert(fluidStack.getType(), fluidStack.getAmount(), ctx);
			if (fill != fluidStack.getAmount())
				return false;
		}
		return true;
	}

	private boolean acceptItemOutputsIntoFermentationVat(List<ItemStack> outputItems, TransactionContext ctx, Storage<ItemVariant> targetInv) {
		try (Transaction t = ctx.openNested()) {
			for (ItemStack itemStack : outputItems) {
				long inserted = targetInv.insert(ItemVariant.of(itemStack), itemStack.getCount(), t);
				if (inserted != itemStack.getCount()) {
					t.commit();
					return false;
				}
			}
			t.commit();
		}
		return true;
	}

	public void readOnlyItems(CompoundTag compound) {
		inputInventory.deserializeNBT(compound.getCompound("InputItems"));
		outputInventory.deserializeNBT(compound.getCompound("OutputItems"));
	}

	public Couple<SmartFluidTankBehaviour> getTanks() {
		return tanks;
	}

	public Couple<SmartInventory> getInvs() {
		return invs;
	}

	// client things

	private void tickVisualizedOutputs() {
		visualizedOutputFluids.forEach(LongAttached::decrement);
		visualizedOutputItems.forEach(LongAttached::decrement);
		visualizedOutputFluids.removeIf(LongAttached::isOrBelowZero);
		visualizedOutputItems.removeIf(LongAttached::isOrBelowZero);
	}

	private void createFluidParticles() {
		RandomSource r = level.random;

		if (!visualizedOutputFluids.isEmpty())
			createOutputFluidParticles(r);

		if (!areFluidsMoving && r.nextFloat() > 1 / 8f)
			return;

		int segments = 0;
		for (SmartFluidTankBehaviour behaviour : getTanks()) {
			if (behaviour == null)
				continue;
			for (SmartFluidTankBehaviour.TankSegment tankSegment : behaviour.getTanks())
				if (!tankSegment.isEmpty(0))
					segments++;
		}
		if (segments < 2)
			return;

		float totalUnits = getTotalFluidUnits(0);
		if (totalUnits == 0)
			return;
		float fluidLevel = Mth.clamp(totalUnits / 2000, 0, 1);
		float rim = 2 / 16f;
		float space = 12 / 16f;
		float surface = worldPosition.getY() + rim + space * fluidLevel + 1 / 32f;

		if (areFluidsMoving) {
			createMovingFluidParticles(surface, segments);
			return;
		}

		for (SmartFluidTankBehaviour behaviour : getTanks()) {
			if (behaviour == null)
				continue;
			for (SmartFluidTankBehaviour.TankSegment tankSegment : behaviour.getTanks()) {
				if (tankSegment.isEmpty(0))
					continue;
				float x = worldPosition.getX() + rim + space * r.nextFloat();
				float z = worldPosition.getZ() + rim + space * r.nextFloat();
				level.addAlwaysVisibleParticle(
						new FluidParticleData(AllParticleTypes.BASIN_FLUID.get(), tankSegment.getRenderedFluid()), x,
						surface, z, 0, 0, 0);
			}
		}
	}

	private void createOutputFluidParticles(RandomSource r) {
		BlockState blockState = getBlockState();
		if (!(blockState.getBlock() instanceof FermentationVatBlock))
			return;
		Direction direction = blockState.getValue(FermentationVatBlock.FACING);
		if (direction == Direction.DOWN)
			return;
		Vec3 directionVec = Vec3.atLowerCornerOf(direction.getNormal());
		Vec3 outVec = VecHelper.getCenterOf(worldPosition)
				.add(directionVec.scale(.65)
						.subtract(0, 1 / 4f, 0));
		Vec3 outMotion = directionVec.scale(1 / 16f)
				.add(0, -1 / 16f, 0);

		for (int i = 0; i < 2; i++) {
			visualizedOutputFluids.forEach(ia -> {
				FluidStack fluidStack = ia.getValue();
				ParticleOptions fluidParticle = FluidFX.getFluidParticle(fluidStack);
				Vec3 m = VecHelper.offsetRandomly(outMotion, r, 1 / 16f);
				level.addAlwaysVisibleParticle(fluidParticle, outVec.x, outVec.y, outVec.z, m.x, m.y, m.z);
			});
		}
	}

	private void createMovingFluidParticles(float surface, int segments) {
		Vec3 pointer = new Vec3(1, 0, 0).scale(1 / 16f);
		float interval = 360f / segments;
		Vec3 centerOf = VecHelper.getCenterOf(worldPosition);
		float intervalOffset = (AnimationTickHolder.getTicks() * 18) % 360;

		int currentSegment = 0;
		for (SmartFluidTankBehaviour behaviour : getTanks()) {
			if (behaviour == null)
				continue;
			for (SmartFluidTankBehaviour.TankSegment tankSegment : behaviour.getTanks()) {
				if (tankSegment.isEmpty(0))
					continue;
				float angle = interval * (1 + currentSegment) + intervalOffset;
				Vec3 vec = centerOf.add(VecHelper.rotate(pointer, angle, Direction.Axis.Y));
				level.addAlwaysVisibleParticle(
						new FluidParticleData(AllParticleTypes.BASIN_FLUID.get(), tankSegment.getRenderedFluid()), vec.x(),
						surface, vec.z(), 1, 0, 0);
				currentSegment++;
			}
		}
	}

	public boolean areFluidsMoving() {
		return areFluidsMoving;
	}

	public boolean setAreFluidsMoving(boolean areFluidsMoving) {
		this.areFluidsMoving = areFluidsMoving;
		ingredientRotationSpeed.chase(areFluidsMoving ? 20 : 0, .1f, LerpedFloat.Chaser.EXP);
		return areFluidsMoving;
	}

	@Nullable
	@Override
	public Storage<FluidVariant> getFluidStorage(@Nullable Direction face) {
		return fluidCapability;
	}

	@Nullable
	@Override
	public Storage<ItemVariant> getItemStorage(@Nullable Direction face) {
		return itemCapability;
	}
}
