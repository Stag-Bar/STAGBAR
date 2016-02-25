package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.service.UserService;

import javax.swing.*;
import java.awt.event.*;

public class LoginUI extends JDialog {
	public static final String TITLE = "Login";
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JPasswordField password;
	private JTextField username;
	private JLabel errorMessage;
	private static final String INVALID_LOGIN = "Invalid Username/Password.";
	private UserService userService;

	// TODO Call super (<T> owner, String title, boolean modal)
	// owner type TBD, title: "Login", modal: true
	// public LoginUI(<T> owner) {
	// super(owner, TITLE, true);
	public LoginUI() {
		userService = new UserService();

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
	}

	private void onOK() {
		if(userService.login(username.getText(), password.getPassword())){
			System.out.println("Login Successful");
			dispose();
		}

		errorMessage.setText(INVALID_LOGIN);
		pack();
	}

	private void onCancel() {
		dispose();
	}

	//TODO Delete test method.
	public static void main(String[] args) {
		LoginUI dialog = new LoginUI();
		dialog.setVisible(true);
		System.exit(0);
	}
}
