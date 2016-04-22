package edu.nku.CSC440.stagbar.ui.user;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.nku.CSC440.stagbar.dataaccess.data.User;

import javax.swing.*;
import java.awt.*;


public class DeleteUserListUI {
    private JPanel contentPane;
    private JCheckBox userCheckBox;
    private User user;
    private boolean expelliarmus;

    public DeleteUserListUI(User user) {
        if (null == user) throw new IllegalArgumentException("User cannot be null.");
        this.user = user;
        $$$setupUI$$$();
        userCheckBox.setText(user.getUsername());
        userCheckBox.addItemListener(e -> userDeletion());
        expelliarmus = false;
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void createUIComponents() {
        userCheckBox = new JCheckBox();
    }

    private void userDeletion() {
        if (userCheckBox.isSelected()) {
            expelliarmus = true;
        } else if (!userCheckBox.isSelected()) {
            expelliarmus = false;
        }

    }

    public User getUser() {
        return user;
    }

    public boolean getDeletionStatus() {
        return expelliarmus;
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        userCheckBox = new JCheckBox();
        userCheckBox.setText("CheckBox");
        contentPane.add(userCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        contentPane.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}