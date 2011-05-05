package fight;

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

import character.Character;

/**
 * 
 * @author Richard Jenkin
 * @version 0.5
 *
 */
public class Fight extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;

	public int FLOOR = 650;

	private Thread timer;
	private final int DELAY = 25;
	private boolean threadSuspended;

	private Character char1;
	private Character char2;

	private BufferedImage background;

	public Fight(int width, int height, BufferedImage background, Character c1, Character c2) {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		setDoubleBuffered(true);

		this.background = background;

		char1 = c1;
		char2 = c2;
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, 0, 0, this);

		g2d.drawImage(char1.getImage(), char1.getX(), char1.getY(), this);
		g2d.drawImage(char2.getImage(), char2.getX(), char2.getY(), this);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		char1.move();
		char2.move();
		repaint();
	}

	public void addNotify() {
		super.addNotify();
		timer = new Thread(this);
		timer.start();
	}

	public void cycle() {
		char1.update();
		char2.update();
	}

	@Override
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
				System.out.println("Interrupted");
			}
		}
	}

	public void setThreadSuspended(boolean threadSuspended) {
		this.threadSuspended = threadSuspended;
	}

	public boolean isThreadSuspended() {
		return threadSuspended;
	}

	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			char1.keyPressed(e);
			char2.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			char1.keyReleased(e);
			char2.keyReleased(e);
		}
	}
}
