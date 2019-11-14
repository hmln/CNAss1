package utils;

import java.io.File;

public class MakePath {
	public static String dir = System.getProperty("user.dir");
	public static String makedir(String concatPath)
	{
		String result = dir.concat(concatPath);
		File file = new File(result);
		if (!file.exists())
		{
			file.mkdirs();
		}
		return result;
	}
}
