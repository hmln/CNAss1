package gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

import utils.*;

import javax.swing.border.LineBorder;
import javax.imageio.ImageIO;
import javax.swing.filechooser.*;

import p2pserver.P2PServer;


public class FriendChat extends JFrame {
	private static final long serialVersionUID = 1L;
	JFrame frame;
	private JLabel friendLabel, userLabel;
	private static JList<String> list;
	private JTextField friendInput;
	private JButton addButton,returnButton,removeButton,requestButton,sendButton,pictureButton, docButton, musicButton,refreshButton;
	private volatile JTextArea chatArea;
	private JTextArea sendMessage;
	private JScrollPane scroll_bar;
	String user = Login.currentuser;
	String partner = "";
	DefaultListModel<String> friendList = null;
	
	private String hostname;
	private int port;
	private PrintWriter writer;
	private OutputStream output;
	private Socket socket = null;
	
	private volatile String message = "";
	
	//friend chat as client to other server
	public FriendChat(String partner) throws Exception
	{
		try {
			System.out.println("Running on other server");
			String socketstat = Login.tcp.receiveSocketInfo(partner).substring(7);
			String[] data = socketstat.split(":",2);
			hostname = data[0];
			port = Integer.parseInt(data[1]);
			socket = new Socket(hostname, port);
			output = socket.getOutputStream();
			writer = new PrintWriter(output,true);
			writer.println(user);
			this.partner = partner;
			writer.println(this.partner);
			initialize();
		} catch (ConnectException e1) {
			JOptionPane.showMessageDialog(null, "User " + partner + " is offline!", "Sorry", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//friend chat as client to own server
	public FriendChat(String partner, String address, int port) throws Exception {
		try {
			System.out.println("Running on own server");
			hostname = address;
			this.port = port;
			socket = new Socket(address,port);
			output = socket.getOutputStream();
			writer = new PrintWriter(output,true);
			writer.println(user);
			this.partner = partner;
			writer.println(this.partner);
			initialize();
		} catch (ConnectException e1) {
			JOptionPane.showMessageDialog(null, "User " + partner + " is offline!", "Sorry", JOptionPane.ERROR_MESSAGE);
		}
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
		
		/*frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent){
		    	try {
		    		socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		});*/
		
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
		

		//Set up scroll pane for Friend List
		JScrollPane scr = new JScrollPane(list);
		scr.setBorder(BorderFactory.createEmptyBorder());
		scr.setBounds(16,30,156,484);
		frame.getContentPane().add(scr);
	
		//Setup Button Add
		addButton = new JButton("Add");
		addButton.setBorder(new RoundedBorder(8));
		addButton.setBounds(385, 8, 70, 25);
		addButton.setBackground(Color.WHITE);
		addButton.setForeground(newBlue);
		frame.getContentPane().add(addButton);
		
		//Setup Button Remove
		removeButton = new JButton("Remove");
		removeButton.setBounds(460, 8, 100, 25);
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
		friendInput.setBounds(200, 8, 178, 26);
		friendInput.setBorder(new RoundedBorder(5));
		friendInput.setColumns(10);
		frame.getContentPane().add(friendInput);
		
		//Set up Label Chat
		userLabel = new JLabel("User " + user + " chat with " + partner);
		userLabel.setFont(new Font("Gabriola", Font.BOLD,22));
		userLabel.setBounds(200, 55, 350, 50);
		userLabel.setForeground(blueAqua);
		frame.getContentPane().add(userLabel);
		
		//Setup Text Area for displaying the message
		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setBounds(192, 110, 480, 303);
		chatArea.setLineWrap(true);
		chatArea.setBorder(new RoundedBorder(8));
		String[] messageList = Login.tcp.updateLog(user, partner).substring(7).split("`");
		for(String str : messageList)
		{
			chatArea.append(str);
			chatArea.append("\n");
		}
		//Scroll
		scroll_bar= new JScrollPane(chatArea);
		scroll_bar.setBounds(192,110,480,303);
		scroll_bar.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(scroll_bar);
		
		//Set up Button Refresh 
		refreshButton = new JButton();
		refreshButton.setBounds(16, 0, 24, 24);
		Image imgRefresh = ImageIO.read(getClass().getResource("/utils/refresh.png"));
		refreshButton.setIcon(new ImageIcon(imgRefresh));
		refreshButton.setBackground(Color.WHITE);
		refreshButton.setFocusPainted(false);
		refreshButton.setBorder(BorderFactory.createEmptyBorder());
		frame.getContentPane().add(refreshButton);
		
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
		friendUpdate();
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
						if (message.equals("file"))
						{
							receiveFile(input);
						}
						else
						{
							chatArea.append(message);
							chatArea.append("\n");
						}
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
			public void mouseClicked(MouseEvent arg) {
				String value = (String)list.getModel().getElementAt(list.locationToIndex(arg.getPoint()));
				try {
					String[] splitSide = value.split(" - ",2);
					if (splitSide[1].equals("Offline"))
						JOptionPane.showMessageDialog(null, "User " + splitSide[0] + " is offline!", "Sorry", JOptionPane.INFORMATION_MESSAGE);
					else 
					{
						if (P2PServer.getUserlist().containsKey(value + ":" + user)) new FriendChat(splitSide[0],Login.ownaddress,Login.ownport);
						else new FriendChat(splitSide[0]);
						frame.dispose();
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
		sendMessage.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n') {
					if (sendMessage.getText().equals("\n")) 
					{
						sendMessage.setText(null);
						return;
					}
					message = user + ":" + sendMessage.getText();
					chatArea.append(message);
					writer.println(message);
					sendMessage.setText(null);
				}
			}
		});
		sendButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String input = sendMessage.getText();
				if (input.equals("")) return;
				message = user + ":" + input + "\n";
				chatArea.append(message);
				writer.println(message);
				sendMessage.setText("");
			}
		});
		pictureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select an image");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Images only", "png", "jpeg","jpg");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					writer.println("file");
					String path = jfc.getSelectedFile().getPath();
					try {
						sendFile(path);
					} catch (IOException e1) {
						e1.printStackTrace();
				} catch (Exception e1) {
						e1.printStackTrace();
				}
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
					writer.println("file");
					String path = jfc.getSelectedFile().getPath();
					try {
						sendFile(path);
					} catch (IOException e1) {
						e1.printStackTrace();
				} catch (Exception e1) {
						e1.printStackTrace();
				}
			}
			}
		});
		musicButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Select a mp3 file");
				jfc.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Music only", "mp3","ogg","wav");
				jfc.addChoosableFileFilter(filter);

				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					writer.println("file");
					String path = jfc.getSelectedFile().getPath();
					try {
						sendFile(path);
					} catch (IOException e1) {
						e1.printStackTrace();
				} catch (Exception e1) {
						e1.printStackTrace();
				}
				}
			}
		});
		buttonOther.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				jfc.setDialogTitle("Custom file format");

				int returnValue = jfc.showDialog(null, "Choose");
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					writer.println("file");
					String path = jfc.getSelectedFile().getPath();
					try {
						sendFile(path);
					} catch (IOException e1) {
						e1.printStackTrace();
				} catch (Exception e1) {
						e1.printStackTrace();
				}
				}
			}
		});
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
	
	public void sendFile(String path) throws Exception {
		DataOutputStream cout=new DataOutputStream(output);
		File file=new File(path);
		cout.writeUTF(file.getName());
		FileInputStream fis=new FileInputStream(file);
		int letter;
		do
		{
			letter=fis.read();
			cout.writeUTF(String.valueOf(letter));
		} while(letter!=-1);
		fis.close();
	}
	public void receiveFile(InputStream input) throws Exception {
		DataInputStream cin =new DataInputStream(input);
		String filename = cin.readUTF();
		String home = System.getProperty("user.home");
    	String destination = home + "/Downloads/" + filename;
		//String destination = "d/" + user + "/" + filename;
    	File file=new File(destination);
		FileOutputStream fout=new FileOutputStream(file);
		int letter;
		do
		{
			letter =Integer.parseInt(cin.readUTF());
		if (letter!=-1) fout.write(letter);
		}while(letter!=-1);
		fout.close();
		chatArea.append(partner + ": I just send you a file name: "+filename+", please check at your download");
    }
}
