package game;

import javax.swing.JFrame;

import menu.MenuMain;
import menu.MenuMain;

/**
 * 
 * @author Richard Jenkin
 * 
 */
public class FightingGame extends JFrame {
	private static final long serialVersionUID = 1L;
	MenuMain menu;

	public FightingGame() {
		// Default things
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setTitle("Fighting Game");
		setResizable(false);

		// Initialise menu and show it.
		menu = new MenuMain(1280, 720, this);
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
	 * Start a fight screen. First removes the menu screen, suspends the menu's
	 * thread, then initialises the fight and starts it.
	 */
	public void startFight() {
		menu.setVisible(false);
		menu.setThreadSuspended(true);
		add(new Board(1280, 720));
		setVisible(false);
		setVisible(true);
	}

	/**
	 * Shows the menu screen after initialisation. Restarts the thread, then
	 * shows it.
	 */
	public void showMenuMain() {
		menu.setThreadSuspended(false);
		menu.setVisible(true);
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