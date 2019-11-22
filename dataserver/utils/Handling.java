package dataserver.utils;

import java.io.IOException;

public class Handling {
	public static String handling(String str) throws IOException
	{
		if (str.isEmpty()) return "";
		String checker = str.substring(0,2);
		String rest = str.substring(2);
		if (checker.equals("su"))
			return Checker.checkSignUp(rest);
		else if (checker.equals("si"))
			return Checker.checkSignIn(rest);
		else if (checker.equals("so"))
			return Checker.checkSignOut(rest);
		else if (checker.equals("af"))
			return Checker.checkAddFriend(rest);
		else if (checker.equals("rf"))
			return Checker.checkRemoveFriend(rest);
		else if (checker.equals("ar"))
			return Checker.checkAddRequest(rest);
		else if (checker.equals("rr"))
			return Checker.checkRemoveRequest(rest);
		else if (checker.equals("fo"))
			return Checker.checkFriendOnline(rest);
		else if (checker.equals("sl"))
			return Checker.checkSendLog(rest);
		else if (checker.equals("rs"))
			return Checker.checkSocketInfo(rest);
		else if (checker.equals("uf"))
			return Checker.checkUpdateFriend(rest);
		else if (checker.equals("ur"))
			return Checker.checkUpdateRequest(rest);
		else if (checker.equals("ul"))
			return Checker.checkUpdateLog(rest);
		else return "err01";
	}
}
