package dataserver.utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import dataserver.data.Account;
import dataserver.data.ChatLog;
import dataserver.data.LogDB;
import dataserver.data.UserDB;

public class Checker {
	//err1: Something wrong
	//err2: Empty field
	//err3: Username not match type
	//err4: Password do not match
	//err5: Account existed
	//err6: Account not existed
	//err7: Add/Remove with yourself
	//err8: Already friend
	//err9: Not friend
	//err10: In request 
	//err11: At your request

	private static final String separator = ":";
	public static String checkSignUp(String input)
	{
		//data 0: username
		//data 1: password
		//data 2: repassword
		try {
			String[] data = input.split(separator,3);
			if (data[0].equals("") || data[1].equals("") || data[2].equals("")) return "err02";
			if (!checkUsername(data[0])) return "err03";
			if (!checkPassword(data[1], data[2])) return "err04";
			Account temp = UserDB.getJson(data[0]);
			if (temp != null) return "err05";
			temp = new Account(data[0],data[1]);
			UserDB.createJson(temp);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "err01";
		}
	}
	
	public static String checkSignIn(String input)
	{
		//data 0: Ip
		//data 1: port
		//data 2: username
		//data 3: password
		try {
			String[] data = input.split(separator,4);
			if (data[2].equals("") || data[3].equals("")) return "err02";
			if (!checkUsername(data[2])) return "err03";
			Account temp = UserDB.getJson(data[2]);
			if (temp == null) return "err06";
			if (!temp.getPassword().equals(data[3])) return "err04";
			temp.setOn();
			temp.updateIp(data[0]);
			temp.updatePort(Integer.parseInt(data[1]));
			UserDB.createJson(temp);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "err01";
		}
	}
	public static String checkSignOut(String input) throws IOException
	{
		Account user = UserDB.getJson(input);
		user.setOff();
		return "success";
	}
	public static String checkAddFriend(String input)
	{
		try {
			int colon = input.indexOf(':');
			String user = input.substring(0,colon);
			String friend = input.substring(colon + 1);
			Account userAccount = UserDB.getJson(user);
			Account friendAccount = UserDB.getJson(friend);
			userAccount.addFriend(friend);
			friendAccount.addFriend(user);
			userAccount.removeRequest(friend);
			return "success";
		} catch (Exception e) {
			return "err01";
		}
	}
	
	public static String checkRemoveFriend(String input)
	{
		//data 0: your account
		//data 1: your wanted-to-remove account
		try {
			String[] data = input.split(separator,2);
			if (!checkUsername(data[1])) return "err03";
			if (data[0].equals(data[1])) return "err07";
			Account userAccount = UserDB.getJson(data[0]);
			Account friendAccount = UserDB.getJson(data[1]);
			userAccount.removeRequest(data[1]);
			if (friendAccount == null) return "err06";
			if (!userAccount.isFriend(data[1])) return "err09";
			userAccount.removeFriend(data[1]);
			friendAccount.removeFriend(data[0]);
			return "success";
		} catch (Exception e) {
			return "err01";
		}
	}
	
	public static String checkAddRequest(String input)
	{
		//data 0: your account
		//data 1: your wanted-to-add account
		try {
			String[] data = input.split(separator,2);
			if (!checkUsername(data[1])) return "err03";
			if (data[0].equals(data[1])) return "err07";
			Account userAccount = UserDB.getJson(data[0]);
			Account friendAccount = UserDB.getJson(data[1]);
			if (friendAccount == null) return "err06";
			if (userAccount.isFriend(data[1])) return "err08";
			if (friendAccount.inRequest(data[0])) return "err10";
			if (userAccount.inRequest(data[1])) return "err11";
			friendAccount.addRequest(data[0]);
			return "success";
		} catch (Exception e) {
			return "err01";
		}
	}
	
	public static String checkRemoveRequest(String input)
	{
		try {
			int colon = input.indexOf(':');
			String user = input.substring(0,colon);
			String friend = input.substring(colon + 1);
			Account userAccount = UserDB.getJson(user);
			userAccount.removeRequest(friend);
			return "success";
		} catch (Exception e) {
			return "err01";
		}
	}
	
	public static String checkFriendOnline(String input) throws IOException
	{
		Account friendAccount = UserDB.getJson(input);
		return "success" + String.valueOf(friendAccount.getStatus());
	}
	
	public static String checkSendLog(String input)
	{
		//data 0: user 1
		//data 1: user 2
		//data 2: message
		try {
			String[] data = input.split(separator,3);
			int LogID = ServerUtils.toInt(data[0]) + ServerUtils.toInt(data[1]);
			ChatLog log = LogDB.getJson(LogID);
			if (log == null) log = new ChatLog(LogID);
			log.addMessage(data[2]);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "err01";
		}	
	}
	
	public static String checkUpdateFriend(String input)
	{
		try
		{
			Account userAccount = UserDB.getJson(input);
			Set<String> friendList = userAccount.getFriendList();
			if (friendList.isEmpty()) return "success";
			Set<String> friendListActive = new HashSet<String>();
			for (String friend: friendList)
			{
				Account friendaccount = UserDB.getJson(friend);
				int status = friendaccount.getStatus()? 1 : 0;
				friendListActive.add(String.valueOf(status) + friend);
			}
			System.out.println(friendListActive);
			return "success" + friendListActive.toString().substring(1,friendListActive.toString().length() - 1).replaceAll("\\s", "");
		} catch (Exception e) {
			e.printStackTrace();
			return "err01";
		}	
	}
	public static String checkUpdateRequest(String input)
	{
		try
		{
		Account userAccount = UserDB.getJson(input);
		Set<String> requestList = userAccount.getRequestList();
		if (requestList.isEmpty()) return "success";
		return "success" + requestList.toString().substring(1,requestList.toString().length() - 1).replaceAll("\\s", "");
		} catch (Exception e) {
			e.printStackTrace();
			return "err01";
		}
	}
	public static String checkUpdateLog(String input) throws IOException 
	{
		//data 0: user 1
		//data 1: user 2
		try {
			String[] data = input.split(separator,2);
			int LogID = ServerUtils.toInt(data[0]) + ServerUtils.toInt(data[1]);
			ChatLog log = LogDB.getJson(LogID);
			if (log == null) log = new ChatLog(LogID);
			String result = ServerUtils.toString(log.getMessage());
			return "success" + result;
		} catch (Exception e) {
			e.printStackTrace();
			return "err01";
		}	
	}
	
	public static String checkSocketInfo(String input) throws IOException 
	{
		Account user = UserDB.getJson(input);
		return "success" + user.getIp() + separator + user.getPort();
	}
	
	public static boolean checkUsername(String username)
	{
		return (username.matches("[A-Za-z0-9_]+"));
	}
	public static boolean checkPassword(String password, String repassword)
	{
		return password.equals(repassword);
	}

	


}
