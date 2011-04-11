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
public class MenuCharSelect extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private FightingGame parent;
	private FileHandler fh;
	private Thread timer;
	private List<BufferedImage> charButtons;
	private List<BufferedImage> highlights;
	private List<BufferedImage> charBig;
	private int characters;
	private int p1HoverChar, p2HoverChar;
	private boolean p1Selected, p2Selected;
	private BufferedImage menuBackground;

	private static final int MENU_X = 605;
	private static final int MENU_Y = 300;

	private boolean threadSuspended;

	private final int DELAY = 5;

	public MenuCharSelect(int width, int height, FightingGame parent, List<BufferedImage> charButtons, List<BufferedImage> highlights,
			List<BufferedImage> charBig, BufferedImage background) {
		this.parent = parent;
		this.charButtons = charButtons;
		this.highlights = highlights;
		this.charBig = charBig;
		this.menuBackground = background;

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		setDoubleBuffered(true);

		characters = charButtons.size();
		p1HoverChar = 0;
		p2HoverChar = 0;
		p1Selected = false;
		p2Selected = false;
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		/* Background Image */
		g2d.drawImage(menuBackground, 0, 0, this);
		/* Character Buttons */
		int end = 0;
		int x = 0;
		int y = 0;
		while (end < characters) {
			g2d.drawImage(charButtons.get(end), MENU_X + (x * 25), MENU_Y + (y * 25), this);
			// N.B. Do not change the order of this if/else statement
			if (end % 3 == 0) {
				// multiple of 3, end of row
				x = 0;
				y++;
			} else {
				// multiple of 2 or 1, same row
				x++;
			}
			end++;
		}
		/* Highlighted Characters */
		x = 0;
		y = 0;
		// P1 Position
		if (p1HoverChar % 3 == 0) {
			x = MENU_X + 50;
		} else if (p1HoverChar % 2 == 0) {
			x = MENU_X + 25;
		} else {
			x = MENU_X;
		}
		y = MENU_Y + ((p1HoverChar / 3) * 25);
		if (p1HoverChar == p2HoverChar) {
			g2d.drawImage(highlights.get(2), x, y, this);
		} else {
			g2d.drawImage(highlights.get(0), x, y, this);
			// P2 Position
			if (p2HoverChar % 3 == 0) {
				x = MENU_X + 50;
			} else if (p2HoverChar % 2 == 0) {
				x = MENU_X + 25;
			} else {
				x = MENU_X;
			}
			y = MENU_Y + ((p2HoverChar / 3) * 25);
			g2d.drawImage(highlights.get(1), x, y, this);
		}
		/* Characters Big */

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionP1(action a) {
		if (!p1Selected) {
			switch (a) {
			case UP:
				break;
			case LEFT:
				p1HoverChar = p1HoverChar == 0 ? characters : p1HoverChar - 1;
				break;
			case DOWN:
				break;
			case RIGHT:
				p1HoverChar = p1HoverChar == characters ? 0 : p1HoverChar + 1;
				break;
			case A:
				p1Selected = true;
				break;
			case B:
				break;
			}
		}
	}

	public void actionP2(action a) {
		if (!p2Selected) {
			switch (a) {
			case UP:
				break;
			case LEFT:
				p2HoverChar = p2HoverChar == 0 ? characters : p2HoverChar - 1;
				break;
			case DOWN:
				break;
			case RIGHT:
				p2HoverChar = p2HoverChar == characters ? 0 : p2HoverChar + 1;
				break;
			case A:
				p2Selected = true;
				break;
			case B:
				break;
			}
		}
	}

	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			System.out.println(e.getKeyCode());
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				actionP1(action.UP);
				break;
			case KeyEvent.VK_A:
				actionP1(action.LEFT);
				break;
			case KeyEvent.VK_S:
				actionP1(action.DOWN);
				break;
			case KeyEvent.VK_D:
				actionP1(action.RIGHT);
				break;
			case KeyEvent.VK_J:
				actionP1(action.A);
				break;
			case KeyEvent.VK_K:
				actionP1(action.B);
				break;
			case KeyEvent.VK_UP:
				actionP2(action.UP);
				break;
			case KeyEvent.VK_LEFT:
				actionP2(action.LEFT);
				break;
			case KeyEvent.VK_DOWN:
				actionP2(action.DOWN);
				break;
			case KeyEvent.VK_RIGHT:
				actionP2(action.RIGHT);
				break;
			case KeyEvent.VK_1:
				actionP2(action.A);
				break;
			case KeyEvent.VK_2:
				actionP2(action.B);
				break;
			}
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyTyped(KeyEvent e) {
		}
	}

	public void addNotify() {
		super.addNotify();
		timer = new Thread(this);
		timer.start();
	}

	public void cycle() {

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

	private enum action {
		UP, LEFT, DOWN, RIGHT, A, B
	}
}
