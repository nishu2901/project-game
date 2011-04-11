package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.JPanel;

import fileHandling.FileHandler;
import game.FightingGame;

/**
 * 
 * @author Richard Jenkin
 * 
 */
public class MenuMain extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private FightingGame parent;
	private Thread timer;
	private List<MenuButton> menuButtons;
	private int totalMenuItems;
	private int selectedMenuItem;
	private BufferedImage menuBackground;

	private boolean threadSuspended;

	private final int DELAY = 5;

	public MenuMain(int width, int height, BufferedImage background, List<MenuButton> menuButtons, FightingGame parent) {
		this.parent = parent;

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		setDoubleBuffered(true);

		this.menuBackground = background;
		this.menuButtons = menuButtons;

		selectedMenuItem = 0;
		totalMenuItems = 2;

		highlightMenuItem(selectedMenuItem);
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.drawImage(menuBackground, 0, 0, this);
		g2d.drawImage(menuButtons.get(0).getImage(), menuButtons.get(0).getX(), menuButtons.get(0).getY(), this);
		g2d.drawImage(menuButtons.get(1).getImage(), menuButtons.get(1).getX(), menuButtons.get(1).getY(), this);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void menuUp() {
		if (selectedMenuItem == 0) {
			selectedMenuItem = totalMenuItems - 1;
		} else {
			selectedMenuItem--;
		}
	}

	public void menuDown() {
		if (selectedMenuItem == totalMenuItems - 1) {
			selectedMenuItem = 0;
		} else {
			selectedMenuItem++;
		}
	}

	public void menuSelect() {
		switch (selectedMenuItem) {
		case 0:
			parent.startFight();
			break;
		case 1:
			parent.close();
			break;
		}
	}

	public void highlightMenuItem(int item) {
		switch (item) {
		case 0:
			menuButtons.get(1).mouseOut();
			menuButtons.get(0).mouseOver();
			break;
		case 1:
			menuButtons.get(0).mouseOut();
			menuButtons.get(1).mouseOver();
			break;
		}
	}

	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			System.out.println(e.getKeyCode());
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				menuUp();
				break;
			case KeyEvent.VK_DOWN:
				menuDown();
				break;
			case KeyEvent.VK_ENTER:
				menuSelect();
			}
			highlightMenuItem(selectedMenuItem);
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
			System.out.print("key typed: ");
			System.out.println(e.getKeyCode());
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				System.out.println("Enter entered");
				switch (selectedMenuItem) {
				case 0:
					parent.startFight();
					break;
				}
			}
		}
	}

	public void addNotify() {
		super.addNotify();
		timer = new Thread(this);
		timer.start();
	}

	public void cycle() {
		System.out.println("Menu Cycle");
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
				System.out.println("Interruped!");
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
