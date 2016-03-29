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
	//first time setup will create set up the user and neccssary tables.
	public void firstTimeSetup(String database, String userName, String password){
		if(createDatabase(database)){
			try {
				//creating user table and adding the first user
				Statement sta = getActiveConnection().createStatement();
				String statement = "CREATE TABLE user (username varchar(50), password varchar(128), permission varchar(5), primary key(username);";
				sta.execute(statement);
				statement = "INSERT INTO users (name, password, permission) VALUES (?, ?, \'Administrator\');";
				PreparedStatement pSta = activeConnection.prepareStatement(statement);
				pSta.setString(1, userName);
				pSta.setString(2, password);
				pSta.execute();
				//creating the type other tables
				statement = "CREATE TABE type (typeid int NOT NULL AUTO_INCREMENT, name varchar(40), kind varchar(10), primary key(typeid));";
				sta.execute(statement);
				statement = "CREATE TABLE alcohol (alcoholid int NOT NULL AUTO_INCREMENT, name varchar(40), typeid int, creationDate date, retireDate date, primary key(alcoholid), foreign key(typeid) REFERENCES type(typeid));";
				sta.execute(statement);
				statement = "CREATE TABLE inventory (entryid int NOT NULL AUTO_INCREMENT, alcohol int, amount double, bottles int, date date, primary key(entryid), foreign key (alcohol) REFERENCES alcohol(alcoholid));";
				sta.execute(statement);
				statement = "CREATE TABLE sales (entryid int NOT NULL AUTO_INCREMENT, alcohol int, amount double, bottles int, date date, primary key(entryid), foreign key(alcoholid) REFERENCES alcohol(alcohol));";
				sta.execute(statement);
				statement = "CREATE TABLE delivery (entryid int NOT NULL AUTO_INCREMENT, alcohol int, amount double, bottles int, date date, primary key(entryid), foreign key(alcohol) REFERENCES alcohol(alcoholid));";
				sta.execute(statement);
				statement = "CREATE TABLE mixeddrink (name varchar(40), retiredate date, primary key(name));";
				sta.execute(statement);
				statement = "CREATE TABLE mixeddrinkingredients (drink varchar(40), ingredientid int, amount double, foreign key(drink) REFERENCES mixeddrink(name), foreign key(ingredientid) REFERENCES alcohol(alcoholid));";
				sta.execute(statement);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/*
	public static Connection makeNewConnection(String username, String password) throws ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		try{
			String url = "jdbc:mysql://stagbar.cgef59ufduu4.us-west-2.rds.amazonaws.com:3306";
			conn = DriverManager.getConnection(url, username, password);
		}
		catch(Exception e){
			conn = null;
			System.out.println(e);
			return conn;
		}
		return conn;
	}
	public static Connection makeNewConnection(String username, String password, String dataBaseName) throws ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		try{
			String url = "jdbc:mysql://stagbar.cgef59ufduu4.us-west-2.rds.amazonaws.com:3306/" + dataBaseName;
			conn = DriverManager.getConnection(url, username, password);
		}
		catch(Exception e){
			conn = null;
			System.out.println(e);
			return conn;
		}
		return conn;
	}*/
	//method to create a new user that has full privileges.  IE the inventory manager 
	private boolean createDatabase(String name){
		
		boolean success = false;
		try {
			String statement = "CREATE DATABASE ?;";
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, name);
			success =  pSta.execute();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return success;
	}
	public boolean createMasterUser(String username, String password) throws ClassNotFoundException{
		String statement = "INSERT INTO users (name, password, permission) VALUES (?, ?, \'Administrator\');";
		PreparedStatement pSta;
		try {
			pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, username);
			pSta.setString(2, password);
			if(pSta.execute())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		/*
		try{
			
			Connection conn = makeNewConnection("stagbar", "Nkucsc440", company);
			
			String statement = "SELECT * FROM mysql.user WHERE user = ?;";
			PreparedStatement pSta = conn.prepareStatement(statement);
			pSta.setString(1, username);
			ResultSet checkResults = pSta.executeQuery();
			boolean exists;
			if(checkResults.next()){
				exists = true;
			}
			else {
				exists = false;
			}
			System.out.println(exists);
			if(exists == false){
				String createUserStatement = "CREATE USER ? @'%' IDENTIFIED BY ?;";
				pSta = conn.prepareStatement(createUserStatement);
				pSta.setString(1, username);
				pSta.setString(2, password);
				pSta.execute();
				
				String grantPrivStatement = "GRANT ALL PRIVILEGES ON *.* TO ? @'%' IDENTIFIED BY ?;";
				pSta = conn.prepareStatement(grantPrivStatement);
				pSta.setString(1, username);
				pSta.setString(2, password);
				pSta.execute();
				
				conn.close();
				return true;
			}
			conn.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
		return false;
	*/
	}
	public boolean createGuestUser(String username, String password) throws ClassNotFoundException{
		String statement = "INSERT INTO users (name, password, permission) VALUES (?, ?, \'Guest\');";
		PreparedStatement pSta;
		try {
			pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, username);
			pSta.setString(2, password);
			if(pSta.execute())
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	//method to delete a user from the database
	protected boolean deleteUser(String username){
		String statement = "DELETE FROM users WHERE username = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, username);
			if(pSta.execute()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		/*
		boolean succesful = false;
		try{
			
			Connection conn = makeNewConnection("stagbar", "Nkucsc440");
			String statement = "DROP USER ?;";
			PreparedStatement pSta = conn.prepareStatement(statement);
			pSta.setString(1, username);
			succesful = pSta.execute();
			
			conn.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
		return succesful;*/
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
	 * Checks database for given drink.
	 *
	 * @param drinkName Drink to check database for.
	 * @return <code>true</code> if given drink is in the database,
	 * <code>false</code> otherwise.
	 * @throws RuntimeException If user's database connection is closed.
	 */
	public boolean doesDrinkExist(String drinkName) {
		
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
		String statement = "SELECT * FROM users WHERE username = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, username);
			ResultSet checkResults = pSta.executeQuery();
			return checkResults.next();
		} catch(SQLException e) {
			throw new RuntimeException("Connection to database has been closed.", e);
		}
	}

	public Set<Alcohol> findActiveAlcoholByType(CustomAlcoholType type, LocalDate startDate, LocalDate endDate) {
		return ConnectMock.findActiveAlcoholByType(type, startDate, endDate);
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
		String statement = "SELECT * FROM alcohol WHERE NAME = ?;";
		ResultSet result;
		ResultSet result2;
		
		Alcohol a;
		//might need changed
		try {
			PreparedStatement pSta = activeConnection.prepareStatement(statement);
			pSta.setString(1, name);
			result = pSta.executeQuery();
			statement = "SELECT * FROM type WHERE typeId = " + result.getInt("alcoholId") + ";";
			pSta = activeConnection.prepareStatement(statement);
			result2 = pSta.executeQuery();
			if(result != null){
				a = new Alcohol(result.getInt("alcoholId"), result.getString("name"), new CustomAlcoholType(result2.getInt("typeId"), result2.getString("name"), AlcoholType.valueOf(result2.getString("kind"))), result.getDate("creationDate").toLocalDate(), result.getDate("retireDate").toLocalDate());
				return a;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Set<Alcohol> findAllAlcohol() {
		String statement = "SELECT * FROM alcohol;";
		ResultSet result;
		try {
			Statement s = activeConnection.createStatement();
			result = s.executeQuery(statement);
			if(result != null){
				return (Set<Alcohol>) result;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return null;
	}

	/** Pulls all custom types from database. */
	public Set<CustomAlcoholType> findAllCustomAlcoholTypes() {
		String statement = "SELECT * FROM type";
		Set<CustomAlcoholType> set = null;
		ResultSet result;
		try {
			Statement s = activeConnection.createStatement();
			result = s.executeQuery(statement);
			while(result.next()){
				set.add(new CustomAlcoholType(result.getInt("typeId"), result.getString("name"), AlcoholType.valueOf(result.getString("kind"))));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return set;
	}

	/** Searches database for all mixed drinks and their corresponding ingredients. */
	public Set<MixedDrink> findAllMixedDrinksAndIngredients() {
		//TODO: Find all mixed drinks
		//TODO: Find ingredients for each mixed drink
		//TODO: Create and return mixed drink set
		return ConnectMock.findAllMixedDrinksAndIngredients();
	}

	/** Retrieves all users from database. */
	public Set<User> findAllUsers() {
		String sql = "Select * from users;";
		ResultSet results;
		Set<User> set = null;
		try {
			Statement s = getActiveConnection().createStatement();
			results = s.executeQuery(sql);
			while(results.next()){
				set.add(new User(results.getString(1), PermissionLevel.valueOf(results.getString(3))));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
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
		Connection c = null;
		c= makeNewConnection("stagbar", "Nkucsc440", database);
		return c;
	}

	/** Sets retire date for given alcohol. */
	public boolean retireAlcohol(int alcoholId, LocalDate date) {
		String statement = "UPDATE alcohol SET retiredate = ? WHERE alcoholId = ?;";
		try {
			PreparedStatement pSta = activeConnection.prepareStatement(statement);
			pSta.setDate(1, Date.valueOf(date));
			pSta.setInt(2, alcoholId);
			return pSta.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
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
		String statement = "INSERT INTO alcohol(name, type, creationDate, retireDate) VALUES (?, ?, ?, ?);";
		try {
			PreparedStatement pSta = activeConnection.prepareStatement(statement);
			pSta.setString(1, alcohol.getName());
			pSta.setInt(2, alcohol.getType().getTypeId());
			pSta.setDate(3, Date.valueOf(alcohol.getCreationDate()));
			pSta.setDate(4, Date.valueOf(alcohol.getRetireDate()));
			return pSta.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}

	/** Creates a new row on the Delivery table with the given entry. */
	public boolean saveDeliveryEntry(Entry entry) {
		return saveEntry(EntryTable.DELIVERY, entry);
	}

	private boolean saveEntry(EntryTable table, Entry entry) {
		//TODO: Save to database. We should be able to use a single query to update all 3 tables.
		// Use the toString method on 'table' to get the table name
		// then add each of the values from 'entry' to the appropriate column.

		System.out.println("Saving to " + table + ": " + entry);

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

