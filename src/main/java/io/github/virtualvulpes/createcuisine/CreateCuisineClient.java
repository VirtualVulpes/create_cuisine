package io.github.virtualvulpes.createcuisine;

import com.simibubi.create.foundation.ClientResourceReloadListener;

import io.github.virtualvulpes.createcuisine.block.AllBlocks;
import io.github.virtualvulpes.createcuisine.events.ClientEvents;
import io.github.virtualvulpes.createcuisine.fluid.AllFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class CreateCuisineClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.OAK_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.SPRUCE_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.BIRCH_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.JUNGLE_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.ACACIA_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.DARK_OAK_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.MANGROVE_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.CHERRY_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.CRIMSON_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.WARPED_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.BAMBOO_TRELLIS.get(), RenderType.cutout());
		BlockRenderLayerMap.INSTANCE.putBlock(AllBlocks.GRAPE_VINES.get(), RenderType.cutout());

		ClientEvents.register();
	}
}
