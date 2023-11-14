package io.github.virtualvulpes.createcuisine.item;

import io.github.virtualvulpes.createcuisine.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
	private static final CreativeModeTab ITEMS = registerItemGroup(FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.GRAPES))
			.title(Component.translatable(("itemGroup.createcuisine.items")))
			.displayItems((context, entries) -> {
			entries.accept(ModBlocks.OAK_TRELLIS);
			entries.accept(ModBlocks.SPRUCE_TRELLIS);
			entries.accept(ModBlocks.BIRCH_TRELLIS);
			entries.accept(ModBlocks.JUNGLE_TRELLIS);
			entries.accept(ModBlocks.ACACIA_TRELLIS);
			entries.accept(ModBlocks.DARK_OAK_TRELLIS);
			entries.accept(ModBlocks.MANGROVE_TRELLIS);
			entries.accept(ModBlocks.CHERRY_TRELLIS);
			entries.accept(ModBlocks.CRIMSON_TRELLIS);
			entries.accept(ModBlocks.WARPED_TRELLIS);
			entries.accept(ModBlocks.BAMBOO_TRELLIS);
			entries.accept(ModItems.GRAPES);
			})
			.build());

	private static CreativeModeTab registerItemGroup(CreativeModeTab group) {
		return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation("creativecuisine", "items"), group);
	}

	public static void registerModItemGroups() {

	}
}
