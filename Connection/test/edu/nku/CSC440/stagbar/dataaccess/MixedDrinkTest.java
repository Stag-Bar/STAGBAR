package edu.nku.CSC440.stagbar.dataaccess;

import edu.nku.CSC440.stagbar.dataaccess.mock.AlcoholMock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MixedDrinkTest {

	@Test
	public void testGetNewIngredients() {
		MixedDrink original = new MixedDrinkBuilder("Drink", false)
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JACK_DANIELS, 1.0))
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 2.0))
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JAMESON, 3.0))
				.build();

		Set<MixedDrinkIngredient> editedIngredients = new HashSet<>();
		editedIngredients.add(new MixedDrinkIngredient(AlcoholMock.JACK_DANIELS, 1.0));
		editedIngredients.add(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 1.5));
		editedIngredients.add(new MixedDrinkIngredient(AlcoholMock.WOODFORD_RESERVE, 1.0));

		original.setIngredients(editedIngredients);

		Set<MixedDrinkIngredient> expectedResults = new HashSet<>();
		expectedResults.add(new MixedDrinkIngredient(AlcoholMock.WOODFORD_RESERVE, 1.0));

		ArrayList<MixedDrinkIngredient> results = new ArrayList<>(original.getNewIngredients());

		assertEquals("Unexpected results returned: " + results, expectedResults.size(), results.size());
		assertTrue("Unexpected results returned: " + results, results.containsAll(expectedResults));

		for(MixedDrinkIngredient expectedIngredient : expectedResults) {
			int index = results.indexOf(expectedIngredient);
			assertNotEquals("Expected ingredient not found: " + expectedIngredient, -1, index);
			MixedDrinkIngredient ingredient = results.remove(index);
			assertEquals("Amount mismatch.", ingredient.getAmount(), expectedIngredient.getAmount(), 0);
		}

		assertTrue("Extra results: " + results, results.isEmpty());
	}

	@Test
	public void testGetRemovedIngredients() {
		MixedDrink original = new MixedDrinkBuilder("Drink", false)
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JACK_DANIELS, 1.0))
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 2.0))
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JAMESON, 3.0))
				.build();

		Set<MixedDrinkIngredient> editedIngredients = new HashSet<>();
		editedIngredients.add(new MixedDrinkIngredient(AlcoholMock.JACK_DANIELS, 1.0));
		editedIngredients.add(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 1.5));
		editedIngredients.add(new MixedDrinkIngredient(AlcoholMock.WOODFORD_RESERVE, 1.0));

		original.setIngredients(editedIngredients);

		Set<MixedDrinkIngredient> expectedResults = new HashSet<>();
		expectedResults.add(new MixedDrinkIngredient(AlcoholMock.JAMESON, 3.0));

		ArrayList<MixedDrinkIngredient> results = new ArrayList<>(original.getRemovedIngredients());

		assertEquals("Unexpected results returned: " + results, expectedResults.size(), results.size());
		assertTrue("Unexpected results returned: " + results, results.containsAll(expectedResults));

		for(MixedDrinkIngredient expectedIngredient : expectedResults) {
			int index = results.indexOf(expectedIngredient);
			assertNotEquals("Expected ingredient not found: " + expectedIngredient, -1, index);
			MixedDrinkIngredient ingredient = results.remove(index);
			assertEquals("Amount mismatch.", ingredient.getAmount(), expectedIngredient.getAmount(), 0);
		}

		assertTrue("Extra results: " + results, results.isEmpty());
	}

	@Test
	public void testGetUpdatedIngredients() {
		MixedDrink original = new MixedDrinkBuilder("Drink", false)
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JACK_DANIELS, 1.0))
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 2.0))
				.addIngredientFromDatabase(new MixedDrinkIngredient(AlcoholMock.JAMESON, 3.0))
				.build();

		Set<MixedDrinkIngredient> editedIngredients = new HashSet<>();
		editedIngredients.add(new MixedDrinkIngredient(AlcoholMock.JACK_DANIELS, 1.0));
		editedIngredients.add(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 1.5));
		editedIngredients.add(new MixedDrinkIngredient(AlcoholMock.WOODFORD_RESERVE, 1.0));

		original.setIngredients(editedIngredients);

		Set<MixedDrinkIngredient> expectedResults = new HashSet<>();
		expectedResults.add(new MixedDrinkIngredient(AlcoholMock.WHISKEY, 1.5));

		ArrayList<MixedDrinkIngredient> results = new ArrayList<>(original.getUpdatedIngredients());

		assertEquals("Unexpected results returned: " + results, expectedResults.size(), results.size());
		assertTrue("Unexpected results returned: " + results, results.containsAll(expectedResults));

		for(MixedDrinkIngredient expectedIngredient : expectedResults) {
			int index = results.indexOf(expectedIngredient);
			assertNotEquals("Expected ingredient not found: " + expectedIngredient, -1, index);
			MixedDrinkIngredient ingredient = results.remove(index);
			assertEquals("Amount mismatch.", ingredient.getAmount(), expectedIngredient.getAmount(), 0);
		}

		assertTrue("Extra results: " + results, results.isEmpty());
	}

}