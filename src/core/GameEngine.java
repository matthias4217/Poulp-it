package core;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import content.GameManager;
import content.GameObject;
import content.Layer;
import content.Player;
import core.exceptions.InvalidArgumentsException;
import core.exceptions.MultipleGameEngineException;
import core.util.*;
import core.util.Ray.Direction;
import levels.Level;

/**
 * Manages the flow of the game; one instance, located on the server.
 * TODO more precise and detailed description
 *
 * @author Raph
 *
 */
public class GameEngine {

	private static boolean alreadyExist = false;	// To ensure there can be only one instance of GameEngine created

	/**
	 * The list of all active GameManagers currently on the scene
	 */
	static LinkedList<GameManager> allGameManagers = new LinkedList<GameManager>();

	/**
	 * The list of all active GameObjects currently on the scene
	 */
	static LinkedList<GameObject> allGameObjects = new LinkedList<GameObject>();

	/**
	 * A convenient access to the players (since the array contains references)
	 */
	static Player[] players;


	static Level level;

	/**
	 * The lenght of a tile in window coordinates.
	 * It is changed in order to change the zoom of the camera.
	 */
	static float tileSize = 32;

	/**
	 * A map which associates to each tile what GameObject is there
	 */
	static Map<int[], LinkedList<GameObject>> gridReferences = new HashMap<int[], LinkedList<GameObject>>();



	/**
	 * Constructor
	 */
	public GameEngine() throws MultipleGameEngineException {
		if (alreadyExist) {
			throw new MultipleGameEngineException();
		}
		alreadyExist = true;
	}


	/**
	 * Initializes the game
	 *
	 * @param nbPlayers
	 * @param levelName
	 * @throws IOException
	 */
	public void init(int nbPlayers, String levelName) throws IOException {

		// Importing the level
		System.out.println("Beginning level importation...");
		level = new Level("levels/" + levelName + ".txt");
		//tiles = level.tileList;
		//InfoTile[][] grid = level.infoTileMatrix;


		// Instanciating players and adding them to the players array
		System.out.println("Instanciating players...");
		players = new Player[nbPlayers];
		for (int i = 0; i < nbPlayers; i++) {
			Vector2 spawnPosition = new Vector2(500*(i+1), 50*(i+1));
			Player playerI = new Player(spawnPosition, 10);
			players[i] = playerI;
			allGameObjects.add(playerI);
		}
		System.out.println("Players instanciation finished");


		// ----


	}


	/**
	 * This method is called each frame of the game and updates all game elements.
	 * This also updates the GameInformation object in order to then transmit the new state of the game to the clients.
	 *
	 * @param deltaTime 		- the time in seconds it took to complete the last frame
	 * @param playerInput		- the current input of the player 		(TODO gérer plusieurs joueurs)
	 * @param gameInformation	- the current state of the game
	 *
	 */
	public void update(float deltaTime, PlayerInput playerInput, GameInformation gameInformation) {

		System.out.println("Current GameInformation:" + playerInput);

		// Applying all GameManagers
		for (GameManager gameManager: allGameManagers) {
			gameManager.apply(deltaTime, playerInput);
		}

		// Updating all GameObjects
		for (GameObject gameObject: allGameObjects) {
			gameObject.update(deltaTime, playerInput);
		}

	}





	/**
	 * Casts a ray which can detect collisions
	 *
	 * @param rayOrigin		- the origin of the ray in absolute coordinates
	 * @param direction		- the direction in which the ray is cast
	 * @param length		- the length of the ray
	 * @param collisionMask	- the Layer on which collisions will be detected
	 *
	 * @return a RaycastHit containing the information about what was hit by the ray.
	 */

	public static RaycastHit raycast(Vector2 rayOrigin, Direction direction, float length, Layer collisionMask) {
		Ray ray = new Ray(rayOrigin, direction, length);
		// The coordinates in the grid this ray is casted from
		int[] tileOrigin = toGridCoordinates(rayOrigin);

		System.out.println("Raycast from tile: " + tileOrigin[0] + ", " + tileOrigin[1]);
		// The coordinates in the grid this ray ends
		int[] tileEnding = toGridCoordinates(ray.getEndingPoint());

		// Now we traverse the tiles line from tileOrigin to tileEnding.

		// Moving horizontally or vertically?
		int var = (direction == Direction.LEFT || direction == Direction.RIGHT) ? 0 : 1; 
		int fixed = tileOrigin[1-var];		// The index of the column or row which is fixed

		int increment = (direction == Direction.DOWN || direction == Direction.LEFT) ? 1 : -1;

		for (int k = tileOrigin[var]; k < tileEnding[var]; k += increment) {
			// Setting the current tile
			int[] currentTile = {(1-var)*k + var*fixed, var*k + (1-var)*fixed};

			// Collisions with other GameObjects
			if (gridReferences.containsKey(currentTile)) {		// If the current tile contains GameObject(s)
				for (GameObject gameObject: gridReferences.get(currentTile)) {
					ray.collision(gameObject);
				}
			}


			// Collisions with tiles





		}






		return null;
	}

	/**
	 * @param A
	 * @return the grid coordinates corresponding with the position of the point A
	 */
	private static int[] toGridCoordinates(Vector2 A) {
		int[] result = {(int) Math.floor(A.x / tileSize), (int) Math.floor(A.y / tileSize)};
		return result;
	}





}
