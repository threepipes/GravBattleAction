package elements;

import main.KeyWords;

public class ActStand extends Action{
	public ActStand(int priority, ActiveElement parent, int[][] mapr, int[][] mapl) {
		super(priority, parent, mapr,mapl);
		// TODO 自動生成されたコンストラクター・スタブ
		name = KeyWords.STAND;
	}
	
	@Override
	public boolean action() {

		if(parent.vx > 0){
			parent.vx -= 0.25;
			if(parent.vx < 0) parent.vx = 0;
		}
		if(parent.vx < 0){
			parent.vx += 0.25;
			if(parent.vx > 0) parent.vx = 0;
		}
		parent.ax = 0;
//		parent.ax = -0.25*parent.dx;
//		if(parent.vx == 0) parent.ax = 0;
//		else if(Math.abs(parent.vx) < 1) parent.ax = -Math.abs(parent.vx);
		return true;
		
	}
}
