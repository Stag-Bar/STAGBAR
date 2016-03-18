package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrink;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MixedDrinkService {

	private static final MixedDrinkService MIXED_DRINK_SERVICE = new MixedDrinkService();

	private MixedDrinkService() {}

	public static MixedDrinkService getInstance() {
		return MIXED_DRINK_SERVICE;
	}

	/**
	 * Checks database for given drink.
	 *
	 * @param drinkName Drink to check database for.
	 * @return <code>true</code> if given drink is in the database,
	 * <code>false</code> otherwise.
	 */
	public boolean doesDrinkExist(String drinkName) {
		return Connect.getInstance().doesDrinkExist(drinkName);
	}

	public Set<String> getAllMixedDrinkNames() {
		Set<String> results = new HashSet<>();
		for(MixedDrink mixedDrink : getAllMixedDrinks()) {
			results.add(mixedDrink.getName());
		}

		return results;
	}

	public Set<MixedDrink> getAllMixedDrinks() {
		return Connect.getInstance().findAllMixedDrinksAndIngredients();
	}

	public boolean retireMixedDrink(MixedDrink mixedDrink) {
		return Connect.getInstance().retireMixedDrink(mixedDrink.getName(), LocalDate.now());
	}

	public boolean saveMixedDrink(MixedDrink mixedDrink) {
		// Save drink.
		if(Connect.getInstance().createMixedDrink(mixedDrink.getName())) {
			// Save all ingredients.
			for(Map.Entry<Alcohol, Double> ingredientEntry : mixedDrink.getIngredients().entrySet()) {
				Connect.getInstance().createMixedDrinkIngredient(mixedDrink.getName(), ingredientEntry.getKey().getAlcoholId(), ingredientEntry.getValue());
			}
		}
		else { return false; }

		return true;
	}

	public boolean updateMixedDrink(MixedDrink mixedDrink) {
		// Save all NEW ingredients.
		for(Map.Entry<Alcohol, Double> ingredientEntry : mixedDrink.getNewIngredients().entrySet()) {
			Connect.getInstance().createMixedDrinkIngredient(mixedDrink.getName(), ingredientEntry.getKey().getAlcoholId(), ingredientEntry.getValue());
		}

		// Update ingredients whose amount has been changed.
		for(Map.Entry<Alcohol, Double> ingredientEntry : mixedDrink.getUpdatedIngredients().entrySet()) {
			Connect.getInstance().updateMixedDrinkIngredient(mixedDrink.getName(), ingredientEntry.getKey().getAlcoholId(), ingredientEntry.getValue());
		}

		// Delete removed ingredients.
		for(Map.Entry<Alcohol, Double> ingredientEntry : mixedDrink.getRemovedIngredients().entrySet()) {
			Connect.getInstance().deleteMixedDrinkIngredient(mixedDrink.getName(), ingredientEntry.getKey().getAlcoholId());
		}

		return true;
	}

}
