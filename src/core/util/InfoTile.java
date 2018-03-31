package core.util;

import java.util.LinkedList;

import content.Tile.TileType;
import content.GameObject;

/**
 * @@@
 * 
 * @author Raph
 *
 */
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
