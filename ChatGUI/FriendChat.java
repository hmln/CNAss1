package ChatGUI;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

import utils.RoundedBorder;
import javax.swing.border.LineBorder;
import javax.imageio.ImageIO;
import javax.swing.filechooser.*;

import data.*;
import server.ServerChat;


public class FriendChat extends JFrame {
	private static final long serialVersionUID = 1L;
	JFrame frame;
	private JLabel friendLabel, userLabel;
	private static JList<String> list;
	private JTextField friendInput;
	private JButton addButton,returnButton,removeButton,requestButton,sendButton,pictureButton, docButton, musicButton;
	private volatile JTextArea chatArea;
	private JTextArea sendMessage;
	private JScrollPane scroll_bar;
	Account user = Login.currentUser;
	Account partner = null;
	DefaultListModel<String> friendList = null;
	
	private String hostname;
	private int port;
	private PrintWriter writer;
	private Socket socket = null;
	
	private volatile String message = "";
	
	//friend chat as client to other server
	public FriendChat(String partner) throws Exception 
	{
		this.partner = UserDB.getJson(partner);
		hostname = this.partner.getIp();
		port = this.partner.getPort();
		socket = new Socket(hostname, port);
		
		OutputStream output = socket.getOutputStream();
		writer = new PrintWriter(output,true);
		writer.println(user.getName());
		
		initialize();
		

	}
	
	//friend chat as client to own server
	//had to put a dummy text
	public FriendChat(String partner, int dummy) throws Exception {
		this.partner = UserDB.getJson(partner);
		socket = new Socket(user.getIp(),user.getPort());

		OutputStream output = socket.getOutputStream();
		writer = new PrintWriter(output,true);
		writer.println(user.getName());
		initialize();
	}
	
