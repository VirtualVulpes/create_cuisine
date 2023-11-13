package io.github.virtualvulpes.createcuisine.block;

import io.github.virtualvulpes.createcuisine.CreateCuisine;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {
	public static final Block TRELLIS = registerBlock("trellis",
			new TrellisBlock(FabricBlockSettings.create().nonOpaque().ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));

	private static Block registerBlock(String name, Block block) {
		registerBlockItem(name, block);
		return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(CreateCuisine.ID, name), block);
	}

	private static Item registerBlockItem(String name, Block block) {
		return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CreateCuisine.ID, name),
				new BlockItem(block, new FabricItemSettings()));
	}

	public static void registerModBlocks() {

	}
}
