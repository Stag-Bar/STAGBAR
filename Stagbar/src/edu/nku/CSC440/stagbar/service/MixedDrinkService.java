package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrinkIngredient;

import java.util.HashSet;
import java.util.Set;

public class MixedDrinkService extends BaseService {

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
		return getDatabase().doesDrinkExist(drinkName);
	}

	public Set<String> getAllMixedDrinkNames() {
		Set<String> results = new HashSet<>();
		for(MixedDrink mixedDrink : getAllMixedDrinks()) {
			results.add(mixedDrink.getName());
		}

		return results;
	}

	public Set<MixedDrink> getAllMixedDrinks() {
		return getDatabase().findAllMixedDrinks();
	}

	public boolean isDrinkNameUnique(String name) {
		return !getAllMixedDrinkNames().contains(name);
	}

	public boolean retireMixedDrink(MixedDrink mixedDrink, boolean isRetired) {
		return getDatabase().retireMixedDrink(mixedDrink.getName(), isRetired);
	}

	/** Saves a new Mixed Drink to the getDatabase(). */
	public boolean saveMixedDrink(MixedDrink mixedDrink) {
		// Save drink.
		if(getDatabase().saveMixedDrink(mixedDrink.getName())) {
			// Save all ingredients.
			for(MixedDrinkIngredient ingredient : mixedDrink.getIngredients()) {
				getDatabase().saveMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount());
			}
		}
		else { return false; }

		return true;
	}

	public boolean updateMixedDrink(MixedDrink mixedDrink) {
		boolean updateFailed = false;

		// Save all NEW ingredients.
		for(MixedDrinkIngredient ingredient : mixedDrink.getNewIngredients()) {
			updateFailed |= getDatabase().saveMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount());
		}

		// Update ingredients whose amount has been changed.
		for(MixedDrinkIngredient ingredient : mixedDrink.getUpdatedIngredients()) {
			updateFailed |= getDatabase().updateMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount());
		}

		// Delete removed ingredients.
		for(MixedDrinkIngredient ingredient : mixedDrink.getRemovedIngredients()) {
			updateFailed |= getDatabase().deleteMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol());
		}

		return updateFailed;
	}

}
