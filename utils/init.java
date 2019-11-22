package utils;

import java.io.IOException;

import dataserver.server.DataServer;
import gui.Login;

public class init {

	public static void main(String[] args ) throws IOException {
		Thread server = new Thread(){
			public void run()
			{
				try {
					//p2p side
					Login login = new Login();
					login.loginWindow.setVisible(true);
					//data server side
					//new DataServer(6789);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		};
		server.start();
	}
}
