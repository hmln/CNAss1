package data;

import java.io.IOException;
import java.util.*;

public class Account
{
	private String userName;
	private char[] password;
	private Set<String> friendlist = new HashSet<String>();
	private Set<String> requestlist = new HashSet<String>();
	public Account(String userName)
	{
		this.userName = userName;
	}
	
	public Account(String userName,char[] password)
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

	public char[] getPassword()
	{
		return password;
	}
	
	public void setPassword(char[] password)
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
		//error value to null when removing friend
		friendlist.remove(username);
		UserDB.createJson(this);
	}
	public Set<String> getFriendList()
	{
		return friendlist;
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
}
