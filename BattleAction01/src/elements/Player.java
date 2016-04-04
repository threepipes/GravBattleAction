package elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import main.KeyWords;
import main.MainPanel;
import scene.Map;

public class Player extends ActiveElement{
	private int offsetX = 0;
	private int offsetY = 0;	
	private int offsetXbg = 0;
	private int offsetYbg = 0;	
	private static int bgX = 1000;
	private static int bgY = 1000;
	private static int stageSizeX = 0;
	private static int stageSizeY = 0;
//	protected Bullet blt = null;
	protected boolean land = false;
//	protected boolean sit = false;
	protected boolean damaged = false;
	protected boolean visible = true;
	protected boolean oldflag = false;
	
	private List<String> weapon = new ArrayList<String>();
	private int weaponNo = 0;
	private HashMap<String, Integer> wpMap = new HashMap<String, Integer>();
	private double wp;
	private double wpRec = 0.05;
	private int wpMax = 100;
	
	protected static final int GRAV_DOWN = 0;
	protected static final int GRAV_UP = 1;
	protected static final int GRAV_RIGHT = 2;
	protected static final int GRAV_LEFT = 3;
//	private int gravDir = GRAV_DOWN;
	private double oldAX;
	private double oldAY;
//	private int oldDX;
//	private int oldDY;
	private int oldKey;
	
	protected static final int KEY_RIGHT = 1;
	protected static final int KEY_LEFT = 2;
	protected static final int KEY_UP = 4;
	protected static final int KEY_DOWN = 8;
	protected static final int KEY_ATTACK = 16;
	protected static final int KEY_WEAPONRIGHT = 32;
	protected static final int KEY_WEAPONLEFT = 64;
	protected static final int KEY_CHGRAV = 128;
	protected static final int KEY_JUMP = 256;
	
	protected int color = 0;
	protected int actmask = 0;

	private Point drawPoint = null;
	protected static final int Size = 24;
	
	public String Debug_Act;
	
	protected int[][][] actmap = {
			{{0,0,0},{0,0,0},{LOOP,1,0}},								//0 stand r
			{{0,0,0},{1,0,0},{LOOP,1,0}},								//1 stand l
			{{0,0,0},{1,1,0},{2,1,0},{3,1,0},{4,1,0},{LOOP,1,0}},				//2 accel r
			{{0,0,0},{1,2,0},{2,2,0},{3,2,0},{4,2,0},{LOOP,1,0}},				//3 accel l
			{{0,0,0},{4,1,0},{0,1,0},{1,1,0},{1,1,0},{2,1,0},{3,1,0},{LOOP,1,0}},	//4 dash r
			{{0,0,0},{4,2,0},{0,2,0},{1,2,0},{1,2,0},{2,2,0},{3,2,0},{LOOP,1,0}},	//5 dash l
			{{0,0,0},{0,3,0},{1,3,0},{LOOP,1,0}},							//6 Jump r
			{{0,0,0},{0,4,0},{1,4,0},{LOOP,1,0}},							//7 Jump l
			{{0,0,0},{2,3,0},{3,3,0},{4,3,2},{3,3,0},{END,1,0}},		//8 land r
			{{0,0,0},{2,4,0},{3,4,0},{4,4,2},{3,4,0},{END,1,0}},		//9 land l
			{{0,0,0},{2,3,0},{3,3,0},{4,3,0},{LOOP,3,0}},		//10 sit r
			{{0,0,0},{2,4,0},{3,4,0},{4,4,0},{LOOP,3,0}},		//11 sit l
			{{0,0,0},{1,3,0},{2,3,0},{2,0,3},{END,1,0}},		//12 damaged r
			{{0,0,0},{1,4,0},{2,4,0},{3,0,3},{END,1,0}},		//13 damaged l
			};
	
	public Player(double x, double y, Map stage, int color) {
		super(x, y, Size/2, Size, stage);
		name = KeyWords.PLAYER;
		sizex = Size/2;
		sizey = Size;
		this.maxspeed = 8;
		this.color = color;
		loadActions(/*filename*/);
		createWeaponSet();
		wp = wpMax;
		gravDir = GRAV_DOWN;
	}
	
