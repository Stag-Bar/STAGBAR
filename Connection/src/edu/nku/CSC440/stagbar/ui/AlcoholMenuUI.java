package edu.nku.CSC440.stagbar.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

public class AlcoholMenuUI {
	private JPanel contentPane;
	private JButton createAlcoholButton;
	private JButton createCustomTypeButton;
	private JButton goBackButton;
	private JButton retireAlcoholButton;

	public AlcoholMenuUI() {
		contentPane.setName("Alcohol Menu");

		createAlcoholButton.addActionListener(e -> onCreateAlcohol());
		retireAlcoholButton.addActionListener(e -> onRetireAlcohol());
		createCustomTypeButton.addActionListener(e -> onCreateCustomType());
		goBackButton.addActionListener(e -> onGoBack());
	}

	/** @noinspection ALL */
	public JComponent $$$getRootComponent$$$() { return contentPane; }

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayoutManager(4, 1, new Insets(10, 10, 10, 10), -1, -1));
		createAlcoholButton = new JButton();
		createAlcoholButton.setText("Create Alcohol");
		contentPane.add(createAlcoholButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		retireAlcoholButton = new JButton();
		retireAlcoholButton.setText("Retire Alcohol");
		contentPane.add(retireAlcoholButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		createCustomTypeButton = new JButton();
		createCustomTypeButton.setText("Create Custom Type");
		contentPane.add(createCustomTypeButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		goBackButton = new JButton();
		goBackButton.setText("Go Back");
		contentPane.add(goBackButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	private void onCreateAlcohol() {
		NewAlcoholUI newAlcoholUI = new NewAlcoholUI();
		uiHacks.goToPanel(contentPane, newAlcoholUI.getContentPane());
	}

	private void onCreateCustomType() {
		//TODO: Naviagte to create custom type panel
	}

	private void onGoBack() {
		uiHacks.killMeThenGoToLastPage(contentPane);
	}

	private void onRetireAlcohol() {
		//TODO: Naviagte to retire beverages panel
	}

	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

}