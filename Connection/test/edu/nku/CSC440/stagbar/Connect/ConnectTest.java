package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.mock.MixedDrinkMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ConnectTest {

	@Test
	public void playground() {
		System.out.println(MixedDrinkMock.FIREBULL);
	}

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	/** FOR FRESH DATABASE ONLY. Add alcohol mocks to database. */
	@Test
	@Ignore
	public void testSaveAlcohol() {
		for(Alcohol alcohol : ConnectMock.findAllAlcohol()) {
			assertTrue(alcohol.print(), Connect.getInstance().saveAlcohol(alcohol));
			System.out.println(alcohol + " added to database");
		}
	}

	/** FOR FRESH DATABASE ONLY. Add custom type mocks to database. */
	@Test
	@Ignore
	public void testSaveCustomAlcoholType() {
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			Connect.getInstance().saveCustomAlcoholType(type);
		}
	}

}