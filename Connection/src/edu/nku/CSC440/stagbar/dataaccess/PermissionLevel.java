package edu.nku.CSC440.stagbar.dataaccess;

public enum PermissionLevel {
	ADMIN("Administrator"), GUEST("Guest");

	String text;

	PermissionLevel(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
