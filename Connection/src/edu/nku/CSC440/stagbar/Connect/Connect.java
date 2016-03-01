package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.Application;

import java.sql.*;
public class Connect {

	private static Connect connect = new Connect();
	private Connection activeConnection;

	private Connect(){}

	public static Connect getInstance(){
		return connect;
	}

	/**
	 * Make a connection to the database.
	 * @param username
	 * @param password
	 * @return Connection to database if successful, otherwise returns <code>null</code>.
	 */
	private Connection makeNewConnection(String username, String password) {
		return makeNewConnection(username, password, "");
	}

	/**
	 * Make a connection to the database.
	 * @param username
	 * @param password
	 * @param dataBaseName
	 * @return Connection to database if successful, otherwise returns <code>null</code>.
	 * @throws RuntimeException If mysql is not setup properly.
	 */
	private Connection makeNewConnection(String username, String password, String dataBaseName) {
		Connection conn;
		try{
			Class.forName("com.mysql.jdbc.Driver"); // What purpose does this line serve? It returns a class we never use.
			String url = "jdbc:mysql://stagbar2.cgef59ufduu4.us-west-2.rds.amazonaws.com:3306/" + dataBaseName;
			conn = DriverManager.getConnection(url, username, password);
		}
		catch(SQLException e){
			conn = null;
		} catch(ClassNotFoundException e){
			throw new RuntimeException("Check mysql setup!", e);
		}
		return conn;
	}

	/**
	 * @return <code>true</code> if given username & password can connect to database
	 * @deprecated Temporary method. Unsecure.
	 */
	public boolean createUserConnection(String username, String password){
		activeConnection = makeNewConnection(username, password, "test");
		if(activeConnection != null){
			Application.getInstance().setConnection(activeConnection);
			return true;
		}

		return false;
	}

	/**
	 * @param name Name of database to create.
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	public boolean createDatabase(String name){
		Connection c;
		boolean success = false;
		try {
			c = makeNewConnection("stagbar", "Nkucsc440");
			Statement sta = c.createStatement();
			success =  sta.execute("CREATE DATABASE " + name + ";");
			c.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * Create a new user that has full privileges.  IE the inventory manager
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	public boolean createMasterUser(String username, String password, String database) {
		try{
			//FIXME: Use existing connection.
			Connection conn = makeNewConnection("stagbar", "Nkucsc440", database);//have to connect to the database with an admin account
																		//to create a new user
			//Statement sta = conn.createStatement();
			boolean exists = doesUserExist(username, conn);
			PreparedStatement pSta;

			if(!exists){
				String createUserStatement = "CREATE USER ? @'%' IDENTIFIED BY ?;";
				pSta = conn.prepareStatement(createUserStatement);
				pSta.setString(1, username);
				pSta.setString(2, password);
				pSta.execute();

				String grantPrivStatement = "GRANT ALL PRIVILEGES ON " + database + " TO ? @'%' IDENTIFIED BY ?;";
				pSta = conn.prepareStatement(grantPrivStatement);
				pSta.setString(1, username);
				pSta.setString(2, password);
				pSta.execute();
				//sta.execute("create user " + "\'" + username + "\'@\'%\'" + " IDENTIFIED BY \'" + password + "\';" );
				//sta.execute("grant all privileges on *.* to \'" + username
					//+ "\'@\'%\' identified by \'" + password + "\';");
				conn.close();
				return true;
			}
			else {
				conn.close();
			}
		}
		catch(SQLException e){
			System.out.println(e);
		}
		return false;
	}

	//TODO: Delete after we discuss maintaining connections.
	/** @deprecated defer to public method.  */
	private boolean doesUserExist(String username, Connection conn) throws SQLException {
		String statement = "SELECT * FROM mysql.user WHERE user = ?;";
		PreparedStatement pSta = conn.prepareStatement(statement);
		pSta.setString(1, username);
		ResultSet checkResults = pSta.executeQuery();
		return checkResults.next();
	}

	/**
	 * Checks database for given username.
	 *
	 * @param username Username to check database for.
	 * @return <code>true</code> if given username is in the database,
	 * <code>false</code> otherwise.
	 * @throws RuntimeException If user's database connection is closed.
	 */
	public boolean doesUserExist(String username){
		String statement = "SELECT * FROM mysql.user WHERE user = ?;";
		try {
			PreparedStatement pSta = activeConnection.prepareStatement(statement);
			pSta.setString(1, username);
			ResultSet checkResults = pSta.executeQuery();
			return checkResults.next();
		} catch(SQLException e) {
			throw new RuntimeException("Connection to database has been closed.",e);
		}
	}

	/**
	 * Delete given user from the database.
	 * @param username User to delete.
	 * @return <code>true</code> if successful, <code>false</code> otherwise.
	 */
	protected boolean deleteUser(String username) {
		boolean succesful = false;
		try{
			Connection conn = makeNewConnection("stagbar", "Nkucsc440");//have to connect as admin.
			String statement = "DROP USER ?;";
			PreparedStatement pSta = conn.prepareStatement(statement);
			pSta.setString(1, username);
			succesful = pSta.execute();
			//Statement sta = conn.createStatement();
			//sta.execute("DROP USER \'" + username + "\';");
			conn.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
		return succesful;
	}

	/** Create a table to store the different kinds of alcohol */
	protected boolean addNewGroup(Connection conn, String groupName, int code){
		boolean successful = false;
		/* code will indicate the type
		 * 1 = bottled beer
		 * 2 = draft beer
		 * 3 = liqour
		 */
		try{
			Statement sta = conn.createStatement();
			String statement = "";
			//PreparedStatement pSta;

			//TODO: Modify tables
			// Instead of creating 3 different tables here for storing the drinks, we can just use 1.
			// We will have to write code to check that the input is an integer for previous/current on bottled beer anyway,
			// we shouldn't need to verify it at the database level as well.
			// It won't cause any problems to have empty fields for some of the drinks
			// This also makes adds and especially

			// The combined table would have previous double, current double, name &
			// another 2 columns:
			// new column 1: ID - a unique primary key, this integer can be auto-incremented by the database on entries
			// new column 2: type - a foreign key to a new table that has 2 columns:
			// Table ALCOHOL_TYPE, Columns ID, type
			// Where ID is the primary key, numbers 1-3
			// & type is the associated string
			
		

			switch (code){
				case 1: statement = "CREATE TABLE " + groupName  + " (name varchar(50), previous integer, current integer, primary key(name));";
						break;
				case 2: statement = "CREATE TABLE " + groupName  + " (name varchar(50), previous double, current double, primary key(name));"; 
						break;
				case 3: statement = "CREATE TABLE " + groupName  + " (name varchar(50), previous double, current double, fullBottles integer, primary key (name));"; 
						break;
			}
			//pSta = conn.prepareStatement(statement);
			//pSta.setString(1, groupName);
			successful = sta.execute(statement);
			//successful = pSta.execute();
			conn.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return successful;
	}

}

