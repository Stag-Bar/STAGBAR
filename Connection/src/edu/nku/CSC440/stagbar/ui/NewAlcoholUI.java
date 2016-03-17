package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.AlcoholType;
import edu.nku.CSC440.stagbar.service.InventoryService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NewAlcoholUI {
	private static final String ERROR_CANNOT_SAVE = "Unable to save new alcohol. Try again later.";
	private static final String ERROR_INVALID_QUANTITIES = "Invalid entry in Bottles/Amount fields.";
	private static final String ERROR_NAME_NOT_UNIQUE = "An alcohol with that name already exists.";
	private static final String ERROR_REQUIRED_FIELDS = "All fields must be filled.";
	private static final String MESSAGE_NEW_ALCOHOL = "%s has been saved.";
	private static final String TITLE_CANNOT_SAVE = "Alcohol Save Failed";
	private static final String TITLE_NEW_ALCOHOL = "Save Successful";
	private JFormattedTextField amountFormattedTextField;
	private JFormattedTextField bottlesFormattedTextField;
	private JPanel contentPane;
	private JLabel errorMessage;
	private JLabel nameLabel;
	private JTextField nameTextField;
	private JButton okButton;
	private JComboBox<AlcoholType> typeComboBox;
	private JLabel typeLabel;

	public NewAlcoholUI() {
		contentPane.setName("New Alcohol");

		okButton.addActionListener(e -> onOK());
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
		int bottles = 0;
		double amount = 0.0;
		boolean validQuantities = true;

		highlightEmptyFields();

		try {
			bottles = Integer.parseInt(bottlesFormattedTextField.getText());
			amount = Double.parseDouble(amountFormattedTextField.getText());
		} catch(NumberFormatException e) {
			validQuantities = false;
		}

		// Check name & type fields are filled.
		if(nameTextField.getText().isEmpty() || null == typeComboBox.getSelectedItem()) {
			errorMessage.setText(ERROR_REQUIRED_FIELDS);
		}
		// Check bottles & amount have valid values.
		else if(!validQuantities) { // UI should already have a red background indicator for invalid fields.
			errorMessage.setText(ERROR_INVALID_QUANTITIES);
		}
		// Check name is unique in database.
		else if(!InventoryService.isNameUnique(nameTextField.getText())) {
			nameLabel.setForeground(Color.RED);
			errorMessage.setText(ERROR_NAME_NOT_UNIQUE);
			nameTextField.selectAll();
			nameTextField.requestFocusInWindow();
		}
		// Save alcohol to database.
		else if(InventoryService.getInstance().saveNewAlcohol(nameTextField.getText(), (AlcoholType)typeComboBox.getSelectedItem(), bottles, amount)) {
			// Display confirmation to user
			JOptionPane.showMessageDialog(contentPane, String.format(MESSAGE_NEW_ALCOHOL, nameTextField.getText()), TITLE_NEW_ALCOHOL, JOptionPane.INFORMATION_MESSAGE);

			//TODO: Navigate user away from page.
			okButton.setEnabled(false);
			uiHacks.killMeThenGoToLastPage(contentPane);
		}
		else {
			JOptionPane.showMessageDialog(contentPane, ERROR_CANNOT_SAVE, TITLE_CANNOT_SAVE, JOptionPane.ERROR_MESSAGE);
		}
	}

	/** Disable bottles/amount fields based on AlcoholType selected. */
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
