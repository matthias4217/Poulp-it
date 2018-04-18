package core;

import javafx.scene.canvas.GraphicsContext;
import levels.Tile;
import content.GameObject;

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
	 * @param gc			- the GraphicContext on which the rendering will be done
	 * @param windowWidth	- the width of the window
	 * @param windowHeight	- the height of the window 
	 */
	public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		for (Tile tile: GameEngine.level.tileList) {		// XXX
			tile.render(gc);
		}

		for (GameObject gameObject: GameEngine.allGameObjects) {		// XXX
			System.out.println("Rendering GameObject " + gameObject + " on " + gameObject.position);
			gameObject.render(gc, windowWidth, windowHeight);
		}
		
		for (Renderable gizmo: GameEngine.debugElements) {
			gizmo.render(gc, windowWidth, windowHeight);
		}



	}



}
