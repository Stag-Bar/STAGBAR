package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Connect {

	private static final String DATABASE_URL = "jdbc:mysql://stagbar.cgef59ufduu4.us-west-2.rds.amazonaws.com:3306";
	private static Connect connect = new Connect();
	private Connection activeConnection;
	private String databaseName;

	private Connect() {}

	public static Connect getInstance() {
		return connect;
	}

	/** Create a table to store the different kinds of alcohol */
	protected boolean addNewGroup(Connection conn, String groupName, int code) {
		boolean successful = false;
		/* code will indicate the type
		 * 1 = bottled beer
		 * 2 = draft beer
		 * 3 = liqour
		 */
		try {
			Statement sta = conn.createStatement();
			String statement = "";
			//PreparedStatement pSta;

			//TODO: Modify tables
			// Instead of creating 3 different tables here for storing the drinks, we can just use 1.
			// We will have to write code to check that the input is an integer for previous/current on bottled beer anyway,
			// we shouldn't need to verify it at the database level as well.
			// It won't cause any problems to have empty fields for some of the drinks

			// The combined table would have previous double, current double, name &
			// another 2 columns:
			// new column 1: ID - a unique primary key, this integer can be auto-incremented by the database on entries
			// new column 2: type - a foreign key to a new table that has 2 columns:
			// Table ALCOHOL_TYPE, Columns ID, type
			// Where ID is the primary key, numbers 1-3
			// & type is the associated string


			switch(code) {
				case 1:
					statement = "CREATE TABLE " + groupName + " (name varchar(50), previous integer, current integer, primary key(name));";
					break;
				case 2:
					statement = "CREATE TABLE " + groupName + " (name varchar(50), previous double, current double, primary key(name));";
					break;
				case 3:
					statement = "CREATE TABLE " + groupName + " (name varchar(50), previous double, current double, fullBottles integer, primary key (name));";
					break;
			}
			//pSta = conn.prepareStatement(statement);
			//pSta.setString(1, groupName);
			successful = sta.execute(statement);
			//successful = pSta.execute();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return successful;
	}

	/**
	 * @param name Name of database to create.
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	public boolean createDatabase(String name) {
		Connection c;
		boolean success = false;
		try {
			c = makeNewMasterConnection(null);
			Statement sta = c.createStatement();
			success = sta.execute("CREATE DATABASE " + name + ";");
			c.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Create a new user that has full privileges.  IE the inventory manager
	 *
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	public boolean createMasterUser(String username, String password, String database) {
		try {
			PreparedStatement pSta;

			if(!doesUserExist(username)) {
				String createUserStatement = "CREATE USER ? @'%' IDENTIFIED BY ?;";
				pSta = getActiveConnection().prepareStatement(createUserStatement);
				pSta.setString(1, username);
				pSta.setString(2, password);
				pSta.execute();

				//FIXME: This statement is not granting priveleges. I had to manually set priveleges in the DB to log in with a new user.
				String grantPrivStatement = "GRANT ALL PRIVILEGES ON " + database + " TO ? @'%' IDENTIFIED BY ?;";
				pSta = getActiveConnection().prepareStatement(grantPrivStatement);
				pSta.setString(1, username);
				pSta.setString(2, password);
				pSta.execute();

				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/** Creates a new mixed drink with given name and no retire date. */
	public boolean createMixedDrink(String name) {
		//TODO: Create new mixed drink.
		return true;
	}

	/** Creates a new mixed drink ingredient for drink with given name. */
	public boolean createMixedDrinkIngredient(String mixedDrinkName, int alcoholId, Double amount) {
		return true;
	}

	/**
	 * @return <code>true</code> if given username & password can connect to database
	 * @deprecated Temporary method. Unsecure.
	 * User does not need connection if we use a single login from system to database.
	 * TODO: Replace with method to check against custom user table.
	 */
	public boolean createUserConnection(String username, String password) {
//		activeConnection = makeNewConnection(username, password, getDatabaseName());
		activeConnection = makeNewMasterConnection(getDatabaseName()); // TODO: Delete line after testing.
		return activeConnection != null;
	}

	/** Deletes given ingredient for given drink. */
	public boolean deleteMixedDrinkIngredient(String mixedDrinkName, int alcoholId) {
		//TODO: Delete removed ingredients
		return true;
	}

	/**
	 * Delete given user from the database.
	 *
	 * @param username User to delete.
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	public boolean deleteUser(String username) {
		boolean succesful = false;
		try {
			Connection conn = makeNewMasterConnection(null);//have to connect as admin.
			String statement = "DROP USER ?;";
			PreparedStatement pSta = conn.prepareStatement(statement);
			pSta.setString(1, username);
			succesful = pSta.execute();
			//Statement sta = conn.createStatement();
			//sta.execute("DROP USER \'" + username + "\';");
			conn.close();
		} catch(SQLException e) {
			System.out.println(e);
		}
		return succesful;
	}

	/**
	 * Checks database for given drink.
	 *
	 * @param drinkName Drink to check database for.
	 * @return <code>true</code> if given drink is in the database,
	 * <code>false</code> otherwise.
	 * @throws RuntimeException If user's database connection is closed.
	 */
	public boolean doesDrinkExist(String drinkName) {
		//TODO: Search database for given drink.
		return false;
	}

	/**
	 * Checks database for given username.
	 *
	 * @param username Username to check database for.
	 * @return <code>true</code> if given username is in the database,
	 * <code>false</code> otherwise.
	 * @throws RuntimeException If user's database connection is closed.
	 */
	public boolean doesUserExist(String username) {
		String statement = "SELECT * FROM mysql.user WHERE user = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, username);
			ResultSet checkResults = pSta.executeQuery();
			return checkResults.next();
		} catch(SQLException e) {
			throw new RuntimeException("Connection to database has been closed.", e);
		}
	}

	/** Searches database for alcohol whose retire date is null or after the start date & whose creation date is before the end date. */
	public Set<Alcohol> findActiveAlcoholForDateRange(LocalDate startDate, LocalDate endDate) {
		//TODO: Find active alcohol
		return ConnectMock.findActiveAlcoholForDateRange(startDate, endDate);
	}

	/**
	 * Searches database for an Alcohol with given name.
	 *
	 * @param name
	 * @return Alcohol with given name if found, otherwise returns <code>null</code>.
	 */
	public Alcohol findAlcoholByName(String name) {
		//TODO: Search database for alcohol with given name
		return null;
	}

	public Set<Alcohol> findAllAlcohol() {
		return findActiveAlcoholForDateRange(LocalDate.MIN, LocalDate.MAX);
	}

	/** Pulls all custom types from database. */
	public Set<CustomAlcoholType> findAllCustomAlcoholTypes() {
		//TODO: find custom types
		return ConnectMock.findAllCustomAlcoholTypes();
	}

	/** Searches database for all mixed drinks and their corresponding ingredients. */
	public Set<MixedDrink> findAllMixedDrinksAndIngredients() {
		//TODO: Find all mixed drinks
		//TODO: Find ingredients for each mixed drink
		//TODO: Create and return mixed drink set
		return null;
	}

	/** Retrieves all users from database. */
	public Set<User> findAllUsers() {
		//TODO: Find users
		return new HashSet<>();
	}

	private Connection getActiveConnection() {
		if(!isConnectionValid()) // Lazy initialization in case connection is closed/corrupted.
		{ activeConnection = makeNewMasterConnection(getDatabaseName()); }

		return activeConnection;
	}

	private String getDatabaseName() {
		if(null == databaseName) { databaseName = getDatabaseNameFromFile(); }

		return databaseName;
	}

	/** Gets database name from file. */
	private String getDatabaseNameFromFile() {
		return "test";
	}

	private boolean isConnectionValid() {
		try {
			return null != activeConnection && activeConnection.isValid(300);
		} catch(SQLException e) {
			return false;
		}
	}

	/**
	 * Make a connection to the database.
	 *
	 * @param username
	 * @param password
	 * @param dataBaseName
	 * @return Connection to database if successful, otherwise returns <code>null</code>.
	 * @throws RuntimeException If mysql is not setup properly.
	 */
	private Connection makeNewConnection(String username, String password, String dataBaseName) {
		Connection conn;
		try {
			Class.forName("com.mysql.jdbc.Driver"); // Register MySQL Driver. Needed?
			String url = DATABASE_URL + (null == dataBaseName ? "" : "/" + dataBaseName);
			conn = DriverManager.getConnection(url, username, password);
		} catch(SQLException e) {
			conn = null;
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Check mysql setup!", e);
		}
		return conn;
	}

	/**
	 * Connect to database as master user.
	 */
	private Connection makeNewMasterConnection(String database) {
		return makeNewConnection("stagbar", "Nkucsc440", database);
	}

	/** Sets retire date for given alcohol. */
	public boolean retireAlcohol(int alcoholId, LocalDate date) {
		//TODO: Retire alcohol
		return true;
	}

	/** Sets retire date for given mixed drink. */
	public boolean retireMixedDrink(String mixedDrink, LocalDate date) {
		//TODO: Set retire date.
		return true;
	}

	/**
	 * Saves a new Alcohol record.
	 * Should be written in a way that can be called effectively once or in a loop.
	 *
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	public boolean saveAlcohol(Alcohol alcohol) {
		//TODO: Save to database
		return true;
	}

	/** Creates a new row on the Delivery table with the given entry. */
	public boolean saveDeliveryEntry(Entry entry) {
		return saveEntry(EntryTable.DELIVERY, entry);
	}

	private boolean saveEntry(EntryTable table, Entry entry) {
		//TODO: Save to database. We should be able to use a single query to update all 3 tables.
		// Use the toString method on 'table' to get the table name
		// then add each of the values from 'entry' to the appropriate column.

		return true;
	}

	/** Creates a new row on the Inventory table with the given entry. */
	public boolean saveInventoryEntry(Entry entry) {
		return saveEntry(EntryTable.INVENTORY, entry);
	}

	/** Creates a new row on the Sales table with the given entry. */
	public boolean saveSalesEntry(Entry entry) {
		return saveEntry(EntryTable.SALES, entry);
	}

	/**
	 * Updates an existing Alcohol record.
	 * Should be written in a way that can be called effectively once or in a loop.
	 *
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 * @deprecated This method may be obsolete. All a user would be able to update here is the type of alcohol.
	 * Retire date would be set in retireAlcohol.
	 * If the user is interested in changing the type, they may as well retire this one and make a new one.
	 */
	public boolean updateAlcohol(Alcohol alcohol) {
		//TODO: Update in database. Obsolete? See comment above.
		return true;
	}

	public boolean updateDeliveryEntry(Entry entry) {
		return updateEntry(EntryTable.DELIVERY, entry);
	}

	// NOTE: Not certain if we should allow updates based on our current workflow.
	// For now I feel we are safe assuming that entries are being made in chronological order, for today's date
	// and the user got all the numbers right the first time.
	@Deprecated
	private boolean updateEntry(EntryTable table, Entry entry) {
		//TODO: Update in database. We should be able to use a single query to update all 3 tables. Obsolete? See comment above.
		// Use the toString method on 'table' to get the table name
		// then add each of the values from 'entry' to the appropriate column.

		return true;
	}

	public boolean updateInventoryEntry(Entry entry) {
		return updateEntry(EntryTable.INVENTORY, entry);
	}

	/** Updates given ingredient for given drink with given value. */
	public boolean updateMixedDrinkIngredient(String mixedDrinkName, int alcoholId, Double amount) {
		//TODO: Update mixed drink
		return true;
	}

	public boolean updateSalesEntry(Entry entry) {
		return updateEntry(EntryTable.SALES, entry);
	}

	/** Updates password for given user to given value. */
	public boolean updateUserPassword(String username, String password) {
		// TODO: Update password
		return true;
	}

	/** Updates permissions for given user to given value. */
	public boolean updateUserPermissions(String username, PermissionLevel permissionLevel) {
		//TODO: Update permissions
		return true;
	}

	private enum EntryTable {
		INVENTORY("Inventory"), DELIVERY("Delivery"), SALES("Sales");
		private String tableName;

		EntryTable(String tableName) {
			this.tableName = tableName;
		}

		public String toString() {
			return tableName;
		}
	}
}

