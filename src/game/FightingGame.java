package game;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;

import menu.MenuCharSelect;
import menu.MenuMain;
import util.GameFileHandler;

/**
 * 
 * @author Richard Jenkin
 * 
 */
public class FightingGame extends JFrame {
	private static final long serialVersionUID = 1L;
	GameFileHandler gfh;
	MenuMain menu;

	private static int WIDTH = 1280;
	private static int HEIGHT = 720;

	public FightingGame() {
		// Default things
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Fighting Game");
		setResizable(false);

		// Image Loader
		this.gfh = new GameFileHandler();

		// Initialise menu and show it.
		menu = new MenuMain(WIDTH, HEIGHT, gfh.loadBackground("menu01.png"), gfh.loadMenuButtons(), this);
		add(menu);
		setVisible(true);
	}

	/**
	 * Constructor
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new FightingGame();
	}

	/**
	 * Shows the menu screen after initialisation. Restarts the thread, then
	 * shows it.
	 */
	public void showMainMenu() {
		menu.setThreadSuspended(false);
		menu.setVisible(true);
		setVisible(false);
		setVisible(true);
	}

	public void showFighterSelectMenu() {
		menu.setVisible(false);
		menu.setThreadSuspended(true);
		add(new MenuCharSelect(WIDTH, HEIGHT, this, gfh.loadCharButtons(), gfh.loadCharButtonHighlights(), gfh.loadCharBig(), gfh.loadBackground("menu02.png")));
		setVisible(false);
		setVisible(true);
	}

	/**
	 * Start a fight screen. First removes the menu screen, suspends the menu's
	 * thread, then initialises the fight and starts it.
	 */
	public void startFight() {
		menu.setVisible(false);
		menu.setThreadSuspended(true);
		add(new Fight(WIDTH, HEIGHT, gfh.loadBackground("london_nationalgallery.jpg")));
		setVisible(false);
		setVisible(true);
	}

	public void startFight2(String char1, String char2) {
		menu.setVisible(false);
		menu.setThreadSuspended(true);
		Player3 p1 = new Player3(WIDTH, HEIGHT, 650, 1, gfh.loadAnimationImages(char1, 1), gfh.loadAnimation(char1));
		Player3 p2 = new Player3(WIDTH, HEIGHT, 650, 2, gfh.loadAnimationImages(char2, 2), gfh.loadAnimation(char2));
		add(new Fight2(WIDTH, HEIGHT, gfh.loadBackground("london_nationalgallery.jpg"), p1, p2));
		setVisible(false);
		setVisible(true);
	}

	/**
	 * Closes the game.
	 */
	public void close() {
		System.exit(0);
	}
}