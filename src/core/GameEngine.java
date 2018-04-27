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
import core.util.Annex.Direction;
import levels.Level;
import levels.Tile.TileType;

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
	public static LinkedList<GameObject> allGameObjects = new LinkedList<GameObject>();

	/**
	 * A list of all elements that can be rendered for debug purpose (ex: rays)
	 */
	public static LinkedList<Renderable> debugElements = new LinkedList<Renderable>();

	/**
	 * A convenient access to the players (since the array contains references)
	 */
	static Player[] players;


	public static Level level;

	/**
	 * The lenght of a tile in window coordinates.
	 * It is changed in order to change the zoom of the camera.
	 */
	public static float tileSize = 32;

	/**
	 * A map which associates to each tile what GameObject is there
	 */
	@Deprecated
	static Map<int[], LinkedList<GameObject>> gridReferences = new HashMap<int[], LinkedList<GameObject>>();


	/**
	 * tileReferences[i][j] contains the list of GameObjects which are in the tile (i, j).
	 * null if there is no GameObject in this tile.
	 * 
	 * It is used for collision detection
	 */
	static LinkedList<GameObject>[][] tileReferences;		// TODO set this each frame







	public static final LinkedList<GameObject> emptyList = new LinkedList<GameObject>();

	/**
	 * This Map associates each TileType with its associated Collider (considering tileSize)
	 */
	public static final Map<TileType, Collider> TILE_TO_COLLIDER = new HashMap<TileType, Collider>();

	private static void initializeTILE_TO_COLLIDER() throws InvalidArgumentsException {
		TILE_TO_COLLIDER.put(TileType.EMPTY, null);

		Vector2[] squareColliderArray = {
				Vector2.ZERO(),
				new Vector2(tileSize, 0),
				new Vector2(tileSize, -tileSize),
				new Vector2(0, -tileSize)
		};
		TILE_TO_COLLIDER.put(TileType.SQUARE, new Collider(squareColliderArray));

		Vector2[] triangle1ColliderArray = {
				Vector2.ZERO(),
				new Vector2(tileSize, 0),
				new Vector2(0, -tileSize)
		};
		TILE_TO_COLLIDER.put(TileType.TRIANGLE_TOP_LEFT, new Collider(triangle1ColliderArray));

		Vector2[] triangle2ColliderArray = {
				Vector2.ZERO(),
				new Vector2(tileSize, 0),
				new Vector2(tileSize, -tileSize),
		};
		TILE_TO_COLLIDER.put(TileType.TRIANGLE_TOP_RIGHT, new Collider(triangle2ColliderArray));

		Vector2[] triangle3ColliderArray = {
				Vector2.ZERO(),
				new Vector2(tileSize, -tileSize),
				new Vector2(0, -tileSize)
		};
		TILE_TO_COLLIDER.put(TileType.TRIANGLE_DOWN_LEFT, new Collider(triangle3ColliderArray));

		Vector2[] triangle4ColliderArray = {
				new Vector2(tileSize, 0),
				new Vector2(tileSize, -tileSize),
				new Vector2(0, -tileSize)
		};
		TILE_TO_COLLIDER.put(TileType.TRIANGLE_DOWN_RIGHT, new Collider(triangle4ColliderArray));
	}









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
	 * @throws InvalidArgumentsException 
	 */
	@SuppressWarnings("unchecked")
	public void init(int nbPlayers, String levelName) throws IOException, InvalidArgumentsException {
		initializeTILE_TO_COLLIDER();

		// Importing the level
		System.out.println("Beginning level importation...");
		level = new Level("levels/" + levelName + ".txt");

		// Initializing the tileReferences matrix
		tileReferences = (LinkedList<GameObject>[][]) new LinkedList[50][50];		// XXX
		for (int i = 0; i < tileReferences.length; i++) {
			for (int j = 0; j < tileReferences[i].length; j++) {
				tileReferences[i][j] = emptyList;
			}
		}


		// Instantiating players and adding them to the players array
		System.out.println("Instanciating players...");
		players = new Player[nbPlayers];
		for (int i = 0; i < nbPlayers; i++) {
			Vector2 spawnPosition = new Vector2((float)Launcher.WINDOW_WIDTH / 2, (float) Launcher.WINDOW_HEIGHT / 2);
			spawnPosition.translate(Vector2.RIGHT().multiply(100 * i));
			spawnPosition = new Vector2(600, 800);		//
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
	 * @throws InvalidArgumentsException 
	 *
	 */
	public void update(float deltaTime, PlayerInput playerInput, GameInformation gameInformation)
			throws InvalidArgumentsException {

//		System.out.println("Current GameInformation: " + gameInformation);
		debugElements.clear();

		// Applying all GameManagers
		for (GameManager gameManager: allGameManagers) {
			gameManager.apply(deltaTime, playerInput);
		}

		// Updating all GameObjects
		for (GameObject gameObject: allGameObjects) {
			gameObject.update(deltaTime, playerInput);
		}

		// Updating the tileReferences matrix
		// TODO

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
	 * @throws InvalidArgumentsException 
	 */

	public static RaycastHit raycast(Vector2 rayOrigin, Direction direction, float length, Layer collisionMask)
			throws InvalidArgumentsException {		
		RaycastHit result = null;

		Ray ray = new Ray(rayOrigin, direction, length);
		debugElements.add(ray);

		// The coordinates in the grid this ray is casted from
		int[] tileOrigin = toTileCoordinates(rayOrigin);

		// The coordinates in the grid this ray ends
		int[] tileEnding = toTileCoordinates(ray.getEndingPoint());

		System.out.println("Raycast " + direction + " from (" + tileOrigin[0] + ", " + tileOrigin[1] +
				") to (" + tileEnding[0] + ", " + tileEnding[1] + "); length = " + length);


		// Now we traverse the tiles line from tileOrigin to tileEnding.
		// Beware: generic code difficult to read...

		// Moving horizontally (0) or vertically (1)?
		int var = (direction == Direction.LEFT || direction == Direction.RIGHT) ? 0 : 1;
		// The index of the column/row which is fixed
		int fixed = tileOrigin[1-var];
		// Moving which way?
		int increment = (direction == Direction.DOWN || direction == Direction.LEFT) ? -1 : 1;

		for (int k = tileOrigin[var]; increment * (tileEnding[var] - k) >= 0; k += increment) {
			// Setting the current tile
			int currentTileX = (1-var)*k + var*fixed;
			int currentTileY = var*k + (1-var)*fixed;


			// Collisions with other GameObjects	TODO
			
			for (GameObject gameObject: tileReferences[currentTileX][currentTileY]) {
				//				ray.collision(gameObject);
			}


			// Collisions with the tile
			
			TileType tileTypeCurrent = level.getTile(currentTileX, currentTileY);
			Collider colliderTile = TILE_TO_COLLIDER.get(tileTypeCurrent);
//			debugElements.add(colliderTile);
			Vector2 colliderOrigin = toWorldCoordinates(currentTileX, currentTileY);
			
			Vector2 normalFromHit = ray.collision(colliderTile, colliderOrigin);

			if (normalFromHit != null) {		// if there is a collision
				result = new RaycastHit(null, ray.getLength(), normalFromHit);
				// XXX LAST TILE ???
				
			}
		}
		return result;
	}

	/**
	 * @param A
	 * @return	the grid coordinates corresponding with the position of the point A
	 */
	private static int[] toTileCoordinates(Vector2 A) {
		int[] result = {(int) Math.floor(A.x / tileSize), (int) Math.floor((Launcher.WINDOW_HEIGHT - A.y) / tileSize)};
		return result;
	}

	/**
	 * @param xTile
	 * @param yTile
	 * @return	the origin point of the tile (x, y) (top-left)
	 */
	private static Vector2 toWorldCoordinates(int xTile, int yTile) {
		return new Vector2(xTile * tileSize, yTile * tileSize);
	}





}
