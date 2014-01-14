package main;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

import scene.Map;
import scene.MessageController;
import scene.MessageWindow;
import elements.Player;

public class SMainGame extends Scene{
	
//	private int offsetX = 0;
//	private int offsetY = 0;	
//	private int offsetXbg = 0;
//	private int offsetYbg = 0;	
//	private int bgX = 0;
//	private int bgY = 0;
	
	private boolean talking;

	private Player[] player = new Player[2];
	
	private DrawPanel panel1;
	private DrawPanel panel2;
	
	public final static int P1color = 0;
	public final static int P2color = 60;
	public final static int P3color = 120;
	public final static int P4color = 180;
	
	public static final int MAP_NUM = 3;
	private int mapNo = 1;
	private Map[] stage = new Map[MAP_NUM];	
//	private MessageWindow messageW;
//	private MessageController messageC;
//	private List<String[]> talkList;
	
	public SMainGame() {
		for(int i=0; i<MAP_NUM; i++)stage[i] = new Map();
		player[0] = new Player(100, 1500, stage[mapNo], 0);
		player[1] = new Player(1400, 1400, stage[mapNo], P2color);
		stage[mapNo].init("map0"+mapNo+".map","map_event0"+mapNo+".evt", player[0], player[1], 100, 1500);
		
		panel1 = new DrawPanel(1,stage[mapNo]);
		panel2 = new DrawPanel(2,stage[mapNo]);
		
		player[0].loadImage("hito.png");
		player[1].loadImage("hito.png");
		
//		messageW = new MessageWindow(20, MainPanel.Height-200, MainPanel.Width-40, 180, "gamefont.png");
//		messageC = new MessageController(messageW, "face.png");
//		Point bgs = stage[mapNo].getBGSize();
//		bgX = bgs.x;
//		bgY = bgs.y;
	}
	
	public JPanel getPanel(int i){
		if(i==1) return panel1;
		else return panel2;
	}
	
//
//	private void setOffset(){
//		offsetX = (int) (player.getX() - MainPanel.Width/2);
//		if(offsetX < 0) offsetX = 0;
//		else if(offsetX > stage[mapNo].getSizeTile().x*Map.BLOCK_SIZE - MainPanel.Width - 12)
//			offsetX = stage[mapNo].getSizeTile().x*Map.BLOCK_SIZE - MainPanel.Width - 12;
//		if(stage[mapNo].getSizeTile().x*Map.BLOCK_SIZE < MainPanel.Width) offsetX = 0; 
//		offsetY = (int) (player.getY() - MainPanel.Height/2);
//		if(offsetY < 0) offsetY = 0;
//		else if(offsetY > stage[mapNo].getSizeTile().y*Map.BLOCK_SIZE - MainPanel.Height - 12)
//			offsetY = stage[mapNo].getSizeTile().y*Map.BLOCK_SIZE - MainPanel.Height - 12;
//		if(stage[mapNo].getSizeTile().x*Map.BLOCK_SIZE < MainPanel.Width) offsetX = 0; 
//	}
//	
//	private void setOffsetBG(){
//		int offsetMaxX = stage[mapNo].getSizeTile().x*Map.BLOCK_SIZE - MainPanel.Width - 12;
//		int offsetMaxY = stage[mapNo].getSizeTile().y*Map.BLOCK_SIZE - MainPanel.Height - 12;
//		offsetXbg = -offsetX*(bgX-MainPanel.Width)/offsetMaxX;
//		offsetYbg = -offsetY*(bgY-MainPanel.Height)/offsetMaxY;
//	}
//	
//	private void checkEvent(){
//		//TODO
//		Event e = stage[mapNo].checkEvent();
//		if(e != null){
//			if(e instanceof MapEvent){
//				MapEvent me = (MapEvent)e;
//				
//				if(mapNo != me.toMap){
//					// player‚ª•Ç‚Ì’†‚És‚­ŠëŒ¯‚ª‚ ‚é(—vF–³“Gˆ—)
//					player.clearAttackCols();
//					stage[mapNo].destMap();
//					mapNo = me.toMap;
//					stage[mapNo].init("map0"+mapNo+".dat","map_event0"+mapNo+".evt", player
//							, me.toX*Map.BLOCK_SIZE, me.toY*Map.BLOCK_SIZE);
//					Point bgs = stage[mapNo].getBGSize();
//					bgX = bgs.x;
//					bgY = bgs.y;
//				}else{
//					player.moveTo(me.toX*Map.BLOCK_SIZE, me.toY*Map.BLOCK_SIZE);
//				}
//			}else if(e instanceof TalkEvent){
//				TalkEvent te = (TalkEvent) e;
//				
//				player.action(KeyWords.STAND);
//				talkList = te.getTalk();
//				
//				messageC.setTalkList(talkList);
//				talking = true;
//			}
//		}
//	}
	
	@Override
	public void update() {

		if(!talking){
//			setOffset();
//			setOffsetBG();
			stage[mapNo].update();
		}
		
	}

	
	@Override
	public void keyCheck(int[] keymask) {
		player[0].keyCheck(keymask[0]);
		player[1].keyCheck(keymask[1]);
	}
	
	@Override
	public void draw(Graphics g) {
		panel1.repaint();
		panel2.repaint();
//		Point offset = player.getOffset();
//		Point offsetBG = player.getOffsetBG();
//		stage[mapNo].draw(g, offset.x, offset.y, offsetBG.x, offsetBG.y);
		
//		messageC.draw(g, player.getY() < 600);
		
	}

}
