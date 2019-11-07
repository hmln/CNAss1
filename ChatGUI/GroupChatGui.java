package ChatGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import FriendRequest.runRequestForm;
import src.RoundedBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.font.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.awt.color.*;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JToolBar;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GroupChatGui extends JFrame {

	private static String IPClient = "", userName = "" , userData = "";
	private static int portClient = 0;
	JFrame frameMain;
	private JLabel labelActiveNow;
	private static JList<String> listActive;
	private JPanel contentPane;
	private JTextField textField_1;
	private JButton sendMessageButton;
	private JTextArea textArea;
	private JTextArea textArea_userInput;
	private JScrollBar scrollBar;
	private JButton sendFileButton;
	private JButton sendPictureButton;
	private JScrollPane scroll_bar;
	private String groupName; //this variable uses for testing only
	
//	private final Action action = new SwingAction();
	private String content;

	/**
	 * Create the frame.
	 */
	public GroupChatGui(String arg, int arg1, String name, String msg) throws Exception {
		IPClient = arg;
		portClient = arg1;
		userName = name;
		userData = msg;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupChatGui window = new GroupChatGui();
					window.frameMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
				);
	}
	
	public GroupChatGui() throws Exception {
		initialize();
	}
	
	private void initialize() throws IOException {
		//Set up colors
		Color newBlue = new Color(0, 79, 109);
		Color wineRed = new Color(139,9,35);
		Color blueAqua = new Color(0, 143, 215);
						
		//Setup Window Group Chat
		frameMain = new JFrame();
		frameMain.setTitle("CHAT GROUP");
		frameMain.setResizable(false);
		frameMain.getContentPane().setBackground(Color.WHITE);
		frameMain.setBounds(540,240,900,560);
		frameMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameMain.getContentPane().setLayout(null);
		
		
//------Left-side: Online Group-------------------------------		
		//Setup Label Online Group
		labelActiveNow = new JLabel("Online Group");
		labelActiveNow.setHorizontalAlignment(SwingConstants.CENTER);
		labelActiveNow.setBounds(12, 4, 156, 25);
		labelActiveNow.setForeground(newBlue);
		labelActiveNow.setFont(new Font("Segoe UI", Font.BOLD,15));
		frameMain.getContentPane().add(labelActiveNow);
		
		//Setup List Online Group
		listActive = new JList<>();
		listActive.setBounds(16, 30, 156, 484);
		listActive.setBorder(new LineBorder(newBlue, 1));
		listActive.setFont(new Font("Segoe UI",Font.PLAIN,15));
//		listActive.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg) {
//				String value = (String)listActive.getModel().getElementAt(listActive.locationToIndex(arg.getPoint()));
//			textNameFriend.setText(value);
//			}
//		});
		
		//Setup scroll for Online Group List
		JScrollPane scr = new JScrollPane(listActive);
		scr.setBorder(BorderFactory.createEmptyBorder());
		scr.setBounds(16,30,156,484);
		frameMain.getContentPane().add(scr);
		
		
//------Top of the Window: Place UserName instruction, TextField to input the username, 
//		button connect, disconnect, log out of the system			
		
		//Setup for Label Group Name instruction
		JLabel lblUserName = new JLabel("Name:");
		lblUserName.setFont(new Font("Segoe UI", Font.BOLD,14));
		lblUserName.setBounds(220, 12, 90, 16);
		lblUserName.setForeground(blueAqua);
		frameMain.getContentPane().add(lblUserName);
		
		//Setup for inputting the Group Name
		textField_1 = new JTextField();
		textField_1.setBounds(273, 8, 178, 26);
		textField_1.setBorder(new RoundedBorder(5));
		frameMain.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		//Setup Connect Button
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.setBorder(new RoundedBorder(8));
		btnNewButton.setBounds(455, 8, 70, 25);
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setForeground(newBlue);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		frameMain.getContentPane().add(btnNewButton);
		
		//Setup Button Choose Mode Chat
		JButton buttonChooseMode = new JButton();
		Image imgChooseMode = ImageIO.read(getClass().getResource("/src/log-out.png"));
		buttonChooseMode.setBounds(635, 1, 40, 40);
		buttonChooseMode.setIcon(new ImageIcon(imgChooseMode));
		frameMain.getContentPane().add(buttonChooseMode);
		buttonChooseMode.setBackground(Color.WHITE);
		buttonChooseMode.setFocusPainted(false);
		buttonChooseMode.setBorder(BorderFactory.createEmptyBorder());
		buttonChooseMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runChooseChatMode amy = new runChooseChatMode();
				amy.main(null);
				frameMain.dispose();
			}
		});
		
		//Setup Button Disconnect
		JButton btnNewButton_1 = new JButton("Disconnect");
		btnNewButton_1.setBounds(530, 8, 100, 25);
		btnNewButton_1.setBorder(new RoundedBorder(8));
		btnNewButton_1.setBackground(Color.WHITE);
		btnNewButton_1.setForeground(wineRed);
		frameMain.getContentPane().add(btnNewButton_1);
		
		//Setup Button Friend Request
		JButton buttonFriendRequest = new JButton();
		Image imgFriendRequest = ImageIO.read(getClass().getResource("/src/add-friend.png"));
		buttonFriendRequest.setBounds(635, 50, 40, 40);
		buttonFriendRequest.setIcon(new ImageIcon(imgFriendRequest));
		frameMain.getContentPane().add(buttonFriendRequest);
		buttonFriendRequest.setBackground(Color.WHITE);
		buttonFriendRequest.setFocusPainted(false);
		buttonFriendRequest.setBorder(BorderFactory.createEmptyBorder());
		buttonFriendRequest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runRequestForm amy = new runRequestForm();
				amy.main(null);
			}
		});
		
