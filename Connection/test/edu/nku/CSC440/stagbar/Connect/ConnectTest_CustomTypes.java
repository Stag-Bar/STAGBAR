package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.data.CustomAlcoholType;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ConnectTest_CustomTypes extends ConnectTest {

	@After
	public void clearTable() {
		assertTrue("Table clear failed.", Connect.getInstance().nukeTable("type"));
	}

	@Test
	public void testCustomType_SaveLoad() {
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			assertTrue("Save of " + type + " failed.", Connect.getInstance().saveCustomAlcoholType(type));
		}

		ArrayList<CustomAlcoholType> databaseResults = new ArrayList<>(Connect.getInstance().findAllCustomAlcoholTypes());
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			int index = databaseResults.indexOf(type);
			assertNotEquals("Failed to find: " + type.print(), -1, index);
			assertEquals("Names do not match for " + type.print(), type.getName(), databaseResults.get(index).getName());
			assertEquals("Kind does not match for " + type.print(), type.getKind(), databaseResults.get(index).getKind());
			assertNotNull(databaseResults.remove(index));
		}

		assertEquals("Extra data in database!", 0, databaseResults.size());

	}
}
