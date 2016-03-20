package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.Entry;
import edu.nku.CSC440.stagbar.service.AlcoholService;
import edu.nku.CSC440.stagbar.service.TypeService;

import javax.swing.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InventoryUI {
	Map<CustomAlcoholType, TypePaneUI> typePaneUIMap;
	private JButton cancelButton;
	private JPanel contentPane;
	private JLabel errorMessage;
	private JButton okButton;
	private JPanel scrollPane;

	public InventoryUI() {
		contentPane.setName("Inventory");

		typePaneUIMap = new HashMap<>();

		populateInventoryByType();

		okButton.addActionListener(e -> onOK());
		cancelButton.addActionListener(e -> onCancel());
	}

	private void createUIComponents() {
		scrollPane = new JPanel();
		scrollPane.setLayout(new BoxLayout(scrollPane, BoxLayout.Y_AXIS));
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public Set<Entry> getEntries() {
		boolean failure = false;
		Set<Entry> results = new HashSet<>();
		for(TypePaneUI typePaneUI : typePaneUIMap.values()) {
			try {
				results.addAll(typePaneUI.getEntries());
			} catch(IllegalStateException e) {
				failure = true;
			}
		}

		if(failure) {
			errorMessage.setText("Inventory entry incomplete.");
			results = null;
		}

		return results;
	}

	private void onCancel() {
		uiHacks.killMeThenGoToLastPage(contentPane);
	}

	private void onOK() {
		Set<Entry> entries = getEntries();
		if(null != entries) {
			AlcoholService.getInstance().saveInventory(entries);
			okButton.setEnabled(false);
			uiHacks.killMeThenGoToLastPage(contentPane);
		}
	}

	private void populateInventoryByType() {
		for(CustomAlcoholType type : TypeService.getInstance().getAllCustomAlcoholTypes()) {
			TypePaneUI typePaneUI = new TypePaneUI(type);

			for(Alcohol alcohol : AlcoholService.getInstance().getAlcoholByType(type, LocalDate.now(), LocalDate.now())) {
				typePaneUI.addEntryRow(alcohol);
			}

			if(!typePaneUIMap.isEmpty()) scrollPane.add(new JSeparator(JSeparator.HORIZONTAL));

			typePaneUIMap.put(type, typePaneUI);
			scrollPane.add(typePaneUI.getContentPane());
		}
	}
}
