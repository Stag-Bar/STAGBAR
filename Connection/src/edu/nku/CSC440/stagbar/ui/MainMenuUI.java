package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuUI {
	private JButton newAlcoholButton;
	private JButton inventoryButton;
	private JButton salesButton;
	private JButton mixedDrinksButton;
	private JButton retireBeverageButton;
	private JButton reportsButton;
	private JButton manageUsersButton;
	private JPanel contentPane;

	public MainMenuUI() {

		manageUsersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {onManageUsers();}
		});
	}

	private void onManageUsers() {
		UserAccountManagementUI manageUsers = new UserAccountManagementUI();
		uiHacks.goToPanel(contentPane, manageUsers.getContentPane());
	}

	public JPanel getContentPane() {
		return contentPane;
	}
}
