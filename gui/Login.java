package gui;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import p2pserver.P2PServer;
import p2pserver.DataSocket;
import utils.*;

import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.*;

public class Login {
	public JFrame loginWindow;
	private JTextField user,dataserver;
	private JPasswordField pass;
	public static String currentuser = "";
	public static DataSocket tcp = null;
	static P2PServer ownServer = null;
	public static int ownport = -1;
	public static String ownaddress = "";
	public static boolean connected;
	public Login() throws IOException {	
		connected = false;
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
		
		//Setup Font
		GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/utils/CarandaPersonalUse-qLOq.ttf").openStream());
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}   
		genv.registerFont(font);
		font = font.deriveFont(12f);
		
		Font customeFont = null;
		try {
			customeFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/utils/Gabriola.ttf").openStream());
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}   
		genv.registerFont(customeFont);
		// make sure to derive the size
		customeFont = customeFont.deriveFont(12f);
		
		//Setup the title of the application
		JLabel labelSys = new JLabel("Login chat application");
		labelSys.setHorizontalAlignment(SwingConstants.CENTER);
		labelSys.setForeground(newBlue);
		labelSys.setFont(new Font("Caranda Personal Use", Font.PLAIN, 32));
		labelSys.setBounds(135, 50, 484, 50);
		loginWindow.getContentPane().add(labelSys);
		
		//Create the text field tell the user to input ServerIP
		JLabel labelserver = new JLabel("ServerIP:");
		labelserver.setFont(new Font("Gabriola", Font.BOLD, 32));
		labelserver.setForeground(newBlue);
		labelserver.setBounds(70, 115, 172, 50);
		loginWindow.getContentPane().add(labelserver);
		
		//Create the text field tell the user to input Username
		JLabel labelUser = new JLabel("Username:");
		labelUser.setFont(new Font("Gabriola", Font.BOLD, 32));
		labelUser.setForeground(newBlue);
		labelUser.setBounds(70, 175, 172, 50);
		loginWindow.getContentPane().add(labelUser);
		
		//Create the text field tell the user to input Password
		JLabel labelPass = new JLabel("Password:");
		labelPass.setFont(new Font("Gabriola", Font.BOLD, 32));
		labelPass.setForeground(newBlue);
		labelPass.setBounds(70, 235, 172, 50);
		loginWindow.getContentPane().add(labelPass);
		
		//Set up for server input
		dataserver = new RoundJTextField(30);
		dataserver.setFont(new Font("Calibri", Font.PLAIN, 24));			
		dataserver.setForeground(newBlue);
		dataserver.setBounds(241, 110, 360, 35);
		loginWindow.getContentPane().add(dataserver);
		dataserver.setColumns(10);
		
		
		//Set up for username input
		user = new RoundJTextField(30);
		user.setFont(new Font("Calibri", Font.PLAIN, 24));
		user.setForeground(newBlue);
		user.setBounds(241, 170, 360, 35);
		loginWindow.getContentPane().add(user);
		user.setColumns(10);
		
		//Set up for password input
		pass = new JPasswordField();
		pass.setFont(new Font("Calibri", Font.PLAIN, 28));
		pass.setForeground(newBlue);
		pass.setBounds(241, 230, 360, 35);
		loginWindow.getContentPane().add(pass);
		
		//Set up contents of the Login Button and EventHandler
		JButton buttonLogin = new JButton("Login");	
		buttonLogin.setFont(new Font("Calibri", Font.BOLD, 18));
		buttonLogin.setBackground(Color.WHITE);
		buttonLogin.setBackground(backGround);
		buttonLogin.setBorder(new LineBorder(border, 1));
		buttonLogin.setForeground(newBlue);
		buttonLogin.setBounds(300, 270, 180, 35);
		buttonLogin.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					String serverIP = dataserver.getText();
					String username = user.getText();
					String password = Converter.toString(pass.getPassword());
					try {
						if (connected == false)
						{
							InetAddress dataServerIp = InetAddress.getByName(serverIP);
							int dataServerport = 6789;
							ownaddress = InetAddress.getLocalHost().getHostAddress();
							ownport = new Random().nextInt(10000)+1024;
							tcp = new DataSocket(dataServerIp,dataServerport);
							connected = true;
						}
						String result = tcp.signIn(username, password);
						if (result.equals("success"))
						{
							currentuser = username;
							initThread();
							loginWindow.dispose();
						}
						if (result.equals("out"))
							JOptionPane.showMessageDialog(null, "Server timeout", "error", JOptionPane.ERROR_MESSAGE);
						reset();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Cannot connect to server", "error", JOptionPane.ERROR_MESSAGE);
						dataserver.setText(null);
					}
				}
			}
		);
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
						loginWindow.dispose();
						new Regis();	
					}
				}
		);
		buttonReg.setBounds(240, 363, 125, 35);
		loginWindow.getContentPane().add(buttonReg);
	}
	public void initThread()
	{
		Thread server = new Thread(){
			public void run()
			{
				try {
					ownServer = new P2PServer(ownport, currentuser);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		Thread client = new Thread() {
			public void run()
			{
				try {
					new ChooseChatMode();
				} catch (IOException e) {
					e.printStackTrace();
				}						
			}
		};
		server.start();
		client.start();
	}
	public void reset()
	{
		dataserver.setText(null);
		user.setText(null);
		pass.setText(null);
	}
}
