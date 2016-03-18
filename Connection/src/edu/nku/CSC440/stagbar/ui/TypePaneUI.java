package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;

import javax.swing.*;
import java.util.Map;

public class TypePaneUI {
	Map<Integer, EntryRowUI> rowUIMap;
	private JPanel contentPane;
	private JPanel rowPane;
	private CustomAlcoholType type;
	private JLabel typeLabel;

	public TypePaneUI(CustomAlcoholType type) {
		this.type = type;
		typeLabel.setText(type.getName());
	}
}
