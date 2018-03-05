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
 * @author matthias
 *
 */
public class GraphicManager {

	public void render(GraphicsContext gc) {
		// Shows the game on screen

		Tile[] tiles = GameEngine.tiles;
		for (Tile tile: tiles) {
			// System.out.println("Rendering tile " + tile);
			tile.render(gc);
		}

		LinkedList<GameObject> allGameObjects = GameEngine.allGameObjects;
		for (GameObject gameObject: allGameObjects) {
			// System.out.println("Rendering " + gameObject + " on " + gameObject.position);
			gameObject.render(gc);
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
