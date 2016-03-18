package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.Entry;

import java.time.LocalDate;
import java.util.Set;

public class InventoryService {

	private static final InventoryService inventoryService = new InventoryService();

	private InventoryService() {}

	public static InventoryService getInstance() {
		return inventoryService;
	}

	public static boolean isNameUnique(String name) {
		return null == Connect.getInstance().findAlcoholByName(name);
	}

	public boolean retireAlcohol(Alcohol alcohol) {
		return Connect.getInstance().retireAlcohol(alcohol.getAlcoholId(), LocalDate.now());
	}

	public boolean saveDeliveries(Set<Entry> deliveryEntries) {
		for(Entry entry : deliveryEntries)
			Connect.getInstance().saveDeliveryEntry(entry);
		return true;
	}

	public boolean saveInventory(Set<Entry> inventoryEntries) {
		for(Entry entry : inventoryEntries)
			Connect.getInstance().saveInventoryEntry(entry);
		return true;
	}

	//TODO: Should we allow initial amounts to be set for alcohol or wait until inventory is done???
	public boolean saveNewAlcohol(String name, CustomAlcoholType type, int bottles, double amount) {
		Alcohol newAlcohol = new Alcohol(Alcohol.NEW_ALCOHOL_ID, name, type, null);

		return Connect.getInstance().saveAlcohol(newAlcohol);
	}

	public boolean saveSales(Set<Entry> salesEntries) {
		for(Entry entry : salesEntries)
			Connect.getInstance().saveSalesEntry(entry);
		return true;
	}
}
