package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.Entry;

import java.time.LocalDate;
import java.util.Set;

public class AlcoholService {

	private static final AlcoholService ALCOHOL_SERVICE = new AlcoholService();

	private AlcoholService() {}

	public static AlcoholService getInstance() {
		return ALCOHOL_SERVICE;
	}

	public Set<Alcohol> getAlcoholByType(CustomAlcoholType type, LocalDate startDate, LocalDate endDate) {
		return Connect.getInstance().findActiveAlcoholByType(type, startDate, endDate);
	}

	public boolean isAlcoholNameUnique(String name, CustomAlcoholType selectedItem) {
		//FIXME: Alcohol should be unique by name AND type.
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
		Alcohol newAlcohol = new Alcohol(Alcohol.NEW_ALCOHOL_ID, name, type, LocalDate.now(), null);

		return Connect.getInstance().saveAlcohol(newAlcohol);
	}

	public boolean saveSales(Set<Entry> salesEntries) {
		for(Entry entry : salesEntries)
			Connect.getInstance().saveSalesEntry(entry);
		return true;
	}
}
