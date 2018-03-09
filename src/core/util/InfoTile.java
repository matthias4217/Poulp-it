/**
 * 
 */
package core.util;

import java.util.LinkedList;

import content.GameObject;
import content.Tile.TileType;

/**
 * @author Raph
 *
 */
public final class InfoTile {

	TileType tileType;
	LinkedList<GameObject> gameObjects;
	/**
	 * @param tileType
	 * @param gameObjects
	 */
	public InfoTile(TileType tileType, LinkedList<GameObject> gameObjects) {
		this.tileType = tileType;
		this.gameObjects = gameObjects;
	}


}
