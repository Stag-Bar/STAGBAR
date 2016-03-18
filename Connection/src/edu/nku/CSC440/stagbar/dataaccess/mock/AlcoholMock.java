package edu.nku.CSC440.stagbar.dataaccess.mock;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;

import java.time.LocalDate;

public class AlcoholMock {

	public static final Alcohol AMERBOCK_DRAFT = new Alcohol(3, "Amerbock", CustomAlcoholTypeMock.DRAFT_BEER, LocalDate.now(), null);
	public static final Alcohol BUDWEISER = new Alcohol(6, "Budweiser", CustomAlcoholTypeMock.DOMESTIC_BEER, LocalDate.now(), null);
	public static final Alcohol BUD_LITE = new Alcohol(4, "Bud Lite", CustomAlcoholTypeMock.DOMESTIC_BEER, LocalDate.now(), null);
	public static final Alcohol BUD_LITE_DRAFT = new Alcohol(2, "Bud Lite", CustomAlcoholTypeMock.DRAFT_BEER, LocalDate.now(), null);
	public static final Alcohol GROG = new Alcohol(15, "Grog", CustomAlcoholTypeMock.DRAFT_BEER, LocalDate.MIN, LocalDate.MIN);
	public static final Alcohol JACK_DANIELS = new Alcohol(12, "Jack Daniel's", CustomAlcoholTypeMock.WHISKEY, LocalDate.now(), null);
	public static final Alcohol JAMESON = new Alcohol(13, "Jameson", CustomAlcoholTypeMock.WHISKEY, LocalDate.now(), null);
	public static final Alcohol MAGIC_HAT = new Alcohol(8, "Magic Hat #9", CustomAlcoholTypeMock.CRAFT_BEER, LocalDate.now(), null);
	public static final Alcohol MICH_ULTRA = new Alcohol(7, "Mich. Ultra", CustomAlcoholTypeMock.DOMESTIC_BEER, LocalDate.now(), null);
	public static final Alcohol MILLER_LITE = new Alcohol(5, "Miller Lite", CustomAlcoholTypeMock.DOMESTIC_BEER, LocalDate.now(), null);
	public static final Alcohol MILLER_LITE_DRAFT = new Alcohol(1, "Miller Lite", CustomAlcoholTypeMock.DRAFT_BEER, LocalDate.now(), null);
	public static final Alcohol TWISTED_TEA_CAN = new Alcohol(9, "Twisted Tea Can", CustomAlcoholTypeMock.CRAFT_BEER, LocalDate.now(), null);
	public static final Alcohol VODKA = new Alcohol(10, "Vodka", CustomAlcoholTypeMock.VODKA, LocalDate.now(), null);
	public static final Alcohol WHISKEY = new Alcohol(11, "Whiskey/Bourbon", CustomAlcoholTypeMock.WHISKEY, LocalDate.now(), null);
	public static final Alcohol WOODFORD_RESERVE = new Alcohol(14, "Woodford Reserve", CustomAlcoholTypeMock.WHISKEY, LocalDate.now(), null);

	private AlcoholMock() {}
}
