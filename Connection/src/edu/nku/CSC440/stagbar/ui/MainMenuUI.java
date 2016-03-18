package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;

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

		inventoryButton.addActionListener(e -> onInventory());
		manageUsersButton.addActionListener(e -> onManageUsers());
		mixedDrinksButton.addActionListener(e -> onMixedDrinks());
		newAlcoholButton.addActionListener(e -> onNewAlcohol());
		reportsButton.addActionListener(e -> onReports());
		retireBeverageButton.addActionListener(e -> onRetireBeverage());
		salesButton.addActionListener(e -> onSales());
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
		InventoryUI inventoryUI = new InventoryUI();
		uiHacks.goToPanel(contentPane, inventoryUI.getContentPane());
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
