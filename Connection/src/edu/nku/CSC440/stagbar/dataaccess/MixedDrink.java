package edu.nku.CSC440.stagbar.dataaccess;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MixedDrink {
	private Map<Alcohol, Double> ingredients;
	private String name;
	private Map<Alcohol, Double> previousIngredients;
	private LocalDate retireDate;

	/** Used when creating new drink. */
	public MixedDrink(String name) {
		this.name = name;
		ingredients = new HashMap<>();
		previousIngredients = null;
	}

	/** Used when loading drink. */
	public MixedDrink(String name, Map<Alcohol, Double> ingredientsStoredInDatabase, LocalDate retireDate) {
		this.name = name;
		this.retireDate = retireDate;
		previousIngredients = ingredientsStoredInDatabase;
		ingredients = new HashMap<>(ingredientsStoredInDatabase);
	}

	public void addIngredient(Alcohol alcohol, double amount) {
		ingredients.put(alcohol, amount);
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		MixedDrink that = (MixedDrink)o;

		return getName().equals(that.getName());
	}

	public Map<Alcohol, Double> getIngredients() {
		return ingredients;
	}

	public String getName() {
		return name;
	}

	public Map<Alcohol, Double> getNewIngredients() {
		if(null == previousIngredients) return ingredients;

		Map<Alcohol, Double> newIngredients = new HashMap<>();

		for(Alcohol alcohol : ingredients.keySet()) {
			if(!previousIngredients.containsKey(alcohol)) {
				newIngredients.put(alcohol, ingredients.get(alcohol));
			}
		}

		return newIngredients;
	}

	public Map<Alcohol, Double> getRemovedIngredients() {
		Map<Alcohol, Double> removedIngredients = new HashMap<>();

		if(null != previousIngredients) {
			for(Alcohol alcohol : previousIngredients.keySet()) {
				if(!ingredients.containsKey(alcohol)) {
					removedIngredients.put(alcohol, previousIngredients.get(alcohol));
				}
			}
		}

		return removedIngredients;
	}

	public LocalDate getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(LocalDate retireDate) {
		this.retireDate = retireDate;
	}

	public Map<Alcohol, Double> getUpdatedIngredients() {
		Map<Alcohol, Double> updatedIngredients = new HashMap<>();

		if(null != previousIngredients) {
			for(Alcohol alcohol : ingredients.keySet()) {
				if(previousIngredients.containsKey(alcohol) && !Objects.equals(previousIngredients.get(alcohol), ingredients.get(alcohol))) {
					updatedIngredients.put(alcohol, ingredients.get(alcohol));
				}
			}
		}

		return updatedIngredients;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	public void removeIngredient(Alcohol alcohol) {
		ingredients.remove(alcohol);
	}

	public void updateIngredient(Alcohol alcohol, double amount) {
		ingredients.put(alcohol, amount);
	}

}
