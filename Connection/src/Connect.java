import java.sql.*;
public class Connect {
	//method to make a connection to the database
	protected Connection makeNewConnection(String username, String password) throws ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		try{
			String url = "jdbc:mysql://stagbar.cgef59ufduu4.us-west-2.rds.amazonaws.com:3306";
			conn = DriverManager.getConnection(url, username, password);
		}
		catch(Exception e){
			System.out.println(e);
		}
		return conn;
	}
	//method to create a new user that has full privileges.  IE the inventory manager 
	protected void createMasterUser(String username, String password) throws ClassNotFoundException{
		try{
			Connection conn = makeNewConnection("*****", "*******");//have to connect to the database with an admin account 
																		//to create a new user
			Statement sta = conn.createStatement();
			sta.execute("create user " + "\'" + username + "\'@\'%\'" + " IDENTIFIED BY \'" + password + "\';" );
			sta.execute("grant all privileges on  on *.* to \'" + username
					+ "\'@\'%\' identifeid by \'" + password + "\';");
			conn.close();
		}
		catch(SQLException e){
			
		}
	}
	//method to delete a user from the database
	protected void deleteUser(String username) throws ClassNotFoundException{
		try{
			
			Connection conn = makeNewConnection("*****", "********");//have to connect as admin.  
			Statement sta = conn.createStatement();
			sta.execute("DELETE FROM mysql.user WHERE User = \'" + username + "\';");
			conn.close();
		}
		catch(SQLException e){
			
		}
	}
	//method to create a table to store the different kinds of alcohol
	protected void addNewGroup(Connection conn, String groupName, int code){
		/* code will indicate the type
		 * 1 = bottled beer
		 * 2 = draft beer
		 * 3 = liqour
		 * 
		 * 
		 */
		try{
			
			Statement sta = conn.createStatement();
			switch (code){
				case 1: sta.execute("CREATE TABLE " + groupName + " (name varchar(50) not null, previous integer, current integer, primary key (name) );  ");
						break;
				case 2: sta.execute("CREATE TABLE " + groupName + " (name varchar(50) not null, previous double, current double, primary key (name) );  ");
						break;
				case 3: sta.execute("CREATE TABLE " + groupName + " (name varchar(50) not null, previous double, current double, fullBottles integer, primary key (name) );  ");
						break;
			}
			conn.close();
		}
		catch(Exception e){
			
		}
	}
	
}
