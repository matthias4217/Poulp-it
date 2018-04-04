package core;

import javafx.scene.canvas.GraphicsContext;
import content.GameObject;
import content.Tile;

/**
 * Client
 * This class communicates with the GameEngine on the server, and draw on the client' screen with the received data.
 *
 * @author Raph
 * @author matthias
 *
 */
public class GraphicManager {


	// @@@ TODO



	/**
	 * Render the game on the GraphicsContext gc.
	 * 
	 * @param gc - the GraphicContext on which the rendering will be done
	 */
	public void render(GraphicsContext gc) {

		System.out.println("Rendering...");
		
		for (Tile tile: GameEngine.tiles) {		// XXX
			tile.render(gc);
		}

		for (GameObject gameObject: GameEngine.allGameObjects) {		// XXX
			System.out.println("Rendering GameObject " + gameObject + " on " + gameObject.position);
			gameObject.render(gc);
		}



	}



}
