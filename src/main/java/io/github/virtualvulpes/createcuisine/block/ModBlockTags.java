package io.github.virtualvulpes.createcuisine.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
	public static final TagKey<Block> TRELLISES = TagKey.create(Registries.BLOCK, new ResourceLocation("createcuisine", "trellises"));

	public static void register(){

	}
}
