package game;

import javax.swing.JFrame;

public class FightingGame extends JFrame {

    public FightingGame() {

        add(new Board(1024, 640));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 640);
        setLocationRelativeTo(null);
        setTitle("Fighting Game");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FightingGame();
    }
}