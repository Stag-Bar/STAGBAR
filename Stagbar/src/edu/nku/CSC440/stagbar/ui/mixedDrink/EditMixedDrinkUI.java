package edu.nku.CSC440.stagbar.ui.mixedDrink;

import edu.nku.CSC440.stagbar.dataaccess.data.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.data.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrinkIngredient;
import edu.nku.CSC440.stagbar.service.AlcoholService;
import edu.nku.CSC440.stagbar.service.MixedDrinkService;
import edu.nku.CSC440.stagbar.service.TypeService;
import edu.nku.CSC440.stagbar.ui.common.AlcoholCheckBox;
import edu.nku.CSC440.stagbar.ui.common.TabUI;
import edu.nku.CSC440.stagbar.ui.common.uiHacks;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EditMixedDrinkUI {
	private final Map<Integer, IngredientRowUI> rowUIMap;
	private JButton cancelButton;
	private Map<Integer, AlcoholCheckBox> checkBoxMap;
	private JPanel contentPane;
	private JLabel errorMessage;
	private JPanel ingredientPane;
	private final ItemListener checkboxListener = e -> onCheck(e);
	private Map<String, MixedDrink> mixedDrinkMap;
	private JComboBox<String> nameComboBox;
	private JLabel nameLabel;
	private JButton okButton;
	private JTabbedPane tabbedPane;

	public EditMixedDrinkUI() {
		$$$setupUI$$$();
		contentPane.setName("Edit Mixed Drink");

		checkBoxMap = new HashMap<>();
		rowUIMap = new HashMap<>();
		mixedDrinkMap = new HashMap<>();
		for(MixedDrink drink : MixedDrinkService.getInstance().getAllMixedDrinks()) {
			mixedDrinkMap.put(drink.getName(), drink);
		}

		populateTabPaneByType();

		okButton.addActionListener(e -> onOk());
		cancelButton.addActionListener(e -> onCancel());

		nameComboBox.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				resetScreen();
				poulateIngredients(((String)e.getItem()));
			}
		});

	}

	/** @noinspection ALL */
	public JComponent $$$getRootComponent$$$() { return contentPane; }

	/**
	 * Method generated by IntelliJ IDEA GUI Designer
	 * >>> IMPORTANT!! <<<
	 * DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$() {
		createUIComponents();
		contentPane = new JPanel();
		contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(5, 1, new Insets(10, 10, 10, 10), -1, -1));
		contentPane.setOpaque(true);
		contentPane.setPreferredSize(new Dimension(575, 750));
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
		contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		okButton = new JButton();
		okButton.setText("OK");
		panel1.add(okButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
		panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		panel1.add(cancelButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JPanel panel2 = new JPanel();
		panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		contentPane.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		errorMessage = new JLabel();
		errorMessage.setForeground(new Color(-65536));
		errorMessage.setText("");
		panel2.add(errorMessage, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(-1, 16), null, 0, false));
		final JSplitPane splitPane1 = new JSplitPane();
		splitPane1.setDividerLocation(300);
		splitPane1.setOrientation(0);
		contentPane.add(splitPane1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
		final JPanel panel3 = new JPanel();
		panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		splitPane1.setLeftComponent(panel3);
		final JPanel panel4 = new JPanel();
		panel4.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		panel3.add(panel4, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		final JScrollPane scrollPane1 = new JScrollPane();
		panel4.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		final JPanel panel5 = new JPanel();
		panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(5, 5, 5, 5), -1, -1));
		scrollPane1.setViewportView(panel5);
		final JPanel panel6 = new JPanel();
		panel6.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		panel5.add(panel6, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final JLabel label1 = new JLabel();
		label1.setFont(new Font(label1.getFont().getName(), Font.BOLD, label1.getFont().getSize()));
		label1.setText("Ingredient");
		panel6.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
		final JLabel label2 = new JLabel();
		label2.setFont(new Font(label2.getFont().getName(), Font.BOLD, label2.getFont().getSize()));
		label2.setText("Amount");
		panel6.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		panel5.add(ingredientPane, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		tabbedPane = new JTabbedPane();
		splitPane1.setRightComponent(tabbedPane);
		final JPanel panel7 = new JPanel();
		panel7.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
		contentPane.add(panel7, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		nameLabel = new JLabel();
		nameLabel.setText("Name:");
		panel7.add(nameLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		panel7.add(nameComboBox, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
	}

	public void addIngredientRow(Alcohol alcohol, double amount) {
		IngredientRowUI ingredientRow = new IngredientRowUI(alcohol, amount);
		IngredientRowUI oldRow = rowUIMap.put(alcohol.getAlcoholId(), ingredientRow); // Ensure each row is unique.
		if(null != oldRow) {
			ingredientPane.remove(oldRow.getContentPane());
		}
		ingredientPane.add(ingredientRow.getContentPane());
	}

	private void createUIComponents() {
		ingredientPane = new JPanel();
		ingredientPane.setLayout(new BoxLayout(ingredientPane, BoxLayout.Y_AXIS));

		Set<String> allDrinkNames = MixedDrinkService.getInstance().getAllMixedDrinkNames();
		nameComboBox = new JComboBox<>(allDrinkNames.toArray(new String[allDrinkNames.size()]));
		nameComboBox.setSelectedIndex(-1);
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public Set<MixedDrinkIngredient> getIngredients() {
		boolean failure = false;
		Set<MixedDrinkIngredient> ingredients = new HashSet<>();

		for(IngredientRowUI ingredientRow : rowUIMap.values()) {
			try {
				ingredients.add(ingredientRow.getIngredient());
			} catch(IllegalStateException e) {
				failure = true;
			}
		}
		if(failure) {
			ingredients = null;
		}
		return ingredients;
	}

	private void onCancel() {
		uiHacks.killMeThenGoToLastPage(contentPane);
	}

	private void onCheck(ItemEvent event) {
		if(AlcoholCheckBox.class.equals(event.getItemSelectable().getClass())) {
			AlcoholCheckBox alcoholCheckBox = (AlcoholCheckBox)event.getItemSelectable();

			if(event.getStateChange() == ItemEvent.SELECTED) { // Add to scrollPane
				addIngredientRow(alcoholCheckBox.getAlcohol(), 0);
			}
			else { // Remove from scrollPane
				removeIngredientRow(alcoholCheckBox.getAlcohol().getAlcoholId());
			}

			getContentPane().revalidate();
		}
	}

	private void onOk() {
		if(validateName()) {
			Set<MixedDrinkIngredient> ingredients = getIngredients();
			if(null != ingredients) { // Save Drink
				MixedDrink editedDrink = mixedDrinkMap.get((String)nameComboBox.getSelectedItem());
				editedDrink.setIngredients(getIngredients());
				MixedDrinkService.getInstance().updateMixedDrink(editedDrink);
				okButton.setEnabled(false);
				uiHacks.killMeThenGoToLastPage(contentPane);
			}
			else {
				errorMessage.setText("Alcohol amounts incomplete.");
			}
		}
	}

	private void populateTabPaneByType() {
		for(CustomAlcoholType type : TypeService.getInstance().getAllCustomAlcoholTypes()) {
			TabUI tabUI = new TabUI(checkboxListener);

			for(Alcohol alcohol : AlcoholService.getInstance().getAlcoholByType(type, LocalDate.now())) {
				AlcoholCheckBox checkBox = tabUI.addCheckbox(alcohol);
				checkBoxMap.put(alcohol.getAlcoholId(), checkBox);
			}

			tabbedPane.addTab(type.toString(), tabUI.getContentPane());
		}
	}

	private void poulateIngredients(String selectedDrinkName) {
		MixedDrink selectedDrink = mixedDrinkMap.get(selectedDrinkName);
		for(MixedDrinkIngredient ingredient : selectedDrink.getIngredients()) {
			// Check checkbox
			AlcoholCheckBox checkBox = checkBoxMap.get(ingredient.getAlcohol().getAlcoholId());
			checkBox.setSelected(true);

			// Add Ingredient row & pre-fill existing amount
			addIngredientRow(ingredient.getAlcohol(), ingredient.getAmount());
		}
	}

	public void removeIngredientRow(int alcoholId) {
		IngredientRowUI ingredientRow = rowUIMap.remove(alcoholId);
		ingredientPane.remove(ingredientRow.getContentPane());
	}

	private void resetScreen() {
		// Uncheck all checkboxes
		for(AlcoholCheckBox checkBox : checkBoxMap.values()) {
			checkBox.setSelected(false);
		}

		// Remove all ingredient rows
		Set<Integer> keySet = new HashSet<>(rowUIMap.keySet());
		for(Integer alcoholId : keySet) {
			removeIngredientRow(alcoholId);
		}
	}

	private boolean validateName() {
		boolean valid = true;
		if(null == nameComboBox.getSelectedItem()) {
			errorMessage.setText("Name field cannot be blank.");
			valid = false;
		}
		nameLabel.setForeground(valid ? Color.BLACK : Color.RED);
		return valid;
	}

}
