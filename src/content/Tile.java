/**
 *
 */
package content;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
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
	public ImageView sprite;
	public TileType type;
	public int tilesize;

	public Tile(Vector2 position, ImageView imageview, int tilesize, TileType type) {
		this.position = position;
		this.sprite = imageview;
		this.tilesize = tilesize;
		this.type = type;
	}

	public void render(GraphicsContext gc) {
		/* Render this Sprite on the GraphicsContext gc.
		 * It may be optimized if we don't do a getImage()
		 */
		System.out.println("x: " + position.x * tilesize + ", y: " + position.y * tilesize);
		gc.drawImage(sprite.getImage(), position.x * tilesize,
				position.y * tilesize);
	}

	public String toString() {
		return type.toString() + " : " + position.toString();
	}

	/*
	 * List of the values type can be
	 */
	public enum TileType { SQUARE, TRIANGLE_UP_RIGHT, TRIANGLE_UP_LEFT,
		TRIANGLE_DOWN_RIGHT, TRIANGLE_DOWN_LEFT}
}
