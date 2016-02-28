package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserAccountManagementUI {
	private JPanel contentPane;
	private JButton createUserButton;
	private JButton deleteUserButton;
	private JButton editUserPermissionsButton;

	public UserAccountManagementUI() {
		contentPane.setName("Manage Users");

		createUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { onCreateUser(); }
		});

		deleteUserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { onDeleteUser(); }
		});

		editUserPermissionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { onEditPermissionsUser(); }
		});
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
