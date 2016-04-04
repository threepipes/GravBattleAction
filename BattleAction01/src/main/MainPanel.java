package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements KeyListener, Runnable{
//	private static final boolean DEBUG = true;
	
	public static final int Width = 800;
	public static final int Height = 370;
	
	private static final int KEY_RIGHT = 1;
	private static final int KEY_LEFT = 2;
	private static final int KEY_UP = 4;
	private static final int KEY_DOWN = 8;
	private static final int KEY_ATTACK = 16;
	private static final int KEY_WEAPONRIGHT = 32;
	private static final int KEY_WEAPONLEFT = 64;
	private static final int KEY_CHGRAV = 128;
	protected static final int KEY_JUMP = 256;
	
	
	private static final int Player1_Left = KeyEvent.VK_LEFT;
	private static final int Player1_RIGHT = KeyEvent.VK_RIGHT;
	private static final int Player1_UP = KeyEvent.VK_UP;
	private static final int Player1_DOWN = KeyEvent.VK_DOWN;
	private static final int Player1_ATTACK = KeyEvent.VK_V;
	private static final int Player1_WEAPONRIGHT = KeyEvent.VK_N;
	private static final int Player1_WEAPONLEFT = KeyEvent.VK_B;
	private static final int Player1_CHGRAV = KeyEvent.VK_C;
	private static final int Player1_JUMP = KeyEvent.VK_SPACE;
	
	private static final int Player2_LEFT = KeyEvent.VK_W;
	private static final int Player2_RIGHT = KeyEvent.VK_R;
	private static final int Player2_UP = KeyEvent.VK_3;
	private static final int Player2_DOWN = KeyEvent.VK_E;
	private static final int Player2_ATTACK = KeyEvent.VK_A;
	private static final int Player2_WEAPONRIGHT = KeyEvent.VK_5;
	private static final int Player2_WEAPONLEFT = KeyEvent.VK_4;
	private static final int Player2_CHGRAV = KeyEvent.VK_T;
	private static final int Player2_JUMP = KeyEvent.VK_6;

	private int[] keymask= new int[2];
	
	private Scene nowScene;
	private JPanel panel1;
	private JPanel panel2;

	public MainPanel() {
		nowScene = new SMainGame();
		panel1 = ((SMainGame)nowScene).getPanel(1);
		panel2 = ((SMainGame)nowScene).getPanel(2);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(panel1);
		this.add(panel2);
		panel1.repaint();
		panel2.repaint();
		
		setPreferredSize(new Dimension(Width, Height*2));
		setFocusable(true);
		addKeyListener(this);
		
		// Threadの開始は一番最後
		Thread anime = new Thread(this);
		anime.start();
	}
	
//	public void paintComponent(Graphics g){
//		super.paintComponent(g);
//		nowScene.draw(g);
//		
////		if(DEBUG){
////			g.setColor(Color.BLACK);
////			g.drawString("px:"+(int)player.getX()+"; py:"+(int)player.getY()
////					+"; (xtile):"+(int)player.getX()/Map.BLOCK_SIZE+"; (ytile):"+(int)player.getY()/Map.BLOCK_SIZE, 40, 20);
////			g.drawString("ox:"+offsetX+"; oy:"+offsetY+"; Life:"+player.getLife()+"; mapNo:"+mapNo, 40, 40);
////			g.drawString("keymask:"+Integer.toBinaryString(keymask)+"; actmask:"+Integer.toBinaryString(actmask), 40, 60);
////		}
//	}
	
	public void update(){
		nowScene.keyCheck(keymask);
		nowScene.update();
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case Player1_Left:
			keymask[0]|= KEY_LEFT;
			break;
		case Player1_RIGHT:
			keymask[0]|= KEY_RIGHT;
			break;
		case Player1_UP:
			keymask[0]|= KEY_UP;
			break;
		case Player1_DOWN:
			keymask[0]|= KEY_DOWN;
			break;
		case Player1_ATTACK:
			keymask[0]|= KEY_ATTACK;
			break;
		case Player1_WEAPONRIGHT:
			keymask[0]|= KEY_WEAPONRIGHT;
			break;
		case Player1_WEAPONLEFT:
			keymask[0]|= KEY_WEAPONLEFT;
			break;
		case Player1_CHGRAV:
			keymask[0]|= KEY_CHGRAV;
			break;
		case Player1_JUMP:
			keymask[0]|= KEY_JUMP;
			break;
		case Player2_LEFT:
			keymask[1] |= KEY_LEFT;
			break;
		case Player2_RIGHT:
			keymask[1] |= KEY_RIGHT;
			break;
		case Player2_UP:
			keymask[1] |= KEY_UP;
			break;
		case Player2_DOWN:
			keymask[1] |= KEY_DOWN;
			break;
		case Player2_ATTACK:
			keymask[1] |= KEY_ATTACK;
			break;
		case Player2_WEAPONRIGHT:
			keymask[1]|= KEY_WEAPONRIGHT;
			break;
		case Player2_WEAPONLEFT:
			keymask[1]|= KEY_WEAPONLEFT;
			break;
		case Player2_CHGRAV:
			keymask[1]|= KEY_CHGRAV;
			break;
		case Player2_JUMP:
			keymask[1]|= KEY_JUMP;
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case Player1_Left:
			keymask[0]&= ~KEY_LEFT;
			break;
		case Player1_RIGHT:
			keymask[0]&= ~KEY_RIGHT;
			break;
		case Player1_UP:
			keymask[0]&= ~KEY_UP;
			break;
		case Player1_DOWN:
			keymask[0]&= ~KEY_DOWN;
			break;
		case Player1_ATTACK:
			keymask[0]&= ~KEY_ATTACK;
			break;
		case Player1_WEAPONRIGHT:
			keymask[0]&= ~KEY_WEAPONRIGHT;
			break;
		case Player1_WEAPONLEFT:
			keymask[0]&= ~KEY_WEAPONLEFT;
			break;
		case Player1_CHGRAV:
			keymask[0]&= ~KEY_CHGRAV;
			break;
		case Player1_JUMP:
			keymask[0]&= ~KEY_JUMP;
			break;
		case Player2_LEFT:
			keymask[1] &= ~KEY_LEFT;
			break;
		case Player2_RIGHT:
			keymask[1] &= ~KEY_RIGHT;
			break;
		case Player2_UP:
			keymask[1] &= ~KEY_UP;
			break;
		case Player2_DOWN:
			keymask[1] &= ~KEY_DOWN;
			break;
		case Player2_ATTACK:
			keymask[1] &= ~KEY_ATTACK;
			break;
		case Player2_WEAPONRIGHT:
			keymask[1]&= ~KEY_WEAPONRIGHT;
			break;
		case Player2_WEAPONLEFT:
			keymask[1]&= ~KEY_WEAPONLEFT;
			break;
		case Player2_CHGRAV:
			keymask[1]&= ~KEY_CHGRAV;
			break;
		case Player2_JUMP:
			keymask[1]&= ~KEY_JUMP;
			break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	@Override
	public void run() {
		while(true){
			update();
//			repaint();
			nowScene.draw(null);
			
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

}
