package dataserver.data;

import java.io.IOException;
import java.util.*;

public class Account
{
	private String userName;
	private String password;
	private Set<String> friendlist = new HashSet<String>();
	private Set<String> requestlist = new HashSet<String>();
	private String ip;
	private int port;
	private boolean isOnline = false;
	
	/*public Account(String userName)
	{
		this.userName = userName;
	}*/
	
	public Account(String userName,String password)
	{
		this.userName = userName;
		this.password = password;
	}
	
	public String getName()
	{
		return userName;
	}
	
	public void setName(String name)
	{
		userName = name;
	}

	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public void addFriend(String friend) throws IOException
	{
		friendlist.add(friend);
		UserDB.createJson(this);
	}
	
	public void removeFriend(String username) throws IOException
	{
		friendlist.remove(username);
		UserDB.createJson(this);
	}
	public Set<String> getFriendList()
	{
		Set<String> newset = new TreeSet<>(friendlist);
		return newset;
	}
	public boolean isFriend(String user)
	{
		for (String entry: friendlist)
		{
			if (entry.equals(user)) return true;
		}
		return false;
	}
	public void addRequest(String request) throws IOException
	{
		requestlist.add(request);
		UserDB.createJson(this);
	}
	public void removeRequest(String username) throws IOException
	{
		requestlist.remove(username);
		UserDB.createJson(this);
	}
	public Set<String> getRequestList()
	{
		return requestlist;
	}
	public boolean inRequest(String user)
	{
		for (String entry: requestlist)
		{
			if (entry.equals(user)) return true;
		}
		return false;
	}
	
	public void updateIp(String newIp) throws IOException
	{
		this.ip = newIp;
		UserDB.createJson(this);
	}
	
	public void updatePort(int newPort) throws IOException
	{
		this.port = newPort;
		UserDB.createJson(this);
	}
	
	public String getIp()
	{
		return ip;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public void setOn()
	{
		isOnline = true;
	}
	public void setOff() throws IOException
	{
		isOnline = false;
		UserDB.createJson(this);
	}
	public boolean getStatus()
	{
		return isOnline;
	}
}

