package ChatGUI;
import utils.RoundJTextField;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import data.Account;
import data.UserDB;

import java.awt.event.*;
import java.io.IOException;

public class Regis {
	JFrame regWindow;
	//private JFrame regCloseConfirm;
	private JTextField user;
	private JPasswordField pass, pass2;
	
	public Regis() {
		init();
	}
	
	private void init() {
		//Set up colors
		Color newBlue = new Color(0, 79, 109);
		
		//Set up window Registration
		regWindow = new JFrame("Sign Up!");
		regWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		regWindow.getContentPane().setLayout(null);
		regWindow.getContentPane().setBackground(Color.WHITE);
		regWindow.setBounds(480, 320, 960, 480);
		regWindow.getContentPane().setLayout(null);
		
		//Setup Regis Label Welcome
		JLabel regWel = new JLabel("Welcome!");
		regWel.setHorizontalAlignment(SwingConstants.CENTER);
		regWel.setForeground(newBlue);
		regWel.setFont(new Font("Caranda Personal Use", Font.PLAIN, 48));
		regWel.setBounds(260, 40, 484, 100);
		
		//regWel.setBorder(new LineBorder(newBlue, 1));
		regWindow.getContentPane().add(regWel);

		//Setup instruction Label
		JLabel instruct = new JLabel("Please fill all your information!");
		instruct.setHorizontalAlignment(SwingConstants.CENTER);
		//instruct.setForeground(newBlue);
		instruct.setFont(new Font("Gabriola", Font.PLAIN, 36));
		instruct.setBounds(280, 115, 484, 40);
				
		//instruct.setBorder(new LineBorder(newBlue, 1));
		regWindow.getContentPane().add(instruct);		
		
		//Set up for Username Label
		JLabel regUser = new JLabel("Username:");
		regUser.setFont(new Font("Gabriola", Font.BOLD, 32));
		regUser.setForeground(newBlue);
		regUser.setBounds(182, 160, 172, 37);
		//regUser.setBorder(new LineBorder(newBlue, 1));
		regWindow.getContentPane().add(regUser);
		
		//Setup for username's input
		user = new RoundJTextField(20);
		user.setFont(new Font("Calibri", Font.PLAIN, 24));
		user.setForeground(newBlue);
		user.setBounds(340,160, 360, 30);
		regWindow.getContentPane().add(user);
		user.setColumns(10);
		
		//Set up for Password Label
		JLabel regPass = new JLabel("Password:");
		regPass.setFont(new Font("Gabriola", Font.BOLD, 32));
		regPass.setForeground(newBlue);
		regPass.setBounds(190, 205, 172, 43);
		regWindow.getContentPane().add(regPass);
		
		//Setup for password's input
		pass = new JPasswordField();
		pass.setFont(new Font("Calibri", Font.PLAIN, 28));
		pass.setForeground(newBlue);
		pass.setBounds(340, 208, 360, 30);
		regWindow.getContentPane().add(pass);
		
		//Set up for Password Label
		JLabel regPass2 = new JLabel("Confirm password:");
		regPass2.setFont(new Font("Gabriola", Font.BOLD, 32));
		regPass2.setForeground(newBlue);
		regPass2.setBounds(85, 250, 292, 43);
		regWindow.getContentPane().add(regPass2);
				
		//Setup for password's input
		pass2 = new JPasswordField();
		pass2.setFont(new Font("Calibri", Font.PLAIN, 28));
		pass2.setForeground(newBlue);
		pass2.setBounds(340, 258, 360, 30);
		regWindow.getContentPane().add(pass2);
		
		//Set up Sign up button
		JButton buttonSignUp = new JButton();
		try {
		    Image imgSignUp = ImageIO.read(getClass().getResource("/utils/correct.png"));
		    buttonSignUp.setIcon(new ImageIcon(imgSignUp));
		    buttonSignUp.setBounds(440, 305, 70, 70);
		    buttonSignUp.setBackground(Color.WHITE);
		    buttonSignUp.setFocusPainted(false);
		    buttonSignUp.setBorder(BorderFactory.createEmptyBorder());
		} 
		catch (Exception ex) {
		    System.out.println(ex);
		}
		
		buttonSignUp.setFont(new Font("Gabriola", Font.BOLD, 30));
		buttonSignUp.setBackground(Color.WHITE);
		buttonSignUp.setForeground(newBlue);
		buttonSignUp.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String username = user.getText();
					char[] password = pass.getPassword();
					char[] repassword = pass2.getPassword();
					try {
						Account result = UserDB.getJson(username);
						if (result != null)
						{
							JOptionPane.showMessageDialog(null, "Account existed", "Failed", JOptionPane.ERROR_MESSAGE);
						}
						else if (password.equals(repassword)) {
							JOptionPane.showMessageDialog(null, "Wrong confirmation", "Failed", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							reset();
							result = new Account(username, password);
							UserDB.createJson(result);
							JOptionPane.showMessageDialog(null, "Account created", "Success", JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		);
		regWindow.getContentPane().add(buttonSignUp);
		
		JButton buttonBack = new JButton();
		//Check the resource first, if it has, it displays
		try {
		    Image img = ImageIO.read(getClass().getResource("/utils/back (2).png"));
		    buttonBack.setIcon(new ImageIcon(img));
		    buttonBack.setBounds(40, 40, 40, 40);
		    buttonBack.setBackground(Color.WHITE);
		    buttonBack.setFocusPainted(false); //disable the border
		    buttonBack.setBorder(BorderFactory.createEmptyBorder());
		    buttonBack.addActionListener(
					new ActionListener() {
						//When the user click on Back Button, return the Welcome Window
						public void actionPerformed(ActionEvent event) {
							//Check login right or wrong
							new Login();
							regWindow.dispose();
							
						}
					}
			);
		    regWindow.getContentPane().add(buttonBack);
		} 
		catch (Exception ex) {
		    System.out.println(ex);
		}
		regWindow.setVisible(true);
	}
	public void reset()
	{
		user.setText(null);
		pass.setText(null);
		pass2.setText(null);
	}
}

