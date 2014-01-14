package elements;

import java.awt.Graphics;

import scene.Map;

public class SubBullet extends AttackCollision{
	double oldX;
	double oldY;
	public SubBullet(double x, double y, double dx, double dy, Map stage) {
		super(x, y, 2, 2, stage);// TODO 自動生成されたコンストラクター・スタブ
		this.vx = dx*15;
		this.vy = dy*10 - 8;
		oldX = x;
		oldY = y;
		ay = 2;
	}
	
	@Override
	public void move() {
		// TODO 自動生成されたメソッド・スタブ
		if(vy+ay > 20) ay = 0;
		oldX = x;
		oldY = y;
		super.move();
		if(checkHitBlock()){
			isAlive = false;
		}
	}
	
	@Override
	public void draw(Graphics g, int offsetX, int offsetY) {
		g.drawLine((int)x-offsetX, (int)y-offsetY, (int)oldX-offsetX, (int)oldY-offsetY);
	}
}
