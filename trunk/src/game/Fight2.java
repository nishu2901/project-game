package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * 
 * @author Richard Jenkin
 * @version 0.5
 * 
 */
public class Fight2 extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	private Thread timer;
	private final int DELAY = 25;
	private boolean threadSuspended;

	private Player3 p1;
	private Player3 p2;

	private BufferedImage background;

	private List<Integer> p1Actions;
	private List<Integer> p2Actions;

	public Fight2(int width, int height, BufferedImage background, Player3 p1, Player3 p2) {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		setDoubleBuffered(true);

		this.p1 = p1;
		this.p2 = p2;

		this.background = background;

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
		g2d.drawImage(background, 0, 0, this);
		g2d.drawImage(p1.getImage(), p1.getX(), p1.getY(), this);
		g2d.drawImage(p2.getImage(), p2.getX(), p2.getY(), this);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}


	public void actionPerformed(ActionEvent e) {
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

	public void addNotify() {
		super.addNotify();
		timer = new Thread(this);
		timer.start();
	}

	public void cycle() {
		p1.update();
		p2.update();
	}

	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {
			cycle();
			repaint();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0) {
				sleep = 2;
			}
			try {
				Thread.sleep(sleep);

				if (isThreadSuspended()) {
					synchronized (this) {
						while (isThreadSuspended())
							wait();
					}
				}
			} catch (InterruptedException e) {
				System.out.println("Interrupted!");
			}

			beforeTime = System.currentTimeMillis();
		}

	}


	public void setThreadSuspended(boolean threadSuspended) {
		this.threadSuspended = threadSuspended;
	}


	public boolean isThreadSuspended() {
		return threadSuspended;
	}

}