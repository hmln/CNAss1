package FriendRequest;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import src.RoundedBorder;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class requestForm extends JFrame {
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
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
		dlm.addElement("ave"); //Test only
		dlm.addElement("steve"); //Test only
		//Note: Can setup function display cai list friend request
		JList<String> list = new JList<>(dlm);// cai list yeu cau ket ban
		
		list.setBounds(16, 34, 402, 194);
		
		//Setup Accept Button
		JButton accept = new JButton("accept");
		accept.addMouseListener(new MouseAdapter() { // chap nhan ket ban thi them ban vao list friend
			@Override
			public void mousePressed(MouseEvent e) {
				dlm.removeElementAt(list.getSelectedIndex());	//remove the target account out of the Request list and added that account on friend list
				//Note: Can setup them cai function add target account ma user chon vo friend list
			}
			
		});
		accept.setBounds(16, 240, 100, 29);
		accept.setEnabled(false);
		accept.setFocusPainted(false);
		accept.setBackground(Color.WHITE);
		accept.setBorder(new RoundedBorder(8));
		contentPane.add(accept);
		
		//Setup Remove Button
		JButton removeButton = new JButton("remove");
		removeButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { //remove the target account out of the Request list
				//Note: Can setup them cai function add target account ma user chon vo friend list
				dlm.removeElementAt(list.getSelectedIndex()); //remove the target account out of the Request list
			}
		});
		removeButton.setEnabled(false);
		removeButton.setBounds(315, 240, 100, 29);
		removeButton.setBackground(Color.WHITE);
		removeButton.setFocusPainted(false);
		removeButton.setBorder(new RoundedBorder(8));
		contentPane.add(removeButton);
		
		//Setup Decline Button
		JButton decline = new JButton("decline");
		decline.addMouseListener(new MouseAdapter() {// khong dong y thi nhan nut nay de huy loi moi ket ban
			@Override
			public void mousePressed(MouseEvent e) {
				dlm.removeElementAt(list.getSelectedIndex());//remove the target account out of the Request list and not added that account on friend list
			}
		});
		decline.setBounds(165, 240, 100, 29);
		decline.setEnabled(false);
		decline.setBackground(Color.WHITE);
		decline.setFocusPainted(false);
		decline.setBorder(new RoundedBorder(8));
		contentPane.add(decline);
		
		//Setup list if the user click on accept, decline or remove button 
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				accept.setEnabled(true);
				decline.setEnabled(true);
				removeButton.setEnabled(true);

				String value = (String)list.getModel().getElementAt(list.locationToIndex(e.getPoint()));
				
				//textNameFriend.setText(value);
			}
		});
		JScrollPane scroller4List = new JScrollPane(list);
		scroller4List.setBounds(16,34,402,194);
		contentPane.add(scroller4List);
	}
}