package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

public class TypeTabUI {
	private ItemListener checkBoxListener;
	private JPanel contentPane;

	public TypeTabUI(ItemListener checkBoxListener) {
		this.checkBoxListener = checkBoxListener;
	}

	public void addAlcoholCheckbox(Alcohol alcohol) {
		AlcoholCheckBox alcoholCheckBox = new AlcoholCheckBox(alcohol);
		alcoholCheckBox.addItemListener(checkBoxListener);
		contentPane.add(alcoholCheckBox);
	}

	private void createUIComponents() {
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(0, 3));
	}

	public JPanel getContentPane() {
		return contentPane;
	}
}
