package p2pserver;

import java.io.*;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import gui.Login;
import utils.Converter;
import utils.ErrorCatching;
//Send and Receive data from data server
public class DataSocket {
	public static Socket socket;
    static String username = "";
    static String password = "";
    static boolean online = false;
    static byte[] buffer = new byte[65535];
    private static final String separator = ":";
    
    private InputStream input;
    private OutputStream output;
    public DataSocket(InetAddress serverIP, int serverPort) throws IOException
    {
    	DataSocket.socket = new Socket(serverIP, serverPort);
    	System.out.println("Connecting to: " + serverIP + " at port " + serverPort);
    	output = socket.getOutputStream();
    	input = socket.getInputStream();
    	socket.setSoTimeout(10000);
       
    }
	public String signIn(String username, String password) throws IOException {
		String result = "si" + Login.ownaddress + separator + Login.ownport + separator + username + separator + password;
        return tcpActive(result);
	}
	public String signUp(String username, String password, String repassword) throws IOException {
		String result = "su" + username + separator + password + separator + repassword;
        return tcpActive(result);
	}
	public String signOut(String username) throws IOException {
		String result = "so" + username;
		return tcpActive(result);
	}
	
	public String addFriend(String user1, String user2) throws IOException {
		String result = "af" + user1 + separator + user2;
        return tcpActive(result);
	}
	
	public String addRequest(String user1, String user2) throws IOException {
		String result = "ar" + user1 + separator + user2;
        return tcpActive(result);
	}
	
	public String removeFriend(String user1, String user2) throws IOException {
		String result = "rf" + user1 + separator + user2;
        return tcpActive(result);
	}
	
	public String removeRequest(String user1, String user2) throws IOException {
		String result = "rr" + user1 + separator + user2;
        return tcpActive(result);
	}
	
	public String updateFriend(String username) throws IOException {
		String result = "uf" + username;
        return tcpActive(result);
	}
	public String updateRequest(String username) throws IOException {
		String result = "ur" + username;
        return tcpActive(result);
	}
	public String updateLog(String user1, String user2) throws IOException {
		String result = "ul" + user1 + separator + user2;
        return tcpActive(result);
	}
	
	public String receiveSocketInfo(String username) throws IOException {
		String result = "rs" + username;
		return tcpActive(result);
	}
	
	public String sendLog(String user1, String user2, String message) throws IOException {
		String result = "sl" + user1 + separator + user2 + separator + message;
		return tcpActive(result);
	}
	
	public String receiveFriendStatus(String username) throws IOException {
		String result = "fo" + username;
		return tcpActive(result);
	}
	
	public String tcpActive(String message) throws IOException
	{
		System.out.println(message);
		buffer = message.getBytes();
        output.write(buffer);
        buffer = new byte[65535];
		while (true)
		{
			try {
				input.read(buffer);
				String received = Converter.build(buffer).toString();
				if (received != "")
				{
					if (received.charAt(0) == 'e')
					{ 
						try {
							Method method;
							method = (new ErrorCatching()).getClass().getMethod(received);
							method.invoke(null);
							return "";
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
					else return received;
				}
			} catch (SocketTimeoutException e) {
                // timeout exception.
                return "out";
            }
		}
	}
}
