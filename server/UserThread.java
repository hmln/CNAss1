package server;

import java.io.*;
import java.net.*;

import data.ChatLog;
import data.LogDB;
import utils.MakePath;
import utils.StrToInt;

public class UserThread extends Thread{
	private Socket socket;
	private ServerChat server;
	private PrintWriter writer;
	private BufferedReader reader;
	private OutputStream output;
	private String username = "";
	private String partner = "";
	private ChatLog log;
	public UserThread(Socket socket, ServerChat server) throws IOException 
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
			String message = "";
			int logID = StrToInt.toInt(username) + StrToInt.toInt(partner);
			while (!message.equals("quit"))
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
					log = LogDB.getJson(logID);
					log.addMessage(message);
				}
			}
			server.removeUser(username,this);
			socket.close();
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
