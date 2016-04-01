
package edu.nku.CSC440.stagbar.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.nku.CSC440.stagbar.Application;
import edu.nku.CSC440.stagbar.Connect.Connect;
import edu.nku.CSC440.stagbar.dataaccess.User;
import edu.nku.CSC440.stagbar.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeleteUserUI {
	private static final String DELETE_SUCCESS = "The deletion of user(s) is successful.";
	private static final String PLEASE_CHECK_BOX = "Please delete at least one user, or hit Cancel button.";
	private static final String TITLE_DELETE_SUCCESS = "The deletion of user(s) is successful.";
	private static final String TITLE_PLEASE_CHECK = "Deletion failed";
	private JButton cancelButton;
	private JPanel contentPane;
	private JButton okButton;
	private JPanel scrollPanel;
	private JCheckBox userCheckBox;
	private ArrayList<Map<Integer, JCheckBox>> userMapList;
	//private JLabel usernameLabel;
	private ArrayList<String> usersToBeDeleted;

	public DeleteUserUI() {
		$$$setupUI$$$();
		usersToBeDeleted = new ArrayList<>();
		userMapList = new ArrayList<>();

		contentPane.setName("Delete User(s)");
		okButton.addActionListener(e -> onOK());
		cancelButton.addActionListener(e -> onCancel());
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
		contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
		final JScrollPane scrollPane1 = new JScrollPane();
		contentPane.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		scrollPanel.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
		scrollPane1.setViewportView(scrollPanel);
		userCheckBox.setText("");
		scrollPanel.add(userCheckBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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

	private void createUIComponents() {
		int counter = 0;
		ArrayList<User> allUsers = new ArrayList<>();
		ArrayList<Map<Integer, JCheckBox>> userMapList = new ArrayList<>();
		for(User user : Connect.getInstance().findAllUsers()) {
			allUsers.add(user);
		}
		for(User thisUser : allUsers) {
			//ensure that current user is not added to list of users to be deleted
			if(!Application.getInstance().getUser().getUsername().equals(thisUser.getUsername())) {
				String userToBeShown = thisUser.getUsername();
				//usernameLabel = new JLabel();
				//usernameLabel.setText(userToBeShown);
				userCheckBox = new JCheckBox(userToBeShown, false); //all are initially false (i.e. unselected)
				Map<Integer, JCheckBox> userMap = new HashMap<>();
				userMap.put(counter, userCheckBox);
				userMapList.add(userMap);
				//scrollPanel.add(userCheckBox);
				counter++;
			}
		}
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	private void getSelections() {
		int count = 0;
		for(Map<Integer, JCheckBox> thisUser : userMapList) {
			if(thisUser.get(count).isSelected()) { //if the current checkbox is selected
				usersToBeDeleted.add(userCheckBox.getText()); //add the user's name into the final list
			}
			count++;
		}
	}

	private void onCancel() {
		uiHacks.killMeThenGoToLastPage(contentPane);
	}

	private void onOK() {
		getSelections();
		if(usersToBeDeleted.isEmpty()) { //if user has not selected anyone to be deleted
			JOptionPane.showMessageDialog(contentPane, PLEASE_CHECK_BOX, TITLE_PLEASE_CHECK, JOptionPane.ERROR_MESSAGE);
		}
		else {
			for(String user : usersToBeDeleted) { //iterate through the final list to delete each user
				UserService.getInstance().deleteUser(user);
			}
			// Display confirmation to user
			JOptionPane.showMessageDialog(contentPane, DELETE_SUCCESS, TITLE_DELETE_SUCCESS, JOptionPane.INFORMATION_MESSAGE);
			okButton.setEnabled(false);
			uiHacks.killMeThenGoToLastPage(contentPane);
		}

		//StringBuilder sb = new StringBuilder();
		//sb.append("Deleted Users: ");
		//Set<String> usernames = new HashSet<>();

		//for(JCheckBox userCheckBox : checkBoxes) {
		// usernames.add(userCheckBox.getText());
		// if(userCheckBox.isSelected()) {
		//    sb.append(userCheckBox.getText()).append(' ');
		// }
		//}
	}


}

