/**
 *
 */
package levels;


import content.Tile;
import content.Tile.TileType;
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
		tiles[0] = new Tile(0, 0, iv_tile_down, TileType.SQUARE);
		tiles[1] = new Tile(1, 1, iv_tile_down, TileType.SQUARE);
	}
}
