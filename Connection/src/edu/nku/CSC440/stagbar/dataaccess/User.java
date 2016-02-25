package edu.nku.CSC440.stagbar.dataaccess;

public class User {
	private String username;
	private String password; // TODO: store this as a hash
	private PermissionLevel permissionLevel;

	public User(String username) {
		this.username = username;
	}

	public User(String username, String password, PermissionLevel permissionLevel) {
		this.username = username;
		this.password = password;
		this.permissionLevel = permissionLevel;
	}

	public PermissionLevel getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(PermissionLevel permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
