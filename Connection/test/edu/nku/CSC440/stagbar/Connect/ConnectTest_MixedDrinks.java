package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrinkIngredient;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.junit.Assert.*;

public class ConnectTest_MixedDrinks extends ConnectTest {

	@BeforeClass
	public static void populateCustomAlcoholTypesAndAlcohol() {
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			assertTrue("Save of " + type + " failed.", Connect.getInstance().saveCustomAlcoholType(type));
		}

		for(Alcohol alcohol : ConnectMock.findAllAlcohol()) {
			assertTrue("Save failed: " + alcohol.print(), Connect.getInstance().saveAlcohol(alcohol));
		}
	}

	@After
	public void clearTable() {
		assertTrue("Table clear failed.", Connect.getInstance().nukeTable("mixedDrinkIngredients"));
		assertTrue("Table clear failed.", Connect.getInstance().nukeTable("mixedDrink"));
	}

	@Test
	public void testMixedDrink_SaveLoad() {
		for(MixedDrink mixedDrink : ConnectMock.findAllMixedDrinksAndIngredients()) {
			assertTrue("Save failed: " + mixedDrink.toString(), Connect.getInstance().saveMixedDrink(mixedDrink.getName()));
			for(MixedDrinkIngredient ingredient : mixedDrink.getIngredients()) {
				assertTrue("Save failed: " + ingredient.toString(), Connect.getInstance().saveMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount()));
			}
		}

		Set<String> drinkNames = Connect.getInstance().findAllMixedDrinkNames();
		for(MixedDrink drink : ConnectMock.findAllMixedDrinksAndIngredients()) {
			assertTrue("Drink not found: " + drink.toString(), drinkNames.remove(drink.getName()));
		}
		assertEquals("Extra data in database!", 0, drinkNames.size());


		ArrayList<MixedDrink> databaseDrinks = new ArrayList<>(Connect.getInstance().findAllMixedDrinksAndIngredients());
		for(MixedDrink drink : ConnectMock.findAllMixedDrinksAndIngredients()) {
			int index = databaseDrinks.indexOf(drink);
			assertNotEquals("Drink not found " + drink, -1, index);
			verifyMixedDrink(drink, databaseDrinks.remove(index));
		}
		assertEquals("Extra data in database!", 0, databaseDrinks.size());

		for(MixedDrink drink : ConnectMock.findAllMixedDrinksAndIngredients()) {
			verifyMixedDrink(drink, Connect.getInstance().findMixedDrinkIngredientsByName(drink.getName()));
		}

	}

	public void verifyMixedDrink(MixedDrink drink, MixedDrink databaseDrink) {
		assertNotNull("Drink not found " + drink, databaseDrink);
		assertEquals("Name mismatch" + drink, drink, databaseDrink);
		assertEquals("Retire value mismatch " + drink, drink.isRetired(), databaseDrink.isRetired());

		ArrayList<MixedDrinkIngredient> databaseDrinkIngredients = new ArrayList<>(databaseDrink.getIngredients());
		for(MixedDrinkIngredient ingredient : drink.getIngredients()) {
			int index = databaseDrinkIngredients.indexOf(ingredient);
			assertNotEquals("Ingredient not found" + ingredient, -1, index);
			MixedDrinkIngredient databaseDrinkIngredient = databaseDrinkIngredients.remove(index);

			assertEquals("Ingredient mismatch " + ingredient, ingredient.getAlcohol(), databaseDrinkIngredient.getAlcohol());
			assertEquals("Ingredient mismatch " + ingredient, ingredient.getAmount(), databaseDrinkIngredient.getAmount(), 0);
		}
	}
}
