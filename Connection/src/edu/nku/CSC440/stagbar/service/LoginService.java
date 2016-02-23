package edu.nku.CSC440.stagbar.service;

//s
public class LoginService {
	public boolean login(String username, char[] password) {
		System.out.println("Username: " + username);
		System.out.println("Password: " + new String(password));

		if(null == username) { return false; }

		String passwordFromUser = new String(password);
		String passwordFromDatabase = getPasswordForUser(username);

		return null != passwordFromDatabase && passwordFromUser.equals(passwordFromDatabase);
	}

	/**
	 * Searches database for given username.
	 * If user is found, returns password for user.
	 * Otherwise returns null.
	 * In future, may return password hash for security.
	 * @param username Username to search database for.
	 * @return Password for user if found; otherwise returns null.
	 */
	private String getPasswordForUser(String username) {
		//TODO: Search database for user's password.

		// TODO: Delete hardcoded test data.
		if("user".equals(username)){
			return "password";
		}

		return null;
	}
}
