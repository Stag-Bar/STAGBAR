package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.AlcoholType;
import edu.nku.CSC440.stagbar.service.InventoryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NewAlcoholUI {
	private static final String ERROR_NAME_NOT_UNIQUE = "An alcohol with that name already exists.";
	private JButton OKButton;
	private JFormattedTextField amountFormattedTextField;
	private JFormattedTextField bottlesFormattedTextField;
	private JPanel contentPane;
	private JLabel errorMessage;
	private JTextField nameTextField;
	private JComboBox<AlcoholType> typeComboBox;
	private JLabel typeLabel;
	private JLabel nameLabel;

	public NewAlcoholUI() {
		contentPane.setName("New Alcohol");

		OKButton.addActionListener(e -> onOK());
		typeComboBox.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				toggleFields((AlcoholType)e.getItem());
			}
		});
	}

	private void createUIComponents() {
		typeComboBox = new JComboBox<>(AlcoholType.values());
		typeComboBox.setSelectedIndex(-1);

		NumberFormat integerNumberInstance = NumberFormat.getIntegerInstance();
		bottlesFormattedTextField = new ImprovedFormattedTextField(integerNumberInstance, 0);
		bottlesFormattedTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		bottlesFormattedTextField.setEnabled(false);

		NumberFormat decimalNumberInstance = new DecimalFormat();
		amountFormattedTextField = new ImprovedFormattedTextField(decimalNumberInstance, 0);
		amountFormattedTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		amountFormattedTextField.setEnabled(false);
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	/** Turns blank fields RED and reverts filled fields to BLACK. */
	private void highlightEmptyFields() {
		nameLabel.setForeground(nameTextField.getText().isEmpty() ?
														Color.RED : Color.BLACK);

		typeLabel.setForeground(null == typeComboBox.getSelectedItem() ?
														Color.RED : Color.BLACK);
	}

	private void onOK() {
		int bottles;
		double amount;

		// Validate Fields
		// TODO: Ensure valid values in bottles/amount (numbers only)
		try {
			bottles = Integer.parseInt(bottlesFormattedTextField.getText());
			amount = Double.parseDouble(amountFormattedTextField.getText());
		} catch(NumberFormatException e) {
			return; // UI should already have a red background indicator for invalid fields.
		}

		// TODO: Check name is unique in database
		if(!InventoryService.isNameUnique(nameTextField.getText())) {
			//TODO: Highlight label
			errorMessage.setText(ERROR_NAME_NOT_UNIQUE);
			nameTextField.selectAll();
			nameTextField.requestFocusInWindow();
		}

		// TODO: Check value selected for type

		// Save
		// TODO: Initiate save to database
		InventoryService.getInstance().saveNewAlcohol(nameTextField.getText(), (AlcoholType)typeComboBox.getSelectedItem(), bottles, amount);
	}

	private void toggleFields(AlcoholType type) {
		switch(type) {
			case SINGLE_SERVE:
				bottlesFormattedTextField.setEnabled(true);
				bottlesFormattedTextField.requestFocusInWindow();

				amountFormattedTextField.setEnabled(false);
				amountFormattedTextField.setText("0.0");
				break;
			case DRAFT:
				bottlesFormattedTextField.setEnabled(false);
				bottlesFormattedTextField.setText("0");

				amountFormattedTextField.setEnabled(true);
				amountFormattedTextField.requestFocusInWindow();
				break;
			case SHELF:
				bottlesFormattedTextField.setEnabled(true);
				bottlesFormattedTextField.requestFocusInWindow();
				amountFormattedTextField.setEnabled(true);
				break;
			default:
				bottlesFormattedTextField.setEnabled(false);
				amountFormattedTextField.setEnabled(false);
				break;
		}
	}
}
