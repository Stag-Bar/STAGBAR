package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.dataaccess.PermissionLevel;

import java.util.Arrays;

/**
 * Intermediary between UI and DataAccess layers.
 * Contains business logic for handling users.
 * This class manipulates methods from classes on the DataAccess layer to perform its functions and does not handle SQL directly.
 */
public class UserService {

	/**
	 * Searches for existing user in database and updates password.
	 *
	 * @param username User to update.
	 * @param password New password for user.
	 * @return <code>true</code> if update is successful, <code>false</code> otherwise.
	 */
	public boolean changePassword(String username, char[] password) {
		//TODO: Update password in database

		//Zero out the possible password, for security.
		Arrays.fill(password, '0');

		return true;
	}

	/**
	 * Searches for existing user in database and updates password.
	 *
	 * @param username        User to update.
	 * @param permissionLevel New permission level for user.
	 * @return <code>true</code> if update is successful, <code>false</code> otherwise.
	 */
	public boolean changePermissions(String username, PermissionLevel permissionLevel) {
		//TODO: Update permission level in database
		return true;
	}

	/**
	 * Checks database for given username.
	 *
	 * @param username Username to check database for.
	 * @return <code>true</code> if given username is in the database,
	 * <code>false</code> otherwise.
	 */
	public boolean doesUserExist(String username) {
		//TODO: Search database for user.

		// TODO: Delete hardcoded test data.
		return "user".equals(username);
	}

	/**
	 * Searches database for given username.
	 * If user is found, returns password for user.
	 * Otherwise returns null.
	 * In future, may return password hash for security.
	 *
	 * @param username Username to search database for.
	 * @return Password for user if found; otherwise returns null.
	 */
	private String getPasswordForUser(String username) {
		//TODO: Search database for user's password.

		// TODO: Delete hardcoded test data.
		if("user".equals(username)) {
			return "password";
		}

		return null;
	}

	public boolean login(String username, char[] password) {
		if(null == username) { return false; }

		String passwordFromUser = new String(password);
		String passwordFromDatabase = getPasswordForUser(username);

		return null != passwordFromDatabase && passwordFromUser.equals(passwordFromDatabase);
	}

	/**
	 * Creates new user entry in database with given username, password and permission level.
	 *
	 * @param username        Username of new user
	 * @param password        Password of new user. Password is hashed before stored in database.
	 * @param permissionLevel Permission level of new user.
	 * @return <code>true</code> if save is successful, <code>false</code> otherwise.
	 */
	public boolean saveNewUser(String username, char[] password, PermissionLevel permissionLevel) {
		//TODO: Create user in database

		//Zero out the possible password, for security.
		Arrays.fill(password, '0');

		return true;
	}
}
