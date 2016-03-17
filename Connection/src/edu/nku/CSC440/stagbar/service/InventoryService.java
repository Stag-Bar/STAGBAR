package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.AlcoholType;

public class InventoryService {

	private static final InventoryService inventoryService = new InventoryService();

	private InventoryService() {}

	public static InventoryService getInstance() {
		return inventoryService;
	}

	public static boolean isNameUnique(String name) {
		return null == Connect.getInstance().findAlcoholByName(name);
	}

	public boolean saveNewAlcohol(String name, AlcoholType type, int bottles, double amount) {
		Alcohol newAlcohol = new Alcohol(name, type, bottles, amount);

		//TODO: save to database
		return Connect.getInstance().saveAlcohol(newAlcohol);
	}
}
