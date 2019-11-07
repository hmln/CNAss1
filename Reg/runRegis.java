package Reg;

import java.awt.EventQueue;

public class runRegis {
	public static void main(String[] args ) {
		EventQueue.invokeLater(
				new Runnable() {
					public void run() {
						try {
							Regis window = new Regis();
							window.regWindow.setVisible(true);
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			);
	}
}
