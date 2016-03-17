package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;

public class UserAccountManagementUI {
	private JPanel contentPane;
	private JButton createUserButton;
	private JButton deleteUserButton;
	private JButton editUserPermissionsButton;

	public UserAccountManagementUI() {
		contentPane.setName("Manage Users");

		createUserButton.addActionListener(e -> onCreateUser());
		deleteUserButton.addActionListener(e -> onDeleteUser());
		editUserPermissionsButton.addActionListener(e -> onEditPermissionsUser());
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	private void onEditPermissionsUser() {

	}

	private void onDeleteUser() {

	}

	private void onCreateUser() {
		CreateUserUI createUserUI = new CreateUserUI();
		uiHacks.goToPanel(contentPane, createUserUI.getContentPane());
	}


}
