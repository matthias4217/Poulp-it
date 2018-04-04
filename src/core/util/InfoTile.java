package core.util;

import java.util.LinkedList;
import content.Tile.TileType;
import content.GameObject;

/**
 * This class stores the information about a tile.
 * 
 * @author Raph
 *
 */
@Deprecated		// Ce qu'on va faire, c'est pas ça : l'objet Level est en fait hyper simple et est une grille de Tiletype; 
				// C'est le GameEngine qui a un dictionnaire des tiles (vides) contenant des GameObjects et qui l'utilise
				// D'ailleurs, ça serait un peu con de stocker une LinkedList pour CHAQUE tile alors que 99% ont rien
public final class InfoTile {

	public TileType tileType;
	LinkedList<GameObject> gameObjects;
	
	
	
	/**
	 * @param tileType
	 */
	public InfoTile(TileType tileType) {
		this.tileType = tileType;
		this.gameObjects = new LinkedList<GameObject>();
	}


}
