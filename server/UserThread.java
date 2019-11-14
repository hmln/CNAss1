package server;

import java.io.*;
import java.net.*;

public class UserThread extends Thread{
	private Socket socket;
	private ServerChat server;
	private PrintWriter writer;
	private String username = "";
	private String destination = "";
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
			//a:abc
			while (!message.equals("quit"))
			{
				//bbb:aaa:Hello
				String get = reader.readLine();
				int pos = get.indexOf(':');
				if (pos == -1) continue;
				destination = get.substring(0, pos);
				System.out.println(pos);
				System.out.println(destination);
				message = get.substring(pos+1);
				server.announce(message,server.findUser(destination));
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
	public String getname()
	{
		return username;
	}
}
