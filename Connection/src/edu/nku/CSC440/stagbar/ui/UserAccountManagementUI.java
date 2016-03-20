package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;

public class UserAccountManagementUI {
	private JButton changePasswordButton;
	private JPanel contentPane;
	private JButton createUserButton;
	private JButton deleteUserButton;
	private JButton editUserPermissionsButton;
	private JButton goBackButton;

	public UserAccountManagementUI() {
		contentPane.setName("Manage Users");

		changePasswordButton.addItemListener(e -> onChangePassword());
		createUserButton.addActionListener(e -> onCreateUser());
		deleteUserButton.addActionListener(e -> onDeleteUser());
		editUserPermissionsButton.addActionListener(e -> onEditPermissionsUser());
		goBackButton.addActionListener(e -> onGoBack());
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	private void onChangePassword() {
		//TODO: Navigate to password change ui
	}

	private void onCreateUser() {
		CreateUserUI createUserUI = new CreateUserUI();
		uiHacks.goToPanel(contentPane, createUserUI.getContentPane());
	}

	private void onDeleteUser() {
		//TODO: Navigate to delete user ui
	}

	private void onEditPermissionsUser() {
		//TODO: Navigate to edit permissions
	}

	private void onGoBack() {
		uiHacks.killMeThenGoToLastPage(contentPane);
	}

}
