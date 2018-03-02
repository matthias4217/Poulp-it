package core.util;

import core.exceptions.InvalidArgumentsException;

/**
 * This class represents a collision mask.
 * In practice, this is simply a list of points which define a polygon.
 * If you want rectangular bounds, use the subclass BoxCollider.
 * The user is trusted to create valid forms.
 *
 * Note: A line (two points) is valid.
 *
 * @author Raph
 *
 */
public class Collider {

	protected Vector2[] pointsArray;
	public Vector2 getPoint(int i) {
		return pointsArray[i];
	}
	protected void setPointsArray(Vector2[] pointsArray) {
		this.pointsArray = pointsArray;
	}

	protected int nbPoints;
	public int getNbPoints() {
		return nbPoints;
	}


	/* Constructor */
	public Collider(Vector2[] pointsArray) throws InvalidArgumentsException {
		if (pointsArray.length < 2) {
			throw new InvalidArgumentsException("The array of points for a CollisionBounds must be at least two");
		}

		this.pointsArray = pointsArray;
		nbPoints = pointsArray.length;
	}

	protected Collider() {
		/* This constructor is only used by the subclass BoxCollider. */
	}


}
