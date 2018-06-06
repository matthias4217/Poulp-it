package content.platformer;

import core.util.Vector2;

/**
 * Struct like class which stores information related to collision of a GameObject.
 *
 * @author Sebastian Lague
 */
public class CollisionInfo {

	/**
	 * Tell in which directions there are collisions
	 */
	public boolean above, below; 
	public boolean left, right;


	/**
	 * Is the GameObject climbing a slope
	 */
	public boolean climbingSlope; 

	/**
	 * Is the GameObject descending a slope
	 */
	public boolean descendingSlope; 

	/**
	 * Indicates if the Object is currently falling from a maxAngleSlope
	 */
	public boolean slidingDownMaxSlope; 


	/**
	 * Angle of the slope encountered and previous one
	 */
	public float slopeAngle, slopeAngleOld; 

	/**
	 * The normal vector to the slope
	 */
	public Vector2 slopeNormal;

	/**
	 * MoveAmount of the GameObject in the previous frame
	 */
	public Vector2 moveAmountOld; 

	/**
	 * Direction faced by the GameObject: -1->left, 1->right
	 */
	public int faceDir;

	/**
	 * Is the GameObject currently falling through a platform
	 */
	public boolean fallingThroughPlatform;



	/* Constructor */
	public CollisionInfo() {
		reset();
	}



	public void reset() {
		above = below = false;
		left = right = false;
		climbingSlope = false;
		descendingSlope = false;
		slidingDownMaxSlope = false;
		slopeNormal = Vector2.ZERO();

		slopeAngleOld = slopeAngle;
		slopeAngle = 0;
	}

}
