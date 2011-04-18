package menu;

import java.awt.image.BufferedImage;

/**
 * TODO: Do we need this class? We can now just use an animation
 * 
 * @author Richard Jenkin
 * 
 */
public class MenuChar {
	private String name;
	private BufferedImage image;
	private int xLoc, yLoc;
	private boolean visible;

	public MenuChar(String name, BufferedImage image, int xLoc, int yLoc) {
		this.name = name;
		this.image = image;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		visible = true;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getImage() {
		return image;
	}

	public int getX() {
		return xLoc;
	}

	public int getY() {
		return yLoc;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
