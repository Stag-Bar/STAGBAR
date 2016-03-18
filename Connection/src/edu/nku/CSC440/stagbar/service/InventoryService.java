package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.AlcoholType;

import java.time.LocalDate;

public class InventoryService {

	private static final InventoryService inventoryService = new InventoryService();
	public static final int NEW_ALCOHOL_ID = -1;

	private InventoryService() {}

	public static InventoryService getInstance() {
		return inventoryService;
	}

	public static boolean isNameUnique(String name) {
		return null == Connect.getInstance().findAlcoholByName(name);
	}


	//TODO: Should we allow initial amounts to be set for alcohol or wait until inventory is done?
	public boolean saveNewAlcohol(String name, AlcoholType type, int bottles, double amount) {
		Alcohol newAlcohol = new Alcohol(NEW_ALCOHOL_ID, name, type, LocalDate.now());

		return Connect.getInstance().saveAlcohol(newAlcohol);
	}
}
