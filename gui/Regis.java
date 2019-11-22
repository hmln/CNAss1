package gui;
import utils.Converter;
import utils.RoundJTextField;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import p2pserver.DataSocket;

import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Random;

public class Regis {
	JFrame regWindow;
	//private JFrame regCloseConfirm;
	private JTextField user, dataserver;
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
		regWel.setFont(new Font("Caranda Personal Use", Font.PLAIN, 36));
		regWel.setBounds(280, 20, 484, 80);
		
		//regWel.setBorder(new LineBorder(newBlue, 1));
		regWindow.getContentPane().add(regWel);

		//Setup instruction Label
		JLabel instruct = new JLabel("Please fill all your information!");
		instruct.setHorizontalAlignment(SwingConstants.CENTER);
		//instruct.setForeground(newBlue);
		instruct.setFont(new Font("Gabriola", Font.PLAIN, 32));
		instruct.setBounds(280, 100, 484, 55);
				
		//instruct.setBorder(new LineBorder(newBlue, 1));
		regWindow.getContentPane().add(instruct);		
		
		//Set up for IP Label
		JLabel regserver = new JLabel("ServerIP:");
		regserver.setFont(new Font("Gabriola", Font.BOLD, 32));
		regserver.setForeground(newBlue);
		regserver.setBounds(232, 160, 172, 37);
		regWindow.getContentPane().add(regserver);
				
		//Setup for IP input
		dataserver = new RoundJTextField(20);
		dataserver.setFont(new Font("Calibri", Font.PLAIN, 24));
		dataserver.setForeground(newBlue);
		dataserver.setBounds(390,160, 360, 30);
		regWindow.getContentPane().add(dataserver);
		dataserver.setColumns(10);
			
		//Set up for Username Label
		JLabel regUser = new JLabel("Username:");
		regUser.setFont(new Font("Gabriola", Font.BOLD, 32));
		regUser.setForeground(newBlue);
		regUser.setBounds(232, 205, 172, 37);
		regWindow.getContentPane().add(regUser);
		
		//Setup for username's input
		user = new RoundJTextField(20);
		user.setFont(new Font("Calibri", Font.PLAIN, 24));
		user.setForeground(newBlue);
		user.setBounds(390,205, 360, 30);
		regWindow.getContentPane().add(user);
		user.setColumns(10);
		
		//Set up for Password Label
		JLabel regPass = new JLabel("Password:");
		regPass.setFont(new Font("Gabriola", Font.BOLD, 32));
		regPass.setForeground(newBlue);
		regPass.setBounds(240, 250, 172, 43);
		regWindow.getContentPane().add(regPass);
		
		//Setup for password's input
		pass = new JPasswordField();
		pass.setFont(new Font("Calibri", Font.PLAIN, 28));
		pass.setForeground(newBlue);
		pass.setBounds(390, 250, 360, 30);
		regWindow.getContentPane().add(pass);
		
		//Set up for Password Label
		JLabel regPass2 = new JLabel("Confirm password:");
		regPass2.setFont(new Font("Gabriola", Font.BOLD, 32));
		regPass2.setForeground(newBlue);
		regPass2.setBounds(135, 295, 292, 43);
		regWindow.getContentPane().add(regPass2);
				
		//Setup for password's input
		pass2 = new JPasswordField();
		pass2.setFont(new Font("Calibri", Font.PLAIN, 28));
		pass2.setForeground(newBlue);
		pass2.setBounds(390, 295, 360, 30);
		regWindow.getContentPane().add(pass2);
		
		//Set up Sign up button
		JButton buttonSignUp = new JButton();
		try {
		    Image imgSignUp = ImageIO.read(getClass().getResource("/utils/correct.png"));
		    buttonSignUp.setIcon(new ImageIcon(imgSignUp));
		    buttonSignUp.setBounds(490, 350, 70, 70);
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
					String password = Converter.toString(pass.getPassword());
					String repassword = Converter.toString(pass2.getPassword());
					String serverIP = dataserver.getText();
					try {
						if (Login.connected == false)
						{
							InetAddress dataServerIp = InetAddress.getByName(serverIP);
							int dataServerport = 6789;
							Login.ownaddress = InetAddress.getLocalHost().getHostAddress();
							Login.ownport = new Random().nextInt(10000)+1024;
							Login.tcp = new DataSocket(dataServerIp,dataServerport);
							Login.connected = true;
						}
						String result = Login.tcp.signUp(username,password,repassword);
						if (result.equals("success"))
							JOptionPane.showMessageDialog(null, "Register success!", "Success", JOptionPane.INFORMATION_MESSAGE);
						if (result.equals("out"))
							JOptionPane.showMessageDialog(null, "No response from server", "error", JOptionPane.ERROR_MESSAGE);
						reset();
					} catch (HeadlessException e) {
						e.printStackTrace();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Cannot connect to server", "error", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					} 
					
				}
			}
		);
		regWindow.getContentPane().add(buttonSignUp);
		
		JButton buttonBack = new JButton();
		//Check the resource first, if it has, it displays
		try {
		    Image img = ImageIO.read(getClass().getResource("/utils/back.png"));
		    buttonBack.setIcon(new ImageIcon(img));
		    buttonBack.setBounds(40, 40, 40, 40);
		    buttonBack.setBackground(Color.WHITE);
		    buttonBack.setFocusPainted(false); //disable the border
		    buttonBack.setBorder(BorderFactory.createEmptyBorder());
		    buttonBack.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							Login login;
							try {
								login = new Login();
								login.loginWindow.setVisible(true);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
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
		dataserver.setText(null);
		user.setText(null);
		pass.setText(null);
		pass2.setText(null);
	}
}

