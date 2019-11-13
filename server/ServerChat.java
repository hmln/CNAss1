package server;

import java.io.*;
import java.net.*;
import java.util.*;
import data.ChatLog;

public class ServerChat {
	private int port;
	private volatile static Set<String> usernameSet = null;
	private Set<UserThread> userThreads = null;
	private ChatLog log = null;
	
	public ServerChat(int port) throws IOException
	{
		this.port = port;
		usernameSet = new HashSet<>();
		userThreads = new HashSet<>();
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
			}
		} catch (IOException ex) 
		{
            System.out.println("Error in the server: " + ex.getMessage());
        }
	}
	
	void addUser(String userName, UserThread user)
	{
		userThreads.add(user);
		usernameSet.add(userName);
	}
	
	void removeUser(String userName, UserThread user) 
	{
		boolean check = usernameSet.remove(userName);
		if (check)
		{
			userThreads.remove(user);
		}
		else
		{
			System.out.println("Error in removing user: "+userName);
		}
	}
	
	public static Set<String> getUserlist()
	{
		return usernameSet;
	}
	void announce(String message, UserThread exception) throws IOException
	{
		addMessagetoLog(message);
		for (UserThread anotherUser : userThreads)
		{
			if (anotherUser != exception)
			{
				anotherUser.sendMessage(message);
			}
		}
	}
	public void addMessagetoLog(String message) throws IOException
	{
		log.addMessage(message);
	}
}
