package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.AlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.Entry;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;

public class EntryRowUI {
	private Alcohol alcohol;
	private JLabel alcoholLabel;
	private JFormattedTextField amountFormattedTextField;
	private JFormattedTextField bottlesFormattedTextField;
	private JPanel contentPane;

	public EntryRowUI(Alcohol alcohol) {
		if(null == alcohol) throw new IllegalArgumentException("Alcohol cannot be null.");

		this.alcohol = alcohol;
		alcoholLabel.setText(alcohol.getName());
		toggleFields(alcohol.getType().getKind());
	}

	private void createUIComponents() {
		NumberFormat integerNumberInstance = NumberFormat.getIntegerInstance();
		bottlesFormattedTextField = new ImprovedFormattedTextField(integerNumberInstance, null);
		bottlesFormattedTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		bottlesFormattedTextField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		bottlesFormattedTextField.setEnabled(false);

		NumberFormat decimalNumberInstance = new DecimalFormat();
		amountFormattedTextField = new ImprovedFormattedTextField(decimalNumberInstance, null);
		amountFormattedTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		amountFormattedTextField.setFocusLostBehavior(JFormattedTextField.PERSIST);
		amountFormattedTextField.setEnabled(false);
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		EntryRowUI that = (EntryRowUI)o;

		return alcohol.equals(that.alcohol);

	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public Entry getEntry() {
		int bottles;
		double amount;
		alcoholLabel.setForeground(Color.BLACK);

		try {
			bottles = Integer.parseInt(bottlesFormattedTextField.getText());
			amount = Double.parseDouble(amountFormattedTextField.getText());
		} catch(NumberFormatException e) {
			alcoholLabel.setForeground(Color.RED);
			throw new IllegalStateException(e);
		}

		return new Entry(alcohol.getAlcoholId(), bottles, amount, LocalDate.now());
	}

	@Override
	public int hashCode() {
		return alcohol.hashCode();
	}

	/** Disable bottles/amount fields based on AlcoholType selected. */
	private void toggleFields(AlcoholType type) {
		switch(type) {
			case BOTTLED:
				bottlesFormattedTextField.setEnabled(true);

				amountFormattedTextField.setEnabled(false);
				amountFormattedTextField.setText("0.0");
				break;
			case DRAFT:
				bottlesFormattedTextField.setEnabled(false);
				bottlesFormattedTextField.setText("0");

				amountFormattedTextField.setEnabled(true);
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
