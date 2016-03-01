package edu.nku.CSC440.stagbar.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainFrame extends JFrame {

/*This isn't anywhere as close to being as done as I'd like it to be when I commit it, but I didn't want to sit on it for so long
  without anybody else getting to take a look at it. I tried for way too long to get this to work with one main menu and adding
  and removing different JPanels using different methods, but I didn't manage to get it quite right so we'll have to settle with
  opening and closing new windows for each menu at this point. This isn't in the right package or anything either yet, so it hasn't been
  integrated with the other two menus we have written at this point. If you want to run it with those you'll have to make that change 
  yourself, otherwise I'll get it done before Monday afternoon when Sam records the demo.
*/
	
JFrame mainFrame;
JPanel currentPane;

public MainFrame(){
	
	JPanel login = new JPanel();
	
	getContentPane().setLayout(null);
	this.mainFrame = new JFrame();
	mainFrame.setBounds(0, 0, 200, 200);
	mainFrame.setVisible(true);
	
	JLabel lblThisIsThe = new JLabel("This is the login screen");
	login.add(lblThisIsThe);
	JButton btnLogin = new JButton("Login");
		
	btnLogin.addActionListener(new ActionListener(){
		
		public void actionPerformed(ActionEvent e){
						
			mainFrame.remove(currentPane);
			DrawMainMenu();
			//mainFrame.repaint();
			System.out.println("Tried to change screens");
				
		}
		
	});
	
	btnLogin.setBounds(137, 159, 89, 23);
	login.add(btnLogin);
	
	login.setVisible(true);
	this.currentPane = login;
	
	//mainFrame.setCurrentPanel(login);
	mainFrame.add(login);
		
}

public void DrawMainMenu(){
	
	JPanel mainMenu = new JPanel();
	mainMenu.setLayout(new GridLayout(4,2));
	
	//getContentPane().setLayout(null);
	mainFrame = new JFrame();
	mainFrame.setBounds(0, 0, 400, 400);
	mainFrame.setVisible(true);
		
	//mainMenu.setLayout(null);
	JButton btnInv = new JButton("Inventory Reports");
	btnInv.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			DrawInventoryMenu();
		}
	});
	mainMenu.add(btnInv);
	
	JButton btnNewAlc = new JButton("Enter New Alcohol");
	btnNewAlc.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			DrawNewAlcoholMenu();
		}
	});
	mainMenu.add(btnNewAlc);
	
	JButton btnNewInv = new JButton("Enter Inventory");
	btnNewInv.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			DrawEnterInventoryMenu();
		}
	});
	mainMenu.add(btnNewInv);
	
	JButton btnSales = new JButton("Enter Alcohol Sales");
	btnSales.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			DrawEnterSalesMenu();
		}
	});
	mainMenu.add(btnSales);
	
	JButton btnRetire = new JButton("Retire Alcohol");
	btnRetire.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			DrawRetireMenu();
		}
	});
	mainMenu.add(btnRetire);
	
	JButton btnAccounts = new JButton("Manage User Accounts");
	btnAccounts.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			DrawAccountMenu();
		}
	});
	mainMenu.add(btnAccounts);
	
	JButton btnCreateMixed = new JButton("Create Mixed Drink");
	btnCreateMixed.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			DrawCreateMixedMenu();
		}
	});
	mainMenu.add(btnCreateMixed);
	
	JButton btnEditMixed = new JButton("Edit Mixed Drinks");
	btnEditMixed.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			DrawEditMixedMenu();
		}
	});
	mainMenu.add(btnEditMixed);
	
	mainMenu.setVisible(true);
	
	currentPane = mainMenu;
	mainFrame.add(mainMenu);
		
}

public void DrawInventoryMenu(){
	
}

public void DrawNewAlcoholMenu(){
	
}

public void DrawEnterInventoryMenu(){
	
}

public void DrawEnterSalesMenu(){
	
}

public void DrawRetireMenu(){
	
}

public void DrawAccountMenu(){
	
}

public void DrawCreateMixedMenu(){
	
}

public void DrawEditMixedMenu(){
	
}


public static void main(String[] args) {
	
	MainFrame mainframe = new MainFrame();
	//mainframe.setVisible(true);
		
}

}



