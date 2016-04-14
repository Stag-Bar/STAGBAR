package edu.nku.CSC440.stagbar.dataaccess.mock;

import edu.nku.CSC440.stagbar.dataaccess.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrinkIngredient;

public class MixedDrinkMock {
	public static final MixedDrink FIREBULL = new MixedDrink("Firebull", false) {{
		addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 2.0));
	}};

	public static final MixedDrink LONG_ISLAND = new MixedDrink("Long Island Iced Tea", false) {{
		addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.VODKA, 1.0));
	}};
}
