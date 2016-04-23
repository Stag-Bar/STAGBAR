package edu.nku.CSC440.stagbar.ui.alcohol;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.nku.CSC440.stagbar.dataaccess.data.Alcohol;
import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrink;
import edu.nku.CSC440.stagbar.dataaccess.data.MixedDrinkIngredient;
import edu.nku.CSC440.stagbar.dataaccess.databaseConnection.Connect;
import edu.nku.CSC440.stagbar.service.AlcoholService;
import edu.nku.CSC440.stagbar.ui.common.uiHacks;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class RetireAlcoholUI {
    private static final String CONGRATS = "The alcohol has been retired!";
    private static final String CONGRATS_PLURAL = "The alcohols have been retired!";
    private static final String TITLE_CONGRATS = "Congratulations!";
    private static final String TITLE_NOTHING = "Nothing happened!";
    private static final String NOTHING = "You haven't retired any alcohol. Bye!";
    private ArrayList<RetireAlcoholListUI> alcoholsToBePurged;
    private JPanel contentPane;
    private JButton okButton;
    private JPanel scrollPane;
    private JButton cancelButton;
    private int alcoholPurged; // number of unfortunate alcohols who will need to be purged

    public RetireAlcoholUI() {
        $$$setupUI$$$();
        contentPane.setName("Retire Alcohol");
        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());
        alcoholPurged = 0;
        populateAlcoholCheckBoxes();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        scrollPane = new JPanel();
        scrollPane.setLayout(new BoxLayout(scrollPane, BoxLayout.Y_AXIS));
    }

    private void onCancel() {
        uiHacks.killMeThenGoToLastPage(contentPane);
    }

    private void onOK() {
        Set<MixedDrink> listOfMixedDrinks = Connect.getInstance().findAllMixedDrinks(); //get a list of all mixed drinks
        Iterator<MixedDrink> iterator = listOfMixedDrinks.iterator();
        for (RetireAlcoholListUI alcoholRow : alcoholsToBePurged) {
            if (alcoholRow.getDeletionStatus()) { //if this alcohol has been marked for retirement in the Bahamas
                boolean retireThisAlcohol = true;
                while (iterator.hasNext()) { //for each mixed drink, check each ingredient
                    MixedDrink mixedDrink = iterator.next();
                    Set<MixedDrinkIngredient> listOfIngredients = mixedDrink.getIngredients();
                    Iterator<MixedDrinkIngredient> iteratorMD = listOfIngredients.iterator();
                    while (iteratorMD.hasNext()) { //for each ingredient inside mixed drink, check if alcohol is part of it
                        MixedDrinkIngredient ingredient = iteratorMD.next();
                        if (alcoholRow.getAlcohol().equals(ingredient.getAlcohol())) { //if alcohol is being actively used as ingredient
                            JOptionPane.showMessageDialog(contentPane, NOTHING, TITLE_NOTHING, JOptionPane.ERROR_MESSAGE);
                            retireThisAlcohol = false;
                        }
                    }
                }
                if (retireThisAlcohol) {
                    alcoholPurged++;
                    Date input = new Date();
                    LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    //Connect.getInstance().retireAlcohol(alcoholRow.getAlcoholId(), date);
                    AlcoholService.getInstance().retireAlcohol(alcoholRow.getAlcohol());

                    // Retiring Alcohol never works because when each Alcohol is created CreateAlcoholUI,
                    // are always given AlcoholId of -1.
                    // The auto-increment of Alcohol which is supposed to happen inside Alcohol.java has not happened.
                    // Therefore, there is no unique identifier for any of the given alcohols

                }
            }
        }

        if (alcoholPurged == 1) // One alcohol has been banished from the tribe... Good riddance!
            JOptionPane.showMessageDialog(contentPane, CONGRATS, TITLE_CONGRATS, JOptionPane.INFORMATION_MESSAGE);
        else if (alcoholPurged > 1) // More than one alcohol has been banished from the tribe! MUAHAHAHA
            JOptionPane.showMessageDialog(contentPane, CONGRATS_PLURAL, TITLE_CONGRATS, JOptionPane.INFORMATION_MESSAGE);
        else // Oh c'mon man, no one banished at all? Please don't waste my time!
            JOptionPane.showMessageDialog(contentPane, NOTHING, TITLE_NOTHING, JOptionPane.INFORMATION_MESSAGE);
        okButton.setEnabled(false);
        uiHacks.killMeThenGoToLastPage(contentPane);
    }

    public void addAlcoholRow(RetireAlcoholListUI alcoholRow) {
        scrollPane.add(alcoholRow.getContentPane());
    }

    private void populateAlcoholCheckBoxes() {
        Set<Alcohol> listOfAlcohols = Connect.getInstance().findAllAlcohol();
        alcoholsToBePurged = new ArrayList<>(); // prepare candidates to be purged
        Iterator<Alcohol> iterator = listOfAlcohols.iterator();
        while (iterator.hasNext()) {
            Alcohol alcohol = iterator.next();
            RetireAlcoholListUI alcoholRow = new RetireAlcoholListUI(alcohol);
            addAlcoholRow(alcoholRow);
            alcoholsToBePurged.add(alcoholRow);
        }
    }


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
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JScrollPane scrollPane1 = new JScrollPane();
        contentPane.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(scrollPane);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        okButton = new JButton();
        okButton.setText("OK");
        panel1.add(okButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        panel1.add(cancelButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