	private void createWeaponSet(){
		weapon.add(KeyWords.GUN);
		wpMap.put(KeyWords.GUN, 3);
		weapon.add(KeyWords.FLOWERGUN);
		wpMap.put(KeyWords.FLOWERGUN, 30);
//		weapon.add(KeyWords.CHANGEGRAV);
		wpMap.put(KeyWords.CHANGEGRAV, 4);
	}
	
	public void setStageSize(Point p){
		stageSizeX = p.x;
		stageSizeY = p.y;
	}
	
	public void loadActions(/*filename*/){
		HashMap<String, Action> acts = new HashMap<String, Action>();
		loadAction(acts,new ActStand(0, this, actmap[0], actmap[1]));
		loadAction(acts,new ActWalk(1, this, actmap[2], actmap[3]));
		loadAction(acts,new ActDash(2, this, actmap[4], actmap[5]));
		loadAction(acts,new ActJump(3, this, actmap[6], actmap[7]));
		loadAction(acts,new ActGun(4, this, 3));
		loadAction(acts,new ActFlowerGun(4, this, 1));
		loadAction(acts,new ActChangeGravity(5, this));
		loadAction(acts,new ActSit(5, this, actmap[10], actmap[11], 12));
		loadAction(acts,new ActLand(6, this, actmap[8], actmap[9]));
		loadAction(acts,new ActDamage(7, this, actmap[12], actmap[13]));
		actions = new Actions(this, acts);
	}
	
	public void loadAction(HashMap<String, Action> acts, Action act){
		acts.put(act.getName(), act);
	}
	
	public void changeMap(Map stage){
		this.stage = stage;
	}
	
	
	public void action(String action){
		actions.reserveAction(action);
	}
	
	public void motionRequest(String act){
		actions.motionRequest(act);
	}
	
	public void clearAttackCols(){
		if(attackCols != null)attackCols.clear();
	}
	
	public boolean landed(){
		if(!oldflag && onGround && oldVY >= 21) return true;
		oldflag = onGround;
		return false;
	}// 1ループで1回しか呼び出してはいけない
	

	private void setOffset(){
		offsetX = (int) (x - MainPanel.Width/2);
		if(offsetX < 0) offsetX = 0;
		else if(offsetX > stageSizeX*Map.BLOCK_SIZE - MainPanel.Width - 12)
			offsetX = stageSizeX*Map.BLOCK_SIZE - MainPanel.Width - 12;
		if(stageSizeX*Map.BLOCK_SIZE < MainPanel.Width) offsetX = 0; 
		offsetY = (int) (y - MainPanel.Height/2);
		if(offsetY < 0) offsetY = 0;
		else if(offsetY > stageSizeY*Map.BLOCK_SIZE - MainPanel.Height - 12)
			offsetY = stageSizeY*Map.BLOCK_SIZE - MainPanel.Height - 12;
		if(stageSizeX*Map.BLOCK_SIZE < MainPanel.Width) offsetX = 0; 
	}
	
	private void setOffsetBG(){
		int offsetMaxX = stageSizeX*Map.BLOCK_SIZE - MainPanel.Width - 12;
		int offsetMaxY = stageSizeY*Map.BLOCK_SIZE - MainPanel.Height - 12;
		offsetXbg = -offsetX*(bgX-MainPanel.Width)/offsetMaxX;
		offsetYbg = -offsetY*(bgY-MainPanel.Height)/offsetMaxY;
	}
	
	public Point getOffset(){
		return new Point(offsetX, offsetY);
	}
	
	public Point getOffsetBG(){
		return new Point(offsetXbg, offsetYbg);
	}
	
