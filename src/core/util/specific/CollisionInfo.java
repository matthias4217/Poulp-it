package core.util.specific;

import core.util.Vector2;

/**
 * Structure which stores many information about a detected collision.
 * Used in the Controller script. 
 * 
 * @author Raph
 *
 */
public class CollisionInfo {

	public boolean above, below;				// | Tell in which directions there is collision
	public boolean left, right;					// |

	public boolean climbingSlope;				// Is the Object climbing a slope
	public boolean descendingSlope;				// Is the Object descending a slope
	public boolean slidingDownMaxSlope;			// Indicates if the Object is currently falling from a maxAngleSlope ; @@@ On va surement le supprimer

	public float slopeAngle, slopeAngleOld;		// Angle of the slope encountered and previous one
	public Vector2 slopeNormal;					// The normal vector to the slope @@@ (en vrai je sais pas trop à quoi ça sert)
	public Vector2 moveAmountOld;				// @@@
	public int faceDir;							// Direction faced by the Object: -1->left, 1->right
	public boolean fallingThroughPlatform;

	public void reset() {
		above = below = false;
		left = right = false;
		climbingSlope = false;
		descendingSlope = false;
		slidingDownMaxSlope = false;
		slopeNormal = Vector2.zero;

		slopeAngleOld = slopeAngle;
		slopeAngle = 0;
	}
}
