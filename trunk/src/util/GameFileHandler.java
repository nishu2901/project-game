package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import menu.MenuButton;

import fileHandling.CSVHandler;
import fileHandling.FileHandler;

public class GameFileHandler {
	FileHandler fh;
	CSVHandler csvh;

	public GameFileHandler() {
		this.fh = new FileHandler();
		this.csvh = new CSVHandler();
	}

	public BufferedImage loadBackground(String name) {
		fh.setInputFolder("images" + File.separator + "backdrop");
		return fh.loadImage(name);
	}

	public List<MenuButton> loadMenuButtons() {
		fh.setInputFolder("images" + File.separator + "menu");
		List<MenuButton> menuButtons = new ArrayList<MenuButton>();
		try {
			menuButtons.add(new MenuButton("play", fh.loadMatchingImages("play", 1, 2), 500, 200));
			menuButtons.add(new MenuButton("exit", fh.loadMatchingImages("exit", 1, 2), 500, 400));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuButtons;
	}

	public List<BufferedImage> loadCharButtons() {
		fh.setInputFolder("images" + File.separator + "char");
		List<BufferedImage> charButtons = new ArrayList<BufferedImage>();
		try {
			charButtons = fh.loadMatchingImages("charButton", 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charButtons;
	}

	public List<BufferedImage> loadCharButtonHighlights() {
		fh.setInputFolder("images" + File.separator + "char");
		List<BufferedImage> charButtonHighlights = new ArrayList<BufferedImage>();
		try {
			charButtonHighlights = fh.loadMatchingImages("charButton", 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charButtonHighlights;
	}

	public List<BufferedImage> loadCharBig() {
		fh.setInputFolder("images" + File.separator + "char");
		List<BufferedImage> charBig = new ArrayList<BufferedImage>();
		try {
			charBig = fh.loadMatchingImages("charButton", 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charBig;
	}

	public List<BufferedImage> loadAnimationImages(String charName, int player) {
		String characterSide = (player == 1) ? "p1" : "p2";
		fh.setInputFolder("images" + File.separator + "animation" + File.separator + charName + File.separator + characterSide);
		List<BufferedImage> animationImages = new ArrayList<BufferedImage>();
		try {
			animationImages = fh.loadMatchingImages("frame", 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return animationImages;
	}

	public List<List<Integer>> loadAnimation(String charName) {
		csvh.setInputFolder("images" + File.separator + "animation" + File.separator + charName);
		return csvh.readCSVinteger(charName);
	}
}
