package utils;
import javax.swing.JOptionPane;

public class ErrorCatching {
	public static void err01()
	{
		JOptionPane.showMessageDialog(null, "Something is wrong, please try again!", "error", JOptionPane.ERROR_MESSAGE);
	}
	public static void err02()
	{
		JOptionPane.showMessageDialog(null, "Please fill in all field!", "error", JOptionPane.ERROR_MESSAGE);
	}
	public static void err03()
	{
		JOptionPane.showMessageDialog(null, "Username not in supported format!", "error", JOptionPane.ERROR_MESSAGE);
	}
	public static void err04()
	{
		JOptionPane.showMessageDialog(null, "Password/Confirmation do not match!", "error", JOptionPane.ERROR_MESSAGE);
	}
	public static void err05()
	{
		JOptionPane.showMessageDialog(null, "Account existed!", "error", JOptionPane.ERROR_MESSAGE);
	}
	public static void err06()
	{
		JOptionPane.showMessageDialog(null, "Account not existed!", "error", JOptionPane.ERROR_MESSAGE);
	}
	public static void err07()
	{
		JOptionPane.showMessageDialog(null, "You cannot interact with yourself!", "error", JOptionPane.ERROR_MESSAGE);
	}
	public static void err08()
	{
		JOptionPane.showMessageDialog(null, "User already in friend list!", "error", JOptionPane.ERROR_MESSAGE);
	}
	public static void err09()
	{
		JOptionPane.showMessageDialog(null, "User is not in your friend list!", "error", JOptionPane.ERROR_MESSAGE);
	}
	public static void err10()
	{
		JOptionPane.showMessageDialog(null, "User is already in your request list!", "error", JOptionPane.ERROR_MESSAGE);
	}
	public static void err11()
	{
		JOptionPane.showMessageDialog(null, "You already requested this person!", "error", JOptionPane.ERROR_MESSAGE);
	}
}
