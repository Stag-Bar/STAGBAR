package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
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

	@Test
	public void testAlcohol_SaveLoad() {
		int initialDatabaseLoad = Connect.getInstance().findAllAlcohol().size(); // In case other tests run first and add extra data to the database.

		for(Alcohol alcohol : ConnectMock.findAllAlcohol()) {
			assertTrue("Save failed: " + alcohol.print(), Connect.getInstance().saveAlcohol(alcohol));
		}

		ArrayList<Alcohol> databaseResults = new ArrayList<>(Connect.getInstance().findAllAlcohol());
		for(Alcohol alcohol : ConnectMock.findAllAlcohol()) {
			int index = databaseResults.indexOf(alcohol);
			assertNotEquals("Failed to find: " + alcohol.print(), -1, index);
			verifyAlcohol(alcohol, databaseResults.remove(index));
		}

		assertEquals("Extra data in database!", initialDatabaseLoad, databaseResults.size());

		databaseResults = new ArrayList<>(Connect.getInstance().findActiveAlcoholForDateRange(LocalDate.ofEpochDay(0), LocalDate.now()));
		for(Alcohol alcohol : ConnectMock.findActiveAlcoholForDateRange(LocalDate.ofEpochDay(0), LocalDate.now())) {
			int index = databaseResults.indexOf(alcohol);
			assertNotEquals("Failed to find: " + alcohol.print(), -1, index);
			verifyAlcohol(alcohol, databaseResults.remove(index));
		}

		assertEquals("Extra data in database!", initialDatabaseLoad, databaseResults.size());

		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			databaseResults = new ArrayList<>(Connect.getInstance().findActiveAlcoholByType(type, LocalDate.ofEpochDay(0), LocalDate.now()));
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
