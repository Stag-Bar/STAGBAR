package edu.nku.CSC440.stagbar.dataaccess.mock;

import edu.nku.CSC440.stagbar.dataaccess.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrinkIngredient;

import java.util.HashSet;

public class MixedDrinkMock {
	public static final MixedDrink FIREBULL = new MixedDrink("Firebull", new HashSet<MixedDrinkIngredient>() {{
		add(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 2.0));
	}}, null);

	public static final MixedDrink LONG_ISLAND = new MixedDrink("Long Island Iced Tea", new HashSet<MixedDrinkIngredient>() {{
		add(new MixedDrinkIngredient(AlcoholMock.VODKA, 1.0));
	}}, null);
}
