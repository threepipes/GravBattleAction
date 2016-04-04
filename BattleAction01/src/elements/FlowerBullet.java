package elements;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import scene.Map;

public class FlowerBullet extends AttackCollision{
	private List<SubBullet> bullets = new ArrayList<SubBullet>();
	private boolean explode = false;
	public FlowerBullet(double x, double y, int sx, int sy, int dx, int dy, Map stage) {
		super(x, y, sx, sy, stage);// TODO 自動生成されたコンストラクター・スタブ
		this.vx = dx*10;
		this.vy = -13;
		ay = 1;
	}

	public void move(){
		double oldX = x;
		double oldY = y;
		super.move();
		if(checkHitBlock() || vy > 0){
			vx = 0;
			vy = 0;
			x = oldX;
			y = oldY;
			if(!explode) explosion();
			explode = true;
		}
		
		if(explode && bullets.size() == 0){
			isAlive = false;
		}
		
		if(explode){

			Iterator<SubBullet> it = bullets.iterator();
			while(it.hasNext()){
				SubBullet tmp = it.next();
				if(!tmp.getAlive()) it.remove();
				else tmp.move();
			}
		}
	}
	
	
	public boolean checkHit(Element ele){
		if(super.checkHit(ele)) return true;
		Iterator<SubBullet> it = bullets.iterator();
		while(it.hasNext()){
			if(it.next().checkHit(ele)) return true;
		}
		return false;
	}
	
	private void explosion(){
		Random rand = new Random();
		double subX = x+sizex/2;
		double subY = y+sizey/2;
		for(int i=0; i<sizex; i++){
			int radX = rand.nextInt(11) - 5;
			int radY = rand.nextInt(11) - 5;
			bullets.add(new SubBullet(subX, subY, (double)radX/10, -(double)radY/10, stage));
		}
	}
	
	@Override
	public void draw(Graphics g, int offsetX, int offsetY) {
		if(!explode)g.fillOval((int)x-offsetX, (int)y-offsetY, sizex++, sizey++);
		Iterator<SubBullet> it = bullets.iterator();
		while(it.hasNext()){
			it.next().draw(g, offsetX, offsetY);
		}
	}
	
}
