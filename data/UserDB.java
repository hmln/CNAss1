package data;

import java.io.*;
import com.google.gson.Gson;

import utils.MakePath;

public class UserDB
{
	private static String UserDBpath  = MakePath.makedir("/src/data/UserData");
	
	//make a json file base on user account
	public static void createJson(Account user) throws IOException
	{
		Gson gson = new Gson();
		Writer writer = new FileWriter(UserDBpath+"/"+user.getName()+".json");
		gson.toJson(user, writer);
		writer.flush();
		writer.close();
	}
	
	//get an account base on username
	public static Account getJson(String username) throws IOException
	{
		Gson gson = new Gson();
		File Directory = new File(UserDBpath);
		Account result = null;
		Reader reader = null;
		for (final File entry : Directory.listFiles())
		{
			reader = new FileReader(entry.getPath());
			result = gson.fromJson(reader, Account.class);
			if (result.getName().equals(username)) return result;
		}
		return null;
	}
	public static String getPath()
	{
		return UserDBpath;
	}
}