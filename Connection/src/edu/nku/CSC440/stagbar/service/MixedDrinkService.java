package edu.nku.CSC440.stagbar.service;

public class MixedDrinkService {

	private static final MixedDrinkService mixedDrinkService = new MixedDrinkService();

	private MixedDrinkService() {}

	public static MixedDrinkService getInstance() {
		return mixedDrinkService;
	}

	//TODO: Add methods for handling mixed drinks.

}
