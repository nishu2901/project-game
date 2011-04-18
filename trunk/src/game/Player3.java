package game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import character.CharacterState;


/**
 * 
 * @author Richard Jenkin
 * 
 */
public class Player3 {
	// Animations and images
	private List<BufferedImage> imageList;
	private Image image;
	private List<List<Integer>> animations;
	private List<Integer> animationsRequested;
	private boolean animationPlaying;
	private int animationFrame;
	private volatile CharacterState state;
	private boolean thistime;

	// Position
	private int x, y;
	private int dx, dy;
	private int playerWidth, playerHeight;
	private int screenWidth, screenHeight;
	private int FLOOR;

	public Player3(int width, int height, int FLOOR, int player, List<BufferedImage> imageList, List<List<Integer>> animations) {
		this.imageList = imageList;
		this.animations = animations;

		image = imageList.get(0);
		playerWidth = image.getWidth(null);
		playerHeight = image.getHeight(null);
		screenWidth = width;
		screenHeight = height;
		this.FLOOR = FLOOR;

		animationsRequested = new LinkedList<Integer>();
		animationPlaying = false;
		animationFrame = -1;
		thistime = true;

		state = CharacterState.STAND;

		x = screenWidth - 100 - playerWidth;
		y = screenHeight - 100;
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
		case KeyEvent.VK_NUMPAD4:
			requestAnimation(CharacterState.STAND_FORWARD);
			// chooseImage(50);
			// dx = -2;
			break;
		case KeyEvent.VK_NUMPAD6:
			requestAnimation(CharacterState.STAND_BACK);
			// chooseImage(50);
			// dx = 2;
			break;
		case KeyEvent.VK_NUMPAD8:

			break;
		case KeyEvent.VK_NUMPAD2:
			requestAnimation(CharacterState.CROUCH);
			break;
		case KeyEvent.VK_NUMPAD0:
			chooseImage(50);
			break;
		}

	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_NUMPAD4:
			// chooseImage(0);
			// dx = 0;
			break;
		case KeyEvent.VK_NUMPAD6:
			// chooseImage(0);
			// dx = 0;
			break;
		case KeyEvent.VK_NUMPAD8:
			// dy = 0;
			break;
		case KeyEvent.VK_NUMPAD2:
			requestAnimation(CharacterState.STAND);
			break;
		case KeyEvent.VK_NUMPAD0:
			chooseImage(0);
			break;
		}
	}

	public void update() {
		// Delay animations by 1
		// thistime = !thistime;
		if (animationPlaying) {
			/* In an animation */
			if (animationFrame == animations.get(animationsRequested.get(0)).size() - 1) {
				/* Animation over */
				System.out.println("!!!ANIMATION FINISHED!!!");
				// Update the image
				chooseImage(animations.get(animationsRequested.get(0)).get(animationFrame));
				// Set the final resting state
				switch (state) {
				case STAND_FORWARD:
					state = CharacterState.STAND;
					break;
				case STAND_BACK:
					state = CharacterState.STAND;
					break;
				case CROUCH_DOWN:
					state = CharacterState.CROUCH;
					break;
				case CROUCH_UP:
					state = CharacterState.STAND;
					break;
				}
				// Reset the start frame
				animationFrame = -1;
				// Remove from animations requested list
				animationsRequested.remove(0);
				// Stop movement
				dx = 0;
				// TODO: SOME KIND OF CHECK HERE?
				// Stop the animation
				animationPlaying = false;
			} else if (thistime) {
				/* Continue Animation */
				// System.out.println("In animation");
				// Update the image
				chooseImage(animations.get(animationsRequested.get(0)).get(animationFrame));
				// Update player position
				switch (state) {
				case STAND_FORWARD:
					dx = -3;
					break;
				case STAND_BACK:
					dx = 3;
					break;
				}
				move();
				// Increment frame for next pass.
				animationFrame++;
			}
		} else if (animationsRequested.size() > 0) {
			/* A new animation! */
			// System.out.println("!!!NEW ANIMATION!!!");
			switch (animationsRequested.get(0)) {
			case 0:
				state = CharacterState.STAND_FORWARD;
				break;
			case 1:
				state = CharacterState.STAND_BACK;
				break;
			case 2: // Crouch down
				state = CharacterState.CROUCH_DOWN;
				break;
			case 3: // Crouch up
				state = CharacterState.CROUCH_UP;
				break;
			}
			// Update Start frame
			animationFrame = 0;
			// Update the image
			chooseImage(animations.get(animationsRequested.get(0)).get(animationFrame));
			// Increment frame for next pass.
			animationFrame++;
			/* Start an animation */
			animationPlaying = true;
		} else {
			// move();
		}
	}

	private void chooseImage(int index) {
		image = imageList.get(index);
		playerWidth = image.getWidth(null);
		playerHeight = image.getHeight(null);
	}

	private void requestAnimation(CharacterState animationID) {
		switch (animationID) {
		case STAND:
			if (animationPlaying) {
				int prev = animationsRequested.get(animationsRequested.size() - 1);
				if (prev != 3) { // not about to crouch
					// Add crouch to animation queue
					animationsRequested.add(3);
				}
			} else {
				// TODO:Replace this with a switch?
				if (state == CharacterState.STAND) {
				} else {
					animationsRequested.add(3);
				}
			}
			break;
		case STAND_PUNCH:
			break;
		case STAND_KICK:
			break;
		case STAND_FORWARD:
			if (animationPlaying) {
				int prev = animationsRequested.get(animationsRequested.size() - 1);
				if (prev != 0) { // not about to crouch
					// Add crouch to animation queue
					animationsRequested.add(0);
				}
			} else {
				// TODO:Replace this with a switch?
				if (state == CharacterState.STAND_FORWARD) {
				} else {
					animationsRequested.add(0);
				}
			}
			break;
		case STAND_BACK:
			if (animationPlaying) {
				int prev = animationsRequested.get(animationsRequested.size() - 1);
				if (prev != 1) { // not about to crouch
					// Add crouch to animation queue
					animationsRequested.add(1);
				}
			} else {
				// TODO:Replace this with a switch?
				if (state == CharacterState.STAND_FORWARD) {
				} else {
					animationsRequested.add(1);
				}
			}
			break;
		case JUMP:
			break;
		case CROUCH: // Want to crouch
			if (animationPlaying) {
				int prev = animationsRequested.get(animationsRequested.size() - 1);
				if (prev != 2) { // not about to crouch
					// Add crouch to animation queue
					animationsRequested.add(2);
				}
			} else {
				// TODO:Replace this with a switch?
				if (state == CharacterState.CROUCH) {
				} else {
					animationsRequested.add(2);
				}
			}
			break;
		case CROUCH_PUNCH:
			break;
		case CROUCH_KICK:
			break;
		case CROUCH_DOWN:
			break;
		case CROUCH_UP:
			break;
		}
	}
}
