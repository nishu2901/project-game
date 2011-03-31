package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import fileHandling.CSVHandler;
import fileHandling.FileHandler;

//import statements
//Check if window closes automatically. Otherwise add suitable code
public class Game extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static CSVHandler csvHandler;
	private static FileHandler fileHandler;

	public static void main(String args[]) {
		csvHandler = new CSVHandler();
		fileHandler = new FileHandler();

		csvHandler.setCSVFolder("animation");
		csvHandler.setFileName("char1");

		fileHandler.setInputFolder("animation" + File.separator + "char1");

		new Game();
	}



	Game() {
		List<int[]> aniTest = csvHandler.readCSVint();
		List<BufferedImage> frames = fileHandler.loadAllImagesMatching("outputImage", 1);



		Graphics g = frames.get(0).getGraphics();
		g.drawImage(frames.get(0), 0, 0, null);




		JLabel jlbHelloWorld = new JLabel("Hello World");
		add(jlbHelloWorld);
		this.setSize(640, 480);
		// pack();
		setVisible(true);
	}	
}