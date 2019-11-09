package data;

import java.io.IOException;
import java.util.*;

public class ChatLog {
	public int currentID = 0;
	public static int nextID = 1;
	Account user1 = null;
	Account user2 = null;
	String server = null;
	
	private Queue<String> messageList = new LinkedList<>();
	private final int maxMessage = 1000;
	
	public ChatLog(Account acc1, Account acc2) throws IOException
	{
		user1 = acc1;
		user2 = acc2;
		currentID = nextID;
		nextID++;
		LogDB.createJson(this);
	}
	public ChatLog(String server) throws IOException
	{
		this.server = server;
		currentID = nextID;
		nextID++;
		LogDB.createJson(this);
	}
	public void addMessage(String message)
	{
		messageList.add(message);
		if (messageList.size() == maxMessage) messageList.remove();
	}
	public String removeMessage()
	{
		if (!messageList.isEmpty()) return messageList.remove();
		else return "";
	}
	
	public int getID()
	{
		return currentID;
	}
}
