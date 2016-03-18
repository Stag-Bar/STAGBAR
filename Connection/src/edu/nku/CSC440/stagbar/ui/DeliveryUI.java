package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.Entry;
import edu.nku.CSC440.stagbar.service.AlcoholService;
import edu.nku.CSC440.stagbar.service.TypeService;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeliveryUI {

	Map<CustomAlcoholType, TypePaneUI> typePaneUIMap;
	private ItemListener checkboxListener;
	private JPanel contentPane;
	private JLabel errorMessage;
	private JButton okButton;
	private JPanel scrollPane;
	private JTabbedPane tabbedPane;

	public DeliveryUI() {
		contentPane.setName("Delivery");

		typePaneUIMap = new HashMap<>();

		populateScrollPaneByType();
		tabbedPane.removeTabAt(0); // Remove default tab
		populateTabPaneByType();


		checkboxListener = e -> onCheck(e);

		okButton.addActionListener(e -> onOK());
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
			errorMessage.setText("Delivery entry incomplete.");
			results = null;
		}

		return results;
	}

	private void onCheck(ItemEvent event) {
		System.out.println(event.getItemSelectable().getClass() + " " + (event.getStateChange() == ItemEvent.SELECTED ? "Selected" : "Deselected"));

//		if(event.getStateChange() == ItemEvent.SELECTED){
//			switch(event.getItemSelectable().getClass())
//		}
//		else {
//
//		}

	}

	private void onOK() {
		Set<Entry> entries = getEntries();
		if(null != entries) {
			AlcoholService.getInstance().saveDeliveries(entries);
			okButton.setEnabled(false);
			uiHacks.killMeThenGoToLastPage(contentPane);
		}
	}

	private void populateScrollPaneByType() {
		for(CustomAlcoholType type : TypeService.getInstance().getAllCustomAlcoholTypes()) {
			TypePaneUI typePaneUI = new TypePaneUI(type);

//			for(Alcohol alcohol : AlcoholService.getInstance().getAlcoholByType(type, LocalDate.now(), LocalDate.now())) {
//				typePaneUI.addEntryRow(alcohol);
//			}

			if(!typePaneUIMap.isEmpty()) scrollPane.add(new JSeparator(JSeparator.HORIZONTAL));

			typePaneUIMap.put(type, typePaneUI);
			scrollPane.add(typePaneUI.getContentPane());
		}
	}

	private void populateTabPaneByType() {
		for(CustomAlcoholType type : TypeService.getInstance().getAllCustomAlcoholTypes()) {
			TypeTabUI typeTabUI = new TypeTabUI(checkboxListener);

			for(Alcohol alcohol : AlcoholService.getInstance().getAlcoholByType(type, LocalDate.now(), LocalDate.now())) {
				typeTabUI.addAlcoholCheckbox(alcohol);
			}

			tabbedPane.addTab(type.toString(), typeTabUI.getContentPane());
		}
	}
}
