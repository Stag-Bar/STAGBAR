package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;

public class ConnectTest_CustomTypes extends ConnectTest {

	@Test
	public void testCustomType_SaveLoad() {
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			assertTrue("Save of " + type + " failed.", Connect.getInstance().saveCustomAlcoholType(type));
		}

		Set<CustomAlcoholType> databaseResults = Connect.getInstance().findAllCustomAlcoholTypes();
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			assertTrue("Failed to find: " + type.print(), databaseResults.remove(type));
		}
	}
}
