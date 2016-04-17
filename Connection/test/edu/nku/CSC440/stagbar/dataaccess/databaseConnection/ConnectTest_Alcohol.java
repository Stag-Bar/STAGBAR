package edu.nku.CSC440.stagbar.dataaccess.databaseConnection;

import edu.nku.CSC440.stagbar.dataaccess.data.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.data.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.data.mock.CustomAlcoholTypeMock;
import edu.nku.CSC440.stagbar.dataaccess.databaseConnection.mock.ConnectMock;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ConnectTest_Alcohol extends ConnectTest {

	@BeforeClass
	public static void populateCustomAlcoholTypes() {
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			assertTrue("Save of " + type + " failed.", Connect.getInstance().saveCustomAlcoholType(type));
		}
	}

	private static void verifyAlcohol(Alcohol first, Alcohol second) {
		assertEquals("Names do not match for " + first.print(), first.getName(), second.getName());
		assertEquals("Type does not match for " + first.print(), first.getType(), second.getType());
		assertEquals("Creation date does not match for " + first.print(), first.getCreationDate(), second.getCreationDate());
		assertEquals("Retire date does not match for " + first.print(), first.getRetireDate(), second.getRetireDate());
	}

	@After
	public void clearTable() {
		assertTrue("Table clear failed.", Connect.getInstance().nukeTable("alcohol"));
	}

	@Test
	public void testAlcohol_Retire() {
		Alcohol alcohol = new Alcohol(Alcohol.NEW_ALCOHOL_ID, "RotGut", CustomAlcoholTypeMock.WHISKEY, LocalDate.now().minus(2, ChronoUnit.YEARS), null);
		assertTrue("Save failed: " + alcohol.print(), Connect.getInstance().saveAlcohol(alcohol));
		assertTrue("Failed to find: " + alcohol.print(), Connect.getInstance().doesActiveAlcoholExist(alcohol.getName(), alcohol.getType(), LocalDate.now()));

		// Load alcohol from database to set proper ID
		for(Alcohol a : Connect.getInstance().findAllAlcohol()) {
			if(alcohol.getName().equals(a.getName()) && alcohol.getType().equals(a.getType()) && null == a.getRetireDate()) {
				alcohol = a;
				break;
			}
		}
		assertNotEquals("Failed to load from database.", Alcohol.NEW_ALCOHOL_ID, alcohol.getAlcoholId());

		assertNull("Sanity check failed: " + alcohol.print(), alcohol.getRetireDate());
		assertTrue("Sanity check failed: " + alcohol.print(), Connect.getInstance().doesActiveAlcoholExist(alcohol.getName(), alcohol.getType(), LocalDate.now()));
		assertFalse("Sanity check failed: " + alcohol.print(), Connect.getInstance().findActiveAlcoholByType(alcohol.getType(), LocalDate.now()).isEmpty());
		assertFalse("Sanity check failed: " + alcohol.print(), Connect.getInstance().findActiveAlcohol(LocalDate.now()).isEmpty());

		// Retire
		assertTrue("Retire Failed: " + alcohol.print(), Connect.getInstance().retireAlcohol(alcohol.getAlcoholId(), LocalDate.now()));

		// Reload from database
		for(Alcohol a : Connect.getInstance().findAllAlcohol()) {
			if(alcohol.getName().equals(a.getName()) && alcohol.getType().equals(a.getType())) {
				alcohol = a;
				break;
			}
		}
		assertNotEquals("Failed to load from database.", Alcohol.NEW_ALCOHOL_ID, alcohol.getAlcoholId());

		assertNotNull("Improper retire: " + alcohol.print(), alcohol.getRetireDate());
		assertFalse("Improper retire: " + alcohol.print(), Connect.getInstance().doesActiveAlcoholExist(alcohol.getName(), alcohol.getType(), LocalDate.now()));
		assertTrue("Improper retire: " + alcohol.print(), Connect.getInstance().findActiveAlcoholByType(alcohol.getType(), LocalDate.now()).isEmpty());
		assertTrue("Improper retire: " + alcohol.print(), Connect.getInstance().findActiveAlcohol(LocalDate.now()).isEmpty());
	}

	@Test
	public void testAlcohol_SaveLoad() {
		int initialDatabaseLoad = Connect.getInstance().findAllAlcohol().size(); // In case other tests run first and add extra data to the database.

		// Save mocks to database.
		for(Alcohol alcohol : ConnectMock.findAllAlcohol()) {
			assertTrue("Save failed: " + alcohol.print(), Connect.getInstance().saveAlcohol(alcohol));
		}

		// Test findAllAlcohol().
		ArrayList<Alcohol> databaseResults = new ArrayList<>(Connect.getInstance().findAllAlcohol());
		for(Alcohol alcohol : ConnectMock.findAllAlcohol()) {
			int index = databaseResults.indexOf(alcohol);
			assertNotEquals("Failed to find: " + alcohol.print(), -1, index);
			verifyAlcohol(alcohol, databaseResults.remove(index));
		}

		assertEquals("Extra data in database!", initialDatabaseLoad, databaseResults.size());

		// Test findActiveAlcohol().
		databaseResults = new ArrayList<>(Connect.getInstance().findActiveAlcohol(LocalDate.now()));
		for(Alcohol alcohol : ConnectMock.findActiveAlcohol()) {
			int index = databaseResults.indexOf(alcohol);
			assertNotEquals("Failed to find: " + alcohol.print(), -1, index);
			verifyAlcohol(alcohol, databaseResults.remove(index));
		}

		assertEquals("Extra data in database!", initialDatabaseLoad, databaseResults.size());

		// Test findActiveAlcoholByType().
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			databaseResults = new ArrayList<>(Connect.getInstance().findActiveAlcoholByType(type, LocalDate.now()));
			for(Alcohol alcohol : ConnectMock.findActiveAlcoholByType(type)) {
				int index = databaseResults.indexOf(alcohol);
				assertNotEquals("Failed to find: " + alcohol.print(), -1, index);
				verifyAlcohol(alcohol, databaseResults.remove(index));
			}

			assertEquals("Extra data in database!", initialDatabaseLoad, databaseResults.size());
		}
	}

	@Test
	public void testVerifyMockCustomAlcoholTypes() {
		ArrayList<CustomAlcoholType> databaseResults = new ArrayList<>(Connect.getInstance().findAllCustomAlcoholTypes());
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			int index = databaseResults.indexOf(type);
			assertNotEquals("Failed to find: " + type.print(), -1, index);
			assertEquals("Names do not match for " + type.print(), type.getName(), databaseResults.get(index).getName());
			assertEquals("Kind does not match for " + type.print(), type.getKind(), databaseResults.get(index).getKind());
		}
	}
}
