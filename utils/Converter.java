package utils;

import java.math.BigInteger;

public class Converter {
	public static int toInt(String str)
	{
		StringBuilder sb = new StringBuilder();
		for (char c : str.toCharArray())
		    sb.append((int)c);

		BigInteger mInt = new BigInteger(sb.toString());
		return mInt.intValue();
	}
	public static String toString(char[] arr)
	{
		StringBuilder sb = new StringBuilder();
		for (char c : arr)
		    sb.append((char)c);
		return sb.toString();
	}
	public static StringBuilder build(byte[] a) 
    { 
        if (a == null) 
            return null; 
        StringBuilder ret = new StringBuilder(); 
        int i = 0; 
        while (a[i] != 0) 
        { 
            ret.append((char) a[i]); 
            i++; 
        } 
        return ret; 
    }
}
