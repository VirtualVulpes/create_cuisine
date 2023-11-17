package io.github.virtualvulpes.createcuisine.block;

import io.github.virtualvulpes.createcuisine.CreateCuisine;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class AllBlockTags {
	public static final TagKey<Block> TRELLISES = TagKey.create(Registries.BLOCK, CreateCuisine.asResource("trellises"));
	public static void register(){

	}
}
