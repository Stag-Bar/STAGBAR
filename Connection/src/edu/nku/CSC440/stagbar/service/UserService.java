package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.Application;
import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.dataaccess.PermissionLevel;
import edu.nku.CSC440.stagbar.dataaccess.User;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Intermediary between UI and DataAccess layers.
 * Contains business logic for handling users.
 * This class manipulates methods from classes on the DataAccess layer to perform its functions and does not handle SQL directly.
 */
public class UserService {

	private static UserService userService = new UserService();

	private UserService() {}

	public static UserService getInstance() {
		return userService;
	}

	/**
	 * Converts given character array to a byte array.
	 *
	 * @param chars Characters to convert.
	 * @return Byte array from given characters.
	 */
	private static byte[] toBytes(char[] chars) {
		CharBuffer charBuffer = CharBuffer.wrap(chars);
		ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
		byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
				byteBuffer.position(), byteBuffer.limit());
		Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
		Arrays.fill(byteBuffer.array(), (byte)0); // clear sensitive data
		return bytes;
	}

	/**
	 * Converts given character array to a SHA-256 hash.
	 *
	 * @param password Password to convert.
	 * @return SHA-256 hash of given password.
	 */
	private static byte[] toHash(char[] password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(toBytes(password));
			return messageDigest.digest();
		} catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

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
	 * Creates new user entry in database with given username, password and permission level.
	 *
	 * @param username        Username of new user
	 * @param password        Password of new user. Password is hashed before stored in database.
	 * @param permissionLevel Permission level of new user.
	 * @return <code>true</code> if save is successful, <code>false</code> otherwise.
	 */
	public boolean createNewUser(String username, char[] password, PermissionLevel permissionLevel) {
		byte[] passwordHash = toHash(password);

		//This method below will create a new user (with all permissions) in the database
		boolean successful = Connect.getInstance().createMasterUser(username, new String(password), "test");
		if(successful) System.out.format("New user %s has been created\n", username);

		//Zero out the possible password, for security.
		Arrays.fill(password, '0');

		return successful;
	}

	/**
	 * Checks database for given username.
	 *
	 * @param username Username to check database for.
	 * @return <code>true</code> if given username is in the database,
	 * <code>false</code> otherwise.
	 */
	public boolean doesUserExist(String username) {
		return Connect.getInstance().doesUserExist(username);
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
	private byte[] getPasswordForUser(String username) {
		//TODO: Search database for user's password.

		// TODO: Delete hardcoded test data.
		if("stagbar".equalsIgnoreCase(username)) {
			return toHash("Nkucsc440".toCharArray());
		}

		return null;
	}

	private PermissionLevel getPermissionsForUser(String username) {
		//TODO: Search database for user's permisison level.

		//TODO: Delete test data
		return PermissionLevel.ADMIN;
	}

	public boolean login(String username, char[] password) {
		if(null == username) return false;

//		byte[] passwordHashFromUser = toHash(password);
//		byte[] passwordHashFromDatabase = getPasswordForUser(username);

//		if(null != passwordHashFromDatabase && Arrays.equals(passwordHashFromDatabase, passwordHashFromUser)) {
		if(Connect.getInstance().createUserConnection(username, new String(password))) {
			User currentUser = new User(username, getPermissionsForUser(username));
			Application.getInstance().setUser(currentUser);
			System.out.format("Login as %s Successful\n", username);
			return true;
		}

		return false;
	}

}
