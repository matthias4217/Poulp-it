/**
 *
 */
package content;

import core.util.Vector2;

/**
 * @author matthias
 *
 */
public class Tile {
	Vector2 position;
	//it is linked to an image, but the image must not be duplicated
	String type;

	public Tile(Vector2 position, String type) {
		this.position = position;
		this.type = type;
	}

}
