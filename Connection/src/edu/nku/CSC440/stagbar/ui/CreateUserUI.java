package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.service.UserService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserUI extends JPanel{
	public static final String ERROR_REQUIRED_FIELDS = "All fields must be filled.";
	private JTextField usernameTextField;
	private JPasswordField passwordPasswordField;
	private JPasswordField confirmPasswordPasswordField;
	private JComboBox permissionLevelComboBox;
	private JButton okButton;
	private JLabel errorMessage;
	private JPanel contentPane;
	private UserService userService;


	public CreateUserUI() {
		userService = new UserService();

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onOK();
			}
		});
	}

	private void onOK() {
		// TODO: Check that all fields are filled
		if(usernameTextField.getText().isEmpty() || 0 == passwordPasswordField.getPassword().length ||
				0 == confirmPasswordPasswordField.getPassword().length || null == permissionLevelComboBox.getSelectedItem())
			errorMessage.setText(ERROR_REQUIRED_FIELDS);

		//TODO: Turn blank fields RED; revert fixed fields to BLACK

		// TODO: Check that passwords match
		// TODO: Check that username is unique in DB
		// TODO: Save user to DB
		// TODO: Display confirmation to user
		System.out.println(errorMessage.getSize());
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("CreateUserUI");
		frame.setContentPane(new CreateUserUI().contentPane);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
