package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.dataaccess.data.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AlcoholService extends BaseService {

	private static final AlcoholService ALCOHOL_SERVICE = new AlcoholService();

	private AlcoholService() {}

	public static AlcoholService getInstance() {
		return ALCOHOL_SERVICE;
	}

	public Set<Alcohol> getAlcoholByType(CustomAlcoholType type, LocalDate endDate) {
		return getDatabase().findActiveAlcoholByType(type, endDate);
	}

	public boolean isAlcoholNameUnique(String name, CustomAlcoholType selectedItem) {
		return getDatabase().doesActiveAlcoholExist(name, selectedItem, LocalDate.now());
	}

	public boolean retireAlcohol(Alcohol alcohol) {
		return getDatabase().retireAlcohol(alcohol.getAlcoholId(), LocalDate.now());
	}

	public boolean saveDeliveries(Set<Entry> deliveryEntries) {
		boolean entryFailed = false;

		for(Entry entry : deliveryEntries)
			entryFailed |= !getDatabase().saveDeliveryEntry(entry);

		return entryFailed;
	}

	public boolean saveInventory(Set<Entry> inventoryEntries) {
		boolean entryFailed = false;

		for(Entry entry : inventoryEntries)
			entryFailed |= !getDatabase().saveInventoryEntry(entry);

		return entryFailed;
	}

	public boolean saveNewAlcohol(String name, CustomAlcoholType type) {
		Alcohol newAlcohol = new Alcohol(Alcohol.NEW_ALCOHOL_ID, name, type, LocalDate.now(), null);

		return getDatabase().saveAlcohol(newAlcohol);
	}

	public boolean saveSales(Set<Entry> salesEntries, Set<DrinkEntry> drinkEntries) {
		Map<Integer, Entry> salesEntryMap = new HashMap<>(salesEntries.size());
		for(Entry salesEntry : salesEntries) {
			salesEntryMap.put(salesEntry.getAlcoholId(), salesEntry);
		}

		for(DrinkEntry drinkEntry : drinkEntries) {
			MixedDrink drink = drinkEntry.getDrink();
			int amountSold = drinkEntry.getAmount();

			for(MixedDrinkIngredient ingredient : drink.getIngredients()) {
				Entry salesEntry = salesEntryMap.get(ingredient.getAlcohol().getAlcoholId());

				if(null == salesEntry) {
					salesEntry = new Entry(ingredient.getAlcohol().getAlcoholId(), 0, 0, LocalDate.now());
					salesEntries.add(salesEntry);
					salesEntryMap.put(ingredient.getAlcohol().getAlcoholId(), salesEntry);
				}

				double amountOfIngredientSold = ingredient.getAmount() * amountSold;
				salesEntry.setAmount(salesEntry.getAmount() + amountOfIngredientSold);
			}
		}

		return saveSales(salesEntries);
	}

	private boolean saveSales(Set<Entry> salesEntries) {
		boolean entryFailed = false;

		for(Entry entry : salesEntries)
			entryFailed |= !getDatabase().saveSalesEntry(entry);

		return entryFailed;
	}
}
