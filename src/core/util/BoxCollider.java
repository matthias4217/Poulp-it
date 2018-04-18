package core.util;

import java.util.Arrays;

/**
 * This is a particular case of Collider where the bounds are a rectangle.
 * This is what you'll want to use in most cases.
 *
 * @author Raph
 *
 */
public class BoxCollider extends Collider {

	protected float width;
	protected float height;
	
	
	/* Constructor */
	public BoxCollider(float width, float height) {		// XXX
		super();
		nbPoints = 4;
		this.width = width;
		this.height = height;
		Vector2[] pointsArray = new Vector2[4];
		pointsArray[0] = new Vector2(0, 0);					// Top left
		pointsArray[1] = new Vector2(width, 0); 			// Top right
		pointsArray[2] = new Vector2(width, -height);		// Bottom right
		pointsArray[3] = new Vector2(0, -height);			// Bottom left
		setPointsArray(pointsArray);
	}


	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}



	public Vector2 getTopLeft() {
		return pointsArray[0];
	}

	public Vector2 getTopRight() {
		return pointsArray[1];
	}

	public Vector2 getBottomLeft() {
		return pointsArray[3];
	}

	public Vector2 getBottomRight() {
		return pointsArray[2];
	}




	@Override public String toString() {
		return "BoxCollider" + Arrays.toString(pointsArray);
	}


}
