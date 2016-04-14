package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;
import java.awt.*;

public class ApplicationUI {

	public static final String APPLICATION_TITLE = "STAGBAR";
	private JFrame frame;

	public ApplicationUI(){
		LoginUI loginUI = new LoginUI(frame);
		loginUI.setVisible(true);

		frame = new JFrame(APPLICATION_TITLE);

		JPanel masterPanel = new JPanel(new CardLayout());
		masterPanel.setName("Master");

		MainMenuUI mainMenuUI = new MainMenuUI();
		masterPanel.add(mainMenuUI.getContentPane());

		frame.setContentPane(masterPanel);
		frame.pack();

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public JFrame getFrame() {
		return frame;
	}

}
