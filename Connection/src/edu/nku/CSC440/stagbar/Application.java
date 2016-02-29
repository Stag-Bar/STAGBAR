package edu.nku.CSC440.stagbar;

import edu.nku.CSC440.stagbar.dataaccess.User;
import edu.nku.CSC440.stagbar.ui.ApplicationUI;

import java.sql.Connection;

/**
 * Class used to keep track of information that applies to the entire application,
 * such as which user is logged in and the connection to the database.
 */
public class Application {

	private static Application application = new Application();
	private Connection connection;
	private User user;
	private ApplicationUI applicationUI;

	private Application(){}

	public static Application getInstance(){
		return application;
	}

	public ApplicationUI getApplicationUI() {
		if(null == applicationUI)
			applicationUI = new ApplicationUI();

		return applicationUI;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
