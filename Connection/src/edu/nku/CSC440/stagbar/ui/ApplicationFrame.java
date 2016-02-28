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

		MainMenuUI mainMenuUI = new MainMenuUI();
		masterPanel.add(mainMenuUI.getContentPane());
		frame.pack();

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LoginUI loginUI = new LoginUI(frame);
		loginUI.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

	//TODO: Delete test main method
	public static void main(String[] args) {
		new ApplicationFrame();
	}

}
