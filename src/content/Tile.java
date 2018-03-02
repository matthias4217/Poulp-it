/**
 *
 */
package content;

import javafx.scene.image.Image;

import core.util.Vector2;

/**
 * @author matthias
 *
 */
public class Tile {
	/*
	 * It can be converted to the position of non-tile objects by multiplying
	 * it by TILESIZE, which will be defined somewhere eventually
	 */
	public Vector2 position;

	/* it is linked to an image, but the image must not be duplicated
	 * for now I don't care, just to test the implementation
	 */
	public Image sprite;
	public TileType type;

	public Tile(Vector2 position, Image sprite, TileType type) {
		this.position = position;
		this.type = type;
		this.sprite = sprite;
	}

	/*
	 * List of the values type can be
	 */
	public enum TileType { SQUARE, TRIANGLE_UP_RIGHT, TRIANGLE_UP_LEFT,
		TRIANGLE_DOWN_RIGHT, TRIANGLE_DOWN_LEFT}
}
