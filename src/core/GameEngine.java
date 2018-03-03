package core;

import java.util.LinkedList;

import content.GameManager;
import content.GameObject;
import content.Player;
import content.Tile;
import core.exceptions.MultipleGameEngineException;
import core.scripts.MonoBehavior;
import core.util.*;
import levels.Level0;

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
	static Tile[] tiles;


	/* Constructor */
	public GameEngine() throws MultipleGameEngineException {
		if (alreadyExist) {
			throw new MultipleGameEngineException();
		}
		alreadyExist = true;
	}

	public void init(int nbPlayers) {
		/* Initialize the game */

		// Imports the level
		Level0 lvl0 = new Level0();
		tiles = lvl0.tiles;
		System.out.println(tiles);

		// Setting up the players array and adding the players to the GameObjects list
		players = new Player[nbPlayers];
		for (int i = 0; i < nbPlayers; i++) {
			Vector2 spawnPosition = new Vector2(10*(i+1), 20*(i+1));
			Player playerI = new Player(spawnPosition, 10, null);
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
			gameManager.apply();
		}
		// Updating all GameObjects
		for (GameObject gameObject: allGameObjects) {
			gameObject.update();
		}
	}






	/**
	 * Cast a ray starting from rayOrigin, in direction and with a specified length.
	 * @return a RaycastHit containing the information about what was hit by the ray.
	 */
	public static RayCastHit raycast(Vector2 rayOrigin, Vector2 direction, float length, Layer collisionMask) {
		Ray ray = new Ray(rayOrigin, direction, length);
		for (GameObject gameObject: allGameObjects) {
			if (gameObject.layer == collisionMask) {
				
			}
			for (Line line: gameObject.collider.pointsArray) {
				checkCollision(ray, line);
			}
		}
	}


}
