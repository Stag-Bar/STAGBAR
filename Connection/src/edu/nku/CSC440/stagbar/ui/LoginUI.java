package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.service.UserService;

import javax.swing.*;
import java.awt.event.*;

public class LoginUI extends JDialog {
	public static final String TITLE = "Login";
	private static final String INVALID_LOGIN = "Invalid Username/Password.";
	private JButton buttonCancel;
	private JButton buttonOK;
	private JPanel contentPane;
	private JLabel errorMessage;
	private JPasswordField password;
	private UserService userService;
	private JTextField username;

	public LoginUI(JFrame owner) {
		super(owner, TITLE, true);
		userService = UserService.getInstance();

		setContentPane(contentPane);
		setTitle(TITLE);
		setModal(true);

		getRootPane().setDefaultButton(buttonOK);

		buttonOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {onOK();}
		});

		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {onCancel();}
		});

		// call onCancel() when cross is clicked
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancel();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		toFront();
	}

	/**
	 * As of now, the only login screen happens when the application is booted.
	 * If the user cannot succeccfully login, the application closes.
	 * Consider changing if we allow switching user while application is running.
	 */
	private void onCancel() {
		System.exit(0);
	}

	private void onOK() {
		if(userService.login(username.getText(), password.getPassword())) {
			System.out.format("Login as %s Successful\n", username.getText());
			dispose();
		}

		errorMessage.setText(INVALID_LOGIN);
		pack();
	}

}
