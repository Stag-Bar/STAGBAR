package edu.nku.CSC440.stagbar.dataaccess.databaseConnection;

import edu.nku.CSC440.stagbar.dataaccess.data.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect implements Database {

	/** @deprecated Alter to change database. Remove once first time setup UI is complete. */
	private static final String CURRENT_WORKING_DATABASE = "test17";
	private static final String DATABASE_URL = "jdbc:mysql://stagbar2.cgef59ufduu4.us-west-2.rds.amazonaws.com:3306";
	private static final Connect connect = new Connect();
	private static final Logger log = Logger.getLogger(Connect.class.getName());
	private Connection activeConnection;
	private String databaseName;

	private Connect() {}

	public static Connect getInstance() {
		return connect;
	}

	@Override
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

	@Override
	public boolean deleteMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol) {
		String sql = "DELETE FROM mixedDrinkIngredients WHERE drink = ? AND alcoholId = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, mixedDrinkName);
			pSta.setInt(2, alcohol.getAlcoholId());
			log.info(pSta.toString());
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	@Override
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

	@Override
	public boolean doesActiveAlcoholExist(String name, CustomAlcoholType type, LocalDate date) {
		String statement = "SELECT * FROM alcohol WHERE name = ? AND typeId = ? AND (retireDate IS NULL OR retireDate > ?);";
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

	@Override
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

	@Override
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

	@Override
	public Set<Alcohol> findActiveAlcohol(LocalDate startDate, LocalDate endDate) {
		String sql = "SELECT a.alcoholId, a.name, a.typeId, a.creationDate, a.retireDate, t.typeId, t.name, t.kind FROM alcohol a, type t WHERE a.typeId = t.typeId AND a.creationDate <= ? AND (a.retireDate IS NULL OR a.retireDate > ?);";

		Set<Alcohol> set = new HashSet<>();
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			ResultSet results;
			pSta.setDate(1, Date.valueOf(endDate));
			pSta.setDate(2, Date.valueOf(startDate));
			results = pSta.executeQuery();
			while(results.next()) {
				set.add(new Alcohol(results.getInt(1), results.getString(2), new CustomAlcoholType(results.getInt(3), results.getString(7), AlcoholType.valueOf(results.getString(8))), results.getDate(4).toLocalDate(), results.getDate(5) == null ? null : results.getDate(5).toLocalDate()));
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return set;
	}

	@Override
	public Set<Alcohol> findActiveAlcohol(LocalDate date) {
		return findActiveAlcohol(date, date);
	}

	@Override
	public Set<Alcohol> findActiveAlcoholByType(CustomAlcoholType type, LocalDate startDate, LocalDate endDate) {
		String sql = "SELECT a.alcoholId, a.name, a.typeId, a.creationDate, a.retireDate, t.typeId, t.name, t.kind FROM alcohol a, type t WHERE t.typeId = ? AND a.typeId = t.typeId AND a.creationDate <= ? AND (a.retireDate IS NULL OR a.retireDate > ?);";

		Set<Alcohol> set = new HashSet<>();
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			ResultSet results;
			pSta.setInt(1, type.getTypeId());
			pSta.setDate(2, Date.valueOf(endDate));
			pSta.setDate(3, Date.valueOf(startDate));
			results = pSta.executeQuery();
			while(results.next()) {
				set.add(new Alcohol(results.getInt(1), results.getString(2), new CustomAlcoholType(results.getInt(3), results.getString(7), AlcoholType.valueOf(results.getString(8))), results.getDate(4).toLocalDate(), results.getDate(5) == null ? null : results.getDate(5).toLocalDate()));
			}
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return set;
	}

	@Override
	public Set<Alcohol> findActiveAlcoholByType(CustomAlcoholType type, LocalDate date) {
		return findActiveAlcoholByType(type, date, date);
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
	public Set<MixedDrink> findAllMixedDrinks() {
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

	@Override
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

	@Override
	public MixedDrink findMixedDrinkByName(String drinkName) {
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

	@Override
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

	@Override
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
				statement = "CREATE TABLE sales (entryId INT NOT NULL AUTO_INCREMENT, alcoholId INT, amount DOUBLE, bottles INT, date DATE NOT NULL, PRIMARY KEY(entryId), FOREIGN KEY (alcoholId) REFERENCES alcohol(alcoholId));";
				sta.execute(statement);
				statement = "CREATE TABLE delivery (entryId INT NOT NULL AUTO_INCREMENT, alcoholId INT, amount DOUBLE, bottles INT, date DATE NOT NULL, PRIMARY KEY(entryId), FOREIGN KEY (alcoholId) REFERENCES alcohol(alcoholId));";
				sta.execute(statement);
				statement = "CREATE TABLE inventory (entryId INT NOT NULL AUTO_INCREMENT, alcoholId INT, amount DOUBLE, bottles INT, date DATE NOT NULL, PRIMARY KEY(entryId), FOREIGN KEY (alcoholId) REFERENCES alcohol(alcoholId));";
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
		if(!isConnectionValid()) // Lazy initialization in case databaseConnection is closed/corrupted.
		{ activeConnection = makeNewMasterConnection(getDatabaseName()); }

		return activeConnection;
	}

	@Override
	public String getDatabaseName() {
		if(null == databaseName) { databaseName = getDatabaseNameFromFile(); }

		return databaseName;
	}

	/** Gets database name from file. */
	private String getDatabaseNameFromFile() { //TODO: Get database name from file
//		return null; // For creating new database
//		return FileIO.readDatabaseName();
		return CURRENT_WORKING_DATABASE; // For testing current working database
	}

	private boolean isConnectionValid() {
		try {
			return null != activeConnection && activeConnection.isValid(300);
		} catch(SQLException e) {
			return false;
		}
	}

	/**
	 * Make a databaseConnection to the database.
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

	/** Connect to database as master user. */
	private Connection makeNewMasterConnection(String database) {
		return makeNewConnection("stagbar", "Nkucsc440", database);
	}

	/** Unrefined method. Currently used for JUnit testing ONLY. */
	protected boolean nukeDatabase(String name) {
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

	/** Unrefined method. Currently used for JUnit testing ONLY. */
	protected boolean nukeTable(String name) {
		boolean success = false;
		String statement_Delete = "DELETE FROM " + name + ";";
		String statement_Reset = "ALTER TABLE " + name + " AUTO_INCREMENT = 1;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(statement_Delete);
			log.info(pSta.toString());
			pSta.execute();

			pSta = getActiveConnection().prepareStatement(statement_Reset);
			log.info(pSta.toString());
			pSta.execute();

			success = true;
		} catch(Exception e) {
			log.log(Level.SEVERE, e.toString(), e);
		}

		return success;
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
	public boolean saveDeliveryEntry(Entry entry) {
		return saveEntry(EntryCategory.DELIVERY, entry);
	}

	private boolean saveEntry(EntryCategory category, Entry entry) {
		log.info("Saving to " + category + ": " + entry);

		String sql = "INSERT INTO " + category.name().toLowerCase() + " (alcoholId, amount, bottles, date) VALUES (?, ?, ?, ?);";
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

	@Override
	public boolean saveInventoryEntry(Entry entry) {
		return saveEntry(EntryCategory.INVENTORY, entry);
	}

	@Override
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

	@Override
	public boolean saveMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol, Double amount) {
		String sql = "INSERT INTO mixedDrinkIngredients (drink, alcoholId, amount) VALUES (?, ?, ?);";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setString(1, mixedDrinkName);
			pSta.setInt(2, alcohol.getAlcoholId());
			pSta.setDouble(3, amount);
			log.info(pSta.toString());
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	@Override
	public boolean saveSalesEntry(Entry entry) {
		return saveEntry(EntryCategory.SALES, entry);
	}

	@Override
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

	@Override
	public boolean updateMixedDrinkIngredient(String mixedDrinkName, Alcohol alcohol, Double amount) {
		String sql = "UPDATE mixedDrinkIngredients SET amount = ? WHERE drink = ? AND alcoholId = ?;";
		try {
			PreparedStatement pSta = getActiveConnection().prepareStatement(sql);
			pSta.setDouble(1, amount);
			pSta.setString(2, mixedDrinkName);
			pSta.setInt(3, alcohol.getAlcoholId());
			log.info(pSta.toString());
			pSta.execute();
			return true;
		} catch(SQLException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
		return false;
	}

	@Override
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

	@Override
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

}
