package edu.nku.CSC440.stagbar.dataaccess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MixedDrink {
	private final String name;
	private Map<MixedDrinkIngredient, MixedDrinkIngredient> ingredients;
	private boolean isRetired;
	private Map<MixedDrinkIngredient, MixedDrinkIngredient> previousIngredients;

	/** Used when creating new drink. */
	public MixedDrink(String name, Set<MixedDrinkIngredient> ingredients) {
		this.name = name;
		this.ingredients = mapFromSet(ingredients);
		previousIngredients = null;
		isRetired = false;
	}

	/** Used when loading drink. */
	public MixedDrink(String name, boolean isRetired) {
		this(name, new HashSet<>());
		this.isRetired = isRetired;
		previousIngredients = new HashMap<>();
	}

	private static Map<MixedDrinkIngredient, MixedDrinkIngredient> mapFromSet(Set<MixedDrinkIngredient> set) {
		Map<MixedDrinkIngredient, MixedDrinkIngredient> map = new HashMap<>(set.size());
		for(MixedDrinkIngredient ingredient : set) {
			map.put(ingredient, ingredient);
		}
		return map;
	}

	/** Populates ingredient collection with ingredients stored in database. FOR USE IN Connect.java ONLY! */
	public void addIngredientFromDatabase(MixedDrinkIngredient ingredient) {
		ingredients.put(ingredient, ingredient);
		previousIngredients.put(ingredient, ingredient);
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		MixedDrink that = (MixedDrink)o;

		return getName().equals(that.getName());
	}

	public Set<MixedDrinkIngredient> getIngredients() {
		return ingredients.keySet();
	}

	public void setIngredients(Set<MixedDrinkIngredient> ingredients) {
		this.ingredients = mapFromSet(ingredients);
	}

	public String getName() {
		return name;
	}

	public Set<MixedDrinkIngredient> getNewIngredients() {
		if(null == previousIngredients) return ingredients.keySet();

		Set<MixedDrinkIngredient> newIngredients = new HashSet<>();

		for(MixedDrinkIngredient ingredient : ingredients.keySet()) {
			if(!previousIngredients.containsKey(ingredient)) {
				newIngredients.add(ingredient);
			}
		}

		return newIngredients;
	}

	public Set<MixedDrinkIngredient> getRemovedIngredients() {
		Set<MixedDrinkIngredient> removedIngredients = new HashSet<>();

		if(null != previousIngredients) {
			for(MixedDrinkIngredient ingredient : previousIngredients.keySet()) {
				if(!ingredients.containsKey(ingredient)) {
					removedIngredients.add(ingredient);
				}
			}
		}

		return removedIngredients;
	}

	public Set<MixedDrinkIngredient> getUpdatedIngredients() {
		Set<MixedDrinkIngredient> updatedIngredients = new HashSet<>();

		if(null != previousIngredients) {
			for(MixedDrinkIngredient ingredient : ingredients.keySet()) {
				if(previousIngredients.containsKey(ingredient) && previousIngredients.get(ingredient).getAmount() != ingredients.get(ingredient).getAmount()) {
					updatedIngredients.add(ingredient);
				}
			}
		}

		return updatedIngredients;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	public boolean isRetired() {
		return isRetired;
	}

	public void setIsRetired(boolean isRetired) {
		this.isRetired = isRetired;
	}

	@Override
	public String toString() {
		return "MixedDrink{" +
				"name='" + name + '\'' +
				", ingredients=" + getIngredients() +
				", isRetired=" + isRetired +
				'}';
	}

}
