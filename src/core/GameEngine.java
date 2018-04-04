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
import core.exceptions.InvalidArgumentsException;
import core.exceptions.MultipleGameEngineException;
import core.util.*;
import levels.LevelOld;

/**
 * Manages the flow of the game; one instance, located on the server.
 * TODO more precise and detailed description
 *
 * @author Raph
 *
 */
public class GameEngine {

	private static boolean alreadyExist = false;		// To ensure there can be only one instance of GameEngine created

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
	Player[] players;

	@Deprecated
	static Tile[] tiles;

	/**
	 * A map which associates to tiles that contains GameObjects to these GameObjects.
	 * It is used to detect collisions other than those with the level itself.
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
		LevelFileParser levelParser = new LevelFileParser("levels/" + levelName + ".txt");
		LevelOld level = levelParser.toLevel();
		tiles = level.tiles;
		InfoTile[][] grid = levelParser.levelGrid;

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
	public void update(float deltaTime, PlayerInput playerInput, GameInformation gameInformation) throws InvalidArgumentsException {

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
	 * 
	 * @throws InvalidArgumentsException if direction is null
	 */

	public static RaycastHit raycast(Vector2 rayOrigin, Vector2 direction, float length, Layer collisionMask) throws InvalidArgumentsException {
		// TODO
		Ray ray = new Ray(rayOrigin, direction, length);

		for (GameObject gameObject: allGameObjects) {

			if (gameObject.layer == collisionMask) {

			}
			/*
			for (Line line: gameObject.collider.pointsArray) {
				checkCollision(ray, line);
			}
			*/
		}
		
		
		
		
		
		
		
		return null;
	}



}
