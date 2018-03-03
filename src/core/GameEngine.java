package core;

import java.io.IOException;
import java.util.LinkedList;

import content.GameManager;
import content.GameObject;
import content.Player;
import content.Tile;
import core.exceptions.MultipleGameEngineException;
import core.scripts.MonoBehavior;
import core.util.*;
import levels.Level;
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

	public void init(int nbPlayers) throws IOException {
		/* Initialize the game */

		// Imports the level
		System.out.println("Beginning level importation...");
		/*
		Level0 lvl0 = new Level0();
		tiles = lvl0.tiles;
		System.out.println(tiles);
		*/
		LevelFileParser levelParser = new LevelFileParser("/home/mondrak/eclipse-workspace/projet-dev/levels/level0.txt");
		System.out.println("levelparser: " + levelParser);
		Level level = levelParser.toLevel();
		tiles = level.tiles;

		// Setting up the players array and adding the players to the GameObjects list
		players = new Player[nbPlayers];
		for (int i = 0; i < nbPlayers; i++) {
			Vector2 spawnPosition = new Vector2(50*(i+1), 100*(i+1));
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
	public static RayCastHit raycast(Vector2 rayOrigin, Vector2 direction, float length, String collisionMask) {
		Ray ray = new Ray(rayOrigin, direction, length);
		for (GameObject gameObject: allGameObjects) {
			if (gameObject.getLayer() = collisionMask) {

			}
			for (Line line: gameObject.collider.pointsArray) {
				checkCollision(ray, line);
			}
		}
	}


}
