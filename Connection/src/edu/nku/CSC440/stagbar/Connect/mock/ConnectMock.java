package edu.nku.CSC440.stagbar.Connect.mock;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.mock.AlcoholMock;
import edu.nku.CSC440.stagbar.dataaccess.mock.CustomAlcoholTypeMock;
import edu.nku.CSC440.stagbar.dataaccess.mock.MixedDrinkMock;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ConnectMock {

	private ConnectMock() {}

	public static Set<Alcohol> findActiveAlcoholByType(CustomAlcoholType type) {
		return findActiveAlcoholByType(type, LocalDate.ofEpochDay(0), LocalDate.now());
	}

	public static Set<Alcohol> findActiveAlcoholByType(CustomAlcoholType type, LocalDate startDate, LocalDate endDate) {
		Set<Alcohol> results = new HashSet<>();
		for(Alcohol alcohol : findActiveAlcoholForDateRange(startDate, endDate)) {
			if(alcohol.getType().equals(type)) {
				results.add(alcohol);
			}
		}
		return results;
	}

	/** Searches for alcohol whose retire date is null or after the start date & whose creation date is before the end date. */
	public static Set<Alcohol> findActiveAlcoholForDateRange(LocalDate startDate, LocalDate endDate) {
		Set<Alcohol> results = new HashSet<>();
		for(Alcohol alcohol : findAllAlcohol()) {
			if(null == alcohol.getRetireDate() || alcohol.getRetireDate().isEqual(startDate) || alcohol.getRetireDate().isAfter(startDate)) {
				if(alcohol.getCreationDate().isEqual(endDate) || alcohol.getCreationDate().isBefore(endDate)) {
					results.add(alcohol);
				}
			}
		}
		return results;
	}

	public static Set<Alcohol> findAllAlcohol() {
		Set<Alcohol> results = new HashSet<>();
		results.add(AlcoholMock.MILLER_LITE_DRAFT);
		results.add(AlcoholMock.BUD_LITE_DRAFT);
		results.add(AlcoholMock.AMERBOCK_DRAFT);
		results.add(AlcoholMock.BUD_LITE);
		results.add(AlcoholMock.MILLER_LITE);
		results.add(AlcoholMock.BUDWEISER);
		results.add(AlcoholMock.MICH_ULTRA);
		results.add(AlcoholMock.MAGIC_HAT);
		results.add(AlcoholMock.TWISTED_TEA_CAN);
		results.add(AlcoholMock.VODKA);
		results.add(AlcoholMock.WHISKEY);
		results.add(AlcoholMock.JACK_DANIELS);
		results.add(AlcoholMock.JAMESON);
		results.add(AlcoholMock.WOODFORD_RESERVE);
		results.add(AlcoholMock.GROG);
		results.add(AlcoholMock.SWILL);

		return results;
	}

	public static Set<CustomAlcoholType> findAllCustomAlcoholTypes() {
		Set<CustomAlcoholType> results = new HashSet<>();
		results.add(CustomAlcoholTypeMock.DOMESTIC_BEER);
		results.add(CustomAlcoholTypeMock.CRAFT_BEER);
		results.add(CustomAlcoholTypeMock.DRAFT_BEER);
		results.add(CustomAlcoholTypeMock.WHISKEY);
		results.add(CustomAlcoholTypeMock.VODKA);

		return results;
	}

	public static Set<MixedDrink> findAllMixedDrinksAndIngredients() {
		Set<MixedDrink> results = new HashSet<>();
		results.add(MixedDrinkMock.FIREBULL);
		results.add(MixedDrinkMock.LONG_ISLAND);
		return results;
	}
}
