package elements;

import main.KeyWords;

public class ActDash   extends Action{
	public ActDash(int priority, ActiveElement parent, int[][] mapr, int[][] mapl) {
		super(priority, parent, mapr, mapl);
		name = KeyWords.DASH;
	}
	
	@Override
	public boolean action() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u

//		if(parent.dx == -1){
			parent.ax = 0.5*parent.dx;
//		}
//		else if(parent.vx < parent.maxspeed) parent.vx += parent.ax;
		return true;
	}
}
