package data;

import java.io.IOException;
import java.util.*;

public class ChatLog {
	public int currentID = 0;
	public static int nextID = 1;
	
	private Queue<String> messageList = new LinkedList<>();
	private final int maxMessage = 1000;
	
	public ChatLog() throws IOException
	{
		currentID = nextID;
		nextID++;
		LogDB.createJson(this);
	}
	public void addMessage(String message) throws IOException
	{
		messageList.add(message);
		LogDB.createJson(this);
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
