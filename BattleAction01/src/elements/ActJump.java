package elements;

import main.KeyWords;

public class ActJump extends Action{
	public ActJump(int priority, ActiveElement parent, int[][] mapr, int[][] mapl) {
		super(priority, parent, mapr, mapl);
		// TODO 自動生成されたコンストラクター・スタブ
		name = KeyWords.JUMP;
	}
	
	@Override
	public boolean action() {

		if(parent.isGround()){
			parent.vy = -20;
			return true;
		}
		return false;
		
	}
}
