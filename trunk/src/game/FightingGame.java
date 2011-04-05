package game;

import javax.swing.JFrame;

import menu.MenuMain;

public class FightingGame extends JFrame {
	private static final long serialVersionUID = 1L;
	MenuMain menu;

	public FightingGame() {


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setTitle("Fighting Game");
		setResizable(false);

		menu = new MenuMain(1280, 720, this);
		add(menu);
		setVisible(true);
	}

	public static void main(String[] args) {
		new FightingGame();
	}

	public void startFight() {
		menu.setVisible(false);
		add(new Board(1280, 720));
		setVisible(false);
		setVisible(true);
	}

	public void startNewMenu(MenuMain menu) {
		add(menu);
	}

	public void close() {
		System.exit(0);
	}
}