	public void keyCheck(int keymask) {
		actmask &= keymask;
		oldKey = keymask;


		action(KeyWords.STAND);
		if((keymask & KEY_LEFT) > 0 && (keymask & KEY_RIGHT) == 0){
//			changeDir(-1);
			dx = -1;
			if(gravDir <= 1){
				if(getVX() < -5)action(KeyWords.DASH);	
				else action(KeyWords.WALK);
			}else{
				action(KeyWords.STAND);
			}
		}
		if((keymask & KEY_RIGHT) > 0){
//			changeDir(1);
			dx = 1;
			if(gravDir <= 1){
				if(getVX() > 5)action(KeyWords.DASH);
				else action(KeyWords.WALK);
			}else{
				action(KeyWords.STAND);
			}
		}
		if((keymask & KEY_UP) > 0 && (~actmask & KEY_UP) > 0){
			dy = -1;
//			action(KeyWords.JUMP);
			actmask |= KEY_UP;
		}else if((gravDir > 1) && ((keymask & KEY_UP) > 0)){
			if(getVY() > 5)action(KeyWords.DASH);
			else action(KeyWords.WALK);

		}
		if((keymask & KEY_JUMP) > 0 && (~actmask & KEY_JUMP) > 0){
			action(KeyWords.JUMP);
			actmask |= KEY_JUMP;
		}
		if((keymask & KEY_DOWN) > 0 && (~actmask & KEY_DOWN) > 0){
			dy = 1;
			if(gravDir > 1){
				if(getVY() > 5)action(KeyWords.DASH);
				else action(KeyWords.WALK);
			}
			actmask |= KEY_DOWN;
		}else if((gravDir > 1) && ((keymask & KEY_DOWN) > 0)){
			if(getVY() > 5)action(KeyWords.DASH);
			else action(KeyWords.WALK);
		}
		if((keymask & KEY_WEAPONRIGHT) > 0 && (~actmask & KEY_WEAPONRIGHT) > 0){
			changeWeapon(true);
			actmask |= KEY_WEAPONRIGHT;
		}
		if((keymask & KEY_WEAPONLEFT) > 0 && (~actmask & KEY_WEAPONLEFT) > 0){
			changeWeapon(false);
			actmask |= KEY_WEAPONLEFT;
		}
		if((keymask & KEY_CHGRAV) > 0 && (~actmask & KEY_CHGRAV) > 0){
			if(wp>0)action(KeyWords.CHANGEGRAV);
			actmask |= KEY_CHGRAV;
		}
		if((keymask & KEY_ATTACK) > 0 && (~actmask & KEY_ATTACK) > 0){
			if(wp>0)action(weapon.get(weaponNo));
			actmask |= KEY_ATTACK;
		}
		if((keymask & ~KEY_ATTACK & ~KEY_UP) == 0){
			action(KeyWords.STAND);
			if(getVX() != 0) motionRequest(KeyWords.WALK);
		}
		// player がattack のみならば，player は stand

		if(landed()){
			action(KeyWords.LAND);
		}else if(!isGround() && getVY() >= 4) 
			motionRequest(KeyWords.JUMP);
		if((keymask & KEY_DOWN) > 0){
			if(onGround)action(KeyWords.SIT);
		}
		
	}
	
	public void changeGravity(boolean def){
		if(def){
			ax = oldAX;
			ay = oldAY;
//			dx = oldDX;
//			dy = oldDY;
		}else{
			oldAX = ax;
			oldAY = ay;
//			oldDX = dx;
//			oldDY = dy;
			if(gravDir == GRAV_UP){
				ay = -ay;
			}else if(gravDir == GRAV_RIGHT){
				ax = ay;
//				dx = -dy;
				ay = oldAX;
//				dy = oldDX;
			}else if(gravDir == GRAV_LEFT){
				ax = -ay;
//				dx = dy;
				ay = oldAX;
//				dy = -oldDX;
			}
		}
	}
	
	public void changeGravityDirection(){
//		if(0 <= dir && dir <= 3) gravDir = dir;
		if(onGround || (oldKey & KEY_UP) > 0){
			gravDir = 1;
		}else if((oldKey & KEY_DOWN) > 0){
			gravDir = 0;
		}else if((oldKey & KEY_RIGHT) > 0){
			gravDir = 2;
		}else if((oldKey & KEY_LEFT) > 0){
			gravDir = 3;
		}else{
			if(gravDir == 1){
				gravDir = 0;
			}else{
				gravDir = 1;
			}
		}
	}
	
