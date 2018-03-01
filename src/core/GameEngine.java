package core;

import java.util.LinkedList;

import content.GameManager;
import content.GameObject;
import content.Player;
import core.exceptions.MultipleGameEngineException;
import core.scripts.MonoBehavior;

/**
 * Manage the flow of the game; one instance.
 * 
 * @author Raph
 *
 */
public class GameEngine {

	static boolean alreadyExist = false;		// To ensure there can be only one instance of GameEngine created

	LinkedList<GameObject> allGameObjects = new LinkedList<GameObject>();
	LinkedList<GameManager> allGameManagers = new LinkedList<GameManager>();
	Player[] players;		// Convenient access to the players (since this array contains references)


	public GameEngine(int nbPlayers) throws MultipleGameEngineException {
		if (alreadyExist) {
			throw new MultipleGameEngineException();
		}
		alreadyExist = true;
		
		players = new Player[nbPlayers];
		for (int i = 0; i < nbPlayers; i++) {
			Player playerI = new Player(10, null);
			players[i] = playerI;
			allGameObjects.add(playerI);
		}
		
		
		
	}

	public void init() {
		/* Initialize the game */
		
		
		
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


	
	
}
