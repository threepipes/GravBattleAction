package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import scene.Map;

public class DrawPanel extends JPanel{
	int pnum;
	Map map;
	public DrawPanel(int pnum, Map map) {
		this.pnum = pnum;
		this.map = map;
		this.setPreferredSize(new Dimension(600,300));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		map.draw(g, 0, 0, 0, 0, pnum-1);
//		g.drawString("Player"+pnum, 40, 40);
	}
}
