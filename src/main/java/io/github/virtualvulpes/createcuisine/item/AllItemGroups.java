package io.github.virtualvulpes.createcuisine.item;

import io.github.virtualvulpes.createcuisine.block.AllBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AllItemGroups {
	private static final CreativeModeTab ITEMS = registerItemGroup(FabricItemGroup.builder().icon(() -> new ItemStack(AllBlocks.GRAPE_VINES.asItem()))
			.title(Component.translatable(("itemGroup.createcuisine.items")))
			.displayItems((context, entries) -> {
			entries.accept(AllBlocks.OAK_TRELLIS);
			entries.accept(AllBlocks.SPRUCE_TRELLIS);
			entries.accept(AllBlocks.BIRCH_TRELLIS);
			entries.accept(AllBlocks.JUNGLE_TRELLIS);
			entries.accept(AllBlocks.ACACIA_TRELLIS);
			entries.accept(AllBlocks.DARK_OAK_TRELLIS);
			entries.accept(AllBlocks.MANGROVE_TRELLIS);
			entries.accept(AllBlocks.CHERRY_TRELLIS);
			entries.accept(AllBlocks.CRIMSON_TRELLIS);
			entries.accept(AllBlocks.WARPED_TRELLIS);
			entries.accept(AllBlocks.BAMBOO_TRELLIS);
			entries.accept(AllBlocks.GRAPE_VINES.asItem());
			})
			.build());

	private static CreativeModeTab registerItemGroup(CreativeModeTab group) {
		return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation("creativecuisine", "items"), group);
	}

	public static void register() {

	}
}
