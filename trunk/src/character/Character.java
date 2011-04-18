package character;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.List;

import player.PlayerControls;

import animation.CharacterAnimation;

public class Character {
	// Animations
	private List<CharacterAnimation> animations;
	private boolean animationPlaying;
	private CharacterState state;

	// Position
	private int x, y;
	private int width, height;
	private int FLOOR;

	// Player Information
	private PlayerControls controls;

	public Character(int startX, int startY, int FLOOR, int player, List<CharacterAnimation> animations, PlayerControls controls) {
		// Initialise Animations
		this.animations = animations;
		animationPlaying = false;
		this.state = CharacterState.STAND;
		this.FLOOR = FLOOR;

		// Initialise Position
		this.x = startX;
		this.y = startY;
		this.width = animations.get(0).getImage().getWidth();
		this.height = animations.get(0).getImage().getHeight();

		// Initialise Player Information
		this.controls = controls;
	}

	public void move() {
		// TODO
	}

	public int getX() {
		// TODO
		return 0;
	}

	public int getY() {
		// TODO
		return 0;
	}

	public Image getImage() {
		// TODO
		return null;
	}

	public void keyPressed(KeyEvent e) {
		// TODO
	}

	public void keyReleased(KeyEvent e) {
		// TODO
	}

	public void update() {
		// TODO
	}

	public void chooseImage(int index) {
		// TODO: Is this method relevant still?
	}

	private void requestAnimation(CharacterState animationID) {
		// TODO
	}

}
