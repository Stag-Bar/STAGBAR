package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.dataaccess.PermissionLevel;
import edu.nku.CSC440.stagbar.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class CreateUserUI {
	public static final String ERROR_CANNOT_SAVE = "Unable to save new user. Try again later.";
	public static final String ERROR_PASSWORD_MATCH = "Password does not match the confirm password.";
	public static final String ERROR_REQUIRED_FIELDS = "All fields must be filled.";
	public static final String ERROR_USER_EXISTS = "A user of that name already exists.";
	public static final String MESSAGE_NEW_USER = "New user %s has been created.";
	public static final String TITLE_CANNOT_SAVE = "User Creation Failed";
	public static final String TITLE_NEW_USER = "User Created";
	private JLabel confirmPasswordLabel;
	private JPasswordField confirmPasswordPasswordField;
	private JPanel contentPane;
	private JLabel errorMessage;
	private JButton okButton;
	private JLabel passwordLabel;
	private JPasswordField passwordPasswordField;
	private JComboBox permissionLevelComboBox;
	private JLabel permissionLevelLabel;
	private UserService userService;
	private JLabel usernameLabel;
	private JTextField usernameTextField;

	public CreateUserUI() {
		userService = new UserService();

		contentPane.setName("Create User");

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onOK();
			}
		});
	}

	private void createUIComponents() {
		permissionLevelComboBox = new JComboBox(PermissionLevel.values());
		permissionLevelComboBox.setSelectedIndex(-1);
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	/** Turns blank fields RED and reverts filled fields to BLACK. */
	private void highlightEmptyFields() {
		usernameLabel.setForeground(usernameTextField.getText().isEmpty() ?
																Color.RED : Color.BLACK);

		passwordLabel.setForeground(0 == passwordPasswordField.getPassword().length ?
																Color.RED : Color.BLACK);

		confirmPasswordLabel.setForeground(0 == confirmPasswordPasswordField.getPassword().length ?
																			 Color.RED : Color.BLACK);

		permissionLevelLabel.setForeground(null == permissionLevelComboBox.getSelectedItem() ?
																			 Color.RED : Color.BLACK);
	}

	private void onOK() {
		highlightEmptyFields();

		// Check that all fields are filled.
		if(usernameTextField.getText().isEmpty() || 0 == passwordPasswordField.getPassword().length ||
				0 == confirmPasswordPasswordField.getPassword().length || null == permissionLevelComboBox.getSelectedItem()) {
			errorMessage.setText(ERROR_REQUIRED_FIELDS);
		}
		// Check that passwords match
		else if(!Arrays.equals(passwordPasswordField.getPassword(), confirmPasswordPasswordField.getPassword())) {
			errorMessage.setText(ERROR_PASSWORD_MATCH);
			passwordLabel.setForeground(Color.RED); // Color reset by highlightEmptyFields() on next OK.
			confirmPasswordLabel.setForeground(Color.RED);
			confirmPasswordPasswordField.selectAll();
			confirmPasswordPasswordField.requestFocusInWindow();
		}
		// Check that username is unique in DB
		else if(userService.doesUserExist(usernameTextField.getText())) {
			errorMessage.setText(ERROR_USER_EXISTS);
			usernameLabel.setForeground(Color.RED);
			usernameTextField.selectAll();
			usernameTextField.requestFocusInWindow();
		}
		// Save user to DB
		else if(userService.saveNewUser(usernameTextField.getText(), passwordPasswordField.getPassword(), (PermissionLevel)permissionLevelComboBox.getSelectedItem())) {
			// Display confirmation to user
			JOptionPane.showMessageDialog(contentPane, String.format(MESSAGE_NEW_USER, usernameTextField.getText()), TITLE_NEW_USER, JOptionPane.INFORMATION_MESSAGE);

			//TODO: Navigate user away from page.
			okButton.setEnabled(false);
			uiHacks.killMeThenGoToLastPage(contentPane);
		}
		else {
			JOptionPane.showMessageDialog(contentPane, ERROR_CANNOT_SAVE, TITLE_CANNOT_SAVE, JOptionPane.ERROR_MESSAGE);
		}
	}

}
