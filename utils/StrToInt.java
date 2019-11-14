package utils;

import java.math.BigInteger;

public class StrToInt {
	public static int toInt(String str)
	{
		StringBuilder sb = new StringBuilder();
		for (char c : str.toCharArray())
		    sb.append((int)c);

		BigInteger mInt = new BigInteger(sb.toString());
		return mInt.intValue();
	}
}
