package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

	public int FLOOR = 500;

	private Timer timer;
	private Player1 p1;
	private Player2 p2;
	private int boardWidth, boardHeight;

	private List<Integer> p1Actions;
	private List<Integer> p2Actions;

	public Board(int width, int height) {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		setDoubleBuffered(true);

		p1 = new Player1(width, height, FLOOR);
		p2 = new Player2(width, height, FLOOR);

		timer = new Timer(5, this);
		timer.start();
		boardWidth = width;
		boardHeight = height;

		p1Actions = new ArrayList<Integer>();
		p2Actions = new ArrayList<Integer>();

		p1Actions.add(KeyEvent.VK_LEFT);
		p1Actions.add(KeyEvent.VK_RIGHT);
		p1Actions.add(KeyEvent.VK_UP);
		p1Actions.add(KeyEvent.VK_DOWN);
		p1Actions.add(KeyEvent.VK_SPACE);

		p2Actions.add(KeyEvent.VK_NUMPAD4);
		p2Actions.add(KeyEvent.VK_NUMPAD6);
		p2Actions.add(KeyEvent.VK_NUMPAD8);
		p2Actions.add(KeyEvent.VK_NUMPAD2);
		p2Actions.add(KeyEvent.VK_NUMPAD0);
	}


	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.CYAN);
		g2d.fillRect(0, 0, boardWidth, FLOOR - 60);
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0, FLOOR - 35, boardWidth, boardHeight);
		g2d.drawImage(p1.getImage(), p1.getX(), p1.getY(), this);
		g2d.drawImage(p2.getImage(), p2.getX(), p2.getY(), this);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}


	public void actionPerformed(ActionEvent e) {
		p1.action();
		p1.move();
		p2.move();
		repaint();
	}


	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			// if (p1Actions.contains(e)) {
			p1.keyPressed(e);
			// } else if (p2Actions.contains(2)) {
			p2.keyPressed(e);
			// }
		}

		public void keyReleased(KeyEvent e) {
			// if (p1Actions.contains(e)) {
			p1.keyReleased(e);
			// } else if (p2Actions.contains(2)) {
			p2.keyReleased(e);
			// }
		}


	}

}