package edu.nku.CSC440.stagbar.dataaccess.databaseConnection;

import edu.nku.CSC440.stagbar.dataaccess.data.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.data.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrinkIngredient;
import edu.nku.CSC440.stagbar.dataaccess.data.mock.MixedDrinkMock;
import edu.nku.CSC440.stagbar.dataaccess.databaseConnection.mock.ConnectMock;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

@Ignore
public class ConnectManualTest {

	@Test
	@Ignore
	public void playground() {
		System.out.println(MixedDrinkMock.FIREBULL);
	}

	@Test
	@Ignore
	public void testCreateAndLoadDatabase() {
		Connect.getInstance().firstTimeSetup("test16", "user", "password");

		// Save custom types.
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			assertTrue(type.print(), Connect.getInstance().saveCustomAlcoholType(type));
		}

		// Save alcohol.
		for(Alcohol alcohol : ConnectMock.findAllAlcohol()) {
			assertTrue(alcohol.print(), Connect.getInstance().saveAlcohol(alcohol));
		}

		// Save drinks to database.
		for(MixedDrink mixedDrink : ConnectMock.findAllMixedDrinks()) {
			assertTrue("Save failed: " + mixedDrink.toString(), Connect.getInstance().saveMixedDrink(mixedDrink.getName()));
			for(MixedDrinkIngredient ingredient : mixedDrink.getIngredients()) {
				assertTrue("Save failed: " + ingredient.toString(), Connect.getInstance().saveMixedDrinkIngredient(mixedDrink.getName(), ingredient.getAlcohol(), ingredient.getAmount()));
			}
		}
	}

	@Test
	@Ignore
	public void testFirstTimeSetup() {
		Connect.getInstance().firstTimeSetup("test15", "user", "password");
	}

	/** FOR FRESH DATABASE ONLY. Add alcohol mocks to database. */
	@Test
	@Ignore
	public void testSaveAlcohol() {
		for(Alcohol alcohol : ConnectMock.findAllAlcohol()) {
			assertTrue(alcohol.print(), Connect.getInstance().saveAlcohol(alcohol));
		}
	}

	/** FOR FRESH DATABASE ONLY. Add custom type mocks to database. */
	@Test
	@Ignore
	public void testSaveCustomAlcoholType() {
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			assertTrue(type.print(), Connect.getInstance().saveCustomAlcoholType(type));
		}
	}

}