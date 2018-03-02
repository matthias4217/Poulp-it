/**
 *
 */
package levels;

import javafx.scene.image.Image;

import content.Tile;
import content.Tile.TileType;
import core.util.Vector2;

/**
 * @author matthias
 *
 */
public class Level0 {


	public Tile[] tiles = new Tile[2];
	Tile tile_0 = new Tile(new Vector2(0, 0), new Image("resources/tile-down.png"), TileType.SQUARE);
	Tile tiles_1 = new Tile(new Vector2(1,0), new Image("resources/tile-down.png"), TileType.SQUARE);

}
