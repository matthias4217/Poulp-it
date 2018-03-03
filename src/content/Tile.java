package content;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import core.util.Vector2;

/**
 * @author matthias
 *
 */
public class Tile {
	
	/**
	 * The size of the side of a tile in the gamespace.
	 * This constant makes the link between the float coordinates used by the game engine and the discrete nature of tiles.
	 */
	public static final float TILE_SIZE = 10f;
	
	
	/*
	 * It can be converted to the position of non-tile objects by multiplying it by TILE_SIZE
	 */
	private Vector2 position;

	/* 
	 * It is linked to an image, but the image must not be duplicated
	 * for now I don't care, just to test the implementation
	 */
	public ImageView sprite;
	
	/**
	 * The type of tile (full block, slope, etc)
	 */
	public TileType type;

	
	
	public Tile(int xInt, int yInt, ImageView imageView, TileType type) {
		this.position = new Vector2(TILE_SIZE * xInt, TILE_SIZE * yInt);
		this.sprite = imageView;
		this.type = type;
	}

	
	/* 
	 * Render this Sprite on the GraphicsContext gc.
	 * It may be optimized if we don't do a getImage()
	 */
	public void render(GraphicsContext gc) {
		System.out.println("x: " + position.x + ", y: " + position.y);
		gc.drawImage(sprite.getImage(), position.x,	position.y);
	}

	
		
	
	
	
	public String toString() {
		return type.toString() + " : " + position.toString();
	}


	

	
	/*
	 * List of the values type can be
	 */
	public enum TileType {
		SQUARE,
		TRIANGLE_UP_RIGHT,
		TRIANGLE_UP_LEFT,
		TRIANGLE_DOWN_RIGHT,
		TRIANGLE_DOWN_LEFT,
		
	}
}
