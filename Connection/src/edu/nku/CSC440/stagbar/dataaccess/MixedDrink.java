package edu.nku.CSC440.stagbar.dataaccess;

import java.util.HashMap;
import java.util.Map;

public class MixedDrink {
	private Map<Alcohol, Double> ingredients;

	public MixedDrink() {
		ingredients = new HashMap<>();
	}

	public void addIngredient(Alcohol alcohol, double amount) {
		ingredients.put(alcohol, amount);
	}

	public void removeIngredient(Alcohol alcohol) {
		ingredients.remove(alcohol);
	}

	public void updateIngredient(Alcohol alcohol, double amount) {

	}

}
