package util;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import animation.CharacterAnimation;
import animation.StaticAnimation;
import character.CharacterState;
import fileHandling.CSVHandler;
import fileHandling.FileHandler;

/**
 * Provides methods for loading animations from file.
 * 
 * @author Richard Jenkin
 * @version 0.5
 * 
 */
public class AnimationIO {
	private FileHandler fh;
	private CSVHandler csvh;

	/**
	 * Constructor. Initialises the <code>FileHandler</code> and
	 * <code>CSVHandler</code>.
	 */
	public AnimationIO() {
		this.fh = new FileHandler();
		this.csvh = new CSVHandler();
	}


	/**
	 * Loads all animations for a specified character and player.
	 * 
	 * @param cName
	 *            The name of the character to load the animations for. This is
	 *            the name of the folder where the character's animations are
	 *            stored.
	 * @param player
	 *            The player who selected this character. Decides which set of
	 *            character images to load.
	 * @return The entire set of character animations.
	 */
	public Map<CharacterState, CharacterAnimation> loadFullCharacterAnimations(String cName, int player) {
		// Create map
		Map<CharacterState, CharacterAnimation> fca = new HashMap<CharacterState, CharacterAnimation>();
		// Set input folders
		String charSide = (player == 1) ? "p1" : "p2";
		String csvFolder = "images" + File.separator + "animation" + File.separator + cName;
		String imgFolder = csvFolder + File.separator + charSide;
		csvh.setInputFolder(csvFolder);
		fh.setInputFolder(imgFolder);

		// Get csv
		List<String[]> csv = csvh.readCSV();

		// Create each animation and add to map
		for (int r = 0; r < csv.size() - 1; r++) {
			String[] row = csv.get(r);
			String[] row2 = csv.get(r + 1);

			// Check we have the right pair of rows
			if (row[0].equals(row2[0])) {

				CharacterState state = CharacterState.valueOf(row[0]);

				if (!fca.containsKey(state)) {
					// Continue only if we haven't already added this animation
					List<BufferedImage> frames = new ArrayList<BufferedImage>();
					List<Point> movement = new ArrayList<Point>();

					// Frames
					for (int col = 1; col < row.length; col++) {
						frames.add(fh.loadImage("frame" + padNumber(row[col]) + ".png"));
					}

					// Movement
					for (int d = 1; d < row2.length; d += 2) {
						movement.add(new Point(Integer.parseInt(row2[d]), Integer.parseInt(row2[d + 1])));
					}

					// Add the animation to the map
					fca.put(state, new CharacterAnimation(state, frames, movement, 0, 0, 0));
				}
			}
		}

		// Return the map of animations
		return fca;
	}

	public StaticAnimation loadStaticAnimation(String animationName, String animationLocation) {
		// TODO
		StaticAnimation sa = new StaticAnimation(null, 0, 0, 0);
		return sa;
	}

	/**
	 * Pads a number with leading 0's. Used for file names.
	 * 
	 * @param number
	 *            The number we wish to pad.
	 * @return The number with leading 0's attached.
	 */
	private String padNumber(String number) {
		String paddedNumber = "";
		if (number.length() >= 5) {
			paddedNumber = number;
		} else if (number.length() >= 4) {
			paddedNumber = "0" + number;
		} else if (number.length() >= 3) {
			paddedNumber = "00" + number;
		} else if (number.length() >= 2) {
			paddedNumber = "000" + number;
		} else {
			paddedNumber = "0000" + number;
		}
		return paddedNumber;
	}
}
