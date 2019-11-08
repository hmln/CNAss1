package Reg;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import Login.runLogin;
import stuffs.RoundJTextField;

import java.awt.event.*;

public class Regis {
	JFrame regWindow;
	//private JFrame regCloseConfirm;
	private JTextField user;
	private JPasswordField pass;
	private JTextField email;
	
	
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
		regWindow.setVisible(true);
		
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
		
		//Set up for Email Label
		JLabel regEmail = new JLabel("email:");
		regEmail.setFont(new Font("Gabriola", Font.BOLD, 32));
		regEmail.setForeground(newBlue);
		regEmail.setBounds(180, 160, 172, 37);
		//regEmail.setBorder(new LineBorder(newBlue, 1));
		regWindow.getContentPane().add(regEmail);
		
		//Set up for Email input
		email = new RoundJTextField(20);
		email.setFont(new Font("Calibri", Font.PLAIN, 24));
		email.setForeground(newBlue);
		email.setBounds(340, 160, 360, 30);
		regWindow.getContentPane().add(email);
		email.setColumns(10);
		
		//Set up for Username Label
		JLabel regUser = new JLabel("username:");
		regUser.setFont(new Font("Gabriola", Font.BOLD, 32));
		regUser.setForeground(newBlue);
		regUser.setBounds(180, 205, 172, 37);
		//regUser.setBorder(new LineBorder(newBlue, 1));
		regWindow.getContentPane().add(regUser);
		
		//Setup for username's input
		email = new RoundJTextField(20);
		email.setFont(new Font("Calibri", Font.PLAIN, 24));
		email.setForeground(newBlue);
		email.setBounds(340,208, 360, 30);
		regWindow.getContentPane().add(email);
		email.setColumns(10);
		
		//Set up for Password Label
		JLabel regPass = new JLabel("password:");
		regPass.setFont(new Font("Gabriola", Font.BOLD, 32));
		regPass.setForeground(newBlue);
		regPass.setBounds(180, 250, 172, 43);
		regWindow.getContentPane().add(regPass);
		
		//Setup for password's input
		pass = new JPasswordField();
		pass.setFont(new Font("Calibri", Font.PLAIN, 28));
		pass.setForeground(newBlue);
		pass.setBounds(340, 258, 360, 30);
		regWindow.getContentPane().add(pass);
		
		//Set up Sign up button
		JButton buttonSignUp = new JButton();
		try {
		    Image imgSignUp = ImageIO.read(getClass().getResource("/stuffs/correct (2).png"));
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
						String password = pass.getText();
						String mail = email.getText();
						//Registered successfully then move to Login Section
						//Note: remember to change the if condition
						//This section for back-end 
						if (username.equals("admin") && password.equals("12345")) {
							user.setText(null);
							pass.setText(null);
							email.setText(null);
							runLogin amy = new runLogin();
							amy.main(null);
						}
						//Something went wrong, retype all the information
						else {
							JOptionPane.showMessageDialog(null, "Fail to register!", "Failed", JOptionPane.ERROR_MESSAGE);
							user.setText(null);
							pass.setText(null);
							email.setText(null);
						}
					}
				}
		);
		regWindow.getContentPane().add(buttonSignUp);
		
		//Setup Back Button image
		JButton buttonBack = new JButton();
		//Check the resource first, if it has, it displays
		try {
		    Image img = ImageIO.read(getClass().getResource("/stuffs/back (2).png"));
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
							runLogin amy = new runLogin();
							amy.main(null);
							regWindow.dispose();
						}
					}
			);
		    regWindow.getContentPane().add(buttonBack);
		} 
		catch (Exception ex) {
		    System.out.println(ex);
		}
	}
}

