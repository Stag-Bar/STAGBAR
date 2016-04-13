package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.dataaccess.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {

	private static final String DATABASE_URL = "jdbc:mysql://stagbar2.cgef59ufduu4.us-west-2.rds.amazonaws.com:3306";
	private static final Logger log = Logger.getLogger(Connect.class.getName());
	private static Connect connect = new Connect();
	private Connection activeConnection;
	private String databaseName;


	private Connect() {}

	public static Connect getInstance() {
		return connect;
	}

	public boolean authenticateUser(String username, String password) {
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?;";
		ResultSet results;
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, username);
			pSta.setString(2, password);
			results = pSta.executeQuery();
			if(results.next()) {
				return true;
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	//method to create a new user that has full privileges.  IE the inventory manager
	private boolean createDatabase(String name) {
		boolean success = false;
		try {
			String statement = "CREATE DATABASE " + name + ";";
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			log.info(pSta.toString());
			pSta.execute();
			success = true;
		} catch(Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		databaseName = name;
		activeConnection = null;

		return success;
	}

	/** Deletes given ingredient for given drink. */
	public boolean deleteMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol) {
		String sql = "DELETE FROM mixeddrinkingredients WHERE drink = ? AND ingredientid = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, mixedDrinkName);
			pSta.setInt(2, alcohol.getAlcoholId());
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Delete user from database */
	public boolean deleteUser(String username) {
		String statement = "DELETE FROM user WHERE username = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, username);
			pSta.execute();
			return true;
		} catch(SQLException e) {
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
		String sql = "SELECT * FROM mixeddrink WHERE name = ?;";
		ResultSet results;
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, drinkName);
			results = pSta.executeQuery();
			if(results.next()) {
				return true;
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
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
		String sql = "SELECT a.alcoholid, a.name, a.typeid, a.creationDate, a.retireDate, t.typeid, t.name, t.kind FROM alcohol a, type t WHERE t.typeid = ? AND a.typeid = t.typeid AND a.creationDate >= ? AND (a.retireDate IS NULL OR a.retireDate <= ?);";

		Set<Alcohol> set = new HashSet<>();
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			ResultSet results;
			pSta.setInt(1, type.getTypeId());
			pSta.setDate(2, Date.valueOf(startDate));

			pSta.setDate(3, Date.valueOf(endDate));
			results = pSta.executeQuery();
			while(results.next()) {
				set.add(new Alcohol(results.getInt(1), results.getString(2), new CustomAlcoholType(results.getInt(3), results.getString(7), AlcoholType.valueOf(results.getString(8))), results.getDate(4).toLocalDate(), results.getDate(5) == null ? null : results.getDate(5).toLocalDate()));
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return set;
	}

	/** Searches database for alcohol whose retire date is null or after the start date & whose creation date is before the end date. */
	public Set<Alcohol> findActiveAlcoholForDateRange(LocalDate startDate, LocalDate endDate) {
		String sql = "SELECT a.alcoholid, a.name, a.typeid, a.creationDate, a.retireDate, t.typeid, t.name, t.kind FROM alcohol a, type t WHERE a.typeid = t.typeid AND a.creationDate >= ? AND (a.retireDate IS NULL OR a.retireDate <= ?);";

		Set<Alcohol> set = new HashSet<>();
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			ResultSet results;
			pSta.setDate(1, Date.valueOf(startDate));
			pSta.setDate(2, Date.valueOf(endDate));
			results = pSta.executeQuery();
			while(results.next()) {
				set.add(new Alcohol(results.getInt(1), results.getString(2), new CustomAlcoholType(results.getInt(3), results.getString(7), AlcoholType.valueOf(results.getString(8))), results.getDate(4).toLocalDate(), results.getDate(5) == null ? null : results.getDate(5).toLocalDate()));
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return set;
	}

	/**
	 * Searches database for an Alcohol with given name.
	 *
	 * @param name
	 * @return Alcohol with given name if found, otherwise returns <code>null</code>.
	 */
	public Alcohol findAlcoholByName(String name) {
		String statement = "SELECT a.alcoholId, a.name, a.typeid, a.creationdate, a.retiredate, t.typeid, t.name, t.kind FROM alcohol a, type t WHERE a.alcoholId = t.typeid AND a.name = ?;";
		ResultSet result;

		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, name);
			result = pSta.executeQuery();

			if(result != null) {
				return new Alcohol(result.getInt(1), result.getString(2), new CustomAlcoholType(result.getInt(3), result.getString(7), AlcoholType.valueOf(result.getString(8))), result.getDate(4).toLocalDate(), result.getDate(5) == null ? null : result.getDate(5).toLocalDate());
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return null;
	}

	public Set<Alcohol> findAllAlcohol() {
		String statement = "SELECT a.alcoholid, a.name, a.typeid, a.creationDate, a.retireDate, t.typeid, t.name, t.kind FROM alcohol a, type t WHERE a.typeid = t.typeid;";
		ResultSet result;
		Set<Alcohol> set = new HashSet<>();

		try {
			Statement s = getActiveConnection().createStatement();
			result = s.executeQuery(statement);

			while(result.next()) {
				set.add(new Alcohol(result.getInt(1), result.getString(2), new CustomAlcoholType(result.getInt(6), result.getString(7), AlcoholType.valueOf(result.getString(8))), result.getDate(4).toLocalDate(), result.getDate(5) == null ? null : result.getDate(5).toLocalDate()));
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return set;
	}

	/** Pulls all custom types from database. */
	public Set<CustomAlcoholType> findAllCustomAlcoholTypes() {
		String statement = "SELECT * FROM type;";
		Set<CustomAlcoholType> set = new HashSet<>();
		ResultSet result;
		try {
			Statement s = getActiveConnection().createStatement();
			result = s.executeQuery(statement);
			while(result.next()) {
				set.add(new CustomAlcoholType(result.getInt(1), result.getString(2), AlcoholType.valueOf(result.getString(3))));
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return set;
	}

	public Set<String> findAllMixedDrinkNames() {
		String sql = "SELECT name FROM mixeddrink;";
		ResultSet results;
		Set<String> set = new HashSet<>();

		try {
			Statement s = getActiveConnection().createStatement();
			results = s.executeQuery(sql);
			while(results.next()) {
				set.add(results.getString(1));
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return set;
	}

	/** Searches database for all mixed drinks and their corresponding ingredients. */
	public Set<MixedDrink> findAllMixedDrinksAndIngredients() {
		String sql = "SELECT mi.*, a.*, d.*, t.* FROM mixeddrinkingredients mi, alcohol a, mixeddrink d, type t WHERE mi.drink = d.name AND ingredientid = alcoholid AND a.typeid = t.typeid ORDER BY d.name;";
		ResultSet results;

		Map<String, MixedDrink> mdMap = new HashMap<>();
		try {
			Statement s = getActiveConnection().createStatement();
			results = s.executeQuery(sql);
			MixedDrink md = null;

			while(results.next()) {
				md = mdMap.get(results.getString(1));
				if(md == null) {
					md = new MixedDrink(results.getString(9), results.getDate(10) == null ? null : results.getDate(10).toLocalDate());
					mdMap.put(results.getString(1), md);
				}
				Alcohol a = new Alcohol(results.getInt(4), results.getString(5), new CustomAlcoholType(results.getInt(11), results.getString(12), AlcoholType.valueOf(results.getString(13))), results.getDate(7).toLocalDate(), results.getDate(8) == null ? null : results.getDate(8).toLocalDate());
				MixedDrinkIngredient i = new MixedDrinkIngredient(a, results.getDouble(3));
				md.addIngredientFromDatabase(i);

			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return new HashSet<MixedDrink>(mdMap.values());
	}

	/** Retrieves all users from database. */
	public Set<User> findAllUsers() {
		String sql = "SELECT username, permission FROM user;";
		ResultSet results;
		Set<User> set = new HashSet<>();
		try {
			Statement s = getActiveConnection().createStatement();
			results = s.executeQuery(sql);
			while(results.next()) {
				set.add(new User(results.getString(1), PermissionLevel.valueOf(results.getString(2))));
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return set;
	}

	public MixedDrink findMixedDrinkIngredientsByName(String drinkName) {
		String sql = "SELECT mi.*, a.*, d.* FROM mixeddrinkingredients mi, alcohol a, mixeddrink d WHERE mi.drink = ? AND mi.drink = d.name AND ingredientid = alcoholid;";
		ResultSet results;
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, drinkName);
			results = pSta.executeQuery();
			MixedDrink md = null;
			Map<String, MixedDrink> mdMap = new HashMap<>();
			while(results.next()) {
				md = mdMap.get(results.getString(1));
				if(md == null) {
					md = new MixedDrink(results.getString(9), results.getDate(10).toLocalDate());
					mdMap.put(results.getString(1), md);
				}
				Alcohol a = new Alcohol(results.getInt(4), results.getString(5), new CustomAlcoholType(results.getInt(11), results.getString(12), AlcoholType.valueOf(results.getString(13))), results.getDate(7).toLocalDate(), results.getDate(8) == null ? null : results.getDate(8).toLocalDate());
				MixedDrinkIngredient i = new MixedDrinkIngredient(a, results.getDouble(3));
				md.addIngredientFromDatabase(i);
			}
			return md;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return null;
	}

	public PermissionLevel findPermissionsForUser(String username) {
		String sql = "SELECT username, permission FROM user WHERE username = ?;";
		ResultSet results;
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, username);
			results = pSta.executeQuery();
			if(results.next()) {
				return PermissionLevel.valueOf(results.getString(2));
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return null;
	}

	/** Creates the user and necessary tables. */
	public void firstTimeSetup(String database, String userName, String password) {
		if(createDatabase(database)) {
			try {
				//creating user table and adding the first user
				Statement sta = getActiveConnection().createStatement();
				String statement = "CREATE TABLE user (username VARCHAR(50), password VARCHAR(128), permission VARCHAR(5), PRIMARY KEY(username));";
				sta.execute(statement);
				statement = "INSERT INTO user (username, password, permission) VALUES (?, ?, \'Administrator\');";
				PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
				pSta.setString(1, userName);
				pSta.setString(2, password);
				pSta.execute();
				//creating the type other tables
				statement = "CREATE TABLE type (typeid INT NOT NULL AUTO_INCREMENT, name VARCHAR(40), kind VARCHAR(10), PRIMARY KEY(typeid));";
				sta.execute(statement);
				statement = "CREATE TABLE alcohol (alcoholid INT NOT NULL AUTO_INCREMENT, name VARCHAR(40), typeid INT, creationDate DATE, retireDate DATE, PRIMARY KEY(alcoholid), FOREIGN KEY(typeid) REFERENCES type(typeid));";
				sta.execute(statement);
				statement = "CREATE TABLE inventory (entryid INT NOT NULL AUTO_INCREMENT, alcohol INT, amount DOUBLE, bottles INT, date DATE, PRIMARY KEY(entryid), FOREIGN KEY (alcohol) REFERENCES alcohol(alcoholid));";
				sta.execute(statement);
				statement = "CREATE TABLE sales (entryid INT NOT NULL AUTO_INCREMENT, alcohol INT, amount DOUBLE, bottles INT, date DATE, PRIMARY KEY(entryid), FOREIGN KEY(alcohol) REFERENCES alcohol(alcoholid));";
				sta.execute(statement);
				statement = "CREATE TABLE delivery (entryid INT NOT NULL AUTO_INCREMENT, alcohol INT, amount DOUBLE, bottles INT, date DATE, PRIMARY KEY(entryid), FOREIGN KEY(alcohol) REFERENCES alcohol(alcoholid));";
				sta.execute(statement);
				statement = "CREATE TABLE mixeddrink (name VARCHAR(40), retiredate DATE, PRIMARY KEY(name));";
				sta.execute(statement);
				statement = "CREATE TABLE mixeddrinkingredients (drink VARCHAR(40), ingredientid INT, amount DOUBLE, FOREIGN KEY(drink) REFERENCES mixeddrink(name), FOREIGN KEY(ingredientid) REFERENCES alcohol(alcoholid), PRIMARY KEY(drink, ingredientid));";
				sta.execute(statement);

			} catch(SQLException e) {
				log.log(Level.SEVERE, e.toString(), e);
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
	private String getDatabaseNameFromFile() { //TODO: Get database name from file
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
		String statement = "UPDATE alcohol SET retiredate = ? WHERE alcoholId = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setDate(1, Date.valueOf(date));
			pSta.setInt(2, alcoholId);
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Sets retire date for given mixed drink. */
	public boolean retireMixedDrink(String mixedDrink, LocalDate date) {
		String sql = "UPDATE mixeddrink SET retireDate = ? WHERE name = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, mixedDrink);
			pSta.setDate(2, Date.valueOf(date));
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/**
	 * Saves a new Alcohol record.
	 * Should be written in a way that can be called effectively once or in a loop.
	 *
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	public boolean saveAlcohol(Alcohol alcohol) {
		String statement = "INSERT INTO alcohol(name, typeid, creationDate, retireDate) VALUES (?, ?, ?, ?);";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, alcohol.getName());
			pSta.setInt(2, alcohol.getType().getTypeId());
			pSta.setDate(3, Date.valueOf(alcohol.getCreationDate()));
			if(alcohol.getRetireDate() != null) { pSta.setDate(4, Date.valueOf(alcohol.getRetireDate())); }
			else { pSta.setDate(4, null); }
			pSta.execute();

			return true;
		} catch(SQLException e) {
			log.severe("Erroneous alcohol: " + alcohol.print());
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	public boolean saveCustomAlcoholType(CustomAlcoholType type) {
		String sql = "INSERT INTO type (name, kind) VALUES (?, ?);";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, type.getName());
			pSta.setString(2, type.getKind().name());
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Creates a new row on the Delivery table with the given entry. */
	public boolean saveDeliveryEntry(Entry entry) {
		return saveEntry(EntryTable.DELIVERY, entry);
	}

	private boolean saveEntry(EntryTable table, Entry entry) {
		log.info("Saving to " + table + ": " + entry);

		String sql = String.format("INSERT INTO %s (alcohol, amount, bottles, date) VALUES (?, ?, ?, ?);", table.toString());
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setInt(1, entry.getAlcoholId());
			pSta.setDouble(2, entry.getAmount());
			pSta.setInt(3, entry.getBottles());
			pSta.setDate(4, Date.valueOf(entry.getDate()));
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Creates a new row on the Inventory table with the given entry. */
	public boolean saveInventoryEntry(Entry entry) {
		return saveEntry(EntryTable.INVENTORY, entry);
	}

	/** Creates a new mixed drink with given name and no retire date. */
	public boolean saveMixedDrink(String name) {
		String sql = "INSERT INTO mixeddrink (name, retireDate) VALUES (?, NULL)";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, name);
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Creates a new mixed drink ingredient for drink with given name. */
	public boolean saveMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol, Double amount) {
		String sql = "INSERT INTO mixeddrinkingredients (drink, ingredientid, amount) VALUES (?, ?, ?);";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, mixedDrinkName);
			pSta.setInt(2, alcohol.getAlcoholId());
			pSta.setDouble(3, amount);
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Creates a new row on the Sales table with the given entry. */
	public boolean saveSalesEntry(Entry entry) {
		return saveEntry(EntryTable.SALES, entry);
	}

	public boolean saveUser(String username, String password, PermissionLevel permissionLevel) {
		String statement = "INSERT INTO user (username, password, permission) VALUES (?, ?, ?);";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, username);
			pSta.setString(2, password);
			pSta.setString(3, permissionLevel.name());
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Updates given ingredient for given drink with given value. */
	public boolean updateMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol, Double amount) {
		String sql = "UPDATE mixeddrinkingredients SET amount = ? WHERE drink = ? AND ingredientid = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setDouble(1, amount);
			pSta.setString(2, mixedDrinkName);
			pSta.setInt(3, alcohol.getAlcoholId());
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
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
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
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
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
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
