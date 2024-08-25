package main;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String arg[]) {
		// 製作一個視窗
		JFrame window = new JFrame("Simple Tetris");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 可以正確關閉
		window.setResizable(false);  // 視窗大小固定
		
		// Add GamePanel to the window
		GamePanel gp = new GamePanel();
		window.add(gp);
		window.pack();
		
		window.setLocationRelativeTo(null); // 設定位置在正中央
		window.setVisible(true); // 確保看得到視窗
		
		gp.launchGame(); // 調用遊戲開始
	}

}
