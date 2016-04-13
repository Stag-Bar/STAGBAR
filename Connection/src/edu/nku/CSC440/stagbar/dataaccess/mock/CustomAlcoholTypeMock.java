package edu.nku.CSC440.stagbar.dataaccess.mock;

import edu.nku.CSC440.stagbar.dataaccess.AlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;

public class CustomAlcoholTypeMock {
	public static final CustomAlcoholType CRAFT_BEER = new CustomAlcoholType(2, "Craft Beer", AlcoholType.BOTTLED);
	public static final CustomAlcoholType DOMESTIC_BEER = new CustomAlcoholType(1, "Domestic Beer", AlcoholType.BOTTLED);
	public static final CustomAlcoholType DRAFT_BEER = new CustomAlcoholType(3, "Draft Beer", AlcoholType.DRAFT);
	public static final CustomAlcoholType VODKA = new CustomAlcoholType(5, "Vodka", AlcoholType.SHELF);
	public static final CustomAlcoholType WHISKEY = new CustomAlcoholType(4, "Whiskey", AlcoholType.SHELF);

	private CustomAlcoholTypeMock() {}
}
