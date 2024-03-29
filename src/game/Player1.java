package game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import fileHandling.FileHandler;

/**
 * 
 * @author Richard Jenkin
 * @version 0.5
 *
 */
public class Player1 {
	private FileHandler fh = new FileHandler();
	private List<BufferedImage> imageList;
	private Image image;

	private int x, y;
	private int dx, dy;
	private int playerWidth, playerHeight;
	private int screenWidth, screenHeight;
	private int FLOOR;

	public Player1(int width, int height, int FLOOR) {
		fh.setInputFolder("images" + File.separator + "animation" + File.separator + "p1");
		try {
			imageList = fh.loadMatchingImages("frame", 1, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		image = imageList.get(0);
		playerWidth = image.getWidth(null);
		playerHeight = image.getHeight(null);
		x = 100;
		y = 100;
		screenWidth = width;
		screenHeight = height;
		this.FLOOR = FLOOR;
	}

	public void action() {

	}

	public void move() {
		x += dx;
		y += dy;

		if (x < 1) {
			x = 1;
		}
		if (y < 1) {
			y = 1;
		}
		if (x + playerWidth > screenWidth) {
			x = screenWidth - playerWidth;
		}
		if (y > 300) {
			y = 300;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return FLOOR - playerHeight;
	}

	public Image getImage() {
		return image;
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			chooseImage(50);
			dx = -2;
			break;
		case KeyEvent.VK_RIGHT:
			chooseImage(50);
			dx = 2;
			break;
		case KeyEvent.VK_UP:
			// dy = -1;
			break;
		case KeyEvent.VK_DOWN:
			chooseImage(1041);
			break;
		case KeyEvent.VK_SPACE:
			chooseImage(50);
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			chooseImage(0);
			dx = 0;
			break;
		case KeyEvent.VK_RIGHT:
			chooseImage(0);
			dx = 0;
			break;
		case KeyEvent.VK_UP:
			// dy = 0;
			break;
		case KeyEvent.VK_DOWN:
			chooseImage(0);
			break;
		case KeyEvent.VK_SPACE:
			chooseImage(0);
			break;
		}
	}

	private void chooseImage(int index) {
		image = imageList.get(index);
		playerWidth = image.getWidth(null);
		playerHeight = image.getHeight(null);
	}
}
