import java.sql.*;
public class Connect {
	//method to make a connection to the database, will return null if unsuccessful.
	protected static Connection makeNewConnection(String username, String password) throws ClassNotFoundException{
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
	//method to create a new user that has full privileges.  IE the inventory manager 
	protected static boolean createMasterUser(String username, String password) throws ClassNotFoundException{
		try{
			Connection conn = makeNewConnection("*****", "****");//have to connect to the database with an admin account 
																		//to create a new user
			//Statement sta = conn.createStatement();
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
				//sta.execute("create user " + "\'" + username + "\'@\'%\'" + " IDENTIFIED BY \'" + password + "\';" );
				//sta.execute("grant all privileges on *.* to \'" + username
					//+ "\'@\'%\' identified by \'" + password + "\';");
				conn.close();
				return true;
			}
			conn.close();
		}
		catch(SQLException e){
			System.out.println(e);
		}
		return false;
	}
	//method to delete a user from the database
	protected static boolean deleteUser(String username) throws ClassNotFoundException{
		boolean succesful = false;
		try{
			
			Connection conn = makeNewConnection("****", "****");//have to connect as admin.  
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

	//method to create a table to store the different kinds of alcohol
	protected static boolean addNewGroup(Connection conn, String groupName, int code){
		boolean successful = false;
		/* code will indicate the type
		 * 1 = bottled beer
		 * 2 = draft beer
		 * 3 = liqour
		 */
		try{
			//Statement sta = conn.createStatement();
			String statement = "";
			PreparedStatement pSta;

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
				case 1: statement = "CREATE TABLE ? (name varchar(50) not null, previous integer, current integer, primary key(name))";
						break;
				case 2: statement = "CREATE TABLE ? (name varchar(50) not null, previous double, current double, primary key(name))"; 
						break;
				case 3: statement = "CREATE TABLE ? (name varchar(50) not null, previous double, current double, fullBottles integer, primary key (name))"; 
						break;
			}
			pSta = conn.prepareStatement(statement);
			pSta.setString(1, groupName);
			successful = pSta.execute();
			conn.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
		return successful;
	}
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		
		//createMasterUser("crewlounge", "1234");
		Connection c = makeNewConnection("stagbar", "Nkucsc440");
		Statement s = c.createStatement();
		ResultSet r = s.executeQuery("SELECT user FROM mysql.user;");
		//deleteUser("crewlounge");
		while(r.next()){
			System.out.println(r.getString(1));
		}
		//deleteUser("crewlounge");
		c.close();
	}
}

