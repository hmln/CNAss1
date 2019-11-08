package Login;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import ChatGUI.runChooseChatMode;
import Reg.runRegis;
import stuffs.RoundJTextField;

import java.awt.event.*;

public class Login {
	JFrame loginWindow;
	private JFrame loginConfirm;
	private JTextField user;
	private JPasswordField pass;
	
	//Create the constructor for the application
	public Login() {
		init();
	}
	
	//Initialize the contents of the frame
	private void init() {
		//Set up the window chat
		loginWindow = new JFrame("Log In To The Nonsense Chat Application");
		loginWindow.setBounds(640, 320, 720, 480);
		loginWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loginWindow.getContentPane().setLayout(null);
		loginWindow.getContentPane().setBackground(Color.WHITE);
		
		//Set up new Color
		Color newBlue = new Color(0, 79, 109);
		Color backGround = new Color(218, 232, 252);
		Color border = new Color(213, 232, 212);
		Color newGray = new Color(183, 184, 186);
		
		//Setup the title of the application
		JLabel labelSys = new JLabel("login chat application");
		labelSys.setHorizontalAlignment(SwingConstants.CENTER);
		labelSys.setForeground(newBlue);
		labelSys.setFont(new Font("Caranda Personal Use", Font.PLAIN, 40));
		labelSys.setBounds(120, 50, 484, 66);
		loginWindow.getContentPane().add(labelSys);
		
		//Create the text field tell the user to input Username
		JLabel labelUser = new JLabel("username:");
		labelUser.setFont(new Font("Gabriola", Font.BOLD, 36));
		labelUser.setForeground(newBlue);
		labelUser.setBounds(70, 148, 172, 37);
		loginWindow.getContentPane().add(labelUser);
		
		//Create the text field tell the user to input Password
		JLabel labelPass = new JLabel("password:");
		labelPass.setFont(new Font("Gabriola", Font.BOLD, 36));
		labelPass.setForeground(newBlue);
		labelPass.setBounds(70, 208, 172, 43);
		loginWindow.getContentPane().add(labelPass);
		
		//Set up for username input
		user = new RoundJTextField(30);
		user.setFont(new Font("Calibri", Font.PLAIN, 24));
		user.setForeground(newBlue);
		user.setBounds(241, 150, 360, 35);
		loginWindow.getContentPane().add(user);
		user.setColumns(10);
		
		//Set up for password input
		pass = new JPasswordField();
		pass.setFont(new Font("Calibri", Font.PLAIN, 28));
		pass.setForeground(newBlue);
		pass.setBounds(241, 210, 360, 35);
		loginWindow.getContentPane().add(pass);
		
		//Set up contents of the Login Button and EventHandler
		JButton buttonLogin = new JButton("Login");
		buttonLogin.setFont(new Font("Calibri", Font.BOLD, 18));
		buttonLogin.setBackground(Color.WHITE);
		buttonLogin.setBackground(backGround);
		buttonLogin.setBorder(new LineBorder(border, 1));
		buttonLogin.setForeground(newBlue);
		buttonLogin.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						String username = user.getText();
						String password = pass.getText();
						
						//Check login right or wrong
						if (username.equals("admin") && password.equals("12345")) {
							user.setText(null);
							pass.setText(null);
							
							//Note: Call Client class to create new Client and Open the Chat GUI
							runChooseChatMode amy = new runChooseChatMode();
							amy.main(null);
							loginWindow.dispose();
						}
						//Something went wrong, retype all the information
						else {
							JOptionPane.showMessageDialog(null, "Invalid Login!", "Login Failed", JOptionPane.ERROR_MESSAGE);
							user.setText(null);
							pass.setText(null);
						}
					}
				}
		);
		buttonLogin.setBounds(300, 270, 180, 35);
		loginWindow.getContentPane().add(buttonLogin);
		
		//Set up for Not member Label
		JLabel notMem = new JLabel("Not a member yet?");
		notMem.setFont(new Font("Gabriola", Font.PLAIN, 28));
		notMem.setForeground(newGray);
		notMem.setBounds(70, 360, 200, 43);
		loginWindow.getContentPane().add(notMem);
		
		//Setup for Registration
		JButton buttonReg = new JButton("Register now!");
		buttonReg.setFont(new Font("Gabriola", Font.BOLD, 26));
		buttonReg.setBackground(Color.WHITE);
		buttonReg.setBorder(BorderFactory.createEmptyBorder());
		buttonReg.setForeground(newBlue);
		buttonReg.setHorizontalAlignment(SwingConstants.LEFT);
		buttonReg.addActionListener(
				//Call new Registration
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						runRegis amy = new runRegis();
						amy.main(null);
						loginWindow.dispose();
					}
				}
		);
		buttonReg.setBounds(240, 363, 125, 35);
		loginWindow.getContentPane().add(buttonReg);
		
		//Separator
		JSeparator separator = new JSeparator();
		separator.setBounds(28, 242, 484, 5);
	}
	
}
