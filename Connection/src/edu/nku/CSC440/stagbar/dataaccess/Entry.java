package edu.nku.CSC440.stagbar.dataaccess;

import java.time.LocalDate;

/**
 * This class is used to store one row of the Inventory, Delivery or Sales tables.
 */
public class Entry {

	/** Unique identifier for each alcohol. Foreign key to alcohol table. */
	private int alcoholId;
	/** Amount in ounces. */
	private double amount;
	private int bottles;
	/** Date of this entry. */
	private LocalDate date;

	public Entry(int alcoholId, double amount, int bottles, LocalDate date) {
		this.alcoholId = alcoholId;
		this.amount = amount;
		this.bottles = bottles;
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Entry entry = (Entry)o;

		if(alcoholId != entry.alcoholId) return false;
		return date.equals(entry.date);
	}

	public int getAlcoholId() {
		return alcoholId;
	}

	public void setAlcoholId(int alcoholId) {
		this.alcoholId = alcoholId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getBottles() {
		return bottles;
	}

	public void setBottles(int bottles) {
		this.bottles = bottles;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		int result = alcoholId;
		result = 31 * result + date.hashCode();
		return result;
	}
}
