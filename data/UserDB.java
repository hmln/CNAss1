package data;

import java.io.*;
import com.google.gson.Gson;

public class UserDB
{
	//get absolute path of file as string, then cast to file and get parent folder, then cast to string again
	private static String UserDBpath = new File(new File("UserDB.java").getAbsolutePath()).getParent().toString().concat("/UserData");
	
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
		if (!Directory.exists())
		{
			Directory.mkdirs();
			return null;
		}
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