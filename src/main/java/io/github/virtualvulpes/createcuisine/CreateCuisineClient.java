package io.github.virtualvulpes.createcuisine;

import io.github.virtualvulpes.createcuisine.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class CreateCuisineClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OAK_TRELLIS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPRUCE_TRELLIS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BIRCH_TRELLIS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.JUNGLE_TRELLIS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ACACIA_TRELLIS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DARK_OAK_TRELLIS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANGROVE_TRELLIS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHERRY_TRELLIS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRIMSON_TRELLIS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WARPED_TRELLIS, RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BAMBOO_TRELLIS, RenderType.cutout());
	}
}
