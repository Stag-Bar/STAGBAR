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
	private JScrollPane scrollPane;

	public InventoryUI() {
		typePaneUIMap = new HashMap<>();

		for(CustomAlcoholType type : TypeService.getInstance().getAllCustomAlcoholTypes()) {
			TypePaneUI typePaneUI = new TypePaneUI(type);

			for(Alcohol alcohol : AlcoholService.getInstance().getAlcoholByType(type, LocalDate.now(), LocalDate.now())) {
				typePaneUI.addEntryRow(alcohol);
			}

			typePaneUIMap.put(type, typePaneUI);
			scrollPane.add(typePaneUI.getContentPane());
		}
	}

	public JPanel getContentPane() {
		return contentPane;
	}
}
