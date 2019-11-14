package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerChat {
	private int port;
	private volatile static Map<String,UserThread> clientList; 
	public ServerChat(int port) throws IOException
	{
		this.port = port;
		clientList = new HashMap<String,UserThread>();
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
				addUser(newUser.getToken(),newUser);
			}
		} catch (IOException ex) {
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
	
	void announce(String message, UserThread user) throws IOException
	{
		for (Map.Entry<String,UserThread> entry : clientList.entrySet()) 
			if (entry.getValue().equals(user))
			{
				user.sendMessage(message);
				return;
			}
	}

	public void forwardFile(String path, UserThread user) throws Exception {
		for (Map.Entry<String,UserThread> entry : clientList.entrySet()) 
			if (entry.getValue().equals(user))
			{
				user.sendFile(path);
				return;
			}
	}
}
