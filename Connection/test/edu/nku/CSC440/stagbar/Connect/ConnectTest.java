package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ConnectTest {

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testSaveAlcohol() {
		for(Alcohol alcohol : ConnectMock.findAllAlcohol()) {
			assertTrue(alcohol.print(), Connect.getInstance().saveAlcohol(alcohol));
			System.out.println(alcohol + " added to database");
		}
	}

	@Test
	public void testSaveCustomAlcoholType() {
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			Connect.getInstance().saveCustomAlcoholType(type);
		}
	}

}