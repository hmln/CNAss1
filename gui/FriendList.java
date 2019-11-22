package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import p2pserver.P2PServer;
import utils.RoundedBorder;

public class FriendList {
	JFrame frame;
	private JLabel friendLabel, userLabel;
	private static JList<String> list;
	private JButton addButton, removeButton,requestButton,returnButton,refreshButton;
	private JTextField friendInput;
	String user = Login.currentuser;
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
		friendLabel.setBounds(100, 60, 156, 16);
		friendLabel.setForeground(newBlue);
		friendLabel.setFont(new Font("Segoe UI", Font.BOLD,18));
		frame.getContentPane().add(friendLabel);
		
		//Set up JList for Friend List
		friendList = new DefaultListModel<String>();
		list = new JList<>(friendList);
		list.setBounds(16, 92, 340, 200);
		list.setBorder(new LineBorder(newBlue, 1));
		list.setFont(new Font("Segoe UI",Font.PLAIN,15));
		

		//Set up scroll pane for Friend List
		JScrollPane scr = new JScrollPane(list);
		scr.setBorder(BorderFactory.createEmptyBorder());
		scr.setBounds(16,92,340,200);
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
		
		//Set up Button Refresh 
		refreshButton = new JButton();
		refreshButton.setBounds(16, 55, 25, 25);
		Image imgRefresh = ImageIO.read(getClass().getResource("/utils/refresh.png"));
		refreshButton.setIcon(new ImageIcon(imgRefresh));
		refreshButton.setBackground(Color.WHITE);
		refreshButton.setFocusPainted(false);
		refreshButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(refreshButton);
		
		//Setup Button Friend Request
		requestButton = new JButton();
		requestButton.setBounds(310, 50, 35, 35);
		Image imgRequest = ImageIO.read(getClass().getResource("/utils/add-friend.png"));
		requestButton.setIcon(new ImageIcon(imgRequest));
		requestButton.setBackground(Color.WHITE);
		requestButton.setFocusPainted(false);
		requestButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(requestButton);
		
		//Set up Label Chat
		userLabel = new JLabel("User: " + user);
		userLabel.setFont(new Font("Segoe UI", Font.PLAIN,18));
		userLabel.setBounds(16, 300, 250, 25);
		userLabel.setForeground(blueAqua);
		frame.getContentPane().add(userLabel);
				
		//Setup Button Return
		returnButton = new JButton();
		returnButton.setBounds(320, 295, 40, 40);
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
					String[] splitSide = value.split(" - ",2);
					String status = Login.tcp.receiveFriendStatus(splitSide[0]).substring(7);
					System.out.println(status);
					if (status.equals("false"))
						JOptionPane.showMessageDialog(null, "User " + splitSide[0] + " is offline!", "Sorry", JOptionPane.INFORMATION_MESSAGE);
					else 
					{
						if (P2PServer.getUserlist().containsKey(splitSide[0] + ":" + user)) new FriendChat(splitSide[0],Login.ownaddress,Login.ownport);
						else new FriendChat(splitSide[0]);
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String destination = friendInput.getText();
					String result = Login.tcp.addRequest(user, destination);
					if (result.equals("success"))
						JOptionPane.showMessageDialog(null, "Request success!", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				friendInput.setText(null);			
			}
		});
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String destination = friendInput.getText();
					String result = Login.tcp.addRequest(user, destination);
					if (result.equals("success"))
					{
						JOptionPane.showMessageDialog(null, "Remove success!", "Success", JOptionPane.INFORMATION_MESSAGE);
						friendUpdate();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				friendInput.setText(null);
			}
		});
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					friendUpdate();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		requestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Request form = new Request();
				form.setVisible(true);
				form.addWindowListener(new java.awt.event.WindowAdapter() {
				    @Override
				    public void windowClosing(WindowEvent windowEvent) {
				        try {
							friendUpdate();
						} catch (IOException e) {
							e.printStackTrace();
						}
				    }
				});
			}
		});
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
		friendUpdate();
		frame.setVisible(true);
	}
	void friendUpdate() throws IOException
	{
		friendList.clear();
		String result = Login.tcp.updateFriend(user);
		//no friend
		if (result.equals("success")) return;
		else
		{
			String[] friends = result.substring(7,result.length()).split(",");
			for (String x: friends)
			{
				String active;
				if (x.charAt(0) == '1') active = " - Online";
				else active = " - Offline";
				friendList.addElement(x.substring(1) + active);
			}
		}
	}
}

