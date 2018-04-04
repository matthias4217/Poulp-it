package levels;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import core.util.Vector2;

/**
 * Class which represents a tile.
 *
 * @author matthias
 *
 */
public class Tile {

	/**
	 * The size of the side of a tile in the gamespace.
	 * This constant makes the link between the float coordinates used by the game engine and the discrete nature of tiles.
	 */
	public static final float TILE_SIZE = 50f;

	/*
	 * It can be converted to the position of non-tile objects by multiplying it by TILE_SIZE.
	 *
	 * Is it just the position in the grid ? (tileMatrix in Level)
	 */
	private Vector2 position;

	/*
	 * It is linked to an image, but the image must not be duplicated;
	 * for now I don't care, just to test the implementation.
	 */
	public ImageView sprite;

	/**
	 * The type of tile (full block, slope, etc)
	 */
	public TileType type;



	/**
	 * @param xInt			The x coordinate on a grid of this Tile
	 * @param yInt			The y coordinate on a grid of this Tile
	 * @param imageView		The appearance of this Tile				@@@ Can be automatically linked to TileType
	 * @param type			The type of the Tile
	 */
	public Tile(int xInt, int yInt, ImageView imageView, TileType type) {
		this.position = new Vector2(TILE_SIZE * xInt, TILE_SIZE * yInt);
		this.sprite = imageView;
		this.type = type;
	}



	/*
	 * Render this Sprite on the GraphicsContext gc.
	 * It may be optimized if we don't use getImage()
	 */
	public void render(GraphicsContext gc) {
		//System.out.println("x: " + position.x + ", y: " + position.y);
		gc.drawImage(sprite.getImage(), position.x,	position.y);
	}



	@Override
	public String toString() {
		return type.toString() + ": " + position.toString();
	}



	/*
	 * Enumeration of the different possible Tiles
	 */
	public enum TileType {
		EMPTY,
		SQUARE,
		TRIANGLE_TOP_RIGHT,
		TRIANGLE_TOP_LEFT,
		TRIANGLE_DOWN_RIGHT,
		TRIANGLE_DOWN_LEFT
	}
}
