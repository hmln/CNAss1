package ChatGUI;
import java.awt.*;
import javax.swing.*;
import utils.RoundedBorder;
import java.awt.event.*;
import java.io.IOException;

import ChatGUI.Login;
import data.Account;
import data.UserDB;

public class requestForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	Account user = Login.currentUser;
	public requestForm() {
		//Set up colors
		Color newBlue = new Color(0, 79, 109);
				
		//Setup the Request Window
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(840, 320, 450, 320);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		//Setup the Label: Friend Request
		JLabel mainLabel = new JLabel("Friend Request");
		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mainLabel.setForeground(newBlue);
		mainLabel.setBounds(6, 6, 438, 24);
		mainLabel.setFont(new Font("Segoe UI",Font.BOLD,16));
//		mainLabel.setFont(new Font());
		contentPane.add(mainLabel);
		
		//Setup list Friend Request
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		java.util.Set<String> temp = user.getRequestList();
		if (temp != null)
		{
			for (String entry: temp)
			{
				dlm.addElement(entry);
			}
		}
		JList<String> list = new JList<>(dlm);
		
		list.setBounds(16, 34, 402, 194);
		
		//Setup Accept Button
		JButton accept = new JButton("Accept");
		accept.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				try {
					Account found = UserDB.getJson(list.getSelectedValue());
					user.addFriend(found.getName());
					found.addFriend(user.getName());
					user.removeRequest(list.getSelectedValue());
					dlm.removeElementAt(list.getSelectedIndex());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		accept.setBounds(16, 240, 100, 29);
		accept.setEnabled(false);
		accept.setFocusPainted(false);
		accept.setBackground(Color.WHITE);
		accept.setBorder(new RoundedBorder(8));
		contentPane.add(accept);
		
		//Setup Refresh Button
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				try {
					//Setup lai update cai friend request form
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//Setup Remove Button
		JButton removeButton = new JButton("Remove");
		removeButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				try {
					user.removeRequest(list.getSelectedValue());
					dlm.removeElementAt(list.getSelectedIndex());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		removeButton.setEnabled(false);
		removeButton.setBounds(315, 240, 100, 29);
		removeButton.setBackground(Color.WHITE);
		removeButton.setFocusPainted(false);
		removeButton.setBorder(new RoundedBorder(8));
		contentPane.add(removeButton);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				accept.setEnabled(true);
				removeButton.setEnabled(true);
			}
		});
		JScrollPane scroller4List = new JScrollPane(list);
		scroller4List.setBounds(16,34,402,194);
		contentPane.add(scroller4List);
	}
}