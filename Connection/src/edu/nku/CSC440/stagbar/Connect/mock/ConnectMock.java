package edu.nku.CSC440.stagbar.Connect.mock;

import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.mock.CustomAlcoholTypeMock;

import java.util.HashSet;
import java.util.Set;

public class ConnectMock {
	private ConnectMock() {}

	public static Set<CustomAlcoholType> findAllCustomAlcoholTypes() {
		Set<CustomAlcoholType> results = new HashSet<>();
		results.add(CustomAlcoholTypeMock.DOMESTIC_BEER);
		results.add(CustomAlcoholTypeMock.CRAFT_BEER);
		results.add(CustomAlcoholTypeMock.DRAFT_BEER);
		results.add(CustomAlcoholTypeMock.WHISKEY);
		results.add(CustomAlcoholTypeMock.VODKA);
		return results;
	}
}
