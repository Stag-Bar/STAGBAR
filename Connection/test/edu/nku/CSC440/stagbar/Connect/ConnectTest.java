package edu.nku.CSC440.stagbar.Connect;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class ConnectTest {

	public static final String TEST_DATABASE = "junitTestDatabase";

	@BeforeClass
	public static void beforeClass() {
		Connect.getInstance().firstTimeSetup(TEST_DATABASE, "user", "password");
	}

	@AfterClass
	public static void tearDown() {
		Connect.getInstance().nukeDatabase(TEST_DATABASE);
	}

}