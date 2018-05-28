package content.maze.old;

import java.awt.Color;
import java.util.Random;


public class AnnexMazeOld {

	public static int randInt(int min, int max) {
		/* Return a random integer in [[min; max[[. */
		Random rn = new Random();
		return rn.nextInt(max-min) + min;
	}

	public static Color randomColorOld() {
		/* Return a random color uniformly among the 16 millions. */
		Random rn = new Random();
		float r = rn.nextFloat();
		float g = rn.nextFloat();
		float b = rn.nextFloat();
		return new Color(r, g, b);
	}





	// Optimal dimensions for the notepad: (59, 26)	


	/*	COUNT_BRANCHES

	N = 1000000
	width = 50
	height = 25

	En partant de (0, 0) : en moyenne 123.668023 intersections.
	En partant du centre : en moyenne 123.691469 intersections.
	 */




}


