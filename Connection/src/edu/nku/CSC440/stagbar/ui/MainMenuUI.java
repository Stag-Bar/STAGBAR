package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuUI {
	private JPanel contentPane;
	private JButton inventoryButton;
	private JButton manageUsersButton;
	private JButton mixedDrinksButton;
	private JButton newAlcoholButton;
	private JButton reportsButton;
	private JButton retireBeverageButton;
	private JButton salesButton;

	public MainMenuUI() {

		contentPane.setName("Main Menu");

		inventoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {onInventory();}
		});

		manageUsersButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {onManageUsers();}
		});

		mixedDrinksButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {onMixedDrinks();}
		});

		newAlcoholButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {onNewAlcohol();}
		});

		reportsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {onReports();}
		});

		retireBeverageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {onRetireBeverage();}
		});

		salesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {onSales();}
		});

	}

	public void disableAdminOnlyButtons() {
		newAlcoholButton.setEnabled(false);
		inventoryButton.setEnabled(false);
		salesButton.setEnabled(false);
		retireBeverageButton.setEnabled(false);
		manageUsersButton.setEnabled(false);

	}

	public JPanel getContentPane() {
		return contentPane;
	}

	private void onInventory() {

	}

	private void onManageUsers() {
		UserAccountManagementUI manageUsers = new UserAccountManagementUI();
		uiHacks.goToPanel(contentPane, manageUsers.getContentPane());
	}

	private void onMixedDrinks() {

	}

	private void onNewAlcohol() {
		NewAlcoholUI newAlcoholUI = new NewAlcoholUI();
		uiHacks.goToPanel(contentPane, newAlcoholUI.getContentPane());
	}

	private void onReports() {

	}

	private void onRetireBeverage() {

	}

	private void onSales() {

	}
}
