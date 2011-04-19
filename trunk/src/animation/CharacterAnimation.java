package animation;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import character.CharacterState;


/**
 * An implementation of <code>Animation</code> for characters, which move as
 * well as animate.
 * 
 * @author Richard Jenkin
 * 
 */
public class CharacterAnimation implements Animation {
	// Labels
	private CharacterState state;

	// Animation
	private List<BufferedImage> frames;
	private int currentFrame;
	private int times;

	// Position
	private int x, y;
	private List<Point> movement;

	public CharacterAnimation(CharacterState state, List<BufferedImage> frames, List<Point> movement, int x, int y, int times) {
		this.state = state;
		this.frames = frames;
		this.movement = movement;
		this.x = x;
		this.y = y;
		this.times = times;
		this.currentFrame = 0;
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
			// Check we want to animate this time
			if (currentFrame == (frames.size() - 1)) {
				// Check if we're at the end of the animation
				currentFrame = 0;
				if (times > 0) {
					// Check if we want to stop
					times--;
				}
			} else {
				// Increment frame
				currentFrame++;
			}

			// Either way, update (x, y) position.
			x += movement.get(currentFrame).x;
			y += movement.get(currentFrame).y;
		}

	}

	public boolean getAnimationPlaying() {
		return (times != 0);
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

	public CharacterState getPlayerState() {
		return state;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
