package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.Connect.Database;

public class BaseService {

	private Database database;

	protected Database getDatabase() {
		if(null == database) database = Connect.getInstance();
		return database;
	}

	protected void setDatabase(Database database) {
		this.database = database;
	}
}
