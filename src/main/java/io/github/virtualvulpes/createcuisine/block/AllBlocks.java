package io.github.virtualvulpes.createcuisine.block;

import com.simibubi.create.Create;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;

import io.github.virtualvulpes.createcuisine.CreateCuisine;
import io.github.virtualvulpes.createcuisine.item.AllItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import static io.github.virtualvulpes.createcuisine.CreateCuisine.REGISTRATE;

public class AllBlocks {
	public static final BlockEntry<TrellisBlock> OAK_TRELLIS = REGISTRATE.object("oak_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD))
			.item().build()
			.register();

	public static final BlockEntry<TrellisBlock> SPRUCE_TRELLIS = REGISTRATE.object("spruce_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD))
			.item().build()
			.register();

	public static final BlockEntry<TrellisBlock> BIRCH_TRELLIS = REGISTRATE.object("birch_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD))
			.item().build()
			.register();

	public static final BlockEntry<TrellisBlock> JUNGLE_TRELLIS = REGISTRATE.object("jungle_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD))
			.item().build()
			.register();

	public static final BlockEntry<TrellisBlock> ACACIA_TRELLIS = REGISTRATE.object("acacia_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD))
			.item().build()
			.register();

	public static final BlockEntry<TrellisBlock> DARK_OAK_TRELLIS = REGISTRATE.object("dark_oak_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD))
			.item().build()
			.register();

	public static final BlockEntry<TrellisBlock> MANGROVE_TRELLIS = REGISTRATE.object("mangrove_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD))
			.item().build()
			.register();

	public static final BlockEntry<TrellisBlock> CHERRY_TRELLIS = REGISTRATE.object("cherry_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).ignitedByLava().sound(SoundType.WOOD).mapColor(MapColor.WOOD))
			.item().build()
			.register();

	public static final BlockEntry<TrellisBlock> CRIMSON_TRELLIS = REGISTRATE.object("crimson_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).sound(SoundType.NETHER_WOOD).mapColor(MapColor.CRIMSON_STEM))
			.item().build()
			.register();

	public static final BlockEntry<TrellisBlock> WARPED_TRELLIS = REGISTRATE.object("warped_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).sound(SoundType.NETHER_WOOD).mapColor(MapColor.WARPED_STEM))
			.item().build()
			.register();

	public static final BlockEntry<TrellisBlock> BAMBOO_TRELLIS = REGISTRATE.object("bamboo_trellis").block(TrellisBlock::new)
			.properties(p -> p.strength(2f, 3f).ignitedByLava().sound(SoundType.BAMBOO_WOOD).mapColor(MapColor.COLOR_YELLOW))
			.item()
			.build()
			.register();

	public static final BlockEntry<GrapeVinesBlock> GRAPE_VINES = REGISTRATE.object("grape_vines").block(GrapeVinesBlock::new)
			.properties(p -> p.instabreak().sound(SoundType.CROP).mapColor(MapColor.PLANT).randomTicks().noCollission().pushReaction(PushReaction.DESTROY))
			.register();

	public static void register() {

	}
}
