package io.github.virtualvulpes.createcuisine.item;

import com.simibubi.create.Create;
import com.simibubi.create.content.equipment.armor.BacktankItem;
import com.tterrag.registrate.util.entry.ItemEntry;

import io.github.virtualvulpes.createcuisine.CreateCuisine;
import io.github.virtualvulpes.createcuisine.block.AllBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;

import static io.github.virtualvulpes.createcuisine.CreateCuisine.REGISTRATE;

public class AllItems {
	public static final ItemEntry<ItemNameBlockItem> GRAPES = REGISTRATE.item("grapes", p -> new ItemNameBlockItem(AllBlocks.GRAPE_VINES.get(), p))
			.properties(p -> p.food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).fast().build()))
			.register();

	public static final ItemEntry<Item> GRAPE_SKINS = REGISTRATE.item("grape_skins", Item::new)
			.register();

	public static void register() {

	}
}
