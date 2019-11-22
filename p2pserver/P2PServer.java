package p2pserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class P2PServer {
	private int port;
	private volatile static Map<String,P2PThread> clientList; 
	private String owner;
	public P2PServer(int port, String owner) throws IOException
	{
		this.port = port;
		this.owner = owner;
		clientList = new HashMap<String,P2PThread>();
		System.out.println("New P2P server created by " + this.owner);
		exe();
	}
	
	public void exe()
	{
		try (ServerSocket server = new ServerSocket(port))
		{
			while(true)
			{
				Socket socket = server.accept();
				P2PThread newUser = new P2PThread(socket, this);
				newUser.start();
				addUser(newUser.getToken(),newUser);
			}
		} catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
        }
	}
	
	void addUser(String userName, P2PThread user)
	{
		clientList.put(userName, user);
	}
	
	P2PThread findUser(String userName)
	{
		return clientList.get(userName);
	}
	
	void removeUser(String userName, P2PThread user) 
	{
		clientList.remove(userName);
	}
	
	public static Map<String, P2PThread> getUserlist()
	{
		return clientList;
	}
	
	void announce(String message, P2PThread user) throws IOException
	{
		for (Map.Entry<String,P2PThread> entry : clientList.entrySet()) 
			if (entry.getValue().equals(user))
			{
				user.sendMessage(message);
				return;
			}
	}

	public void forwardFile(String path, P2PThread user) throws Exception {
		for (Map.Entry<String,P2PThread> entry : clientList.entrySet()) 
			if (entry.getValue().equals(user))
			{
				user.sendFile(path);
				return;
			}
	}

	public String getOwner()
	{
		return owner;
	}
	/*public void forwardDoc(String path, UserThread user) throws IOException {
		for (Map.Entry<String,UserThread> entry : clientList.entrySet()) 
			if (entry.getValue().equals(user))
			{
				user.sendDoc(path);
				return;
			}
		
	}*/
}
