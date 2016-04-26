package edu.nku.CSC440.stagbar.ui.alcohol;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;


public class CreateCustomGroupUI {
	
	
	
	private JTextField textField;
	private JPanel mainpanel;
	
	public CreateCustomGroupUI(){
		
	
		
		
		mainpanel = new JPanel();
		//getContentPane().add(mainpanel);
		mainpanel.setLayout(new GridLayout(2, 2, 100, 100));
		
		mainpanel.setName("Custom Group");
		mainpanel.setVisible(true);
		//mainpanel.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel panel1 = new JPanel();
		mainpanel.add(panel1);
		JLabel lblAlcoholName = new JLabel("Alcohol Name");
		panel1.add(lblAlcoholName);
		
		JPanel panel2 = new JPanel();
		textField = new JTextField();
		mainpanel.add(panel2);
		panel2.add(textField);
		textField.setColumns(10);
		
		JPanel panel3 = new JPanel();
		mainpanel.add(panel3);
		JLabel lblNewLabel = new JLabel("Type");
		panel3.add(lblNewLabel);
		
		JPanel panel4 = new JPanel();
		mainpanel.add(panel4);
		String[] options = {"Bottled","Draft","Shelf"}; 
		JComboBox box = new JComboBox(options);
		panel4.add(box);
		mainpanel.add(panel4);
		
		
	}
	
	public JPanel getContentPane() {
        return mainpanel;
    }
	

}