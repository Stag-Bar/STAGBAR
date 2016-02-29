package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;
import java.awt.*;

public class ApplicationUI {

	private JFrame frame;
	private static final String APPLICATION_TITLE = "STAGBAR";
	private JPanel masterPanel;

	public ApplicationUI(){
		frame = new JFrame(APPLICATION_TITLE);

		masterPanel = new JPanel(new CardLayout());
		masterPanel.setName("Master");

		MainMenuUI mainMenuUI = new MainMenuUI();
		masterPanel.add(mainMenuUI.getContentPane());

		frame.setContentPane(masterPanel);
		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LoginUI loginUI = new LoginUI(frame);
		loginUI.setVisible(true);
	}

	public JFrame getFrame() {
		return frame;
	}

}