//------Middle: Place ChatLog and display it---------------

		//Set up Label Chat With + UserName
		JLabel wel = new JLabel("Chat With " + groupName);
		wel.setFont(new Font("Caranda Personal Use", Font.PLAIN,25));
		wel.setBounds(200, 55, 300, 50);
		wel.setForeground(blueAqua);
		frameMain.getContentPane().add(wel);
		
		//Setup ChatLog
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(192, 110, 500, 303);
		textArea.setLineWrap(true);
		textArea.setBorder(new RoundedBorder(8));
//		frameMain.getContentPane().add(textArea);
		
		//Setup scroll for ChatLog
		scroll_bar= new JScrollPane(textArea);
		scroll_bar.setBounds(192,110,500,303);
		scroll_bar.setBorder(BorderFactory.createEmptyBorder());
		frameMain.getContentPane().add(scroll_bar);
		
		//frameMain.getContentPane().add(scrollBar);
		
//------Bottom: TextField to Type and Button for transferring file-----------
		/*
		sendFileButton = new JButton("send file");
		sendFileButton.setBounds(184, 50, 82, 59);
		sendFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		*/
		
		//Setup for User's input messages
		textArea_userInput = new JTextArea();
		textArea_userInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					content ="You: "  + textArea_userInput.getText();
					textArea.append(content);
					textArea_userInput.setText("");
			}
			}
		});
		textArea_userInput.setBounds(192, 472, 465, 30);
		textArea_userInput.setLineWrap(true);
		textArea_userInput.setBorder(new RoundedBorder(8));
