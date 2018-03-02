package core;

import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;

import content.GameObject;
import content.Tile;
import core.GameEngine;

/**
 * Client
 * This class communicates with the GameEngine on the server, and draw on the client' screen with the received data.
 *
 * @author Raph
 *
 */
public class GraphicManager {

	public void render(GraphicsContext gc) {
		// Shows the game on screen
		LinkedList<GameObject> allGameObjects = GameEngine.allGameObjects;
		Tile[] tiles = GameEngine.tiles;
		for (GameObject gameObject: allGameObjects) {
			System.out.println("Rendering " + gameObject + " on " + gameObject.position);
			gameObject.render(gc);
		}
		for (Tile tile: tiles) {
			tile.render(gc);
		}
	}


	/*
	 * Manage keyboard events
	 * Envoi des donn�es au GameEngine
	 *
	 * Interface utilisateur
	 * Affichage
	 *
	 *
	 *
	 *
	 */

}
