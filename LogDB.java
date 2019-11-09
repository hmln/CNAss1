package data;

import java.io.*;
import com.google.gson.Gson;

public class LogDB {
	//get absolute path of file as string, then cast to file and get parent folder, then cast to string again
		private static String LogDBpath = new File(new File("UserDB.java").getAbsolutePath()).getParent().toString().concat("/src/Log");
		
		//make a json file base on Chatlog 
		public static void createJson(ChatLog log) throws IOException
		{
			Gson gson = new Gson();
			Writer writer = new FileWriter(LogDBpath+"/"+log.getID()+".json");
			gson.toJson(log, writer);
			writer.flush();
			writer.close();
		}
		
		//get an account base on id
		public static ChatLog getJson(int id) throws IOException
		{
			Gson gson = new Gson();
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
