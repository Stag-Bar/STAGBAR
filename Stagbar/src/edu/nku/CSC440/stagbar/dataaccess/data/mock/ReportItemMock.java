package edu.nku.CSC440.stagbar.dataaccess.data.mock;

import edu.nku.CSC440.stagbar.dataaccess.data.ReportItem;
import edu.nku.CSC440.stagbar.dataaccess.data.ReportItemBuilder;

public class ReportItemMock {
	public static final ReportItem BUDWEISER = new ReportItemBuilder(AlcoholMock.BUDWEISER)
			.setBottlesPrevious(12)
			.setBottlesCurrent(24)
			.setBottlesDelivered(24)
			.setBottlesSold(12)
			.build();
	public static final ReportItem MILLER_LITE = new ReportItemBuilder(AlcoholMock.MILLER_LITE)
			.setBottlesPrevious(24)
			.setBottlesCurrent(18)
			.setBottlesDelivered(48)
			.setBottlesSold(52)
			.build();
	public static final ReportItem WHISKEY = new ReportItemBuilder(AlcoholMock.WHISKEY)
			.setBottlesPrevious(3)
			.setBottlesCurrent(3)
			.setBottlesDelivered(0)
			.setBottlesSold(0)
			.setAmountPrevious(12)
			.setAmountCurrent(8)
			.setAmountDelivered(0)
			.setAmountSold(4)
			.build();
}
