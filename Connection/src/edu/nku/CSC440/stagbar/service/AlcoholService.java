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

	public Set<Alcohol> getAlcoholByType(CustomAlcoholType type, LocalDate endDate) {
		return Connect.getInstance().findActiveAlcoholByType(type, LocalDate.ofYearDay(1970, 1), endDate);
	}

	public boolean isAlcoholNameUnique(String name, CustomAlcoholType selectedItem) {
		//FIXME: Alcohol should be unique by name AND type.
		return null == Connect.getInstance().findAlcoholByName(name);
	}

	public boolean retireAlcohol(Alcohol alcohol) {
		return Connect.getInstance().retireAlcohol(alcohol.getAlcoholId(), LocalDate.now());
	}

	public boolean saveDeliveries(Set<Entry> deliveryEntries) {
		boolean entryFailed = false;

		for(Entry entry : deliveryEntries)
			entryFailed |= !Connect.getInstance().saveDeliveryEntry(entry);

		return entryFailed;
	}

	public boolean saveInventory(Set<Entry> inventoryEntries) {
		boolean entryFailed = false;

		for(Entry entry : inventoryEntries)
			entryFailed |= !Connect.getInstance().saveInventoryEntry(entry);

		return entryFailed;
	}

	public boolean saveNewAlcohol(String name, CustomAlcoholType type) {
		Alcohol newAlcohol = new Alcohol(Alcohol.NEW_ALCOHOL_ID, name, type, LocalDate.now(), null);

		return Connect.getInstance().saveAlcohol(newAlcohol);
	}

	public boolean saveSales(Set<Entry> salesEntries) {
		boolean entryFailed = false;

		for(Entry entry : salesEntries)
			entryFailed |= !Connect.getInstance().saveSalesEntry(entry);

		return entryFailed;
	}
}
