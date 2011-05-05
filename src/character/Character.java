package character;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

import player.PlayerControls;

import animation.CharacterAnimation;

/**
 * 
 * @author Richard Jenkin
 * @version 0.5
 *
 */
public class Character {
	// Animations
	private Map<CharacterState, CharacterAnimation> animations;
	private boolean animationPlaying;
	private CharacterState state;

	// Position
	private int x, y;
	private int screenWidth, screenHeight;
	private int width, height;
	private int FLOOR;

	// Player Information
	private PlayerControls controls;

	public Character(int startX, int startY, int screenWidth, int screenHeight, int FLOOR, int player, Map<CharacterState, CharacterAnimation> animations,
			PlayerControls controls) {
		// Initialise Animations
		this.animations = animations;
		animationPlaying = false;
		this.state = CharacterState.STAND;
		this.FLOOR = FLOOR;

		// Initialise Position
		this.x = startX;
		this.y = startY;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.width = animations.get(0).getImage().getWidth();
		this.height = animations.get(0).getImage().getHeight();

		// Initialise Player Information
		this.controls = controls;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImage() {
		return animations.get(state).getImage();
	}

	public void chooseImage(int index) {
		// TODO: Is this method relevant still?
	}

	public void move() {
		// NOTE: Y is not needed, as it should always be zero in the current
		// implementation.
		int nextX = x + animations.get(state).getX();

		int playerWidth = animations.get(state).getImage().getWidth();

		if (nextX < 1) {
			nextX = 1;
		} else if (nextX + animations.get(state).getImage().getWidth() > screenWidth) {
			nextX = screenWidth - playerWidth;
		}
	}

	public void update() {
		// TODO
		if (animationPlaying) {
			// We are playing an animation
			if (animations.get(state).getAnimationPlaying()) {
				// The current animation is still going
				animations.get(state).update();
				// TODO STUFF GOES HERE
			} else {
				// The current animation has stopped!
				// TODO STUFF GOES HERE
			}
		} else {
			// TODO STUFF GOES HERE
		}
		// TODO: IS THIS RIGHT? ASDF
		move();
	}

	public void keyPressed(KeyEvent e) {
		// TODO
	}

	public void keyReleased(KeyEvent e) {
		// TODO
	}

	private void requestAnimation(CharacterState animationID) {
		// TODO
	}

}
