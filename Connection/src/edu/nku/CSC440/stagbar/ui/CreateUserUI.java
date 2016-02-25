package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Anthony.
 */
public class CreateUserUI {
	private JTextField usernameTextField;
	private JPasswordField passwordPasswordField;
	private JPasswordField confirmPasswordPasswordField;
	private JComboBox permissionLevelComboBox;
	private JButton okButton;
	private JLabel errorMessage;


	public CreateUserUI() {

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onConfirm();
			}
		});
	}

	private void onConfirm() {
		// TODO: Check that all fields are filled
		// TODO: Check that passwords match
		// TODO: Check that username is unique in DB
		// TODO: Save user to DB
		// TODO: Display confirmation to user
	}
}
