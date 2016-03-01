package edu.nku.CSC440.stagbar.ui.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Deprecated
class CardLayoutDemo1 extends JFrame {

	private JPanel jPanel1;
	private JButton navHomeButt;
	private JButton navNextButt;
	private JButton navPreviousButt;
	private JPanel panelContainer;

	public CardLayoutDemo1() {
		initComponents();


		panelContainer.add(createSamplePanel("Home Panel "), ""+0);
		for(int i=1; i < 10; i++)
		{
			panelContainer.add(createSamplePanel("Panel "+i), ""+i);
		}
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		navPreviousButt = new JButton();
		navNextButt = new JButton();
		navHomeButt = new JButton();
		panelContainer = new JPanel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel1.setBackground(new java.awt.Color(255, 255, 255));

		navPreviousButt.setText("Previous");
		navPreviousButt.setPreferredSize(new Dimension(90, 23));
		jPanel1.add(navPreviousButt);

		navNextButt.setText("next");
		navNextButt.setPreferredSize(new Dimension(90, 23));
		jPanel1.add(navNextButt);

		navHomeButt.setText("Back to Home");
		jPanel1.add(navHomeButt);



		panelContainer.setPreferredSize(new Dimension(400, 300));
		panelContainer.setLayout(new CardLayout());
		// setting the card layout

		getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);
		getContentPane().add(panelContainer, BorderLayout.CENTER);




		navNextButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				CardLayout cardLayout = (CardLayout) panelContainer.getLayout();
				cardLayout.next(panelContainer);
				// using cardLayout next() to go  to next panel
			}
		});
		navHomeButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				CardLayout cardLayout = (CardLayout) panelContainer.getLayout();
				cardLayout.first(panelContainer);
				// suing first to get to the home panel
			}
		});
		navPreviousButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				CardLayout cardLayout = (CardLayout) panelContainer.getLayout();
				cardLayout.previous(panelContainer);

				// using previous to get to previous(left)panel
			}
		});

		pack();
	}

	public JPanel createSamplePanel(String panelTitle)
	{
		JPanel samplePanel = new JPanel();
		samplePanel.add(new JLabel(panelTitle));

		return samplePanel;
	}


	public static void main(String args[]) {

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new CardLayoutDemo1().setVisible(true);
			}
		});
	}

}