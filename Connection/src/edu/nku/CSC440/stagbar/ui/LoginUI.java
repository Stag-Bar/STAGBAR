package edu.nku.CSC440.stagbar.ui;

import edu.nku.CSC440.stagbar.service.LoginService;

import javax.swing.*;
import java.awt.event.*;

// s
public class LoginUI extends JDialog {
	private JPanel contentPane;
	private JButton buttonOK;
	private JButton buttonCancel;
	private JPasswordField password;
	private JTextField username;
	private JLabel errorMessage;
	private static final String INVALID_LOGIN = "Invalid Username/Password.";
	private LoginService loginService;

	public LoginUI() {
		loginService = new LoginService();

		setContentPane(contentPane);
		setModal(true);
		setTitle("Login");

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
		if(loginService.login(username.getText(), password.getPassword())){
			System.out.println("Login Successful");
			dispose();
		}

		errorMessage.setText(INVALID_LOGIN);
		pack();
	}

	private void onCancel() {
		dispose();
	}

	public static void main(String[] args) {
		LoginUI dialog = new LoginUI();
		dialog.setVisible(true);
		System.exit(0);
	}
}
