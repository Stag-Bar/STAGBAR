package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Connect.mock.ConnectMock;
import edu.nku.CSC440.stagbar.dataaccess.*;
import edu.nku.CSC440.stagbar.dataaccess.mock.CustomAlcoholTypeMock;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
public class Connect {

	private static final String DATABASE_URL = "jdbc:mysql://stagbar2.cgef59ufduu4.us-west-2.rds.amazonaws.com:3306";
	private static Connect connect = new Connect();
	private Connection activeConnection;
	private String databaseName;

	private Connect() {}

	public static Connect getInstance() {
		return connect;
	}

	public static void main(String[] args) {
		getInstance().saveCustomAlcoholType(CustomAlcoholTypeMock.CRAFT_BEER); // Sample test insert
//		getInstance().firstTimeSetup("test11", "user", "password");
	}

	

	//method to create a new user that has full privileges.  IE the inventory manager
	private boolean createDatabase(String name) {

		boolean success = false;
		try {
			String statement = "CREATE DATABASE " + name + ";";
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			System.out.println(pSta.toString());
			pSta.execute();
			success = true;

		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		databaseName = name;
		activeConnection = null;

		return success;
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

	public boolean createUser(String username, String password, PermissionLevel permissionLevel) {
		String statement = "INSERT INTO user (username, password, permission) VALUES (?, ?, ?);";
		PreparedStatement pSta;
		try {
			pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, username);
			pSta.setString(2, password);
			pSta.setString(3, permissionLevel.name());
			System.out.println(pSta);
			pSta.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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

	//method to delete a user from the database
	public boolean deleteUser(String username) {
		String statement = "DELETE FROM user WHERE username = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, username);
			pSta.execute();
			return true;
			
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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
		String statement = "SELECT * FROM user WHERE username = ?;";
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
		String statement = "SELECT a.alcoholId, a.name, a.creationdate, a.retiredate, t.typeid, t.name, t.kind FROM alcohol a, type t WHERE a.alcoholId = t.typeid;";
		ResultSet result;
		ResultSet result2;

		Alcohol a;
		//might need changed
		try {
			
			PreparedStatement pSta = activeConnection.prepareStatement(statement);
			pSta.setString(1, name);
			result = pSta.executeQuery();
			statement = "SELECT * FROM type WHERE typeId = " + result.getInt("alcoholId") + ";";
			if(result != null){
				return new Alcohol(result.getInt("a.alcoholId"), result.getString("a.name"), new CustomAlcoholType(result.getInt("t.typeId"), result.getString("t.name"), AlcoholType.valueOf(result.getString("t.kind"))), result.getDate("a.creationDate").toLocalDate(), result.getDate("a.retireDate").toLocalDate());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Set<Alcohol> findAllAlcohol() {
		String statement = "SELECT a.alcoholid, a.name, a.type, a.creationDate, a.retireDate, t.typeid, t.name, t.kind FROM alcohol a, type t WHERE a.alcoholid = t.typeid;";
		ResultSet result;
		Set<Alcohol> set = new HashSet<>();
		
		try {
			Statement s = activeConnection.createStatement();
			result = s.executeQuery(statement);
			statement = "SELECT * FROM type WHERE typeid = " + result.getInt(3) + ";";
			s = activeConnection.createStatement();
			ResultSet result2 = s.executeQuery(statement);
			
			while(result.next()){
				set.add(new Alcohol(result.getInt("a.alcoholid"), result.getString("a.name"), new CustomAlcoholType(result.getInt("t.typeid"), result.getString("t.name"), AlcoholType.valueOf(result.getString("t.kind"))), result.getDate(4).toLocalDate(), result.getDate(5).toLocalDate()));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return set;
	}

	/** Pulls all custom types from database. */
	public Set<CustomAlcoholType> findAllCustomAlcoholTypes() {
		String statement = "SELECT * FROM type";
		Set<CustomAlcoholType> set = new HashSet<>();
		ResultSet result;
		try {
			Statement s = activeConnection.createStatement();
			result = s.executeQuery(statement);
			while(result.next()) {
				set.add(new CustomAlcoholType(result.getInt(1), result.getString(2), AlcoholType.valueOf(result.getString(3))));
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
		String sql = "Select * from user;";
		ResultSet results;
		Set<User> set = new HashSet<>();
		try {
			Statement s = getActiveConnection().createStatement();
			results = s.executeQuery(sql);
			while(results.next()){
				set.add(new User(results.getString(1), PermissionLevel.valueOf(results.getString(3))));
			}
		} catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
	}

	//first time setup will create set up the user and neccssary tables.
	public void firstTimeSetup(String database, String userName, String password) {
		if(createDatabase(database)) {
			try {
				//creating user table and adding the first user
				Statement sta = getActiveConnection().createStatement();
				String statement = "CREATE TABLE user (username VARCHAR(50), password VARCHAR(128), permission VARCHAR(15), PRIMARY KEY(username));";
				sta.execute(statement);
				statement = "INSERT INTO user (username, password, permission) VALUES (?, ?, \'Administrator\');";
				PreparedStatement pSta = activeConnection.prepareStatement(statement);
				pSta.setString(1, userName);
				pSta.setString(2, password);
				pSta.execute();
				//creating the type other tables
				statement = "CREATE TABLE type (typeid INT NOT NULL AUTO_INCREMENT, name VARCHAR(40), kind VARCHAR(10), PRIMARY KEY(typeid));";
				sta.execute(statement);
				statement = "CREATE TABLE alcohol (alcoholid int NOT NULL AUTO_INCREMENT, name varchar(40), typeid int, creationDate date, retireDate date, primary key(alcoholid), foreign key(typeid) REFERENCES type(typeid));";
				sta.execute(statement);
				statement = "CREATE TABLE inventory (entryid INT NOT NULL AUTO_INCREMENT, alcohol INT, amount DOUBLE, bottles INT, date DATE, PRIMARY KEY(entryid), FOREIGN KEY (alcohol) REFERENCES alcohol(alcoholid));";
				sta.execute(statement);
				statement = "CREATE TABLE sales (entryid int NOT NULL AUTO_INCREMENT, alcohol int, amount double, bottles int, date date, primary key(entryid), foreign key(alcohol) REFERENCES alcohol(alcoholid));";
				sta.execute(statement);
				statement = "CREATE TABLE delivery (entryid INT NOT NULL AUTO_INCREMENT, alcohol INT, amount DOUBLE, bottles INT, date DATE, PRIMARY KEY(entryid), FOREIGN KEY(alcohol) REFERENCES alcohol(alcoholid));";
				sta.execute(statement);
				statement = "CREATE TABLE mixeddrink (name VARCHAR(40), retiredate DATE, PRIMARY KEY(name));";
				sta.execute(statement);
				statement = "CREATE TABLE mixeddrinkingredients (drink VARCHAR(40), ingredientid INT, amount DOUBLE, FOREIGN KEY(drink) REFERENCES mixeddrink(name), FOREIGN KEY(ingredientid) REFERENCES alcohol(alcoholid));";
				sta.execute(statement);

			} catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
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
//		return null; // For creating new database
		return "test11"; // For testing current working database
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
			System.out.println(url);
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
			pSta.execute();
			return true;
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
			pSta.execute();
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return false;
	}

	private boolean saveCustomAlcoholType(CustomAlcoholType type) {
		String sql = "INSERT INTO type (name, kind) VALUES (?, ?);";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, type.getName());
			pSta.setString(2, type.getKind().name());
			pSta.execute();
			return true;
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
		String sql = "UPDATE user SET password = ? WHERE username = ?;";
		
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, password);
			pSta.setString(2, username);
			pSta.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

	/** Updates permissions for given user to given value. */
	public boolean updateUserPermissions(String username, PermissionLevel permissionLevel) {
		String sql = "UPDATE user SET permission = ? WHERE userName = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, permissionLevel.name());
			pSta.setString(2, username);
			pSta.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
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

