package edu.nku.CSC440.stagbar.dataaccess.data;

public class ReportItemBuilder {
	private Alcohol alcohol;
	private double amountCurrent;
	private double amountDelivered;
	private double amountPrevious;
	private double amountSold;
	private int bottlesCurrent;
	private int bottlesDelivered;
	private int bottlesPrevious;
	private int bottlesSold;

	ReportItemBuilder(Alcohol alcohol) {
		this.alcohol = alcohol;
	}

	public ReportItem build() {
		return new ReportItem(alcohol, amountCurrent, amountDelivered, amountPrevious, amountSold, bottlesCurrent, bottlesDelivered, bottlesPrevious, bottlesSold);
	}

	public ReportItemBuilder setAlcohol(Alcohol alcohol) {
		this.alcohol = alcohol;
		return this;
	}

	public ReportItemBuilder setAmountCurrent(double amountCurrent) {
		this.amountCurrent = amountCurrent;
		return this;
	}

	public ReportItemBuilder setAmountDelivered(double amountDelivered) {
		this.amountDelivered = amountDelivered;
		return this;
	}

	public ReportItemBuilder setAmountPrevious(double amountPrevious) {
		this.amountPrevious = amountPrevious;
		return this;
	}

	public ReportItemBuilder setAmountSold(double amountSold) {
		this.amountSold = amountSold;
		return this;
	}

	public ReportItemBuilder setBottlesCurrent(int bottlesCurrent) {
		this.bottlesCurrent = bottlesCurrent;
		return this;
	}

	public ReportItemBuilder setBottlesDelivered(int bottlesDelivered) {
		this.bottlesDelivered = bottlesDelivered;
		return this;
	}

	public ReportItemBuilder setBottlesPrevious(int bottlesPrevious) {
		this.bottlesPrevious = bottlesPrevious;
		return this;
	}

	public ReportItemBuilder setBottlesSold(int bottlesSold) {
		this.bottlesSold = bottlesSold;
		return this;
	}
}