/**
 *
 */
package levels;


import content.Tile;
import content.Tile.TileType;
import core.util.Vector2;

/**
 * @author matthias
 *
 */
public class Level0 {


	public static Tile[] tiles = new Tile[2];

	public void init() {
		tiles[0] = new Tile(new Vector2(0, 0), "resources/tile-down.png", 32, TileType.SQUARE);
		tiles[1] = new Tile(new Vector2(1, 0), "resources/tile-down.png", 32, TileType.SQUARE);
	}
}
