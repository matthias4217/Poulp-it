package content.maze;

import content.GameObject;
import content.Layer;
import content.Tag;
import content.maze.scripts.Controller;
import core.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * 
 * 
 * @author Raph
 *
 */
public class PlayerMaze extends GameObject {

	/**
	 * The color of the player
	 */
	public static Color playerColor = Color.CHARTREUSE;

	/**
	 * The ratio of the player dimensions compared to one "tile"'s dimensions
	 */
	public static float MARGIN = 0.9f;



	Maze maze;		// Yes, the maze object is actually included in the player object



	public PlayerMaze(Vector2 position) {
		super(position,
				null,
				Layer.DEFAULT,
				Tag.DEFAULT,
				null,

				new Controller());
	}






	/**
	 * Render this Sprite on the GraphicsContext gc.
	 */
	@Override public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		/* 
		 * We override the GameObject render method because the player is not represented by a specific sprite but is
		 * simply a full circle which has to be adapted to the size of the maze.
		 */
		double playerScale = MARGIN * maze.calculateWallSize(windowWidth, windowHeight);

		gc.fillOval(position.x, windowHeight - position.y, playerScale, playerScale);
	}

}
