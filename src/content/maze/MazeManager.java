package content.maze;

import content.GameManager;
import core.PlayerInput;

/**
 * A GameManager which ensure there is no inconsistancy between the Maze object and the player in it.
 * It is necessary since the Maze size on screen is dependant of its size.
 * 
 * @author Raph
 *
 */
public class MazeManager implements GameManager {





	@Override
	public void apply(float deltaTime, PlayerInput gameInformation, PlayerInput previousPlayerInput) {
		// TODO
	}

}
