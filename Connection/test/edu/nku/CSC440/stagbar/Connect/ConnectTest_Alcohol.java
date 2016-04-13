package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ConnectTest_Alcohol extends ConnectTest {

	/** FOR FRESH DATABASE ONLY. Add alcohol mocks to database. */
	@Test
	@Ignore
	public void testSaveAlcohol() {
		for(Alcohol alcohol : ConnectMock.findAllAlcohol()) {
			assertTrue(alcohol.print(), Connect.getInstance().saveAlcohol(alcohol));
			System.out.println(alcohol + " added to database");
		}
	}
}
