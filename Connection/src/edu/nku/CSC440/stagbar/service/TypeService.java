package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;

import java.util.Set;

public class TypeService {

	private static final TypeService typeService = new TypeService();

	private TypeService() {}

	public static TypeService getInstance() {
		return typeService;
	}

	// TODO: Add methods for handling custom types.

	public Set<CustomAlcoholType> getAllCustomAlcoholTypes() {
		return Connect.getInstance().findAllCustomAlcoholTypes();
	}

}
