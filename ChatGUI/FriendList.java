package ChatGUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import data.Account;
import data.UserDB;
import server.ServerChat;
import utils.RoundedBorder;

public class FriendList {
	JFrame frame;
	private JLabel friendLabel, userLabel;
	private static JList<String> list;
	private JButton addButton, removeButton,requestButton,returnButton;
	private JTextField friendInput;
	Account user = Login.currentUser;
	DefaultListModel<String> friendList = null;
	
	public FriendList() throws Exception {
		initialize();
	}
	
	private void initialize() throws IOException {
		//Set up colors
		
		Color newBlue = new Color(0, 79, 109);
		Color wineRed = new Color(139,9,35);
		Color blueAqua = new Color(0, 143, 215);
		
		//Set up the Chat Window
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setBounds(680,240,380,370);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Set up Label Friend List
		friendLabel = new JLabel("Friend");
		friendLabel.setHorizontalAlignment(SwingConstants.CENTER);
		friendLabel.setBounds(90, 60, 156, 16);
		friendLabel.setForeground(newBlue);
		friendLabel.setFont(new Font("Segoe UI", Font.BOLD,15));
		frame.getContentPane().add(friendLabel);
		
		//Set up JList for Friend List
		friendList = new DefaultListModel<String>();
		list = new JList<>(friendList);
		list.setBounds(16, 85, 200, 200);
		list.setBorder(new LineBorder(newBlue, 1));
		list.setFont(new Font("Segoe UI",Font.PLAIN,15));
		for (String entry : Login.currentUser.getFriendList())
		friendList.addElement(entry);

		//Set up scroll pane for Friend List
		JScrollPane scr = new JScrollPane(list);
		scr.setBorder(BorderFactory.createEmptyBorder());
		scr.setBounds(16,85,200,200);
		frame.getContentPane().add(scr);

		//Setup Button Add
		addButton = new JButton("Add");
		addButton.setBorder(new RoundedBorder(8));
		addButton.setBounds(170, 15, 70, 25);
		addButton.setBackground(Color.WHITE);
		addButton.setForeground(newBlue);
		frame.getContentPane().add(addButton);
				
		//Setup Button Remove
		removeButton = new JButton("Remove");
		removeButton.setBounds(250, 15, 100, 25);
		removeButton.setBorder(new RoundedBorder(8));
		removeButton.setBackground(Color.WHITE);
		removeButton.setForeground(wineRed);
		frame.getContentPane().add(removeButton);
				
		//Setup Friendname Input
		friendInput = new JTextField();
		friendInput.setBounds(10, 10, 150, 35);
		friendInput.setBorder(new RoundedBorder(5));
		friendInput.setColumns(10);
		frame.getContentPane().add(friendInput);
		
		//Setup Button Friend Request
		requestButton = new JButton();
		requestButton.setBounds(310, 50, 30, 30);
		Image imgRequest = ImageIO.read(getClass().getResource("/utils/add-friend.png"));
		requestButton.setIcon(new ImageIcon(imgRequest));
		requestButton.setBackground(Color.WHITE);
		requestButton.setFocusPainted(false);
		requestButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(requestButton);
		
		//Set up Label Chat
		userLabel = new JLabel("User: " + user.getName());
		userLabel.setFont(new Font("Segoe UI", Font.PLAIN,15));
		userLabel.setBounds(16, 300, 250, 25);
		userLabel.setForeground(blueAqua);
		frame.getContentPane().add(userLabel);
				
		//Setup Button Return
		returnButton = new JButton();
		returnButton.setBounds(280, 300, 40, 40);
		Image returnImage = ImageIO.read(getClass().getResource("/utils/log-out.png"));
		returnButton.setIcon(new ImageIcon(returnImage));
		returnButton.setBackground(Color.WHITE);
		returnButton.setFocusPainted(false);
		returnButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(returnButton);
		
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg) {
				String value = (String)list.getModel().getElementAt(list.locationToIndex(arg.getPoint()));
				try {
					if (ServerChat.getUserlist().containsKey(value)) new FriendChat(value,0);
					else new FriendChat(value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String destination = friendInput.getText();
				try {
					//dont add yourself
					if (destination.equals(user.getName())) JOptionPane.showMessageDialog(null, "Why add yourself duh", "Lmao", JOptionPane.ERROR_MESSAGE);
					else
					{
						Account result = UserDB.getJson(destination);
						//no account matched
						if (result == null) JOptionPane.showMessageDialog(null, "Invalid account!", "Failed", JOptionPane.ERROR_MESSAGE);
						//account is already friend
						else if (user.isFriend(result.getName())) JOptionPane.showMessageDialog(null, "Account is already your friend!", "Failed", JOptionPane.ERROR_MESSAGE);
						//account is in request
						else if (result.inRequest(user.getName())) JOptionPane.showMessageDialog(null, "Already requested!", "Failed", JOptionPane.ERROR_MESSAGE);
						else if (user.inRequest(result.getName())) JOptionPane.showMessageDialog(null, "User is in request list!", "Lol", JOptionPane.INFORMATION_MESSAGE);
						//else
						else
						{
							result.addRequest(user.getName());
							JOptionPane.showMessageDialog(null, "Request success!", "OK", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				friendInput.setText(null);			
			}
		});
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String destination = friendInput.getText();
				try {
					//dont remove yourself
					if (destination.equals(user.getName())) JOptionPane.showMessageDialog(null, "Why remove yourself duh", "Lmao", JOptionPane.ERROR_MESSAGE);
					else 
					{
						Account result = UserDB.getJson(destination); 
						//account not exist
						if (result == null) JOptionPane.showMessageDialog(null, "Invalid account!", "Failed", JOptionPane.ERROR_MESSAGE);
						//not your friend
						else if (!user.isFriend(result.getName())) JOptionPane.showMessageDialog(null, "Account is not your friend!", "Failed", JOptionPane.ERROR_MESSAGE);
						//else
						else
						{
							result.removeFriend(user.getName());
							user.removeFriend(result.getName());
							friendUpdate();
							JOptionPane.showMessageDialog(null, "Remove success!", "OK", JOptionPane.INFORMATION_MESSAGE);
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				friendInput.setText(null);
			}
		});
		requestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestForm form = new requestForm();
				form.setVisible(true);
				friendUpdate();
			}
		});
		frame.setVisible(true);
		returnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new ChooseChatMode();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
			}
		});
	}
	void friendUpdate()
	{
		friendList.clear();
		for (String entry : user.getFriendList())
			friendList.addElement(entry);
	}
}
