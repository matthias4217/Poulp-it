package core.util;

/**
 * This is a particular case of Collider where the bounds are a rectangle.
 * This is what you'll want to use in most cases.
 *
 * @author Raph
 *
 */
public class BoxCollider extends Collider {

	/* Constructor */
	public BoxCollider(float width, float height) {		// XXX
		super();
		nbPoints = 4;
		Vector2[] pointsArray = new Vector2[4];
		pointsArray[0] = new Vector2(0, 0);
		pointsArray[1] = new Vector2(-width, 0); 
		pointsArray[2] = new Vector2(-width, -height);
		pointsArray[3] = new Vector2(0, -height);
		setPointsArray(pointsArray);
	}


	public float getWidth() {
		return pointsArray[3].x - pointsArray[0].x;
	}

	public float getHeight() {
		return pointsArray[1].y - pointsArray[1].y;
	}


	public Vector2 getTopRight() {
		return pointsArray[0];
	}

	public Vector2 getTopLeft() {
		return pointsArray[1];
	}

	public Vector2 getBottomLeft() {
		return pointsArray[2];
	}

	public Vector2 getBottomRight() {
		return pointsArray[3];
	}
	
	
	
	
	
}
