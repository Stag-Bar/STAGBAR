package edu.nku.CSC440.stagbar.dataaccess.mock;

import edu.nku.CSC440.stagbar.dataaccess.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrinkBuilder;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrinkIngredient;

public class MixedDrinkMock {
	public static final MixedDrink FIREBULL = new MixedDrinkBuilder("Firebull", false)
			.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 2.0))
			.build();

	public static final MixedDrink LONG_ISLAND = new MixedDrinkBuilder("Long Island Iced Tea", false)
			.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.VODKA, 1.0))
			.build();

}
