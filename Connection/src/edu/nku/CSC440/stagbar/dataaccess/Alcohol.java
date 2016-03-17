package edu.nku.CSC440.stagbar.dataaccess;

public class Alcohol {

	private double amount;
	private int bottles;
	private String name;
	private AlcoholType type;

	public Alcohol(String name, AlcoholType type, int bottles, double amount) {
		this.amount = amount;
		this.bottles = bottles;
		this.name = name;
		this.type = type;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AlcoholType getType() {
		return type;
	}

	public void setType(AlcoholType type) {
		this.type = type;
	}
}
