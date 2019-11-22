package gui;
import java.awt.*;
import javax.swing.*;

import gui.Login;
import utils.RoundedBorder;
import java.awt.event.*;
import java.io.IOException;

public class Request extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	String user = Login.currentuser;
	DefaultListModel<String> requestList;
	public Request(){
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
		requestList = new DefaultListModel<String>();
		try {
			requestUpdate();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		JList<String> list = new JList<>(requestList);
		
		list.setBounds(16, 34, 402, 194);
		
		//Setup Accept Button
		JButton accept = new JButton("accept");
		accept.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				try {
					Login.tcp.addFriend(user, list.getSelectedValue());
					requestList.removeElementAt(list.getSelectedIndex());
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
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				try {
					requestUpdate();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		refreshButton.setBounds(170, 240, 100, 29);
		refreshButton.setEnabled(false);
		refreshButton.setFocusPainted(false);
		refreshButton.setBackground(Color.WHITE);
		refreshButton.setBorder(new RoundedBorder(8));
		contentPane.add(refreshButton);
		
		
		//Setup Remove Button
		JButton removeButton = new JButton("remove");
		removeButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) { 
				try {
					Login.tcp.removeRequest(user, list.getSelectedValue());
					requestList.removeElementAt(list.getSelectedIndex());
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
	
	void requestUpdate() throws IOException
	{
		requestList.clear();
		String result = Login.tcp.updateRequest(user);
		//no request
		if (result.equals("success")) return;
		else
		{
			String[] friends = result.substring(7,result.length()).split(",");
			for (String x: friends)
				requestList.addElement(x);
		}
	}
}