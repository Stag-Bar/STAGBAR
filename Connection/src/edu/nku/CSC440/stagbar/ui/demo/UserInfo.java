package edu.nku.CSC440.stagbar.ui.demo;

@SuppressWarnings("ALL")
public class UserInfo {
	private String firstName;
	private String lastName;

	public UserInfo(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() { return firstName; }

	public String getLastName() { return lastName; }
}

