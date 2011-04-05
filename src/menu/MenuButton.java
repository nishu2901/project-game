package menu;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

public class MenuButton {
	private String name;
	private List<BufferedImage> imageList;
	private int currentImage;
	private int xLoc, yLoc;
	private int width, height;
	private boolean visible;

	public MenuButton(String name, List<BufferedImage> images, int xLoc, int yLoc) {
		this.name = name;
		this.imageList = images;
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		width = imageList.get(0).getWidth();
		height = imageList.get(0).getHeight();

		currentImage = 0;
		visible = true;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getImage() {
		return imageList.get(currentImage);
	}

	public int getX() {
		return xLoc;
	}

	public int getY() {
		return yLoc;
	}

	public Rectangle getBounds() {
		return new Rectangle(xLoc, yLoc, width, height);
	}

	public void mouseOver() {
		currentImage = 1;
	}

	public void mouseOut() {
		currentImage = 0;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
