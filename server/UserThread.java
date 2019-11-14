package server;

import java.io.*;
import java.net.*;

import data.ChatLog;
import data.LogDB;
import utils.StrToInt;

public class UserThread extends Thread{
	private Socket socket;
	private ServerChat server;
	private PrintWriter writer;
	private String username = "";
	private String partner = "";
	private ChatLog log;
	public UserThread(Socket socket, ServerChat server) 
	{
        this.socket = socket;
        this.server = server;
    }
	
	public void run()
	{
		try
		{	
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			OutputStream output = socket.getOutputStream();
			writer = new PrintWriter(output,true);
			username = reader.readLine();
			partner = reader.readLine();
			server.addUser(username + ":" + partner, this);
			String message = "";
			int logID = StrToInt.toInt(username) + StrToInt.toInt(partner);
			while (!message.equals("quit"))
			{
				message = reader.readLine();
				if (!message.equals("")) 
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
		}
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
