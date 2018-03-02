/**
 *
 */
package levels;


import content.Tile;
import content.Tile.TileType;
import core.util.Vector2;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author matthias
 *
 */
public class Level0 {


	public static Tile[] tiles = new Tile[2];

	public Level0() {
		Image tile_down = new Image("resources/tile-down.png");
		ImageView iv_tile_down = new ImageView(tile_down);
		tiles[0] = new Tile(new Vector2(0, 0), iv_tile_down, 32, TileType.SQUARE);
		tiles[1] = new Tile(new Vector2(1, 1), iv_tile_down, 32, TileType.SQUARE);
	}
}
