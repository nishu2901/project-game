package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.Timer;

import fileHandling.FileHandler;
import game.FightingGame;

public class MenuMain extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private FightingGame parent;
	private FileHandler fh;
	private Timer timer;
	private MenuButton buttonPlay;
	private MenuButton buttonExit;
	private int totalMenuItems;
	private int selectedMenuItem;
	private BufferedImage menuBackground;


	public MenuMain(int width, int height, FightingGame parent) {
		this.parent = parent;

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);
		setDoubleBuffered(true);

		timer = new Timer(5, this);
		timer.start();

		selectedMenuItem = 0;
		totalMenuItems = 2;

		fh = new FileHandler();
		fh.setInputFolder("images" + File.separator + "menu");

		try {
			buttonPlay = new MenuButton("play", fh.loadMatchingImages("play", 1, 2), 500, 200);
			buttonExit = new MenuButton("exit", fh.loadMatchingImages("exit", 1, 2), 500, 400);
			menuBackground = fh.loadMatchingImages("menu", 1, 1).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		highlightMenuItem(selectedMenuItem);
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.drawImage(menuBackground, 0, 0, this);
		g2d.drawImage(buttonPlay.getImage(), buttonPlay.getX(), buttonPlay.getY(), this);
		g2d.drawImage(buttonExit.getImage(), buttonExit.getX(), buttonExit.getY(), this);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
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
			buttonExit.mouseOut();
			buttonPlay.mouseOver();
			break;
		case 1:
			buttonPlay.mouseOut();
			buttonExit.mouseOver();
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

}
