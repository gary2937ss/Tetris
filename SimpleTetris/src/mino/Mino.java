package mino;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;
import main.PlayManager;

public class Mino { // 所有方塊的父類別，其它子類別的功能將由這裡延伸
	
	public Block b[] = new Block[4];
	public Block tempB[] = new Block[4];
	int autoDropCounter = 0; // 每秒掉落一次
	public int direction = 1; // There are 4 directions (1/2/3/4) 方塊旋轉四個方向
	boolean leftCollision, rightCollision, bottomCollision; // 檢查方塊碰撞
	public boolean active = true; // 當方塊到達底部時，會停在底部，並於最上層產生新的方塊
	public boolean deactivating; // 當方塊到達底部時，能夠在底部自由滑動一小段間
	int deactivateCounter = 0;
	
	public void create(Color c) {
		b[0] = new Block(c);
		b[1] = new Block(c);
		b[2] = new Block(c);
		b[3] = new Block(c);
		tempB[0] = new Block(c);
		tempB[1] = new Block(c);
		tempB[2] = new Block(c);
		tempB[3] = new Block(c);

	}
	public void setXY(int x, int y) {}
	public void updateXY(int direction) {
		
		checkRotationCollision();
		// 如果沒有發生碰撞，可以旋轉方塊 ; 但如果發生碰撞方塊不會旋轉
		if(leftCollision == false && rightCollision == false && bottomCollision == false) {
			
			this.direction = direction;
			b[0].x= tempB[0].x;
			b[0].y= tempB[0].y;
			b[1].x= tempB[1].x;
			b[1].y= tempB[1].y;
			b[2].x= tempB[2].x;
			b[2].y= tempB[2].y;
			b[3].x= tempB[3].x;
			b[3].y= tempB[3].y;
		}

	}
	public void getDirection1() {}
	public void getDirection2() {}
	public void getDirection3() {}
	public void getDirection4() {}
	public void checkMovementCollision() { // 檢查方塊移動時的碰撞
		
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		
		// Check static block collision
		checkStaticBlockCollision();
		
		// Check frame collision 確認與Frame與的碰撞
		// Left wall 左邊的牆
		for(int i=0 ; i<b.length ; i++) {
			if(b[i].x == PlayManager.left_x) {
				leftCollision = true;
			}
		}
		// Right wall 右邊的牆
		for(int i=0 ; i<b.length ; i++) {
			if(b[i].x + Block.SIZE == PlayManager.right_x) {
				rightCollision = true;
			}
		}
		// Botton wall 最下層
		for(int i=0 ; i<b.length ; i++) {
			if(b[i].y + Block.SIZE == PlayManager.bottom_y) {
				bottomCollision = true;
			}
		}
	}
	public void checkRotationCollision() { // 檢查方塊旋轉時的碰撞
		
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		
		// Check static block collision
		checkStaticBlockCollision();
		
		// Check frame collision 確認與Frame與的碰撞
		// Left wall 左邊的牆
		for(int i=0 ; i<b.length ; i++) {
			if(tempB[i].x < PlayManager.left_x) {
				leftCollision = true;
			}
		}
		// Right wall 右邊的牆
		for(int i=0 ; i<b.length ; i++) {
			if(tempB[i].x + Block.SIZE > PlayManager.right_x) {
				rightCollision = true;
			}
		}
		// Botton wall 最下層
		for(int i=0 ; i<b.length ; i++) {
			if(tempB[i].y + Block.SIZE > PlayManager.bottom_y) {
				bottomCollision = true;
			}
		}
	}
	public void checkStaticBlockCollision() { // 確定方塊在下發所發生的碰撞
		
		for(int i = 0; i< PlayManager.staticBlocks.size(); i++) {
			
			int targetX = PlayManager.staticBlocks.get(i).x;
			int targetY = PlayManager.staticBlocks.get(i).y;
			
			// check down
			for(int j = 0; j < b.length; j++) {
				if(b[j].y + Block.SIZE == targetY && b[j].x == targetX) {
					bottomCollision = true;
				}
			}
			// check left
			for(int j = 0; j < b.length; j++) {
				if(b[j].x - Block.SIZE == targetX && b[j].y == targetY) {
					leftCollision = true;
				}
			}
			for(int j = 0; j < b.length; j++) {
				if(b[j].x + Block.SIZE == targetX && b[j].y == targetY) {
					rightCollision = true;
				}
			}
			
		}
		
	}
	
	public void update() {
		
		if(deactivating) {
			deactivating();
		}
		
		// Move the mino
		if(KeyHandler.upPressed) {
			switch(direction) { // 每當你按下W鍵的時候方塊都會旋轉
			case 1: getDirection2();break;
			case 2: getDirection3();break;
			case 3: getDirection4();break;
			case 4: getDirection1();break;
			}
			KeyHandler.upPressed = false;
			GamePanel.se.play(3, false);
		}
		
		checkMovementCollision();
		
		if(KeyHandler.downPressed) {
			// 假如方塊到最下層沒有碰撞，則不會再下降
			if(bottomCollision == false) {
				b[0].y += Block.SIZE;
				b[1].y += Block.SIZE;
				b[2].y += Block.SIZE;
				b[3].y += Block.SIZE;
				
				// When moved down, reset the autoDropCounter
				autoDropCounter = 0;
			}
			KeyHandler.downPressed =  false; //當按S的時候方塊會下降一格
		}
		if(KeyHandler.leftPressed) {
			if(leftCollision == false) {
				b[0].x -= Block.SIZE;
				b[1].x -= Block.SIZE;
				b[2].x -= Block.SIZE;
				b[3].x -= Block.SIZE;
			}	
			
			KeyHandler.leftPressed =  false;
	
		}
		if(KeyHandler.rightPressed) {
			if(rightCollision == false) {
				b[0].x += Block.SIZE;
				b[1].x += Block.SIZE;
				b[2].x += Block.SIZE;
				b[3].x += Block.SIZE;
			}
			
			KeyHandler.rightPressed =  false;
	
		}	
		if(bottomCollision) {
			if(deactivating == false) {
				GamePanel.se.play(4, false);
			}
			deactivating = true;
		}
		else {
			autoDropCounter ++; // the counter increases in every frame
			if(autoDropCounter == PlayManager.dropInterval) {
				// the mino goes down
				b[0].y += Block.SIZE;
				b[1].y += Block.SIZE;
				b[2].y += Block.SIZE;
				b[3].y += Block.SIZE;
				autoDropCounter = 0;
			}
		}
	}
	public void deactivating() {
		
		deactivateCounter++;
		
		// Wait 45 frames until diactivate 等待45幀
		if(deactivateCounter == 45) {
			
			deactivateCounter = 0;
			checkMovementCollision(); // check if the bottom is still hitting
			// 如果方塊到底部過個45楨的時間，就會無法移動並停留在底部
			// if the bottom is still hitting after 45 frames, deactivate the mino
			if(bottomCollision) {
				active = false;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int margin = 2; // 目的是為了讓方塊看起來有邊距間隔開來的
		
		g2.setColor(b[0].c); // 設置顏色
		g2.fillRect(b[0].x+margin, b[0].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
		g2.fillRect(b[1].x+margin, b[1].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
		g2.fillRect(b[2].x+margin, b[2].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
		g2.fillRect(b[3].x+margin, b[3].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
		
	}

}
