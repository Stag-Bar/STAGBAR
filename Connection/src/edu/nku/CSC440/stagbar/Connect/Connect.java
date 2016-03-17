package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;

import java.sql.*;

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
			System.out.println(e);
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
			System.out.println(e);
		}
		return false;
	}

	/**
	 * @return <code>true</code> if given username & password can connect to database
	 * @deprecated Temporary method. Unsecure.
	 * User does not need connection if we use a single login from system to database.
	 */
	public boolean createUserConnection(String username, String password) {
//		activeConnection = makeNewConnection(username, password, getDatabaseName());
		activeConnection = makeNewMasterConnection(getDatabaseName()); // TODO: Delete line after testing.
		return activeConnection != null;
	}

	/**
	 * Delete given user from the database.
	 *
	 * @param username User to delete.
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	protected boolean deleteUser(String username) {
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
	private Connection makeNewMasterConnection(String database){
		return makeNewConnection("stagbar", "Nkucsc440", database);
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

	/**
	 * Updates an existing Alcohol record.
	 * Should be written in a way that can be called effectively once or in a loop.
	 *
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	public boolean updateAlcohol(Alcohol alcohol) {
		//TODO: Update in database
		return true;
	}

}

