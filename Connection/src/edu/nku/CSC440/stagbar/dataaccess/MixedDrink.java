package edu.nku.CSC440.stagbar.dataaccess;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MixedDrink {
	private Map<MixedDrinkIngredient, MixedDrinkIngredient> ingredients;
	private String name;
	private Map<MixedDrinkIngredient, MixedDrinkIngredient> previousIngredients;
	private LocalDate retireDate;

	/** Used when creating new drink. */
	public MixedDrink(String name, Set<MixedDrinkIngredient> ingredients) {
		this.name = name;
		this.ingredients = mapFromSet(ingredients);
		previousIngredients = null;
		retireDate = null;
	}

	/** Used when loading drink. */
	public MixedDrink(String name, Set<MixedDrinkIngredient> ingredientsStoredInDatabase, LocalDate retireDate) {
		this(name, ingredientsStoredInDatabase);
		this.retireDate = retireDate;
		previousIngredients = mapFromSet(ingredientsStoredInDatabase);
	}

	private static Map<MixedDrinkIngredient, MixedDrinkIngredient> mapFromSet(Set<MixedDrinkIngredient> set) {
		Map<MixedDrinkIngredient, MixedDrinkIngredient> map = new HashMap<>(set.size());
		for(MixedDrinkIngredient ingredient : set) {
			map.put(ingredient, ingredient);
		}
		return map;
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

	public LocalDate getRetireDate() {
		return retireDate;
	}

	public void setRetireDate(LocalDate retireDate) {
		this.retireDate = retireDate;
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

	@Override
	public String toString() {
		return "MixedDrink{" +
				"name='" + name + '\'' +
				", ingredients=" + getIngredients() +
				", retireDate=" + retireDate +
				'}';
	}

}
