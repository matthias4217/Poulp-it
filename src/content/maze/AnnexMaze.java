package content.maze;

import java.util.Random;
import javafx.scene.paint.Color;

/**
 * This class contains the annex methods used by the Maze object.
 * 
 * @author Raph
 *
 */
public class AnnexMaze {

	/**
	 * @param min
	 * @param max
	 * @return a random integer in [[min; max[
	 */
	public static int randInt(int min, int max) {
		Random rn = new Random();
		return rn.nextInt(max-min) + min;
	}

	/**
	 * @return a random color uniformly among the 16 millions
	 */
	public static Color randomColor() {
		Random rn = new Random();
		float r = rn.nextFloat();
		float g = rn.nextFloat();
		float b = rn.nextFloat();
		return new Color(r, g, b, 1);
	}


}
