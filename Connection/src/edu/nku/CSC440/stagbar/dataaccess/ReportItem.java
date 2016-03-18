package edu.nku.CSC440.stagbar.dataaccess;

public class ReportItem {

	private Alcohol alcohol;
	private double amountCurrent;
	private double amountDelivered;
	private double amountPrevious;
	private double amountSold;
	private int bottlesCurrent;
	private int bottlesDelivered;
	private int bottlesPrevious;
	private int bottlesSold;

	public ReportItem(Alcohol alcohol) {
		if(null == alcohol) throw new IllegalArgumentException("Alcohol cannot be null.");
		this.alcohol = alcohol;
	}

	public ReportItem(Alcohol alcohol, double amountCurrent, double amountDelivered, double amountPrevious, double amountSold, int bottlesCurrent, int bottlesDelivered, int bottlesPrevious, int bottlesSold) {
		this(alcohol);
		this.amountCurrent = amountCurrent;
		this.amountDelivered = amountDelivered;
		this.amountPrevious = amountPrevious;
		this.amountSold = amountSold;
		this.bottlesCurrent = bottlesCurrent;
		this.bottlesDelivered = bottlesDelivered;
		this.bottlesPrevious = bottlesPrevious;
		this.bottlesSold = bottlesSold;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		ReportItem that = (ReportItem)o;

		return getAlcohol().equals(that.getAlcohol());
	}

	public Alcohol getAlcohol() {
		return alcohol;
	}

	public double getAmountCurrent() {
		return amountCurrent;
	}

	public void setAmountCurrent(double amountCurrent) {
		this.amountCurrent = amountCurrent;
	}

	public double getAmountDelivered() {
		return amountDelivered;
	}

	public void setAmountDelivered(double amountDelivered) {
		this.amountDelivered = amountDelivered;
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

	public int getBottlesDelivered() {
		return bottlesDelivered;
	}

	public void setBottlesDelivered(int bottlesDelivered) {
		this.bottlesDelivered = bottlesDelivered;
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

	/** Calculates discrepancy for amount. */
	public double getDiscrepency_Amount() {
		return amountPrevious - (amountCurrent - amountDelivered + amountSold);
	}

	/** Calculates discrepancy for bottles. */
	public int getDiscrepency_Bottles() {
		return bottlesPrevious - (bottlesCurrent - bottlesDelivered + bottlesSold);
	}

	@Override
	public int hashCode() {
		return getAlcohol().hashCode();
	}
}
