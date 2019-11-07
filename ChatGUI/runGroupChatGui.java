package ChatGUI;

import java.awt.EventQueue;

public class runGroupChatGui {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * Launch the application.
		 */
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupChatGui window = new GroupChatGui();
					window.frameMain.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

