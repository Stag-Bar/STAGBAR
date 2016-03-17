package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.AlcoholType;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NewAlcoholUI {
	private JButton OKButton;
	private JFormattedTextField amountFormattedTextField;
	private JFormattedTextField bottlesFormattedTextField;
	private JPanel contentPane;
	private JLabel errorMessage;
	private JTextField nameTextField;
	private JComboBox<AlcoholType> typeComboBox;

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

	private void onOK() {

	}

	private void toggleFields(AlcoholType type) {
		switch(type) {
			case SINGLE_SERVE:
				bottlesFormattedTextField.setEnabled(true);
				bottlesFormattedTextField.requestFocusInWindow();

				amountFormattedTextField.setEnabled(false);
				amountFormattedTextField.setText("0");
				break;
			case DRAFT:
				bottlesFormattedTextField.setEnabled(false);
				bottlesFormattedTextField.setText("0.0");

				amountFormattedTextField.setEnabled(true);
				amountFormattedTextField.requestFocusInWindow();
				break;
			case SHELF:
				bottlesFormattedTextField.setEnabled(true);
				amountFormattedTextField.setEnabled(true);
				break;
			default:
				bottlesFormattedTextField.setEnabled(false);
				amountFormattedTextField.setEnabled(false);
				break;
		}
	}
}
