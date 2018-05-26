package content.maze;

import content.GameObject;
import content.Layer;
import content.Tag;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;
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

	public static float TIME_BETWEEN_TWO_STEPS = 0.04f;

	/**
	 * The color of the player
	 */
	public static Color PLAYER_COLOR = Color.CHARTREUSE;

	/**
	 * The ratio of the player dimensions compared to one "tile"'s dimensions
	 */
	public static float PLAYER_TILE_RATIO = 0.5f;
	private static float K = (1 - PLAYER_TILE_RATIO) / 2;



	public int x;
	public int y;

	private Maze maze;		// Yes, the maze object is actually included in the player object

	private float moveCooldown = 0;		// The time left before the player is able to move again



	public PlayerMaze(int mazeWidth, int mazeHeight) {
		this(mazeWidth, mazeHeight, false);
	}

	public PlayerMaze(int mazeWidth, int mazeHeight, boolean mazeIsFantastic) {
		super(null, null, Layer.DEFAULT, Tag.DEFAULT, null);

		maze = new Maze(mazeWidth, mazeHeight, mazeIsFantastic);
	}



	@Override
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput)
			throws InvalidArgumentsException {

		if (moveCooldown <= 0) {		// if we're not in move cooldown
			float xInput = Vector2.dotProduct(playerInput.directionalInput, Vector2.RIGHT());
			float yInput = Vector2.dotProduct(playerInput.directionalInput, Vector2.UP());

			if (xInput != 0 && yInput == 0) {
				int direction = (int) Math.signum(xInput);
				if (maze.canMoveLeftRight(x, y, direction)) {
					x += direction;
					moveCooldown = TIME_BETWEEN_TWO_STEPS;
				}
			} else if (xInput == 0 && yInput != 0) {
				int direction = (int) Math.signum(yInput);
				if (maze.canMoveUpDown(x, y, direction)) {
					y -= direction;
					moveCooldown = TIME_BETWEEN_TWO_STEPS;
				}
			}
		} else {
			moveCooldown -= deltaTime;
		}

	}




	/**
	 * Render this Sprite on the GraphicsContext gc.
	 */
	@Override public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		/* 
		 * We override the GameObject render method because the player is not represented by a specific sprite but is
		 * simply a full circle which has to be adapted to the size of the maze.
		 */
		this.maze.render(gc, windowWidth, windowHeight);


		double positionX = maze.x0 + (x + K) * maze.wallSize;
		double positionY = maze.y0 + (y + K) * maze.wallSize;

		double playerScale = PLAYER_TILE_RATIO * maze.wallSize;

		gc.setFill(PLAYER_COLOR);
		gc.fillOval(positionX, positionY, playerScale, playerScale);
	}

}
