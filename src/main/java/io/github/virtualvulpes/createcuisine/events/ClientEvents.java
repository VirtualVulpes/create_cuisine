package io.github.virtualvulpes.createcuisine.events;

import com.mojang.blaze3d.shaders.FogShape;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import com.simibubi.create.foundation.fluid.FluidHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;

import com.tterrag.registrate.fabric.SimpleFlowableFluid;

import io.github.fabricators_of_create.porting_lib.event.client.FogEvents;
import io.github.virtualvulpes.createcuisine.fluid.AllFluids;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;

public class ClientEvents {

	public ClientEvents() {
	}

	public static void register() {
		FogEvents.RENDER_FOG.register(ClientEvents::getFogDensity);
		FogEvents.SET_COLOR.register(ClientEvents::getFogColor);
	}

	public static boolean getFogDensity(FogRenderer.FogMode mode, FogType type, Camera camera, float partialTick, float renderDistance, float nearDistance, float farDistance, FogShape shape, FogEvents.FogData fogData) {
		Level level = Minecraft.getInstance().level;
		BlockPos blockPos = camera.getBlockPosition();
		FluidState fluidState = level.getFluidState(blockPos);
		if (camera.getPosition().y >= (double)((float)blockPos.getY() + fluidState.getHeight(level, blockPos))) {
			return false;
		} else {
			Fluid fluid = fluidState.getType();
			Entity entity = camera.getEntity();
			if (AllFluids.GRAPE_JUICE.get().isSame(fluid)) {
				fogData.scaleFarPlaneDistance(0.03125F);
				return true;
			} else if (entity.isSpectator()) {
				return false;
			} else {
				ItemStack divingHelmet = DivingHelmetItem.getWornItem(entity);
				if (!divingHelmet.isEmpty()) {
					if (FluidHelper.isWater(fluid)) {
						fogData.scaleFarPlaneDistance(6.25F);
						return true;
					}

					if (FluidHelper.isLava(fluid) && AllItems.NETHERITE_DIVING_HELMET.isIn(divingHelmet)) {
						fogData.setNearPlaneDistance(-4.0F);
						fogData.setFarPlaneDistance(20.0F);
						return true;
					}
				}

				return false;
			}
		}
	}

	public static void getFogColor(FogEvents.ColorData event, float partialTicks) {
		Camera info = event.getCamera();
		Level level = Minecraft.getInstance().level;
		BlockPos blockPos = info.getBlockPosition();
		FluidState fluidState = level.getFluidState(blockPos);
		if (info.getPosition().y > blockPos.getY() + fluidState.getHeight(level, blockPos))
			return;

		Fluid fluid = fluidState.getType();

		if (AllFluids.GRAPE_JUICE.get()
				.isSame(fluid)) {
			event.setRed(98 / 255f);
			event.setGreen(32 / 255f);
			event.setBlue(32 / 255f);
			return;
		}
	}
}
