package edu.nku.CSC440.stagbar.Connect;

import edu.nku.CSC440.stagbar.dataaccess.PermissionLevel;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConnectTest_UserManagement extends ConnectTest {

	@Test
	public void testFindAllUsers() {
		assertFalse(Connect.getInstance().findAllUsers().isEmpty());
	}

	@Test
	public void testUserManagement_PasswordChange() {
		final String username = "junit_password";
		final String password = "junitPassword";
		final String newPassword = "u11N3V3Rgue$$i7";
		assertTrue("User creation failed.", Connect.getInstance().saveUser(username, password, PermissionLevel.GUEST));
		assertTrue(Connect.getInstance().updateUserPassword(username, newPassword));
		assertTrue("Authentication failed.", Connect.getInstance().authenticateUser(username, newPassword));
	}

	@Test
	public void testUserManagement_PermissionChange() {
		final String username = "junit_permission";
		final String password = "junitPassword";
		assertTrue("User creation failed.", Connect.getInstance().saveUser(username, password, PermissionLevel.GUEST));
		assertEquals("Permission level set incorrectly on user create.", PermissionLevel.GUEST, Connect.getInstance().findPermissionsForUser(username));
		assertTrue(Connect.getInstance().updateUserPermissions(username, PermissionLevel.ADMIN));
		assertEquals("Permission level set incorrectly on permission update.", PermissionLevel.ADMIN, Connect.getInstance().findPermissionsForUser(username));
	}

	@Test
	public void testUserManagement_UserCreation() {
		final String username = "junit_user";
		final String password = "junitPassword";
		assertTrue("User creation failed.", Connect.getInstance().saveUser(username, password, PermissionLevel.GUEST));
		assertTrue("User creation failed.", Connect.getInstance().doesUserExist(username));
		assertTrue("Authentication failed.", Connect.getInstance().authenticateUser(username, password));
		assertEquals("Permission level set incorrectly on user create.", PermissionLevel.GUEST, Connect.getInstance().findPermissionsForUser(username));
	}

	@Test
	public void testUserManagement_UserDeletion() {
		final String username = "junit_deleteme";
		final String password = "junitPassword";
		assertTrue("User creation failed.", Connect.getInstance().saveUser(username, password, PermissionLevel.GUEST));
		assertTrue("User creation failed.", Connect.getInstance().doesUserExist(username));
		assertTrue("User deletion failed.", Connect.getInstance().deleteUser(username));
		assertFalse("User deletion failed.", Connect.getInstance().doesUserExist(username));
	}
}
