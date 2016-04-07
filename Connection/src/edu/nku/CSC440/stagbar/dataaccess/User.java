package edu.nku.CSC440.stagbar.dataaccess;

/** Class used by the application to store information on the current user. */
public class User {
	private PermissionLevel permissionLevel;
	private String username;

	public User(String username, PermissionLevel permissionLevel) {
		this.username = username;
		this.permissionLevel = permissionLevel;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		User user = (User)o;

		return getUsername().equals(user.getUsername());
	}

	public PermissionLevel getPermissionLevel() {
		return permissionLevel;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		return getUsername().hashCode();
	}

	public boolean isAdmin(){
		return PermissionLevel.ADMIN.equals(permissionLevel);
	}
}
