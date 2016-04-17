package edu.nku.CSC440.stagbar.dataaccess;

public class DrinkEntry {
	final int amount;
	final MixedDrink drink;

	public DrinkEntry(MixedDrink drink, int amount) {
		this.drink = drink;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public MixedDrink getDrink() {
		return drink;
	}
}
