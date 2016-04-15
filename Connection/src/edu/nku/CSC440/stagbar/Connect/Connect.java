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
	private static final Connect connect = new Connect();
	private static final Logger log = Logger.getLogger(Connect.class.getName());
	private Connection activeConnection;
	private String databaseName;

	private Connect() {}

	public static Connect getInstance() {
		return connect;
	}

	public static void main(String args[]){

		getInstance().retireAlcohol(12, LocalDate.now());
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
		String sql = "DELETE FROM mixedDrinkIngredients WHERE drink = ? AND alcoholId = ?;";
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
			log.info("Deleted user: " + username);
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Checks database for an alcohol with given name & type that is active (not retired as of given date, inclusive).
	 *
	 * @return <code>true</code> if specified alcohol is defined in database and is active, <code>false</code> otherwise.
	 */
	public boolean doesActiveAlcoholExist(String name, CustomAlcoholType type, LocalDate date) {
		String statement = "SELECT * FROM alcohol WHERE name = ? AND typeId = ? AND (retireDate IS NULL OR retireDate <= ?);";
		ResultSet result;

		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, name);
			pSta.setInt(2, type.getTypeId());
			pSta.setDate(3, Date.valueOf(date));
			result = pSta.executeQuery();

			if(result.next()) {
				return true;
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
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
		String sql = "SELECT * FROM mixedDrink WHERE name = ?;";
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
		String sql = "SELECT a.alcoholId, a.name, a.typeId, a.creationDate, a.retireDate, t.typeId, t.name, t.kind FROM alcohol a, type t WHERE t.typeId = ? AND a.typeId = t.typeId AND a.creationDate >= ? AND (a.retireDate IS NULL OR a.retireDate <= ?);";

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
		String sql = "SELECT a.alcoholId, a.name, a.typeId, a.creationDate, a.retireDate, t.typeId, t.name, t.kind FROM alcohol a, type t WHERE a.typeId = t.typeId AND a.creationDate >= ? AND (a.retireDate IS NULL OR a.retireDate <= ?);";

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

	public Set<Alcohol> findAllAlcohol() {
		String statement = "SELECT a.alcoholId, a.name, a.typeId, a.creationDate, a.retireDate, t.typeId, t.name, t.kind FROM alcohol a, type t WHERE a.typeId = t.typeId;";
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
		String sql = "SELECT name FROM mixedDrink;";
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
		String sql = "SELECT mi.*, a.*, d.*, t.* FROM mixedDrinkIngredients mi, alcohol a, mixedDrink d, type t WHERE mi.drink = d.name AND mi.alcoholId = a.alcoholId AND a.typeId = t.typeId ORDER BY d.name;";
		ResultSet results;

		Map<String, MixedDrink> mdMap = new HashMap<>();
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			results = pSta.executeQuery();
			MixedDrink md;

			while(results.next()) {
				md = mdMap.get(results.getString(1));
				if(md == null) {
					md = new MixedDrink(results.getString(9), results.getBoolean(10));
					mdMap.put(results.getString(1), md);
				}
				Alcohol a = new Alcohol(results.getInt(4), results.getString(5), new CustomAlcoholType(results.getInt(11), results.getString(12), AlcoholType.valueOf(results.getString(13))), results.getDate(7).toLocalDate(), results.getDate(8) == null ? null : results.getDate(8).toLocalDate());
				MixedDrinkIngredient i = new MixedDrinkIngredient(a, results.getDouble(3));
				md.addIngredientFromDatabase(i);
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return new HashSet<>(mdMap.values());
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
		String sql = "SELECT mi.*, a.*, d.*, t.* FROM mixedDrinkIngredients mi, alcohol a, mixedDrink d, type t WHERE mi.drink = d.name AND mi.alcoholId = a.alcoholId AND a.typeId = t.typeId AND mi.drink = ? ORDER BY d.name;";
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
					md = new MixedDrink(results.getString(9), results.getBoolean(10));
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
				String statement = "CREATE TABLE user (username VARCHAR(40), password VARCHAR(128), permission CHAR(5), PRIMARY KEY(username));";
				sta.execute(statement);
				statement = "INSERT INTO user (username, password, permission) VALUES (?, ?, ?);";
				PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
				pSta.setString(1, userName);
				pSta.setString(2, password);
				pSta.setString(3, PermissionLevel.ADMIN.name());
				pSta.execute();
				//creating the type other tables
				statement = "CREATE TABLE type (typeId INT NOT NULL AUTO_INCREMENT, name VARCHAR(40) NOT NULL, kind VARCHAR(10) NOT NULL, PRIMARY KEY(typeId));";
				sta.execute(statement);
				statement = "CREATE TABLE alcohol (alcoholId INT NOT NULL AUTO_INCREMENT, name VARCHAR(40) NOT NULL, typeId INT, creationDate DATE NOT NULL, retireDate DATE, PRIMARY KEY(alcoholId), FOREIGN KEY(typeId) REFERENCES type(typeId));";
				sta.execute(statement);
				statement = "CREATE TABLE entry (entryId INT NOT NULL AUTO_INCREMENT, category VARCHAR(9) NOT NULL, alcoholId INT, amount DOUBLE, bottles INT, date DATE NOT NULL, PRIMARY KEY(entryId), FOREIGN KEY (alcoholId) REFERENCES alcohol(alcoholId));";
				sta.execute(statement);
				statement = "CREATE TABLE mixedDrink (name VARCHAR(40) NOT NULL, isRetired BOOLEAN NOT NULL, PRIMARY KEY(name));";
				sta.execute(statement);
				statement = "CREATE TABLE mixedDrinkIngredients (drink VARCHAR(40), alcoholId INT, amount DOUBLE NOT NULL, FOREIGN KEY(drink) REFERENCES mixedDrink(name), FOREIGN KEY(alcoholId) REFERENCES alcohol(alcoholId), PRIMARY KEY(drink, alcoholId));";
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

	public String getDatabaseName() {
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

	/** Unrefined method. Currently used for JUnit testing ONLY. */
	public boolean nukeDatabase(String name) {
		boolean success = false;
		try {
			String statement = "DROP DATABASE IF EXISTS " + name + ";";
			PreparedStatement pSta = makeNewMasterConnection(null).prepareStatement(statement);
			log.info(pSta.toString());
			pSta.execute();
			success = true;
		} catch(Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		databaseName = null;
		activeConnection = null;

		return success;
	}

	/** Sets retire date for given alcohol. */
	public boolean retireAlcohol(int alcoholId, LocalDate date) {
		String statement = "UPDATE alcohol SET retireDate = ? WHERE alcoholId = ?;";
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
	public boolean retireMixedDrink(String mixedDrink, boolean isRetired) {
		String sql = "UPDATE mixedDrink SET isRetired = ? WHERE name = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setBoolean(1, isRetired);
			pSta.setString(2, mixedDrink);
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
		String statement = "INSERT INTO alcohol(name, typeId, creationDate, retireDate) VALUES (?, ?, ?, ?);";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, alcohol.getName());
			pSta.setInt(2, alcohol.getType().getTypeId());
			pSta.setDate(3, Date.valueOf(alcohol.getCreationDate()));
			if(alcohol.getRetireDate() != null) { pSta.setDate(4, Date.valueOf(alcohol.getRetireDate())); }
			else { pSta.setDate(4, null); }
			pSta.execute();

			log.info("Saved " + alcohol.print());

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

			log.info("Saved: " + type.print());

			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Creates a new row on the Delivery table with the given entry. */
	public boolean saveDeliveryEntry(Entry entry) {
		return saveEntry(EntryCategory.DELIVERY, entry);
	}

	private boolean saveEntry(EntryCategory category, Entry entry) {
		log.info("Saving to " + category + ": " + entry);

		String sql = "INSERT INTO entry (category, alcoholId, amount, bottles, date) VALUES (?, ?, ?, ?, ?);";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, category.name());
			pSta.setInt(2, entry.getAlcoholId());
			pSta.setDouble(3, entry.getAmount());
			pSta.setInt(4, entry.getBottles());
			pSta.setDate(5, Date.valueOf(entry.getDate()));
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Creates a new row on the Inventory table with the given entry. */
	public boolean saveInventoryEntry(Entry entry) {
		return saveEntry(EntryCategory.INVENTORY, entry);
	}

	/** Creates a new mixed drink with given name and no retire date. */
	public boolean saveMixedDrink(String name) {
		String sql = "INSERT INTO mixedDrink (name, isRetired) VALUES (?, FALSE)";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, name);
			pSta.execute();

			log.info("Saved: " + name);
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Creates a new mixed drink ingredient for drink with given name. */
	public boolean saveMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol, Double amount) {
		String sql = "INSERT INTO mixedDrinkIngredients (drink, alcoholId, amount) VALUES (?, ?, ?);";
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
		return saveEntry(EntryCategory.SALES, entry);
	}

	public boolean saveUser(String username, String password, PermissionLevel permissionLevel) {
		String statement = "INSERT INTO user (username, password, permission) VALUES (?, ?, ?);";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement);
			pSta.setString(1, username);
			pSta.setString(2, password);
			pSta.setString(3, permissionLevel.name());
			pSta.execute();

			log.info("Saving: " + username + ", " + permissionLevel);
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Updates given ingredient for given drink with given value. */
	public boolean updateMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol, Double amount) {
		String sql = "UPDATE mixedDrinkIngredients SET amount = ? WHERE drink = ? AND alcoholId = ?;";
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

			log.info("Password updated for: " + username);
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	/** Updates permissions for given user to given value. */
	public boolean updateUserPermissions(String username, PermissionLevel permissionLevel) {
		String sql = "UPDATE user SET permission = ? WHERE username = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, permissionLevel.name());
			pSta.setString(2, username);
			pSta.execute();

			log.info("Permissions updated: " + username + ", " + permissionLevel.name());
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	private enum EntryCategory {
		INVENTORY, DELIVERY, SALES
	}
}
