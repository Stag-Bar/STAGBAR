package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.dataaccess.data.AlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.data.CustomAlcoholType;

import java.util.Set;

public class TypeService extends BaseService {

	private static final TypeService TYPE_SERVICE = new TypeService();

	private TypeService() {}

	public static TypeService getInstance() {
		return TYPE_SERVICE;
	}

	// TODO: Add methods for handling custom types.

	public Set<CustomAlcoholType> getAllCustomAlcoholTypes() {
		return getDatabase().findAllCustomAlcoholTypes();
	}

	public boolean saveCustomAlcoholType(String name, AlcoholType kind) {
		return getDatabase().saveCustomAlcoholType(new CustomAlcoholType(CustomAlcoholType.NEW_CUSTOM_TYPE_ID, name, kind));
	}

}
