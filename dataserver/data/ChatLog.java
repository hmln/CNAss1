package dataserver.data;

import java.io.IOException;
import java.util.*;

public class ChatLog {
	private int token;
	
	private Queue<String> messageList = new LinkedList<>();
	private final int maxMessage = 100;
	
	public ChatLog(int number) throws IOException
	{
		token = number;
		LogDB.createJson(this);
	}
	public void addMessage(String message) throws IOException
	{
		messageList.add(message);
		if (messageList.size() == maxMessage) messageList.remove();
		LogDB.createJson(this);
	}
	
	public int getID()
	{
		return token;
	}
	
	public Queue<String> getMessage()
	{
		return messageList;
	}
}
