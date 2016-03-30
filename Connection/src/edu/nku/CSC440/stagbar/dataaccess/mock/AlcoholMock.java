package edu.nku.CSC440.stagbar.dataaccess.mock;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AlcoholMock {

	public static final Alcohol AMERBOCK_DRAFT = new Alcohol(5, "Amerbock", CustomAlcoholTypeMock.DRAFT_BEER, LocalDate.now(), null);
	public static final Alcohol BUDWEISER = new Alcohol(8, "Budweiser", CustomAlcoholTypeMock.DOMESTIC_BEER, LocalDate.now(), null);
	public static final Alcohol BUD_LITE = new Alcohol(6, "Bud Lite", CustomAlcoholTypeMock.DOMESTIC_BEER, LocalDate.now(), null);
	public static final Alcohol BUD_LITE_DRAFT = new Alcohol(4, "Bud Lite", CustomAlcoholTypeMock.DRAFT_BEER, LocalDate.now(), null);
	public static final Alcohol GROG = new Alcohol(18, "Grog", CustomAlcoholTypeMock.DRAFT_BEER, LocalDate.now().minus(2, ChronoUnit.YEARS), LocalDate.now().minus(1, ChronoUnit.YEARS));
	public static final Alcohol JACK_DANIELS = new Alcohol(14, "Jack Daniel's", CustomAlcoholTypeMock.WHISKEY, LocalDate.now(), null);
	public static final Alcohol JAMESON = new Alcohol(15, "Jameson", CustomAlcoholTypeMock.WHISKEY, LocalDate.now(), null);
	public static final Alcohol MAGIC_HAT = new Alcohol(10, "Magic Hat #9", CustomAlcoholTypeMock.CRAFT_BEER, LocalDate.now(), null);
	public static final Alcohol MICH_ULTRA = new Alcohol(9, "Mich. Ultra", CustomAlcoholTypeMock.DOMESTIC_BEER, LocalDate.now(), null);
	public static final Alcohol MILLER_LITE = new Alcohol(7, "Miller Lite", CustomAlcoholTypeMock.DOMESTIC_BEER, LocalDate.now(), null);
	public static final Alcohol MILLER_LITE_DRAFT = new Alcohol(3, "Miller Lite", CustomAlcoholTypeMock.DRAFT_BEER, LocalDate.now(), null);
	public static final Alcohol SWILL = new Alcohol(17, "Swill", CustomAlcoholTypeMock.DRAFT_BEER, LocalDate.now().minus(2, ChronoUnit.YEARS), LocalDate.now().minus(1, ChronoUnit.YEARS));
	public static final Alcohol TWISTED_TEA_CAN = new Alcohol(11, "Twisted Tea Can", CustomAlcoholTypeMock.CRAFT_BEER, LocalDate.now(), null);
	public static final Alcohol VODKA = new Alcohol(12, "Vodka", CustomAlcoholTypeMock.VODKA, LocalDate.now(), null);
	public static final Alcohol WHISKEY = new Alcohol(13, "Whiskey/Bourbon", CustomAlcoholTypeMock.WHISKEY, LocalDate.now(), null);
	public static final Alcohol WOODFORD_RESERVE = new Alcohol(16, "Woodford Reserve", CustomAlcoholTypeMock.WHISKEY, LocalDate.now(), null);

	private AlcoholMock() {}
}
