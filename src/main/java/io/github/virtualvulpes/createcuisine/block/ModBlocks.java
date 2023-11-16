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
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.CaveVinesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class ModBlocks {
	public static final Block OAK_TRELLIS = registerBlockWithItem("oak_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block SPRUCE_TRELLIS = registerBlockWithItem("spruce_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block BIRCH_TRELLIS = registerBlockWithItem("birch_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block JUNGLE_TRELLIS = registerBlockWithItem("jungle_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block ACACIA_TRELLIS = registerBlockWithItem("acacia_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block DARK_OAK_TRELLIS = registerBlockWithItem("dark_oak_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block MANGROVE_TRELLIS = registerBlockWithItem("mangrove_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block CHERRY_TRELLIS = registerBlockWithItem("cherry_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD)));
	public static final Block CRIMSON_TRELLIS = registerBlockWithItem("crimson_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD).mapColor(MapColor.CRIMSON_STEM)));
	public static final Block WARPED_TRELLIS = registerBlockWithItem("warped_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD).mapColor(MapColor.WARPED_STEM)));
	public static final Block BAMBOO_TRELLIS = registerBlockWithItem("bamboo_trellis",
			new TrellisBlock(FabricBlockSettings.create().strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.BAMBOO_WOOD).mapColor(MapColor.COLOR_YELLOW)));
	public static final Block GRAPE_VINES = registerBlock("grape_vines",
			new GrapeVinesBlock(FabricBlockSettings.create().breakInstantly().sound(SoundType.CROP).mapColor(MapColor.PLANT).randomTicks().noCollission().pushReaction(PushReaction.DESTROY)));

	private static Block registerBlockWithItem(String name, Block block) {
		registerBlockItem(name, block);
		return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(CreateCuisine.ID, name), block);
	}

	private static Block registerBlock(String name, Block block) {
		return Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(CreateCuisine.ID, name), block);
	}

	private static Item registerBlockItem(String name, Block block) {
		return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(CreateCuisine.ID, name),
				new BlockItem(block, new FabricItemSettings()));
	}

	public static void registerModBlocks() {

	}
}
