package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConnectTest {

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testSaveAlcohol() {
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			Connect.getInstance().saveCustomAlcoholType(type);
		}
	}

	@Test
	public void testSaveCustomAlcoholType() {
		for(CustomAlcoholType type : ConnectMock.findAllCustomAlcoholTypes()) {
			Connect.getInstance().saveCustomAlcoholType(type);
		}
	}

}