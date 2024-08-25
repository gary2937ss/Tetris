package mino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle{ //俄羅斯方塊本身是由四個方塊組成，這個類代表一個方塊
	
	public int x, y;
	public static final int SIZE = 30; // 30 x 30 的方塊
	public Color c;
	
	public Block(Color c) { // 因為每個方塊顏色都不一樣，所以設置這個
		this.c = c;
	}
	public void draw(Graphics2D g2) {
		int margine = 2;
		g2.setColor(c);
		g2.fillRect(x+margine, y+margine, SIZE-(margine*2), SIZE-(margine*2));
	}

}
