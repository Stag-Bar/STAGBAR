package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NewAlcoholUI {
	private JPanel contentPane;
	private JTextField nameTextField;
	private JComboBox typeComboBox;
	private JButton OKButton;
	private JLabel errorMessage;
	private JFormattedTextField bottlesFormattedTextField;
	private JFormattedTextField amountFormattedTextField;

	public NewAlcoholUI(){
	}

	private void createUIComponents() {
		NumberFormat integerNumberInstance = NumberFormat.getIntegerInstance();
		bottlesFormattedTextField = new ImprovedFormattedTextField(integerNumberInstance, 0);
		bottlesFormattedTextField.setHorizontalAlignment(SwingConstants.RIGHT);

		NumberFormat decimalNumberInstance = new DecimalFormat();
		amountFormattedTextField = new ImprovedFormattedTextField(decimalNumberInstance, 0);
		amountFormattedTextField.setHorizontalAlignment(SwingConstants.RIGHT);

	}

	public JPanel getContentPane() {
		return contentPane;
	}
}
