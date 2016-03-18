package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import edu.nku.CSC440.stagbar.service.AlcoholService;
import edu.nku.CSC440.stagbar.service.TypeService;

import javax.swing.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class InventoryUI {
	Map<CustomAlcoholType, TypePaneUI> typePaneUIMap;
	private JPanel contentPane;
	private JPanel scrollPane;

	public InventoryUI() {
		contentPane.setName("Inventory");

		typePaneUIMap = new HashMap<>();

		populateInventoryByType();
	}

	private void createUIComponents() {
		scrollPane = new JPanel();
		scrollPane.setLayout(new BoxLayout(scrollPane, BoxLayout.Y_AXIS));
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	private void populateInventoryByType() {
		for(CustomAlcoholType type : TypeService.getInstance().getAllCustomAlcoholTypes()) {
			TypePaneUI typePaneUI = new TypePaneUI(type);

			for(Alcohol alcohol : AlcoholService.getInstance().getAlcoholByType(type, LocalDate.now(), LocalDate.now())) {
				typePaneUI.addEntryRow(alcohol);
			}

			typePaneUIMap.put(type, typePaneUI);
			scrollPane.add(typePaneUI.getContentPane());
		}
	}
}
