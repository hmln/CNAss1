package utils;

import java.awt.EventQueue;
import java.io.IOException;
import ChatGUI.Login;

public class init {

	public static void main(String[] args ) throws IOException {
		EventQueue.invokeLater(
			new Runnable() {
				public void run() {
					try {
						Login window = new Login();
						window.loginWindow.setVisible(true);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		);
	}
}
