package edu.nku.CSC440.stagbar.dataaccess;

public class MixedDrinkIngredient {
	private Alcohol alcohol;
	private double amount;

	public MixedDrinkIngredient(Alcohol alcohol, double amount) {
		if(null == alcohol) throw new IllegalArgumentException("Alcohol cannot be null.");
		this.alcohol = alcohol;
		this.amount = amount;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		MixedDrinkIngredient that = (MixedDrinkIngredient)o;

		return getAlcohol().equals(that.getAlcohol());
	}

	public Alcohol getAlcohol() {
		return alcohol;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return getAlcohol().hashCode();
	}

	@Override
	public String toString() {
		return "MixedDrinkIngredient{" +
				"alcohol=" + alcohol +
				", amount=" + amount +
				'}';
	}
}
