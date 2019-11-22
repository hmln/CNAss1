package dataserver.data;

import java.io.*;

import com.google.gson.Gson;

import dataserver.utils.MakePath;

public class LogDB {
	private static String LogDBpath  = MakePath.makedir("/src/dataserver/data/Log");
	public static void createJson(ChatLog log) throws IOException
	{
		Gson gson = new Gson();
		File Directory = new File(LogDBpath);
		if (!Directory.exists())
			Directory.mkdirs();
		Writer writer = new FileWriter(LogDBpath+"/"+log.getID()+".json");
		gson.toJson(log, writer);
		writer.flush();
		writer.close();
	}
	
	public static ChatLog getJson(int id) throws IOException
	{
		Gson gson = new Gson();
		File file = new File(LogDBpath+"/"+id+".json");
		if (!file.exists()) return null;
		ChatLog result = null;
		Reader reader = new FileReader(LogDBpath+"/"+id+".json");
		result = gson.fromJson(reader, ChatLog.class);
		reader.close();
		return result;
	}
	public static String getPath()
	{
		return LogDBpath;
	}
}
