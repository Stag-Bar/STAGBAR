package edu.nku.CSC440.stagbar.ui.user;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.nku.CSC440.stagbar.Application;
import edu.nku.CSC440.stagbar.dataaccess.data.PermissionLevel;
import edu.nku.CSC440.stagbar.dataaccess.data.User;
import edu.nku.CSC440.stagbar.dataaccess.databaseConnection.Connect;
import edu.nku.CSC440.stagbar.ui.common.uiHacks;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

public class EditUserPermissionUI {

    private JButton cancelButton;
    private JButton okButton;
    private JPanel contentPane;
    private JPanel scrollPane;
    private JLabel editUserPermission;
    private ArrayList<UserListUI> usersToBeModified;
    private static final String CONGRATS = "Change of Permission Level is successful!";
    private static final String CONGRATS_PLURAL = "Changes of Permission Level are successful!";
    private static final String TITLE_CONGRATS = "Congratulations!";
    private static final String TITLE_NOTHING = "Nothing happened!";
    private static final String NOTHING = "You haven't changed anyone's permission level. Bye!";
    private int userChanged = 0;

    public EditUserPermissionUI() {
        $$$setupUI$$$();
        contentPane.setName("Edit User Permission");
        okButton.addActionListener(e -> onOK());
        cancelButton.addActionListener(e -> onCancel());
        populateUserPermissions();
    }

    public void addUserRow(UserListUI userRow) {
        scrollPane.add(userRow.getContentPane());
    }

    private void onOK() {
        for (UserListUI userRow : usersToBeModified) {
            if (userRow.getConversionStatus()) { //if this user has been converted
                userChanged++;
                if (userRow.getUser().getPermissionLevel().equals(PermissionLevel.ADMIN)) {
                    Connect.getInstance().updateUserPermissions(userRow.getUser().getUsername(), PermissionLevel.GUEST);
                } else if (userRow.getUser().getPermissionLevel().equals(PermissionLevel.GUEST)) {
                    Connect.getInstance().updateUserPermissions(userRow.getUser().getUsername(), PermissionLevel.ADMIN);
                }
            }
        }
        if (userChanged == 1) //One user has undergone conversion...Good!
            JOptionPane.showMessageDialog(contentPane, CONGRATS, TITLE_CONGRATS, JOptionPane.INFORMATION_MESSAGE);
        else if (userChanged > 1) //More than one user has undergone conversion...Hallelujah!
            JOptionPane.showMessageDialog(contentPane, CONGRATS_PLURAL, TITLE_CONGRATS, JOptionPane.INFORMATION_MESSAGE);
        else//Oh c'mon man, no conversion at all? Please don't waste my time!
            JOptionPane.showMessageDialog(contentPane, NOTHING, TITLE_NOTHING, JOptionPane.INFORMATION_MESSAGE);
        okButton.setEnabled(false);
        uiHacks.killMeThenGoToLastPage(contentPane);
    }

    private void onCancel() {
        uiHacks.killMeThenGoToLastPage(contentPane);
    }

    private void createUIComponents() {
        scrollPane = new JPanel();
        scrollPane.setLayout(new BoxLayout(scrollPane, BoxLayout.Y_AXIS));
    }

    private void populateUserPermissions() {
        Set<User> listOfUsers = Connect.getInstance().findAllUsers();
        usersToBeModified = new ArrayList<>();
        Iterator<User> iterator = listOfUsers.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (!(user.equals(Application.getInstance().getUser()))) { //NEVER allow current user to edit own permission
                UserListUI userRow = new UserListUI(user);
                addUserRow(userRow);
                usersToBeModified.add(userRow);     }
        }
    }

    public JPanel getContentPane() {
        return contentPane;
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
        contentPane.setLayout(new GridLayoutManager(3, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        editUserPermission = new JLabel();
        editUserPermission.setText("Edit User Permission");
        editUserPermission.setDisplayedMnemonic('E');
        editUserPermission.setDisplayedMnemonicIndex(0);
        panel1.add(editUserPermission, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setViewportView(scrollPane);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(panel3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        cancelButton.setMnemonic('C');
        cancelButton.setDisplayedMnemonicIndex(0);
        panel4.add(cancelButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel4.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        okButton = new JButton();
        okButton.setText("OK");
        okButton.setMnemonic('O');
        okButton.setDisplayedMnemonicIndex(0);
        panel4.add(okButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}





