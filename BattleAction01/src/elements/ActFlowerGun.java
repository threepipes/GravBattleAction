package elements;

import java.util.ArrayList;

import main.KeyWords;

public class ActFlowerGun extends Action{
	int maxnum;
	public ActFlowerGun(int priority, ActiveElement parent, int num) {
		super(priority, parent, null, null);
		name = KeyWords.FLOWERGUN;
		this.maxnum = num;
	}
	
	@Override
	public boolean action() {
		if(parent.attackCols == null){
			parent.attackCols = new ArrayList<AttackCollision>();
		}
		if(parent.attackCols.size() < maxnum){
			parent.attackCols.add(new FlowerBullet(parent.x, parent.y+parent.sizey/2-8, 1, 1, parent.dx, 0, parent.stage));
			return true;
		}
		return false;
		
	}
}
