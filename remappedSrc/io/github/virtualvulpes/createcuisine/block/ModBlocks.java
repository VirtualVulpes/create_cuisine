package io.github.virtualvulpes.createcuisine.block;

import io.github.virtualvulpes.createcuisine.CreateCuisine;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;

public class ModBlocks {
	public static final Block TRELLIS = registerBlock("trellis",
			new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));

	private static Block registerBlock(String name, Block block) {
		registerBlockItem(name, block);
		return Registry.register(RegistryKeys.BLOCK, new Identifier(CreateCuisine.ID, name), block);
	}

	private static Item registerBlockItem(String name, Block block) {
		return Registry.register(RegistryKeys.ITEM, new Identifier(CreateCuisine.ID, name),
				new BlockItem(block, new FabricItemSettings()));
	}
}
