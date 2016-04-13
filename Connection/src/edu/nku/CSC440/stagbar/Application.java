package edu.nku.CSC440.stagbar;

import edu.nku.CSC440.stagbar.dataaccess.User;
import edu.nku.CSC440.stagbar.ui.ApplicationUI;

/**
 * Class used to keep track of information that applies to the entire application,
 * such as which user is logged in.
 */
public class Application {

	private static final Application application = new Application();
	private ApplicationUI applicationUI;
	private User user;

	private Application(){}

	public static Application getInstance(){
		return application;
	}

	public ApplicationUI getApplicationUI() {
		if(null == applicationUI)
			applicationUI = new ApplicationUI();

		return applicationUI;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