	private void initialize() throws IOException {
		//Set up colors
		
		Color newBlue = new Color(0, 79, 109);
		Color wineRed = new Color(139,9,35);
		Color blueAqua = new Color(0, 143, 215);
		
		//Set up the Chat Window
		frame = new JFrame();
		frame.setTitle("Friendly Nonsense Chat");
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(false);
		frame.setBounds(680,240,700,560);
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
		for (String entry : user.getFriendList())
		friendList.addElement(entry);

		//Set up scroll pane for Friend List
		JScrollPane scr = new JScrollPane(list);
		scr.setBorder(BorderFactory.createEmptyBorder());
		scr.setBounds(16,30,156,484);
		frame.getContentPane().add(scr);
	
		//Setup Button Add
		addButton = new JButton("Add");
		addButton.setBorder(new RoundedBorder(8));
		addButton.setBounds(455, 8, 70, 25);
		addButton.setBackground(Color.WHITE);
		addButton.setForeground(newBlue);
		frame.getContentPane().add(addButton);
		
		//Setup Button Remove
		removeButton = new JButton("Remove");
		removeButton.setBounds(530, 8, 100, 25);
		removeButton.setBorder(new RoundedBorder(8));
		removeButton.setBackground(Color.WHITE);
		removeButton.setForeground(wineRed);
		frame.getContentPane().add(removeButton);
		
		//Setup Button Return
		returnButton = new JButton();
		returnButton.setBounds(635, 1, 40, 40);
		Image returnImage = ImageIO.read(getClass().getResource("/utils/log-out.png"));
		returnButton.setIcon(new ImageIcon(returnImage));
		returnButton.setBackground(Color.WHITE);
		returnButton.setFocusPainted(false);
		returnButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(returnButton);
		
		//Setup Friendname Input
		friendInput = new JTextField();
		friendInput.setBounds(273, 8, 178, 26);
		friendInput.setBorder(new RoundedBorder(5));
		friendInput.setColumns(10);
		frame.getContentPane().add(friendInput);
		
		//Set up Label Chat
		userLabel = new JLabel("User " + user.getName() + " chat with " + partner.getName());
		userLabel.setFont(new Font("Caranda Personal Use", Font.PLAIN,22));
		userLabel.setBounds(200, 55, 350, 50);
		userLabel.setForeground(blueAqua);
		frame.getContentPane().add(userLabel);
		
		//Setup Text Area for displaying the message
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBounds(192, 110, 480, 303);
		chatArea.setLineWrap(true);
		chatArea.setBorder(new RoundedBorder(8));
		//frameMain.getContentPane().add(textArea);
		
		//Scroll
		scroll_bar= new JScrollPane(chatArea);
		scroll_bar.setBounds(192,110,480,303);
		scroll_bar.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(scroll_bar);
		
		//Setup Button Friend Request
		requestButton = new JButton();
		requestButton.setBounds(635, 50, 40, 40);
		Image imgRequest = ImageIO.read(getClass().getResource("/utils/add-friend.png"));
		requestButton.setIcon(new ImageIcon(imgRequest));
		requestButton.setBackground(Color.WHITE);
		requestButton.setFocusPainted(false);
		requestButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(requestButton);
		
		sendMessage = new JTextArea();
		sendMessage.setBounds(192, 472, 435, 40);
		sendMessage.setLineWrap(true);
		sendMessage.setBorder(new RoundedBorder(5));
		
		//Setup scroll pane for Text Area Typing Message Window
		JScrollPane sp = new JScrollPane(sendMessage);
		sp.setBounds(192,472,435,40);
		sp.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(sp);
		
		//Setup Button Send Message
		sendButton = new JButton();
		sendButton.setBounds(640, 475, 30, 30);
		Image imgIcon_1 = ImageIO.read(getClass().getResource("/utils/right-arrow.png"));
		sendButton.setIcon(new ImageIcon(imgIcon_1));
		sendButton.setBackground(Color.WHITE);
		sendButton.setFocusPainted(false);
		sendButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(sendButton);
				
		//Setup Button Send Picture
		pictureButton = new JButton();
		pictureButton.setBounds(240, 430, 40, 40);
		Image img = ImageIO.read(getClass().getResource("/utils/picture.png"));
		pictureButton.setIcon(new ImageIcon(img));
		pictureButton.setBackground(Color.WHITE);
		pictureButton.setFocusPainted(false);
		pictureButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(pictureButton);
		
		//Send file document button
		docButton = new JButton();
		Image imgicon = ImageIO.read(getClass().getResource("/utils/file.png"));
		docButton.setIcon(new ImageIcon(imgicon));
		docButton.setBounds(203, 435, 30, 30);
		docButton.setBackground(Color.WHITE);
		docButton.setFocusPainted(false);
		docButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(docButton);
		
		//Music Button
		musicButton = new JButton();
		Image imgMusic = ImageIO.read(getClass().getResource("/utils/music-player.png"));
		musicButton.setIcon(new ImageIcon(imgMusic));
		musicButton.setBounds(285, 435, 30, 30);
		musicButton.setBackground(Color.WHITE);
		musicButton.setFocusPainted(false);
		musicButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(musicButton);
		
		//Other Type Button
		JButton buttonOther = new JButton();
		
		Image imgOther = ImageIO.read(getClass().getResource("/utils/folder.png"));
		buttonOther.setIcon(new ImageIcon(imgOther));
		buttonOther.setBounds(325, 435, 30, 30);
		buttonOther.setBackground(Color.WHITE);
		buttonOther.setFocusPainted(false);
		buttonOther.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(buttonOther);
		
//////////////////////////////////////////////////////////////////////////
		//Read Thread
		Thread read = new Thread()
		{
			public void run()
			{
				try
				{
					InputStream input = socket.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(input));
					while (true)
					{
						message = reader.readLine();
						chatArea.append(message);
						chatArea.append("\n");
					}
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		};
		
		read.start();
		
		
/////////////////////////////////////////////////////////////////////////
		//Listener

		list.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unlikely-arg-type")
			public void mousePressed(MouseEvent arg) {
				String value = (String)list.getModel().getElementAt(list.locationToIndex(arg.getPoint()));
				try {
					if (ServerChat.getUserlist().containsValue(value)) new FriendChat(value,0);
					new FriendChat(value);
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
		requestButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestForm form = new requestForm();
				form.setVisible(true);
				friendUpdate();
			}
		});
		sendMessage.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					if (sendMessage.getText().equals("\n")) 
					{
						sendMessage.setText(null);
						return;
					}
					System.out.print(sendMessage.getText());
					message = user.getName() + ":" + sendMessage.getText();
					chatArea.append(message);
					writer.println(partner.getName() + ":" + message);
					sendMessage.setText(null);
				}
			}
		});
		sendButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String input = sendMessage.getText();
				if (input.equals("")) return;
				message = user.getName() + ":" + input + "\n";
				chatArea.append(message);
				writer.println(partner.getName() + ":" + message);
				sendMessage.setText("");
			}
		});
		pictureButton.addActionListener(new ActionListener() {
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
		docButton.addActionListener(new ActionListener() {
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
		musicButton.addActionListener(new ActionListener() {
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
		buttonOther.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Custom file format");

				int returnValue = jfc.showDialog(null, "Choose");
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(jfc.getSelectedFile().getPath());//Test only
					try {
						sendFile(jfc.getSelectedFile().getPath().toString());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		frame.setVisible(true);
	}
	void friendUpdate()
	{
		friendList.clear();
		for (String entry : user.getFriendList())
			friendList.addElement(entry);
	}
	void sendFile(String path) throws IOException
	{
		byte[] mybytearray = new byte[1024];
        InputStream is = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream(path);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int bytesRead = is.read(mybytearray, 0, mybytearray.length);
        bos.write(mybytearray, 0, bytesRead);
        bos.close();

	}
}
