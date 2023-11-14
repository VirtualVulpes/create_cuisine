package io.github.virtualvulpes.createcuisine;

import com.jozufozu.flywheel.backend.RenderLayer;

import io.github.virtualvulpes.createcuisine.block.ModBlocks;
import io.github.virtualvulpes.createcuisine.item.ModItemGroups;
import io.github.virtualvulpes.createcuisine.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class CreateCuisine implements ModInitializer {
	public static final String ID = "createcuisine";
	public static final String NAME = "Create Cuisine";

	@Override
	public void onInitialize() {
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}
}
