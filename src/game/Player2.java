package game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fileHandling.FileHandler;

/**
 * 
 * @author Richard Jenkin
 * 
 */
public class Player2 {
	// Animations and images
	private FileHandler fh = new FileHandler();
	private List<BufferedImage> imageList;
	private Image image;
	private List<List<Integer>> animations;
	private List<Integer> animationsRequested;
	private boolean animationPlaying;
	private int animationFrame;
	private volatile PlayerState state;
	private boolean thistime;

	// Position
	private int x, y;
	private int dx, dy;
	private int playerWidth, playerHeight;
	private int screenWidth, screenHeight;
	private int FLOOR;

	/**
	 * Constructor.
	 * 
	 * @param width
	 * @param height
	 * @param FLOOR
	 */
	public Player2(int width, int height, int FLOOR) {
		fh.setInputFolder("images" + File.separator + "animation" + File.separator + "p2");
		try {
			imageList = fh.loadMatchingImages("frame", 1, 900);
		} catch (Exception e) {
			e.printStackTrace();
		}
		image = imageList.get(0);
		playerWidth = image.getWidth(null);
		playerHeight = image.getHeight(null);
		screenWidth = width;
		screenHeight = height;
		this.FLOOR = FLOOR;

		animations = new ArrayList<List<Integer>>();
		animationsRequested = new LinkedList<Integer>();
		animationPlaying = false;
		animationFrame = -1;
		thistime = true;

		state = PlayerState.STAND;

		dummyAnimations();

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
			requestAnimation(PlayerState.STAND_FORWARD);
			// chooseImage(50);
			// dx = -2;
			break;
		case KeyEvent.VK_NUMPAD6:
			requestAnimation(PlayerState.STAND_BACK);
			// chooseImage(50);
			// dx = 2;
			break;
		case KeyEvent.VK_NUMPAD8:

			break;
		case KeyEvent.VK_NUMPAD2:
			requestAnimation(PlayerState.CROUCH);
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
			requestAnimation(PlayerState.STAND);
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
					state = PlayerState.STAND;
					break;
				case STAND_BACK:
					state = PlayerState.STAND;
					break;
				case CROUCH_DOWN:
					state = PlayerState.CROUCH;
					break;
				case CROUCH_UP:
					state = PlayerState.STAND;
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
				state = PlayerState.STAND_FORWARD;
				break;
			case 1:
				state = PlayerState.STAND_BACK;
				break;
			case 2: // Crouch down
				state = PlayerState.CROUCH_DOWN;
				break;
			case 3: // Crouch up
				state = PlayerState.CROUCH_UP;
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

	private void requestAnimation(PlayerState animationID) {
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
				if (state == PlayerState.STAND) {
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
				if (state == PlayerState.STAND_FORWARD) {
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
				if (state == PlayerState.STAND_FORWARD) {
				} else {
					animationsRequested.add(1);
				}
			}
			break;
		case STAND_JUMP:
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
				if (state == PlayerState.CROUCH) {
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

	public enum PlayerState {
		STAND, STAND_PUNCH, STAND_KICK, STAND_FORWARD, STAND_BACK, STAND_JUMP, CROUCH, CROUCH_PUNCH, CROUCH_KICK, CROUCH_DOWN, CROUCH_UP
	}

	// TODO: Remove this after loading animations has been implemented.
	private void dummyAnimations() {
		List<Integer> animation1 = new ArrayList<Integer>();
		animation1.add(23);
		animation1.add(24);
		animation1.add(25);
		animation1.add(26);
		animation1.add(27);
		animation1.add(28);
		animation1.add(29);
		animation1.add(30);
		animation1.add(31);
		animation1.add(32);
		animation1.add(33);
		animation1.add(34);
		animation1.add(35);
		animation1.add(36);
		animation1.add(37);
		animation1.add(38);
		animation1.add(39);
		animation1.add(40);
		animation1.add(41);
		animation1.add(42);
		animation1.add(43);
		animation1.add(44);
		animations.add(animation1);
		List<Integer> animation2 = new ArrayList<Integer>();
		animation2.add(44);
		animation2.add(43);
		animation2.add(42);
		animation2.add(41);
		animation2.add(40);
		animation2.add(39);
		animation2.add(38);
		animation2.add(37);
		animation2.add(36);
		animation2.add(35);
		animation2.add(34);
		animation2.add(33);
		animation2.add(32);
		animation2.add(31);
		animation2.add(30);
		animation2.add(29);
		animation2.add(28);
		animation2.add(27);
		animation2.add(26);
		animation2.add(25);
		animation2.add(24);
		animation2.add(23);
		animations.add(animation2);
		List<Integer> animation3 = new ArrayList<Integer>();
		animation3.add(850);
		animation3.add(851);
		animation3.add(852);
		animation3.add(853);
		animation3.add(854);
		animation3.add(855);
		animation3.add(856);
		animation3.add(857);
		animation3.add(858);
		animation3.add(859);
		animation3.add(860);
		animation3.add(861);
		animation3.add(862);
		animation3.add(863);

		animations.add(animation3);
		List<Integer> animation4 = new ArrayList<Integer>();
		animation4.add(864);
		animation4.add(865);
		animation4.add(866);
		animation4.add(867);
		animation4.add(868);
		animation4.add(869);
		animation4.add(870);
		animation4.add(871);
		animation4.add(872);
		animation4.add(873);
		animation4.add(874);
		animation4.add(875);
		animation4.add(876);
		animation4.add(877);
		animation4.add(878);
		animation4.add(879);
		animation4.add(880);
		animation4.add(881);
		animations.add(animation4);
	}
}
