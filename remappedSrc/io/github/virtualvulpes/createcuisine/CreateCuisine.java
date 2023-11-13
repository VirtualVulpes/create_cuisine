package io.github.virtualvulpes.createcuisine;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class CreateCuisine implements ModInitializer {
	public static final String ID = "createcuisine";
	public static final String NAME = "Create Cuisine";

	@Override
	public void onInitialize() {

	}

	public static Identifier id(String path) {
		return new Identifier(ID, path);
	}
}
