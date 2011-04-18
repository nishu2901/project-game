package animation;

import java.awt.image.BufferedImage;

/**
 * A class for holding a group of <code>BufferedImage</code>s, which will choose
 * the image to display automatically when update() is called. Can play the
 * animation a set number of times.
 * 
 * @author Richard Jenkin
 * 
 */
public interface Animation {

	/**
	 * Tells the animation to stop playing after the current iteration has
	 * finished. To stop the animation immediately, call stopAnimation()
	 * instead.
	 */
	public void notifyStop();

	/**
	 * Set the animation back to the first frame.
	 */
	public void resetAnimation();

	/**
	 * Stop the animation immediately. To stop the animation after the current
	 * iteration, call notifyStop() instead.
	 */
	public void stopAnimation();

	/**
	 * Call this method to update the current image. If this method is not
	 * called, the animation will not animate.
	 */
	public void update();

	/**
	 * Returns the current frame of the animation.
	 * 
	 * @return The current frame.
	 */
	public BufferedImage getImage();

	/**
	 * Returns the x-position of the animation on screen.
	 * 
	 * @return The x-position.
	 */
	public int getX();

	/**
	 * Returns the y-position of the animation on screen.
	 * 
	 * @return The y-position.
	 */
	public int getY();

}
