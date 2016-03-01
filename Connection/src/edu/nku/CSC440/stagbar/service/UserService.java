package edu.nku.CSC440.stagbar.service;

import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.dataaccess.PermissionLevel;

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

	private UserService(){}

	public static UserService getInstance(){
		return userService;
	}

	/**
	 * Converts given character array to a SHA-256 hash.
	 * @param password Password to convert.
	 * @return SHA-256 hash of given password.
	 */
	private static byte[] getHash(char[] password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(toBytes(password));
			return messageDigest.digest();
		} catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Converts given character array to a byte array.
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
	private byte[] getPasswordForUser(String username) {
		//TODO: Search database for user's password.

		// TODO: Delete hardcoded test data.
		if("user".equals(username)) {
			return getHash("password".toCharArray());
		}

		return null;
	}

	public boolean login(String username, char[] password) {
		if(null == username) return false;

		byte[] passwordHashFromUser = getHash(password);
		byte[] passwordHashFromDatabase = getPasswordForUser(username);

		if(null != passwordHashFromDatabase && Arrays.equals(passwordHashFromDatabase, passwordHashFromUser)){
			//TODO: Create Connection with database
//			Application.getInstance().setConnection([ConnectionFromDatabase]);
			return true;
		}
		return false;
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
		byte[] passwordHash = getHash(password);

		//This method below will create a new user (with all permissions) in the database
			Connect.getInstance().createMasterUser(username, new String(password), "test");
		//Zero out the possible password, for security.
		Arrays.fill(password, '0');

		return true;
	}

}
