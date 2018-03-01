package core.util;

import core.exceptions.InvalidArgumentsException;

/**
 * This class represents a collision mask.
 * In practice, this is simply a list of points which define a polygon.
 * If you want rectangular bounds, use the subclass CollisionBox.
 * The user is trusted to create valid forms.
 *
 * Note: A line (two points) is valid.
 *
 * @author Raph
 *
 */
public class CollisionBounds {

	private Vector2[] pointsArray;
	public Vector2 getPoint(int i) {
		return pointsArray[i];
	}
	public void setPointsArray(Vector2[] pointsArray) {
		this.pointsArray = pointsArray;
	}

	private int nbPoints;
	public int getNbPoints() {
		return nbPoints;
	}


	/* Constructor */
	public CollisionBounds(Vector2[] pointsArray) throws InvalidArgumentsException {
		if (pointsArray.length < 2) {
			throw new InvalidArgumentsException("The array of points for a CollisionBounds must be at least two");
		}

		this.pointsArray = pointsArray;
		nbPoints = pointsArray.length;
	}

	public CollisionBounds() {

	}


}
