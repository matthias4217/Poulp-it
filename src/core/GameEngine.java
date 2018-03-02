package core;

import java.util.LinkedList;

import content.GameManager;
import content.GameObject;
import content.Player;
import core.exceptions.MultipleGameEngineException;
import core.scripts.MonoBehavior;
import core.util.*;

/**
 * Manage the flow of the game; one instance.
 *
 * @author Raph
 *
 */
public class GameEngine {

	private static boolean alreadyExist = false;		// To ensure there can be only one instance of GameEngine created

	static LinkedList<GameManager> allGameManagers = new LinkedList<GameManager>();
	static LinkedList<GameObject> allGameObjects = new LinkedList<GameObject>();
	Player[] players;		// Convenient access to the players (since the array contains references)


	/* Constructor */
	public GameEngine() throws MultipleGameEngineException {
		if (alreadyExist) {
			throw new MultipleGameEngineException();
		}
		alreadyExist = true;
	}

	public void init(int nbPlayers) {
		/* Initialize the game */

		// Setting up the players array and adding the players to the GameObjects list
		players = new Player[nbPlayers];
		for (int i = 0; i < nbPlayers; i++) {
			Player playerI = new Player(10, null);
			playerI.position = new Vector2(10 * (i+1), 20*(i+1));
			players[i] = playerI;
			allGameObjects.add(playerI);
			System.out.println("Added player at " + playerI.position);
		}

		// ----



	}




	public void update() {
		/* Called each frame */


		// Applying all GameManagers
		for (GameManager gameManager: allGameManagers) {
			gameManager.update();
		}
		// Updating all GameObjects
		for (GameObject gameObject: allGameObjects) {
			for (MonoBehavior script: gameObject.scripts) {
				script.update();
			}
		}
	}






	/**
	 * Cast a ray starting from rayOrigin, in direction and with a specified length.
	 * @return a RaycastHit containing the information about what was hit by the ray.
	 */
	public static RaycastHit raycast(Vector2 rayOrigin, Vector2 direction, float length, String collisionMask) {
		Ray ray = new Ray(rayOrigin, direction, length);
		for (GameObject gameObject: allGameObjects) {
			for (Line line: gameObject.collider.pointsArray) {
				checkCollision(ray, line);
			}
			}
		}


	}



}
