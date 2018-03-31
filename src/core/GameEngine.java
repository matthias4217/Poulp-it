package core;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import content.GameManager;
import content.GameObject;
import content.Layer;
import content.Player;
import content.Tile;
import content.Tile.TileType;
import core.exceptions.MultipleGameEngineException;
import core.scripts.MonoBehavior;
import core.util.*;
import levels.Level;
import levels.Level0;

/**
 * Manages the flow of the game; one instance.
 *
 * @author Raph
 *
 */
public class GameEngine {

	private static boolean alreadyExist = false;		// To ensure there can be only one instance of GameEngine created

	static LinkedList<GameManager> allGameManagers = new LinkedList<GameManager>();
	static LinkedList<GameObject> allGameObjects = new LinkedList<GameObject>();
	Player[] players;		// Convenient access to the players (since the array contains references)
	static Tile[] tiles;		//
	


	/* Constructor */
	public GameEngine() throws MultipleGameEngineException {
		if (alreadyExist) {
			throw new MultipleGameEngineException();
		}
		alreadyExist = true;
	}
	

	/**
	 * Initialize the game
	 * 
	 * @param nbPlayers
	 * @param levelName
	 * @throws IOException
	 */
	public void init(int nbPlayers, String levelName) throws IOException {
		
		// Imports the level
		System.out.println("Beginning level importation...");
		/*
		Level0 lvl0 = new Level0();
		tiles = lvl0.tiles;
		System.out.println(tiles);
		*/
		
		
		LevelFileParser levelParser = new LevelFileParser("levels/" + levelName + ".txt");
		Level level = levelParser.toLevel();
		tiles = level.tiles;
		InfoTile[][] grid = levelParser.levelGrid;


		/* A map which associates to each tile what GameObject is there */
		Map<int[], LinkedList<GameObject>> gridReferences = new HashMap<int[], LinkedList<GameObject>>();

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


	/**
	 * Called each frame
	 * 
	 * @param deltaTime		The timestamp of the current frame given in nanoseconds
	 */
	public void update(long deltaTime) {

		// Applying all GameManagers
		for (GameManager gameManager: allGameManagers) {
			gameManager.apply(deltaTime);
		}
		
		// Updating all GameObjects
		for (GameObject gameObject: allGameObjects) {
			gameObject.update(deltaTime);
		}
	
	}




	/**
	 * Cast a ray starting from rayOrigin, in direction and with a specified length.
	 * @return a RaycastHit containing the information about what was hit by the ray.
	 * 
	 * @@@ TODO !!
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