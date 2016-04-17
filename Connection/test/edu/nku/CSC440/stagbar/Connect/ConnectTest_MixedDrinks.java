package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.data.*;
import edu.nku.CSC440.stagbar.dataaccess.data.mock.AlcoholMock;
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
	public void testDeleteIngredient() {
		// Save drink to database.
		MixedDrink mixedDrink = new MixedDrinkBuilder("Good Times", false)
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 2.0))
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.WOODFORD_RESERVE, 1.0))
				.build();
		assertTrue("Save failed: " + mixedDrink.toString(), Connect.getInstance().saveMixedDrink(mixedDrink.getName()));
		for(MixedDrinkIngredient ingredient : mixedDrink.getIngredients()) {
			assertTrue("Save failed: " + ingredient.toString(), Connect.getInstance().saveMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount()));
		}

		// Sanity check
		verifyMixedDrink(mixedDrink, Connect.getInstance().findMixedDrinkByName(mixedDrink.getName()));

		// Delete ingredient
		assertTrue(Connect.getInstance().deleteMixedDrinkIngredient(mixedDrink.getName(), AlcoholMock.WOODFORD_RESERVE));

		// Verify
		mixedDrink = new MixedDrinkBuilder("Good Times", false)
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 2.0))
				.build();
		verifyMixedDrink(mixedDrink, Connect.getInstance().findMixedDrinkByName(mixedDrink.getName()));
	}

	@Test
	public void testFakeDrink() {
		final String fakeDrink = "Fake Drink";
		assertFalse("False positive: " + fakeDrink, Connect.getInstance().doesDrinkExist(fakeDrink));
	}

	@Test
	public void testMixedDrink_SaveLoad() {

		// Save drinks to database.
		for(MixedDrink mixedDrink : ConnectMock.findAllMixedDrinks()) {
			assertTrue("Save failed: " + mixedDrink.toString(), Connect.getInstance().saveMixedDrink(mixedDrink.getName()));
			for(MixedDrinkIngredient ingredient : mixedDrink.getIngredients()) {
				assertTrue("Save failed: " + ingredient.toString(), Connect.getInstance().saveMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount()));
			}
		}

		// Test findAllMixedDrinkNames()
		Set<String> drinkNames = Connect.getInstance().findAllMixedDrinkNames();
		for(MixedDrink drink : ConnectMock.findAllMixedDrinks()) {
			assertTrue("Drink not found: " + drink.toString(), drinkNames.remove(drink.getName()));
		}
		assertEquals("Extra data in database!", 0, drinkNames.size());

		// Test doesDrinkExist()
		for(MixedDrink drink : ConnectMock.findAllMixedDrinks()) {
			assertTrue("Drink not found: " + drink.toString(), Connect.getInstance().doesDrinkExist(drink.getName()));
		}

		//Test findAllMixedDrinks()
		ArrayList<MixedDrink> databaseDrinks = new ArrayList<>(Connect.getInstance().findAllMixedDrinks());
		for(MixedDrink drink : ConnectMock.findAllMixedDrinks()) {
			int index = databaseDrinks.indexOf(drink);
			assertNotEquals("Drink not found " + drink, -1, index);
			verifyMixedDrink(drink, databaseDrinks.remove(index));
		}
		assertEquals("Extra data in database!", 0, databaseDrinks.size());

		// Test findMixedDrinkByName()
		for(MixedDrink drink : ConnectMock.findAllMixedDrinks()) {
			verifyMixedDrink(drink, Connect.getInstance().findMixedDrinkByName(drink.getName()));
		}

	}

	@Test
	public void testRetireReinstateDrink() {
		// Save drink to database.
		MixedDrink mixedDrink = new MixedDrinkBuilder("Martini", false)
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.VODKA, 2.0))
				.build();
		assertTrue("Save failed: " + mixedDrink.toString(), Connect.getInstance().saveMixedDrink(mixedDrink.getName()));
		for(MixedDrinkIngredient ingredient : mixedDrink.getIngredients()) {
			assertTrue("Save failed: " + ingredient.toString(), Connect.getInstance().saveMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount()));
		}

		// Sanity check
		verifyMixedDrink(mixedDrink, Connect.getInstance().findMixedDrinkByName(mixedDrink.getName()));

		// Retire drink
		assertTrue(Connect.getInstance().retireMixedDrink(mixedDrink.getName(), true));

		// Verify
		mixedDrink.setIsRetired(true);
		verifyMixedDrink(mixedDrink, Connect.getInstance().findMixedDrinkByName(mixedDrink.getName()));

		// Reinstate drink
		assertTrue(Connect.getInstance().retireMixedDrink(mixedDrink.getName(), false));

		// Verify
		mixedDrink.setIsRetired(false);
		verifyMixedDrink(mixedDrink, Connect.getInstance().findMixedDrinkByName(mixedDrink.getName()));
	}

	@Test
	public void testUpdateDrink() {
		// Save drink to database.
		MixedDrink mixedDrink = new MixedDrinkBuilder("J&J", false)
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JACK_DANIELS, 1.5))
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JAMESON, 1.5))
				.build();
		assertTrue("Save failed: " + mixedDrink.toString(), Connect.getInstance().saveMixedDrink(mixedDrink.getName()));
		for(MixedDrinkIngredient ingredient : mixedDrink.getIngredients()) {
			assertTrue("Save failed: " + ingredient.toString(), Connect.getInstance().saveMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount()));
		}

		// Sanity check
		verifyMixedDrink(mixedDrink, Connect.getInstance().findMixedDrinkByName(mixedDrink.getName()));

		// Update drink
		assertTrue(Connect.getInstance().updateMixedDrinkIngredient(mixedDrink.getName(), AlcoholMock.JACK_DANIELS, 2.0));

		// Verify
		mixedDrink = new MixedDrinkBuilder("J&J", false)
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JACK_DANIELS, 2.0))
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JAMESON, 1.5))
				.build();
		verifyMixedDrink(mixedDrink, Connect.getInstance().findMixedDrinkByName(mixedDrink.getName()));
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
