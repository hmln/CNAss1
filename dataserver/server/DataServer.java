package dataserver.server;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class DataServer {
	private volatile static Map<String,DataThread> clientList;
	public static ServerSocket server; 
	public DataServer(int port) throws IOException
	{
		clientList = new HashMap<String,DataThread>();
		server = new ServerSocket(port);
		System.out.println("Data server opened on: " + InetAddress.getLocalHost().getHostAddress() + " on port " + port);
		exe();
	}
	public void exe() throws IOException 
    { 
		while(true)
		{
			Socket socket = server.accept();
			
			DataThread newUser = new DataThread(socket, this);
			newUser.start();
			System.out.println("Server accepted user " +newUser.getName());
			addUser(newUser.getName(),newUser);
		}	
    } 
	void addUser(String userName, DataThread user)
	{
		clientList.put(userName, user);
	}
	void removeUser(String userName) 
	{
		clientList.remove(userName);
	}
}
