package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrinkIngredient;

import java.time.LocalDate;
import java.util.HashSet;
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

	public boolean isDrinkNameUnique(String name) {
		return !getAllMixedDrinkNames().contains(name);
	}

	public boolean retireMixedDrink(MixedDrink mixedDrink, boolean isRetired) {
		return Connect.getInstance().retireMixedDrink(mixedDrink.getName(), isRetired ? LocalDate.now() : null);
	}

	/** Saves a new Mixed Drink to the database. */
	public boolean saveMixedDrink(MixedDrink mixedDrink) {
		// Save drink.
		if(Connect.getInstance().saveMixedDrink(mixedDrink.getName())) {
			// Save all ingredients.
			for(MixedDrinkIngredient ingredient : mixedDrink.getIngredients()) {
				Connect.getInstance().saveMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount());
			}
		}
		else { return false; }

		return true;
	}

	public boolean updateMixedDrink(MixedDrink mixedDrink) {
		boolean updateFailed = false;

		// Save all NEW ingredients.
		for(MixedDrinkIngredient ingredient : mixedDrink.getIngredients()) {
			updateFailed |= Connect.getInstance().saveMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount());
		}

		// Update ingredients whose amount has been changed.
		for(MixedDrinkIngredient ingredient : mixedDrink.getUpdatedIngredients()) {
			updateFailed |= Connect.getInstance().updateMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount());
		}

		// Delete removed ingredients.
		for(MixedDrinkIngredient ingredient : mixedDrink.getUpdatedIngredients()) {
			updateFailed |= Connect.getInstance().deleteMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol());
		}

		return updateFailed;
	}

}
