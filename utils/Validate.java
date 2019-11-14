package utils;

import java.awt.HeadlessException;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JOptionPane;

import ChatGUI.Login;
import data.Account;
import data.UserDB;

public class Validate {
	public static String currentUser = "";
	public static boolean validateUsername(String username)
	{
		return (username.matches("[A-Za-z0-9_]+"));
	}
	public static boolean validatePassword(char[] password, char[] repassword)
	{
		return Arrays.equals(password, repassword);
	}
	public static boolean validateRegis(String username, char[] password, char[] repassword) throws Exception
	{
		if (username.equals("") || password.length == 0 || repassword.length == 0)
			JOptionPane.showMessageDialog(null, "Input all field lol", "Lmao", JOptionPane.ERROR_MESSAGE);
		else if (!validateUsername(username))
			JOptionPane.showMessageDialog(null, "Account not in format", "Sorry", JOptionPane.ERROR_MESSAGE);
		else if (!validatePassword(password,repassword))
			JOptionPane.showMessageDialog(null, "Confirm failed", "Lol", JOptionPane.ERROR_MESSAGE);
		else if (!validateAccount(username))
			JOptionPane.showMessageDialog(null, "Too bad account existed", "Rip", JOptionPane.ERROR_MESSAGE);
		else return true;
		return false;
	}
	public static boolean validateLogin(String username, char[] password) throws HeadlessException, IOException
	{
		if (username.equals("") || password.length == 0)
			JOptionPane.showMessageDialog(null, "Input all field lol", "Lmao", JOptionPane.ERROR_MESSAGE);
		else if (!validateAccount(username,password))
			JOptionPane.showMessageDialog(null, "Nice try", "Hacker!", JOptionPane.ERROR_MESSAGE);
		else return true;
		return false;
	}
	public static boolean validateAccount(String username, char[] password) throws IOException
	{
		Account result = UserDB.getJson(username);
		if (result != null && validatePassword(result.getPassword(),password)) 
		{
			Login.currentUser = result;
			return true;
		}
		else return false;
	}
	public static boolean validateAccount(String username) throws IOException
	{
		return UserDB.getJson(username) == null;
	}
	public static void validateAddFriend(Account myAccount, String friend)
	{
		try {
			//dont add yourself
			if (friend.equals(myAccount.getName())) JOptionPane.showMessageDialog(null, "Why add yourself duh", "Lmao", JOptionPane.ERROR_MESSAGE);
			else
			{
				Account friendAccount = UserDB.getJson(friend);
				// invalid account
				if (friendAccount == null) JOptionPane.showMessageDialog(null, "Not that name, friend", "Nice", JOptionPane.ERROR_MESSAGE);
				//account is already friend
				else if (myAccount.isFriend(friendAccount.getName())) JOptionPane.showMessageDialog(null, "Why add me again?", "Haha", JOptionPane.ERROR_MESSAGE);
				//account is in request
				else if (myAccount.inRequest(friendAccount.getName())) JOptionPane.showMessageDialog(null, "Accept me dude!", "...", JOptionPane.ERROR_MESSAGE);
				//already requested
				else if (friendAccount.inRequest(myAccount.getName())) JOptionPane.showMessageDialog(null, "Wait for my response lol", "gg", JOptionPane.INFORMATION_MESSAGE);
				//else
				else
				{
					friendAccount.addRequest(myAccount.getName());
					JOptionPane.showMessageDialog(null, "Request success!", "OK", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static void validateRemoveFriend(Account myAccount, String friend)
	{
		try {
			//dont remove yourself
			if (friend.equals(myAccount.getName())) JOptionPane.showMessageDialog(null, "Why remove yourself duh", "Lmao", JOptionPane.ERROR_MESSAGE);
			else 
			{
				Account friendAccount = UserDB.getJson(friend); 
				//account not exist
				if (friendAccount == null) JOptionPane.showMessageDialog(null, "Not that name, friend", "Stranger", JOptionPane.ERROR_MESSAGE);
				//not your friend
				else if (!myAccount.isFriend(friendAccount.getName())) JOptionPane.showMessageDialog(null, "Why remove a stranger?", "...", JOptionPane.ERROR_MESSAGE);
				//else
				else
				{
					friendAccount.removeFriend(myAccount.getName());
					myAccount.removeFriend(friendAccount.getName());
					JOptionPane.showMessageDialog(null, "Remove success!", "OK", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
