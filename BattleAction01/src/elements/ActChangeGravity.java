package elements;

import main.KeyWords;

public class ActChangeGravity extends Action{
	public ActChangeGravity(int priority, ActiveElement parent) {
		super(priority, parent, null, null);
		// TODO 自動生成されたコンストラクター・スタブ
		name = KeyWords.CHANGEGRAV;
	}
	
	@Override
	public boolean action() {
		if(parent.getGravDir() == 0)parent.changeGravityDirection(1);
		else parent.changeGravityDirection(0);
		return true;
		
	}
}
