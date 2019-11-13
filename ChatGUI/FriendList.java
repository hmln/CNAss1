package ChatGUI;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;

import server.ServerChat;

public class FriendList {
	JFrame frame;
	private JLabel friendLabel;
	private static JList<String> list;
	DefaultListModel<String> friendList = null;
	
	public FriendList() throws Exception {
		initialize();
	}
	
	private void initialize() throws IOException {
		//Set up colors
		
		Color newBlue = new Color(0, 79, 109);
		
		//Set up the Chat Window
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setBounds(680,240,200,560);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Set up Label Friend List
		friendLabel = new JLabel("Friend");
		friendLabel.setHorizontalAlignment(SwingConstants.CENTER);
		friendLabel.setBounds(12, 6, 156, 16);
		friendLabel.setForeground(newBlue);
		friendLabel.setFont(new Font("Segoe UI", Font.BOLD,15));
		frame.getContentPane().add(friendLabel);
		
		//Set up JList for Friend List
		friendList = new DefaultListModel<String>();
		list = new JList<>(friendList);
		list.setBounds(16, 30, 156, 484);
		list.setBorder(new LineBorder(newBlue, 1));
		list.setFont(new Font("Segoe UI",Font.PLAIN,15));
		for (String entry : Login.currentUser.getFriendList())
		friendList.addElement(entry);

		//Set up scroll pane for Friend List
		JScrollPane scr = new JScrollPane(list);
		scr.setBorder(BorderFactory.createEmptyBorder());
		scr.setBounds(16,30,156,484);
		frame.getContentPane().add(scr);
		
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg) {
				String value = (String)list.getModel().getElementAt(list.locationToIndex(arg.getPoint()));
				FriendChat newchat;
				try {
					if (ServerChat.getUserlist().contains(value)) newchat = new FriendChat(value,0);
					else newchat = new FriendChat(value);
					newchat.frame.setVisible(true);
					frame.dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