//		frameMain.getContentPane().add(textArea_1);
		
		//Setup for scrolling in the User's input TextField
		JScrollPane sp = new JScrollPane(textArea_userInput);
		sp.setBounds(192,472,465,40);
		sp.setBorder(BorderFactory.createEmptyBorder());
		frameMain.getContentPane().add(sp);
			
		
		//Setup for send Message button
		sendMessageButton = new JButton();//send message
		sendMessageButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				content ="You: " + textArea_userInput.getText() + "\n";
				textArea.append(content);
				textArea_userInput.setText("");
			}
		});
		
		sendMessageButton.setBounds(660, 475, 30, 30);
		sendMessageButton.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		sendMessageButton.setBackground(new Color(253, 245, 230));
		Image imgIcon_1 = ImageIO.read(getClass().getResource("/src/right-arrow.png"));
		sendMessageButton.setIcon(new ImageIcon(imgIcon_1));
		sendMessageButton.setBackground(Color.WHITE);
		sendMessageButton.setFocusPainted(false);
		sendMessageButton.setBorder(BorderFactory.createEmptyBorder());
		sendMessageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		frameMain.getContentPane().add(sendMessageButton);
		
		
		//Setup Send Image
		sendPictureButton = new JButton();//send picture
		sendPictureButton.setBounds(240, 430, 40, 40);
		Image img = ImageIO.read(getClass().getResource("/src/picture.png"));
		sendPictureButton.setIcon(new ImageIcon(img));
		sendPictureButton.setBackground(Color.WHITE);
		sendPictureButton.setFocusPainted(false);
		sendPictureButton.setBorder(BorderFactory.createEmptyBorder());
		sendPictureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select an image");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images only", "png", "jpeg");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(jfc.getSelectedFile().getPath());//Test only
					//Note: change into transferring the image file
				}
			}
		});
		frameMain.getContentPane().add(sendPictureButton);
		
		//Setup send File Button
		JButton sendFilebutton = new JButton();
		sendFilebutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select a document");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Document only", "docx", "doc","pdf");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(jfc.getSelectedFile().getPath());//Test only
					//Note: change into transferring the document file
				}
			}
		});
		Image imgicon = ImageIO.read(getClass().getResource("/src/file.png"));
		sendFilebutton.setIcon(new ImageIcon(imgicon));
		sendFilebutton.setBounds(203, 435, 30, 30);
		sendFilebutton.setBackground(Color.WHITE);
		sendFilebutton.setFocusPainted(false);
		sendFilebutton.setBorder(BorderFactory.createEmptyBorder());
		frameMain.getContentPane().add(sendFilebutton);
		
		//Music Button
		JButton buttonMusic = new JButton();
		buttonMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select a mp3 file");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Mp3 only", "mp3");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(jfc.getSelectedFile().getPath());//Test only
					//Note: change into transferring the mp3 file
				}
			}
		});
		Image imgMusic = ImageIO.read(getClass().getResource("/src/music-player.png"));
		buttonMusic.setIcon(new ImageIcon(imgMusic));
		buttonMusic.setBounds(285, 435, 30, 30);
		buttonMusic.setBackground(Color.WHITE);
		buttonMusic.setFocusPainted(false);
		buttonMusic.setBorder(BorderFactory.createEmptyBorder());
		frameMain.getContentPane().add(buttonMusic);
		
		//Other Type Button
		JButton buttonOther = new JButton();
		buttonOther.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Custom file format");

				int returnValue = jfc.showDialog(null, "Choose");
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(jfc.getSelectedFile().getPath());//Test only
					//Note: please change
				}
			}
		});
		Image imgOther = ImageIO.read(getClass().getResource("/src/folder.png"));
		buttonOther.setIcon(new ImageIcon(imgOther));
		buttonOther.setBounds(325, 435, 30, 30);
		buttonOther.setBackground(Color.WHITE);
		buttonOther.setFocusPainted(false);
		buttonOther.setBorder(BorderFactory.createEmptyBorder());
		frameMain.getContentPane().add(buttonOther);
	
//------This Part: Right-side is different with Chat 1-1--------
		//Setup List Member in Group chat
		JList list = new JList();
		list.setBounds(712, 30, 164, 484);
		list.setBorder(new LineBorder(newBlue, 1));
		
		JScrollPane sp4GroupMember = new JScrollPane(list);
		sp4GroupMember.setBounds(712,30,164,484);
		sp4GroupMember.setBorder(BorderFactory.createEmptyBorder());
		frameMain.getContentPane().add(sp4GroupMember);
		
		//Setup Label Member
		JLabel lblNewLabel = new JLabel("Group Member");
		lblNewLabel.setForeground(new Color(50, 205, 50));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(712, 6, 164, 25);
		lblNewLabel.setForeground(wineRed);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD,15));
		frameMain.getContentPane().add(lblNewLabel);
		
	}
}