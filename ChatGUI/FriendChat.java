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

import Login.runLogin;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import FriendRequest.runRequestForm;


public class FriendChat extends JFrame {

	private static String IPClient = "", userName = "" , userData = "";
	private static int portClient = 0;
	JFrame frameMain;
	private JLabel labelActiveNow;
	private static JList<String> listActive;
	private JPanel contentPane;
	private JTextField textField_1;
	private JButton btnNewButton_2;
	private JTextArea textArea;
	private JTextArea textArea_1;
	private JScrollBar scrollBar;
	private JButton sendFileButton;
	private JButton btnNewButton_4;
	private JScrollPane scroll_bar;
//	private final Action action = new SwingAction();
	private String content;

	/**
	 * Create the frame.
	 */
	public FriendChat(String arg, int arg1, String name, String msg) throws Exception {
		IPClient = arg;
		portClient = arg1;
		userName = name;
		userData = msg;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FriendChat window = new FriendChat();
					window.frameMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public FriendChat() throws Exception {
		initialize();
	}
	
	private void initialize() throws IOException {
		//Set up colors
		Color newBlue = new Color(0, 79, 109);
		Color wineRed = new Color(139,9,35);
		Color blueAqua = new Color(0, 143, 215);
		
		//Set up the Chat Window
		frameMain = new JFrame();
		frameMain.setTitle("CHAT 1 - 1 Friend");
		frameMain.getContentPane().setBackground(Color.WHITE);
		frameMain.setResizable(false);
		frameMain.setBounds(680,240,700,560);
		frameMain.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameMain.getContentPane().setLayout(null);
		
		
//------Setup for Left Side of Window: Online List-----------------------------		
		//Set up Label Friend List
		labelActiveNow = new JLabel("Online Friend");
		labelActiveNow.setHorizontalAlignment(SwingConstants.CENTER);
		labelActiveNow.setBounds(12, 6, 156, 16);
		labelActiveNow.setForeground(newBlue);
		labelActiveNow.setFont(new Font("Segoe UI", Font.BOLD,15));
		frameMain.getContentPane().add(labelActiveNow);
		
		//Set up JList for Online Friend List
		listActive = new JList<>();
		listActive.setBounds(16, 30, 156, 484);
		listActive.setBorder(new LineBorder(newBlue, 1));
		listActive.setFont(new Font("Segoe UI",Font.PLAIN,15));
		
		//Setup Event for Online Friend List
		/*
		 * listActive.addMouseListener(new MouseAdapter() {
		 
			@Override
			public void mouseClicked(MouseEvent arg) {
				String value = (String)listActive.getModel().getElementAt(listActive.locationToIndex(arg.getPoint()));
			textNameFriend.setText(value);
			}
		});
		*/

		//Set up scroll pane for Friend List
		JScrollPane scr = new JScrollPane(listActive);
		scr.setBorder(BorderFactory.createEmptyBorder());
		scr.setBounds(16,30,156,484);
		
		//Add scroll pane into mainWindow
		frameMain.getContentPane().add(scr);
		
//------Top of the Window: Place UserName instruction, TextField to input the username, 
//		button connect, disconnect, log out of the system
		
		//Setup Label Username
		JLabel lblUserName = new JLabel("Username");
		lblUserName.setFont(new Font("Segoe UI", Font.BOLD,14));
		lblUserName.setBounds(200, 12, 74, 16);
		lblUserName.setForeground(blueAqua);
		frameMain.getContentPane().add(lblUserName);
		
		//Setup Button Connect
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
		
		//Setup Button Choose Mode
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
		
		
		
		//Setup Username's Input
		textField_1 = new JTextField();
		textField_1.setBounds(273, 8, 178, 26);
		textField_1.setBorder(new RoundedBorder(5));
		frameMain.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
//------Middle: Place ChatLog and display it---------------
		
		//Set up Label Chat With + UserName
		JLabel wel = new JLabel("Chat With " + userName);
		wel.setFont(new Font("Caranda Personal Use", Font.PLAIN,25));
		wel.setBounds(200, 55, 120, 50);
		wel.setForeground(blueAqua);
		frameMain.getContentPane().add(wel);
		
		//Setup Text Area for displaying the message
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(192, 110, 480, 303);
		textArea.setLineWrap(true);
		textArea.setBorder(new RoundedBorder(8));
		//frameMain.getContentPane().add(textArea);
		
		//Scroll
		scroll_bar= new JScrollPane(textArea);
		scroll_bar.setBounds(192,110,480,303);
		scroll_bar.setBorder(BorderFactory.createEmptyBorder());
		frameMain.getContentPane().add(scroll_bar);
		
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
		
//------Bottom: TextField to Type and Button for transferring file-----------
		
		//Setup Text Area for typing the message
		textArea_1 = new JTextArea();
		textArea_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					content ="You:"  + textArea_1.getText();
					textArea.append(content);
					textArea_1.setText(null);
					
				}
			}
		});
		textArea_1.setBounds(192, 472, 435, 40);
		textArea_1.setLineWrap(true);
		textArea_1.setBorder(new RoundedBorder(5));
		//frameMain.getContentPane().add(textArea_1);
		
		//Setup scroll pane for Text Area Typing Message Window
		JScrollPane sp = new JScrollPane(textArea_1);
		sp.setBounds(192,472,435,40);
		sp.setBorder(BorderFactory.createEmptyBorder());
		frameMain.getContentPane().add(sp);
		
		//Setup Button Send Message
		btnNewButton_2 = new JButton();//send message
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				content ="You: " + textArea_1.getText() + "\n";
				textArea.append(content);
				textArea_1.setText("");
			}
		});
		btnNewButton_2.setBounds(640, 475, 30, 30);
		btnNewButton_2.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		btnNewButton_2.setBackground(new Color(253, 245, 230));
		Image imgIcon_1 = ImageIO.read(getClass().getResource("/src/right-arrow.png"));
		btnNewButton_2.setIcon(new ImageIcon(imgIcon_1));
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.setFocusPainted(false);
		btnNewButton_2.setBorder(BorderFactory.createEmptyBorder());
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		frameMain.getContentPane().add(btnNewButton_2);
				
		//Setup Button Send Picture
		btnNewButton_4 = new JButton();//send picture
		btnNewButton_4.setBounds(240, 430, 40, 40);
		Image img = ImageIO.read(getClass().getResource("/src/picture.png"));
		btnNewButton_4.setIcon(new ImageIcon(img));
		btnNewButton_4.setBackground(Color.WHITE);
		btnNewButton_4.setFocusPainted(false);
		btnNewButton_4.setBorder(BorderFactory.createEmptyBorder());
		btnNewButton_4.addActionListener(new ActionListener() {
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
		frameMain.getContentPane().add(btnNewButton_4);
		
		//Send file document button
		JButton button = new JButton();
		button.addActionListener(new ActionListener() {
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
		button.setIcon(new ImageIcon(imgicon));
		button.setBounds(203, 435, 30, 30);
		button.setBackground(Color.WHITE);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createEmptyBorder());
		frameMain.getContentPane().add(button);
		
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
		
//------Right-side of Chat Window: User Image--------------
		//Setup User Image
//		ImageIcon imgIcon = new ImageIcon(new ImageIcon("user.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
//		JLabel lblNewLabel = new JLabel("New label");
//		lblNewLabel.setBounds(605, 56, 80, 80);
//		lblNewLabel.setIcon(imgIcon);
//		frameMain.getContentPane().add(lblNewLabel);
	}
}
