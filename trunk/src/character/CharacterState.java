package character;

/**
 * Enumerator for the state a player's character is in.
 * 
 * @author Richard Jenkin
 * @version 0.5
 * 
 */
public enum CharacterState {
	// Standing
	STAND, STAND_PUNCH, STAND_KICK, STAND_FORWARD, STAND_BACK, STAND_BLOCK,
	// Jumping
	JUMP, JUMP_PUNCH, JUMP_KICK,
	// Crouching
	CROUCH, CROUCH_PUNCH, CROUCH_KICK, CROUCH_DOWN, CROUCH_UP, CROUCH_FORWARDS, CROUCH_BACK, CROUCH_BLOCK,
	// Floor
	FLOOR,
	// Standing reactions
	STAND_REACT_FACE, STAND_REACT_FOOT, STAND_REACT_FACE_FALL, STAND_REACT_FOOT_FALL,
	// Jumping reactions
	JUMP_REACT_FALL,
	// Crouching reactions
	CROUCH_REACT, CROUCH_REACT_FALL,
	// Other
	INTRO, CELEBRATION;
}