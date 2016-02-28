package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;
import java.awt.*;

public class ApplicationFrame {

	private JFrame frame;
	private static final String APPLICATION_TITLE = "STAGBAR";
	private static final String TITLE_MANAGE_USERS = "Manage Users";
	private JPanel masterPanel;

	ApplicationFrame(){
		frame = new JFrame(APPLICATION_TITLE);

//		masterPanel = new JPanel(new PreferredCardLayout());
		masterPanel = new JPanel(new CardLayout());
		masterPanel.setName("Master");

		frame.setContentPane(masterPanel);


		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setScreen_UserAccountManagement(){
		UserAccountManagementUI userAccountManagementUI = new UserAccountManagementUI();
		masterPanel.add(userAccountManagementUI.getContentPane(), TITLE_MANAGE_USERS);
		setTitle(TITLE_MANAGE_USERS);
		frame.pack();
	}

	public JFrame getFrame() {
		return frame;
	}

	private void setTitle(String screen){
		frame.setTitle(String.format("STAGBAR - %s", screen));
	}




	//TODO: Delete test main method
	public static void main(String[] args) {
		ApplicationFrame frame = new ApplicationFrame();
		frame.setScreen_UserAccountManagement();
	}

}
