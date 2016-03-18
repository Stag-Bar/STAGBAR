package edu.nku.CSC440.stagbar.dataaccess;

public class Alcohol {

	private double amountCurrent;
	private double amountPrevious;
	private double amountSold;
	private int bottlesCurrent;
	private int bottlesPrevious;
	private int bottlesSold;
	private String name;
	private AlcoholType type;

	public Alcohol(String name, AlcoholType type) {
		this.name = name;
		this.type = type;
	}

	public Alcohol(String name, AlcoholType type, int bottlesCurrent, double amountCurrent) {
		this.amountCurrent = amountCurrent;
		this.bottlesCurrent = bottlesCurrent;
		this.name = name;
		this.type = type;
	}

	public Alcohol(String name, AlcoholType type, int bottlesCurrent, double amountCurrent, int bottlesPrevious, double amountPrevious) {
		this.amountCurrent = amountCurrent;
		this.bottlesCurrent = bottlesCurrent;
		this.name = name;
		this.amountPrevious = amountPrevious;
		this.bottlesPrevious = bottlesPrevious;
		this.type = type;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Alcohol alcohol = (Alcohol)o;

		if(!getName().equals(alcohol.getName())) return false;
		return getType() == alcohol.getType();

	}

	public double getAmountCurrent() {
		return amountCurrent;
	}

	public void setAmountCurrent(double amountCurrent) {
		this.amountCurrent = amountCurrent;
	}

	public double getAmountPrevious() {
		return amountPrevious;
	}

	public void setAmountPrevious(double amountPrevious) {
		this.amountPrevious = amountPrevious;
	}

	public double getAmountSold() {
		return amountSold;
	}

	public void setAmountSold(double amountSold) {
		this.amountSold = amountSold;
	}

	public int getBottlesCurrent() {
		return bottlesCurrent;
	}

	public void setBottlesCurrent(int bottlesCurrent) {
		this.bottlesCurrent = bottlesCurrent;
	}

	public int getBottlesPrevious() {
		return bottlesPrevious;
	}

	public void setBottlesPrevious(int bottlesPrevious) {
		this.bottlesPrevious = bottlesPrevious;
	}

	public int getBottlesSold() {
		return bottlesSold;
	}

	public void setBottlesSold(int bottlesSold) {
		this.bottlesSold = bottlesSold;
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

	@Override
	public int hashCode() {
		int result = getName().hashCode();
		result = 31 * result + getType().hashCode();
		return result;
	}
}
