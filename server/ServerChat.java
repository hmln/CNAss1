package server;

import java.io.*;
import java.net.*;
import java.util.*;
import data.ChatLog;

public class ServerChat {
	private int port;
	private volatile static Map<String,UserThread> clientList; 
	private ChatLog log = null;
	public ServerChat(int port) throws IOException
	{
		this.port = port;
		clientList = new HashMap<String,UserThread>();
		log = new ChatLog();
		exe();
	}
	
	public void exe()
	{
		try (ServerSocket server = new ServerSocket(port))
		{
			while(true)
			{
				Socket socket = server.accept();
				UserThread newUser = new UserThread(socket, this);
				newUser.start();
				addUser(newUser.getname(),newUser);
				/*for (Map.Entry<String,UserThread> user : clientList.entrySet()) 
				{
					System.out.println(user.getKey() + ", ");
			    }*/
				
			}
		} catch (IOException ex) 
		{
            System.out.println("Error in the server: " + ex.getMessage());
        }
	}
	
	void addUser(String userName, UserThread user)
	{
		clientList.put(userName, user);
	}
	
	UserThread findUser(String userName)
	{
		return clientList.get(userName);
	}
	
	void removeUser(String userName, UserThread user) 
	{
		clientList.remove(userName);
	}
	
	public static Map<String, UserThread> getUserlist()
	{
		return clientList;
	}
	void announce(String message, UserThread userThread) throws IOException
	{
		for (Map.Entry<String,UserThread> user : clientList.entrySet()) 
		{
			if (user.getValue().equals(userThread))
			{
				userThread.sendMessage(message);
				//System.out.println(userThread.getname());
				return;
			}
	    }
	}
	public void addMessagetoLog(String message) throws IOException
	{
		log.addMessage(message);
	}
}
