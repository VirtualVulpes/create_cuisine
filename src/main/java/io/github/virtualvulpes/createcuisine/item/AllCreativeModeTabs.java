package io.github.virtualvulpes.createcuisine.item;

import io.github.virtualvulpes.createcuisine.CreateCuisine;
import io.github.virtualvulpes.createcuisine.block.AllBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static io.github.virtualvulpes.createcuisine.CreateCuisine.REGISTRATE;

public class AllCreativeModeTabs {

	private static final CreativeModeTab MAIN_TAB = registerItemGroup(FabricItemGroup.builder().icon(() -> new ItemStack(AllBlocks.GRAPE_VINES.asItem()))
			.title(Component.translatable(("creativeModeTab.createcuisine.main_tab")))
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
			entries.accept(AllBlocks.GRAPE_VINES);
			entries.accept(AllItems.GRAPE_SKINS);
			entries.accept(AllBlocks.FERMENTATION_VAT);
			})
			.build());

	private static CreativeModeTab registerItemGroup(CreativeModeTab group) {
		return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CreateCuisine.asResource("main_tab"), group);
	}

	public static void register() {

	}
}
