
package edu.nku.CSC440.stagbar.ui.user;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.nku.CSC440.stagbar.Application;
import edu.nku.CSC440.stagbar.dataaccess.data.User;
import edu.nku.CSC440.stagbar.service.UserService;
import edu.nku.CSC440.stagbar.ui.common.uiHacks;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeleteUserUI {
	private static final String DELETE_SUCCESS = "The deletion of user(s) is successful.";
	private static final String PLEASE_CHECK_BOX = "Please delete at least one user, or hit Cancel button.";
	private static final String TITLE_DELETE_SUCCESS = "The deletion of user(s) is successful.";
	private static final String TITLE_PLEASE_CHECK = "Deletion failed";
	private final Map<User, JCheckBox> checkBoxMap;
	private JButton cancelButton;
	private JPanel contentPane;
	private JButton okButton;
	private JPanel scrollPane;

	public DeleteUserUI() {
		$$$setupUI$$$();
		checkBoxMap = new HashMap<>();

		contentPane.setName("Delete User(s)");
		okButton.addActionListener(e -> onOK());
		cancelButton.addActionListener(e -> onCancel());

		populateUserCheckBoxes();
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
		contentPane = new JPanel();
		contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
		final JScrollPane scrollPane1 = new JScrollPane();
		contentPane.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		scrollPane = new JPanel();
		scrollPane.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
		scrollPane1.setViewportView(scrollPane);
		final JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
		contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.setMnemonic('C');
		cancelButton.setDisplayedMnemonicIndex(0);
		panel1.add(cancelButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		final Spacer spacer1 = new Spacer();
		panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
		okButton = new JButton();
		okButton.setText("OK");
		okButton.setMnemonic('O');
		okButton.setDisplayedMnemonicIndex(0);
		panel1.add(okButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	private Set<User> getSelectedUsers() {
		Set<User> usersToBeDeleted = new HashSet<>();

		for(Map.Entry<User, JCheckBox> entry : checkBoxMap.entrySet()) {
			if(entry.getValue().isSelected()) { // If the current checkbox is selected...
				usersToBeDeleted.add(entry.getKey()); // Add user into deletion list.
			}
		}
		return usersToBeDeleted;
	}

	private void onCancel() {
		uiHacks.killMeThenGoToLastPage(contentPane);
	}

	private void onOK() {
		Set<User> usersToBeDeleted = getSelectedUsers();

		if(usersToBeDeleted.isEmpty()) { // user has not selected anyone to be deleted
			JOptionPane.showMessageDialog(contentPane, PLEASE_CHECK_BOX, TITLE_PLEASE_CHECK, JOptionPane.ERROR_MESSAGE);
		}
		else {
			StringBuilder deletedUsers = new StringBuilder();

			for(User user : usersToBeDeleted) { // delete each user
				UserService.getInstance().deleteUser(user);

				if(0 != deletedUsers.length()) deletedUsers.append("\t");
				deletedUsers.append(user.getUsername());
			}
			// Display confirmation to user
			JOptionPane.showMessageDialog(contentPane, DELETE_SUCCESS + '\n' + deletedUsers.toString(), TITLE_DELETE_SUCCESS, JOptionPane.INFORMATION_MESSAGE);
			okButton.setEnabled(false);
			uiHacks.killMeThenGoToLastPage(contentPane);
		}
	}

	private void populateUserCheckBoxes() {
		for(User user : UserService.getInstance().getAllUsers()) {
			// Ensure current user is not added to list of users that can be deleted.
			if(!Application.getInstance().getUser().equals(user)) {
				JCheckBox userCheckBox = new JCheckBox(user.getUsername(), false); //all are initially false (i.e. unselected)
				checkBoxMap.put(user, userCheckBox);
				scrollPane.add(userCheckBox);
			}
		}
	}


}

