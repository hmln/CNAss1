package ChatGUI;

import java.awt.EventQueue;

public class runChooseChatMode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						try {
							ChooseChatMode amy = new ChooseChatMode();
							amy.mainWindow.setVisible(true);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			);
		
		
	}

}
