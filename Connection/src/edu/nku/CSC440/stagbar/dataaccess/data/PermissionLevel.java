package edu.nku.CSC440.stagbar.dataaccess.data;

public enum PermissionLevel {
	ADMIN("Administrator"), GUEST("Guest");

	final String text;

	PermissionLevel(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
