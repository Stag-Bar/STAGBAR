package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.Entry;
import edu.nku.CSC440.stagbar.dataaccess.MixedDrink;
import edu.nku.CSC440.stagbar.service.AlcoholService;
import edu.nku.CSC440.stagbar.service.MixedDrinkService;
import edu.nku.CSC440.stagbar.service.TypeService;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SalesUI {
	Map<CustomAlcoholType, TypePaneUI> typePaneUIMap;
	private final ItemListener checkboxListener = e -> onCheck(e);
	private JPanel contentPane;
	private JLabel errorMessage;
	private JButton okButton;
	private JPanel scrollPane;
	private JTabbedPane tabbedPane;

	public SalesUI() {
		contentPane.setName("Sales");

		typePaneUIMap = new HashMap<>();

		populateScrollPaneByType();
		populateTabPaneByType();
		populateTabPaneWithDrinks();


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
			errorMessage.setText("Sales entry incomplete.");
			results = null;
		}

		return results;
	}

	public void onCheck(ItemEvent event) {
		if(AlcoholCheckBox.class.equals(event.getItemSelectable().getClass())) {
			AlcoholCheckBox alcoholCheckBox = (AlcoholCheckBox)event.getItemSelectable();
			TypePaneUI typePaneUI = typePaneUIMap.get(alcoholCheckBox.getAlcohol().getType());

			if(event.getStateChange() == ItemEvent.SELECTED) { // Add to scrollPane
				typePaneUI.addEntryRow(alcoholCheckBox.getAlcohol());
			}
			else { // Remove from scrollPane
				typePaneUI.removeEntryRow(alcoholCheckBox.getAlcohol().getAlcoholId());
			}

			typePaneUI.getContentPane().revalidate();
		}
	}

	private void onOK() {
		Set<Entry> entries = getEntries();
		if(null != entries) {
			AlcoholService.getInstance().saveSales(entries);
			okButton.setEnabled(false);
			uiHacks.killMeThenGoToLastPage(contentPane);
		}
	}

	private void populateScrollPaneByType() {
		for(CustomAlcoholType type : TypeService.getInstance().getAllCustomAlcoholTypes()) {
			TypePaneUI typePaneUI = new TypePaneUI(type);

			if(!typePaneUIMap.isEmpty()) scrollPane.add(new JSeparator(JSeparator.HORIZONTAL));

			typePaneUIMap.put(type, typePaneUI);
			scrollPane.add(typePaneUI.getContentPane());
		}
	}

	private void populateScrollPaneWithDrinks() {
		for(CustomAlcoholType type : TypeService.getInstance().getAllCustomAlcoholTypes()) {
			TypePaneUI typePaneUI = new TypePaneUI(type);

			if(!typePaneUIMap.isEmpty()) scrollPane.add(new JSeparator(JSeparator.HORIZONTAL));

			typePaneUIMap.put(type, typePaneUI);
			scrollPane.add(typePaneUI.getContentPane());
		}
	}

	private void populateTabPaneByType() {
		for(CustomAlcoholType type : TypeService.getInstance().getAllCustomAlcoholTypes()) {
			TabUI tabUI = new TabUI(checkboxListener);

			for(Alcohol alcohol : AlcoholService.getInstance().getAlcoholByType(type, LocalDate.now(), LocalDate.now())) {
				tabUI.addCheckbox(alcohol);
			}

			tabbedPane.addTab(type.toString(), tabUI.getContentPane());
		}
	}

	private void populateTabPaneWithDrinks() {
		TabUI tabUI = new TabUI(checkboxListener);

		for(MixedDrink mixedDrink : MixedDrinkService.getInstance().getAllMixedDrinks()) {
			tabUI.addCheckbox(mixedDrink);
		}

		tabbedPane.addTab("Mixed Drinks", tabUI.getContentPane());
	}

}
