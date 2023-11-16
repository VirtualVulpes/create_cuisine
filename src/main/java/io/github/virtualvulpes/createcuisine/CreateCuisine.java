package io.github.virtualvulpes.createcuisine;

import com.tterrag.registrate.Registrate;

import io.github.virtualvulpes.createcuisine.block.ModBlockTags;
import io.github.virtualvulpes.createcuisine.block.AllBlocks;
import io.github.virtualvulpes.createcuisine.fluid.AllFluids;
import io.github.virtualvulpes.createcuisine.item.AllItemGroups;
import io.github.virtualvulpes.createcuisine.item.AllItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

public class CreateCuisine implements ModInitializer {
	public static final String ID = "createcuisine";
	public static final String NAME = "Create Cuisine";
	public static final Registrate REGISTRATE = Registrate.create(ID);

	@Override
	public void onInitialize() {
		AllBlocks.register();
		AllItems.register();
		AllItemGroups.register();
		ModBlockTags.register();

		REGISTRATE.register();
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}
}
