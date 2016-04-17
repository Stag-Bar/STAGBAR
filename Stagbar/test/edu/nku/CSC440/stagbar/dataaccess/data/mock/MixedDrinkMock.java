package edu.nku.CSC440.stagbar.dataaccess.data.mock;

import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrinkBuilder;
import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrinkIngredient;

public class MixedDrinkMock {
	public static final MixedDrink FIREBULL = new MixedDrinkBuilder("Firebull", false)
			.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 2.0))
			.build();

	public static final MixedDrink J_AND_J = new MixedDrinkBuilder("J&J", false)
			.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JACK_DANIELS, 1.5))
			.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JAMESON, 1.5))
			.build();

	public static final MixedDrink LONG_ISLAND = new MixedDrinkBuilder("Long Island Iced Tea", false)
			.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.VODKA, 1.0))
			.build();

	public static final MixedDrink MARTINI = new MixedDrinkBuilder("Martini", false)
			.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.VODKA, 2.0))
			.build();
}
