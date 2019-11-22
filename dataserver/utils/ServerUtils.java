package dataserver.utils;

import java.math.BigInteger;
import java.util.*;

public class ServerUtils {
	//convert String to its integer value, use for making log
	public static int toInt(String str)
	{
		StringBuilder sb = new StringBuilder();
		for (char c : str.toCharArray())
		    sb.append((int)c);

		BigInteger mInt = new BigInteger(sb.toString());
		return mInt.intValue();
	}
	
	//build a String from bytes
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
	
	//build a String from queue, avoid using toString as told
	public static String toString (Queue<String> q) 
    { 
		StringBuilder result = new StringBuilder();
	    for(Object item:q) {
	        result.append(item.toString());
	        result.append("`"); //optional
	    }
	    String result2 = result.toString();
		return result2.substring(0,result2.length() - 1);
    }
}
