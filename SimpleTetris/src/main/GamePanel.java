package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	// 設定視窗的長寬
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	final int FPS = 60;
	Thread gameThread; // 設定遊戲的時間緒，運行本遊戲
	PlayManager pm;
	public static Sound music = new Sound(); // 音樂
	public static Sound se = new Sound();    // 音效
	
	public GamePanel() {
		
		// 視窗外觀的設置
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(Color.black);
		this.setLayout(null);
		// Implement KeyListener
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
		
		pm = new PlayManager();
		
	}
	public void launchGame() { 
		gameThread = new Thread(this);
		gameThread.start(); // 當啟動時,thread會去調用run()方法
		
		music.play(0,true);
		music.loop();
	}

	@Override
	public void run() {
		// Game Loop 創建一個遊戲的循環
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();// 每秒都會重新繪製
				delta--;
			}	
		}
	}
	public void update() {	// 任何你的訊息(如分數)都會在update更新
		
		if(KeyHandler.pausePressed == false && pm.gameOver == false) {
			pm.update();
		}
	}
	public void paintComponent(Graphics g) { // 繪製
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		pm.draw(g2);
	}
	

}
