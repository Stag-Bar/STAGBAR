package edu.nku.CSC440.stagbar.dataaccess.mock;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrink;

import java.util.HashMap;

public class MixedDrinkMock {
	public static final MixedDrink FIREBULL = new MixedDrink("Firebull", new HashMap<Alcohol, Double>() {{
		put(AlcoholMock.WHISKEY, 2.0);
	}}, null);

	public static final MixedDrink LONG_ISLAND = new MixedDrink("Long Island Iced Tea", new HashMap<Alcohol, Double>() {{
		put(AlcoholMock.VODKA, 1.0);
	}}, null);
}
