package edu.nku.CSC440.stagbar.dataaccess.data;

public class MixedDrinkBuilder {
	MixedDrink mixedDrink;

	public MixedDrinkBuilder(String name, boolean isRetired) {
		mixedDrink = new MixedDrink(name, isRetired);
	}

	public MixedDrinkBuilder addIngredientFromDatabase(MixedDrinkIngredient ingredient) {
		mixedDrink.addIngredientFromDatabase(ingredient);
		return this;
	}

	public MixedDrink build() {
		return mixedDrink;
	}

}
