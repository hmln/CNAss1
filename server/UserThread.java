package server;

import java.io.*;
import java.net.*;

public class UserThread extends Thread{
	private Socket socket;
	private ServerChat server;
	private PrintWriter writer;
	private String username = "";
	
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
			server.addUser(username, this);
			String message = "";
			String serverMessage;
			
			while (!message.equals("quit"))
			{
				message = reader.readLine();
				server.announce(message,this);
			}
			server.removeUser(username,this);
			socket.close();
			
			serverMessage = username + "quitted";
			server.announce(serverMessage, this);
		} catch (IOException e)
		{
			System.out.println("Error at user: " + e.getMessage());
			server.removeUser(username, this);
		}
	}
	
	void sendMessage(String message) throws IOException 
	{
        writer.print(message);
        writer.println();
    }
	public String getname()
	{
		return username;
	}
}
