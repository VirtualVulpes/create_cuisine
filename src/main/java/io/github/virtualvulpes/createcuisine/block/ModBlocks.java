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
	public static final Block OAK_TRELLIS = registerBlock("oak_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block SPRUCE_TRELLIS = registerBlock("spruce_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block BIRCH_TRELLIS = registerBlock("birch_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block JUNGLE_TRELLIS = registerBlock("jungle_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block ACACIA_TRELLIS = registerBlock("acacia_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block DARK_OAK_TRELLIS = registerBlock("dark_oak_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block MANGROVE_TRELLIS = registerBlock("mangrove_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block CHERRY_TRELLIS = registerBlock("cherry_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block CRIMSON_TRELLIS = registerBlock("crimson_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD).mapColor(MapColor.CRIMSON_STEM)));
	public static final Block WARPED_TRELLIS = registerBlock("warped_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD).mapColor(MapColor.WARPED_STEM)));
	public static final Block BAMBOO_TRELLIS = registerBlock("bamboo_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.BAMBOO_WOOD).mapColor(MapColor.COLOR_YELLOW)));

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
