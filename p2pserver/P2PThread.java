package p2pserver;

import java.io.*;
import java.net.*;

import gui.Login;

public class P2PThread extends Thread{
	private Socket socket;
	private P2PServer server;
	private PrintWriter writer;
	private BufferedReader reader;
	private OutputStream output;
	private String username = "";
	private String partner = "";
	public P2PThread(Socket socket, P2PServer server) throws IOException 
	{
        this.socket = socket;
        this.server = server;
        output = socket.getOutputStream();
    }
	
	public void run()
	{
		try
		{	
			InputStream input = socket.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
			writer = new PrintWriter(output,true);
			username = reader.readLine();
			partner = reader.readLine();
			server.addUser(username + ":" + partner, this);
			System.out.println(username + ":" + partner);
			String message = "";
			while (true)
			{
				message = reader.readLine();
				if (message.equals("file"))
				{
					String path = receiveFile(input);
					server.forwardFile(path, server.findUser(partner + ":" + username));
					
				}
				else if (!message.equals("")) 
				{
					server.announce(message,server.findUser(partner + ":" + username));
					try {
						Login.tcp.sendLog(username, partner, message);
					} catch (IOException e) {
						server.announce("Unable to store to server",server.findUser(username + ":" + partner));
						e.printStackTrace();
					}
				}
			}
		} catch (IOException e)
		{
			System.out.println("Error at user: " + e.getMessage());
			server.removeUser(username, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendFile(String path) throws Exception {
		sendMessage("file");
		DataOutputStream cout=new DataOutputStream(output);
		File file=new File(path);	
		cout.writeUTF(file.getName());
		FileInputStream fin=new FileInputStream(file);
		int letter;
		do
		{
			letter = fin.read();
			cout.writeUTF(Integer.toString(letter));
		} while(letter!=-1);
		fin.close();
    }

    public String receiveFile(InputStream input) throws Exception {
    	DataInputStream cin=new DataInputStream(input);
    	
    	String filename=cin.readUTF();
    	String path = MakePath.makedir("/src/data/file") + "/" + filename;
    	File file=new File(path);
    	FileOutputStream fout=new FileOutputStream(file);
    	int letter;
    	while((letter=Integer.parseInt(cin.readUTF()))!=-1)
    	{
    		fout.write(letter);
    	}
    	fout.close();
    	return path;
    }
	
	void sendMessage(String message) throws IOException 
	{
        writer.println(message);
    }
	public String getToken()
	{
		return username + ":" + partner;
	}
}
