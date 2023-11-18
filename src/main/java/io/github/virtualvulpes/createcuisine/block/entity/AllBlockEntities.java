package io.github.virtualvulpes.createcuisine.block.entity;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.basin.BasinRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import io.github.virtualvulpes.createcuisine.block.AllBlocks;
import io.github.virtualvulpes.createcuisine.block.renderer.FermentationVatRenderer;

import static io.github.virtualvulpes.createcuisine.CreateCuisine.REGISTRATE;

public class AllBlockEntities {

	public static final BlockEntityEntry<FermentationVatBlockEntity> FERMENTATION_VAT = REGISTRATE
			.blockEntity("fermentation_vat", FermentationVatBlockEntity::new)
			.validBlocks(AllBlocks.FERMENTATION_VAT)
			.renderer(() -> FermentationVatRenderer::new)
			.register();


	public static void register(){

	}
}
