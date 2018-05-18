package levels;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

import java.util.LinkedList;

import core.exceptions.InvalidArgumentsException;
import core.util.Collider;
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
	public static final float TILE_SIZE = 64f;		// XXX

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
	 * The collider associated with a specific tileType (origin point up-left)
	 * Used for the collision system
	 * 
	 * @param tileType
	 * @return	the collider matching with the TileType
	 * @throws InvalidArgumentsException 
	 */
	@Deprecated
	public static Collider associatedCollider(TileType tileType) throws InvalidArgumentsException {
		
		// Amélioration : ne pas recalculer à chaque fois
		// -> avoir les références initialisées
		
		if (tileType == TileType.EMPTY) {
			return null;
		}
		
		// First: creating a list with the four possible points
		LinkedList<Vector2> pointsList = new LinkedList<Vector2>();
		pointsList.add(new Vector2(0, 0));					// Top left
		pointsList.add(new Vector2(TILE_SIZE, 0)); 			// Top right
		pointsList.add(new Vector2(TILE_SIZE, -TILE_SIZE));	// Bottom right
		pointsList.add(new Vector2(0, -TILE_SIZE));			// Bottom left

		// Then: removing some points according to the case
		switch (tileType) {
		case SQUARE:
			break;
		case TRIANGLE_DOWN_LEFT:
			pointsList.remove(1);
			break;
		case TRIANGLE_DOWN_RIGHT:
			pointsList.remove(0);
			break;
		case TRIANGLE_TOP_LEFT:
			pointsList.remove(2);
			break;
		case TRIANGLE_TOP_RIGHT:
			pointsList.remove(3);
			break;
		default:
			System.out.println("Tile not considered: " + tileType);
			return null;
		}
		
		
		// Transfering into a list
		Vector2[] pointsArray = new Vector2[pointsList.size()];
		pointsList.toArray(pointsArray);
		
		Collider result = new Collider(pointsArray); 
		return result;
	}




	/**
	 * Constructor
	 * 
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
		gc.drawImage(sprite.getImage(), position.x, position.y);
	}



	@Override public String toString() {
		return type.toString() + ": " + position;
	}




	/*
	 * Enumeration of the different possible Tiles
	 */
	public enum TileType {
		EMPTY,
		SQUARE,
		TRIANGLE_TOP_LEFT,
		TRIANGLE_TOP_RIGHT,
		TRIANGLE_DOWN_RIGHT,
		TRIANGLE_DOWN_LEFT
	}
}
