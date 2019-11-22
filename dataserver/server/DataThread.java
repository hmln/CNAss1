package dataserver.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import dataserver.data.Account;
import dataserver.data.UserDB;
import dataserver.utils.Handling;
import dataserver.utils.ServerUtils;

public class DataThread extends Thread{
	private Socket socket;
	private DataServer server;
	private OutputStream output;
	private String username = "";
	private static boolean usernameget;
	public DataThread(Socket socket, DataServer server) throws IOException 
	{
		usernameget = false;
        this.socket = socket;
        this.server = server;
        output = socket.getOutputStream();
    }
	
	public void run()
	{
		try
		{	
			InputStream input = socket.getInputStream();
			String message = "";
			while (true)
			{
				byte[] buffer = new byte[65535];
				input.read(buffer);
				message = ServerUtils.build(buffer).toString();
				System.out.println(message);
				String output = Handling.handling(message);
				if (message.substring(0,2).equals("si") && output.equals("success") && usernameget == false)
				{
					String[] data = message.split(":",4);
					username = data[2];
					usernameget = true;
				}
				sendBack(output);
			}
		} catch (IOException e)
		{
			System.out.println(this.getName() + " has quitted");
			server.removeUser(this.getName());
			try {
				Account usertosetoffline = UserDB.getJson(username);
				usertosetoffline.setOff();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	void sendBack(String message) throws IOException 
	{
        byte[] buffer= new byte[65535];
        buffer = message.getBytes();
        output.write(buffer);
    }
}
