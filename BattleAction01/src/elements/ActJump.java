package elements;

import main.KeyWords;

public class ActJump extends Action{
	public ActJump(int priority, ActiveElement parent, int[][] mapr, int[][] mapl) {
		super(priority, parent, mapr, mapl);
		// TODO �����������ꂽ�R���X�g���N�^�[�E�X�^�u
		name = KeyWords.JUMP;
	}
	
	@Override
	public boolean action() {

		if(parent.isGround()){
			parent.vy = -24;
			return true;
		}
		return false;
		
	}
}
