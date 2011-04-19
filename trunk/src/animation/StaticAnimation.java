package animation;

import java.awt.image.BufferedImage;
import java.util.List;


/**
 * An implementation of <code>Animation</code> for static images which do not
 * move.
 * 
 * @author Richard Jenkin
 * 
 */
public class StaticAnimation implements Animation {
	// Animation
	private List<BufferedImage> frames;
	private int currentFrame;
	private int times;

	// Position
	private int x, y;

	/**
	 * Constructor.
	 * 
	 * @param frames
	 *            <code>List</code> of <code>BufferedImage</code> to use for the
	 *            animation. Images should be in the order they are to be played
	 *            in.
	 * @param x
	 *            The x-position of the image on screen.
	 * @param y
	 *            The y-position of the image on screen.
	 * @param times
	 *            The number of times to play the animation. Must be a positive
	 *            integer, or -1. If set to -1. will repeat forever.
	 */
	public StaticAnimation(List<BufferedImage> frames, int x, int y, int times) {
		this.frames = frames;
		this.currentFrame = 0;
		this.x = x;
		this.y = y;
		this.times = times;
	}

	public void notifyStop() {
		times = 1;
	}

	public void resetAnimation() {
		currentFrame = 0;
	}

	public void stopAnimation() {
		times = 0;
	}

	public void update() {
		if ((times > 0) || (times == -1)) {
			if (currentFrame == frames.size() - 1) {
				currentFrame = 0;
				if (times > 0) {
					times--;
				}
			} else {
				currentFrame++;
			}
		}
	}

	public BufferedImage getImage() {
		return frames.get(currentFrame);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
