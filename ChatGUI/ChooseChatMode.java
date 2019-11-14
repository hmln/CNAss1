package ChatGUI;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.*;
import java.io.IOException;

public class ChooseChatMode {
	JFrame mainWindow;
	private JButton friendMode;
	private JButton groupMode;
	private JButton logOut;
	
	public ChooseChatMode() throws IOException{
		init();
	}
	
	private void init() throws IOException {
		//Set up colors
		Color newBlue = new Color(0, 79, 109);
		Color newGray = new Color(84, 88, 94);
		Color wineRed = new Color(139,9,35);
		
		//Set up window Registration
		mainWindow = new JFrame("Choose Chat Mode!");
		mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainWindow.getContentPane().setLayout(null);
		mainWindow.getContentPane().setBackground(Color.WHITE);
		mainWindow.setBounds(480, 320, 960, 480);
		mainWindow.getContentPane().setLayout(null);
		
		//Setup Label Choose Mode Chat
		JLabel chatMode = new JLabel("Please Choose Chat Mode!");
		chatMode.setHorizontalAlignment(SwingConstants.CENTER);
		chatMode.setForeground(newBlue);
		chatMode.setFont(new Font("Caranda Personal Use", Font.PLAIN, 48));
		chatMode.setBounds(200, 40, 600, 100);
		
		//chatMode.setBorder(new LineBorder(newBlue, 1));
		mainWindow.getContentPane().add(chatMode);

		//Setup Label Chat Group
		JLabel chatGroup = new JLabel("Chat With Your Group!");
		chatGroup.setHorizontalAlignment(SwingConstants.LEFT);
		chatGroup.setForeground(newBlue);
		chatGroup.setFont(new Font("Gabriola", Font.BOLD, 32));
		chatGroup.setBounds(145, 300, 280, 40);
				
		//chatGroup.setBorder(new LineBorder(newBlue, 1));
		mainWindow.getContentPane().add(chatGroup);	
		
		//Setup Label Chat Friend 1 by 1
		JLabel chatFriend = new JLabel("Chat With Your Friend Privately!");
		chatGroup.setHorizontalAlignment(SwingConstants.LEFT);
		chatFriend.setFont(new Font("Gabriola", Font.BOLD, 32));
		chatFriend.setForeground(newBlue);
		chatFriend.setBounds(480, 300, 400, 37);
		//chatFriend.setBorder(new LineBorder(newBlue, 1));
		mainWindow.getContentPane().add(chatFriend);
		
		//Setup Button Chat Group
		groupMode = new JButton();
		groupMode.setBounds(220, 150, 130, 130);
		
		//Setup image for Chat Group button
		Image imgGroupMode = ImageIO.read(getClass().getResource("/utils/team.png"));
		groupMode.setIcon(new ImageIcon(imgGroupMode));
		groupMode.setBackground(Color.WHITE);
		groupMode.setFocusPainted(false);
		groupMode.setBorder(BorderFactory.createEmptyBorder());
		mainWindow.getContentPane().add(groupMode);	
		//Setup Event Chat Group
		groupMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				/*try {
					GroupChatGui chat = new GroupChatGui();
					chat.frameMain.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				mainWindow.dispose();*/
				JOptionPane.showMessageDialog(null, "Comming soon!", "Plz wait", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		
		//Setup Button Chat Friend
		friendMode = new JButton();
		friendMode.setBounds(600, 150, 130, 130);
		
		//Setup image for Chat 1 -1 button
		Image imgFriendMode = ImageIO.read(getClass().getResource("/utils/boss.png"));
		friendMode.setIcon(new ImageIcon(imgFriendMode));
		friendMode.setBackground(Color.WHITE);
		friendMode.setFocusPainted(false);
		friendMode.setBorder(BorderFactory.createEmptyBorder());
		mainWindow.getContentPane().add(friendMode);
		//Setup Event Chat 1-1
		friendMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					new FriendList();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				mainWindow.dispose();
			}
		});
		
		
		//Setup Label Not Your Account
		JLabel notAccount = new JLabel("Not Your Account?");
		notAccount.setFont(new Font("Gabriola", Font.PLAIN, 30));
		notAccount.setForeground(newGray);
		notAccount.setBounds(355, 360, 200, 43);
		mainWindow.getContentPane().add(notAccount);
		
		//Setup for Logout
		logOut = new JButton("Sign Out");
		logOut.setFont(new Font("Gabriola", Font.BOLD, 26));
		logOut.setBackground(Color.WHITE);
		logOut.setBorder(BorderFactory.createEmptyBorder());
		logOut.setForeground(wineRed);
		logOut.setHorizontalAlignment(SwingConstants.LEFT);
		logOut.addActionListener(
				//Call new Login
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						Login login = new Login();
						login.loginWindow.setVisible(true);
						mainWindow.dispose();
					}
				}
		);
		logOut.setBounds(535, 363, 80, 35);
		mainWindow.getContentPane().add(logOut);
		mainWindow.setVisible(true);
	}
}
