package io.github.virtualvulpes.createcuisine.item;

import io.github.virtualvulpes.createcuisine.CreateCuisine;
import io.github.virtualvulpes.createcuisine.block.TrellisBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class ModItems {
	public static final Item GRAPES = registerItem("grapes", new FabricItemSettings().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).fast().build()));

	private static Item registerItem(String name, FabricItemSettings settings) {
		return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CreateCuisine.ID, name),
				new Item(settings));
	}

	public static void registerModItems() {

	}
}
