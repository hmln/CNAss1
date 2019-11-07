package Login;

import java.awt.EventQueue;

public class runLogin {
	public static void main(String[] args ) {
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
