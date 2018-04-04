package core.util;

/**
 * A class which stores information related to collision of a GameObject.
 *
 * @author Sebastian Lague
 */
public class CollisionInfo {

	public boolean above, below;				// | Tell in which directions there are collisions
	public boolean left, right;					// |

	public boolean climbingSlope;				// Is the GameObject climbing a slope
	public boolean descendingSlope;				// Is the GameObject descending a slope
	public boolean slidingDownMaxSlope;			// Indicates if the Object is currently falling from a maxAngleSlope

	public float slopeAngle, slopeAngleOld;		// Angle of the slope encountered and previous one
	public Vector2 slopeNormal;					// The normal vector to the slope @@@ (en vrai je sais pas trop à quoi ça sert)
	public Vector2 moveAmountOld;				// MoveAmount of the GameObject in the previous frame
	public int faceDir;							// Direction faced by the GameObject: -1->left, 1->right
	public boolean fallingThroughPlatform;		// Is the GameObject currently falling through a platform



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