	public int getGravDir(){
		return gravDir;
	}
	
	
	public void move(){
		// しゃがんで小さくなったサイズを戻す
		coly = 0;
		colys = 24;
		String act = actions.doAction();	Debug_Act = act;//TODO
		
		oldflag = onGround;
		changeGravity(false);
		super.move();
		changeGravity(true);
		if(hitBlock){
			gravDir = GRAV_DOWN;
		}
		if(!oldflag && onGround) land = true;

		setOffset();
		setOffsetBG();
		if(act != null && (act.equals(weapon.get(weaponNo)) || act.equals(KeyWords.CHANGEGRAV))){
			wp -= wpMap.get(act);
			if(wp <= -30) wp = -30;
		}
		if(wp<100){
			wp+=wpRec;
			if(wp>100) wp = 100;
		}
		if(wp<0){
			maxspeed = 5;
			if(vx > 5) vx = 5;
			if(vx < -5) vx = -5;
		}
		if(wp>0)maxspeed = 8;
		if(maxlife/2<life) wpRec = 0.1;
		if(maxlife/4<life && life <= maxlife/2) wpRec = 0.2;
		if(life <= maxlife/4) wpRec = 0.5;
		
		drawPoint = actions.getDrawPoint();
	}
	
	
	public void damage(int val, boolean isLeft){
		if(!nodamage){
			life -= val;
			nodamage = true;
			vy = -10;
			onGround = false;
			vx -= isLeft ? 10 : -10;
			TimerTask task = new FlashTask();
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(task, 0, 200);
			action(KeyWords.DAMAGE);
		}
	}
	
	public int getLife(){
		return life;
	}
	
	public void changeWeapon(boolean right){
		if(right) weaponNo++;
		else weaponNo--;
		if(weaponNo>=weapon.size()) weaponNo = 0;
		if(weaponNo<0) weaponNo = weapon.size()-1;
	}
	
	public void drawUIs(Graphics g, int drawx, int drawy){
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLACK);
		g2.fillRect(drawx, drawy, 450, 30);
		g2.setColor(Color.WHITE);
		g2.drawString("HP: ", drawx+5, drawy+20);
		g2.drawString("WP: ", drawx+190, drawy+20);
		g2.drawString(weapon.get(weaponNo), drawx+380, drawy+20);
		g2.setColor(Color.ORANGE);
		if(life>=0)g2.fillRect(drawx+40, drawy+10, 125*life/maxlife, 10);
		if(wp>0)g2.fillRect(drawx+190+40, drawy+10, (int)(125*wp/wpMax), 10);
		if(wp<0){
			g2.setColor(Color.CYAN);
			g2.fillRect(drawx+190+40, drawy+10, (int)(125*(-wp)/wpMax), 10);
			g2.setColor(Color.ORANGE);
		}
		g2.setStroke(new BasicStroke(4));
		g2.drawRect(drawx+35, drawy+5, 135, 20);
		g2.drawRect(drawx+190+35, drawy+5, 135, 20);
		g2.setStroke(new BasicStroke(1));
	}
	
	
	public void draw(Graphics g, int offsetX, int offsetY){

		g.setColor(Color.BLACK);
		super.draw(g, offsetX, offsetY);
		if(image == null)g.drawRect((int)x-offsetX, (int)y-offsetY, Size/2-1, Size-1);
		else{
			try{
				if(visible)g.drawImage(image, (int)x-offsetX, (int)y-offsetY,
						(int)x-offsetX+sizex, (int)y-offsetY+sizey,
						drawPoint.x*sizex+color, drawPoint.y*sizey,
						drawPoint.x*sizex+sizex-1+color, drawPoint.y*sizey+sizey-1
						,null);
			}catch(ArrayIndexOutOfBoundsException e){
				System.err.println("iact:"+iact+"; icount:"+icount);
			}
			
		}
		
		actions.update();
	}
	
	public class FlashTask extends TimerTask{
		private int count = 10;

		@Override
		public void run() {
			if(count < 10){
				damaged = false;
				visible = !visible;
			}
			
			if(count <= 0){
				nodamage = false;
				visible = true;
				cancel();
			}
			count--;
		}

	}

}
