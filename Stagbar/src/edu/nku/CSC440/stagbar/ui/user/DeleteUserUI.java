
package edu.nku.CSC440.stagbar.ui.user;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import edu.nku.CSC440.stagbar.Application;
import edu.nku.CSC440.stagbar.dataaccess.data.User;
import edu.nku.CSC440.stagbar.dataaccess.databaseConnection.Connect;
import edu.nku.CSC440.stagbar.ui.common.uiHacks;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DeleteUserUI {
	private static final String CONGRATS = "The user has been deleted!";
	private static final String CONGRATS_PLURAL = "The users have been deleted!";
	private static final String TITLE_CONGRATS = "Congratulations!";
	private static final String TITLE_NOTHING = "Nothing happened!";
	private static final String NOTHING = "You haven't deleted any user. Bye!";
	private ArrayList<DeleteUserListUI> usersToBePurged;
	private JButton cancelButton;
	private JPanel contentPane;
	private JButton okButton;
	private JPanel scrollPane;
	private int userPurged;

	public DeleteUserUI() {
		$$$setupUI$$$();
		contentPane.setName("Delete User");
		okButton.addActionListener(e -> onOK());
		cancelButton.addActionListener(e -> onCancel());
		userPurged = 0;
		populateUserCheckBoxes();
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
		int banishFromTribe = JOptionPane.showConfirmDialog(null, "Are you absolutely sure?", "Delete?",  JOptionPane.YES_NO_OPTION);
		if (banishFromTribe == JOptionPane.YES_OPTION)
		{
			for (DeleteUserListUI userRow : usersToBePurged) {
				if (userRow.getDeletionStatus()) { //if this user has been purged
					userPurged++;
					Connect.getInstance().deleteUser(userRow.getUser().getUsername());
				}
			}
			if (userPurged == 1) // One user has been banished from the tribe... Good riddance!
				JOptionPane.showMessageDialog(contentPane, CONGRATS, TITLE_CONGRATS, JOptionPane.INFORMATION_MESSAGE);
			else if (userPurged > 1) // More than one user has been banished from the tribe! MUAHAHAHA
				JOptionPane.showMessageDialog(contentPane, CONGRATS_PLURAL, TITLE_CONGRATS, JOptionPane.INFORMATION_MESSAGE);
			else // Oh c'mon man, no one banished at all? Please don't waste my time!
				JOptionPane.showMessageDialog(contentPane, NOTHING, TITLE_NOTHING, JOptionPane.INFORMATION_MESSAGE);
			okButton.setEnabled(false);
			uiHacks.killMeThenGoToLastPage(contentPane);
		}

	}

	public void addUserRow(DeleteUserListUI userRow) {
		scrollPane.add(userRow.getContentPane());
	}

	private void populateUserCheckBoxes() {
		Set<User> listOfUsers = Connect.getInstance().findAllUsers();
		usersToBePurged = new ArrayList<>();
		Iterator<User> iterator = listOfUsers.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (!(user.equals(Application.getInstance().getUser()))) { //NEVER allow current user to delete self
				DeleteUserListUI userRow = new DeleteUserListUI(user);
				addUserRow(userRow);
				usersToBePurged.add(userRow);
			}
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

	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {
		return contentPane;
	}
}

