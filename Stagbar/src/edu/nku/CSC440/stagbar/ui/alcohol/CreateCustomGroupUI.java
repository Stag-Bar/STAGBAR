package edu.nku.CSC440.stagbar.ui.alcohol;

import javax.swing.*;

import edu.nku.CSC440.stagbar.dataaccess.data.AlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.data.CustomAlcoholType;
import edu.nku.CSC440.stagbar.dataaccess.databaseConnection.Connect;
import edu.nku.CSC440.stagbar.service.AlcoholService;
import edu.nku.CSC440.stagbar.ui.common.uiHacks;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Set;


public class CreateCustomGroupUI {
	
	private JPanel mainpanel;
	private JTextField nameField;
	private JComboBox box;
	
	public CreateCustomGroupUI(){
				
		mainpanel = new JPanel();
		mainpanel.setLayout(new GridLayout(3, 2, 100, 100));
		
		mainpanel.setName("Custom Group");
		mainpanel.setVisible(true);
				
		JPanel panel1 = new JPanel();
		mainpanel.add(panel1);
		JLabel lblAlcoholName = new JLabel("Alcohol Name");
		panel1.add(lblAlcoholName);
		
		JPanel panel2 = new JPanel();
		nameField = new JTextField();
		mainpanel.add(panel2);
		panel2.add(nameField);
		nameField.setColumns(10);
		
		JPanel panel3 = new JPanel();
		mainpanel.add(panel3);
		JLabel lblNewLabel = new JLabel("Type");
		panel3.add(lblNewLabel);
		
		JPanel panel4 = new JPanel();
		mainpanel.add(panel4);
		String[] options = {"Bottled","Draft","Shelf"}; 
		box = new JComboBox<String>(options);
		panel4.add(box);
		mainpanel.add(panel4);
		
		JPanel panel5 = new JPanel();
		mainpanel.add(panel5);
		JButton back = new JButton("Back");
		back.addActionListener(e -> onBack());
		panel5.add(back);
		
		JPanel panel6 = new JPanel();
		mainpanel.add(panel6);
		JButton submit = new JButton("Submit");
		submit.addActionListener(e -> onSubmit());
		panel6.add(submit);
				
	}
			
	private void onBack(){
		uiHacks.killMeThenGoToLastPage(mainpanel);
	}
	
	private void onSubmit(){
		
		CustomAlcoholType newGroup;
		Set set = Connect.getInstance().findAllCustomAlcoholTypes();
		int index = set.size();
		String name = nameField.getText();
		
		if ((String)box.getSelectedItem() == "Bottled"){
			newGroup = new CustomAlcoholType(index, name, AlcoholType.BOTTLED);
		}
		else if ((String)box.getSelectedItem() == "Draft"){
			newGroup = new CustomAlcoholType(index, name, AlcoholType.DRAFT);
		}
		else{
			newGroup = new CustomAlcoholType(index, name, AlcoholType.SHELF);
		}
		
		Connect.getInstance().saveCustomAlcoholType(newGroup);
				
	}
		
	
	public JPanel getContentPane() {
        return mainpanel;
    }
	

}