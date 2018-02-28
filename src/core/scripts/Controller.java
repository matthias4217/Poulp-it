package core.scripts;

import core.util.*;
import core.util.specific.CollisionInfo;

/**
 * @@@ 
 * 
 * @author Raph
 *
 */
//@TODO: make traversable platform more logical (currently, they are traversable but have side walls)

public class Controller extends RaycastController {

	public float maxSlopeAngle = 80;		// The maximum slope angle that can be climbed or descended (obviously in degree)

	public CollisionInfo collisions;
	
	public Vector2 playerInput;

	@Override
	public void start() {
		super.start();
		collisions.faceDir = 1;

	}

	public void move(Vector2 moveAmount, boolean standingOnPlatform) {		// Called when not related to some inputs
		move(moveAmount, Vector2.zero, standingOnPlatform);
	}

	public void move(Vector2 moveAmount, Vector2 input) {
		move(moveAmount, input, false);
	}
	
	public void move(Vector2 moveAmount, Vector2 input, boolean standingOnPlatform) {
		updateRaycastOrigins();

		collisions.reset();
		collisions.moveAmountOld = moveAmount;
		playerInput = input;

		if (moveAmount.y < 0) {
			descendSlope(ref moveAmount);
		}

		if (moveAmount.x != 0) {
			collisions.faceDir = (int) Math.signum(moveAmount.x);
		}

		horizontalCollisions (ref moveAmount);
		if (moveAmount.y != 0) {
			verticalCollisions (ref moveAmount);
		}

		transform.Translate (moveAmount);

		if (standingOnPlatform) {
			collisions.below = true;
		}
	}

	void horizontalCollisions(ref Vector2 moveAmount) {
		float directionX = collisions.faceDir;
		float rayLength = Mathf.Abs(moveAmount.x) + skinWidth;		// The more we are moving, the longer the rays are

		if (Mathf.Abs(moveAmount.x) < skinWidth) {
			rayLength = 2*skinWidth;
		}

		for (int i = 0; i < horizontalRayCount; i++) {
			Vector2 rayOrigin = (directionX == -1) ? raycastOrigins.bottomLeft : raycastOrigins.bottomRight;
			rayOrigin += Vector2.up * (horizontalRaySpacing * i);
			RaycastHit2D hit = Physics2D.Raycast(rayOrigin, Vector2.right * directionX, rayLength, collisionMask);

			Debug.DrawRay(rayOrigin, Vector2.right * directionX, Color.red);

			if (hit) {

				if (hit.distance == 0) {	// If we're actually IN an obstacle; @@@ BTW has to be changed in order to crush the character.
					continue;
				}

				float slopeAngle = Vector2.Angle(hit.normal, Vector2.up);

				if (i == 0 && slopeAngle <= maxSlopeAngle) {
					if (collisions.descendingSlope) {
						collisions.descendingSlope = false;
						moveAmount = collisions.moveAmountOld;
					}
					float distanceToSlopeStart = 0;
					if (slopeAngle != collisions.slopeAngleOld) {
						distanceToSlopeStart = hit.distance - skinWidth;
						moveAmount.x -= distanceToSlopeStart * directionX;
					}
					climbSlope(ref moveAmount, slopeAngle, hit.normal);
					moveAmount.x += distanceToSlopeStart * directionX;
				}

				if (!collisions.climbingSlope || slopeAngle > maxSlopeAngle) {
					moveAmount.x = (hit.distance - skinWidth) * directionX;
					rayLength = hit.distance;		// Reducing the lenght of the next rays casted to avoid collisions further than this one

					if (collisions.climbingSlope) {
						moveAmount.y = Math.tan(collisions.slopeAngle * Math.Deg2Rad) * Math.abs(moveAmount.x);
					}

					collisions.left = (directionX == -1);
					collisions.right = (directionX == 1);
				}
			}
		}
	}

	void verticalCollisions(ref Vector2 moveAmount) {
		float directionY = Math.signum(moveAmount.y);
		float rayLength = Math.abs(moveAmount.y) + skinWidth;		// The more we are moving, the longer the rays are

		for (int i = 0; i < verticalRayCount; i++) {

			Vector2 rayOrigin = (directionY == -1) ? raycastOrigins.bottomLeft : raycastOrigins.topLeft;
			rayOrigin += Vector2.right * (verticalRaySpacing * i + moveAmount.x);
			RaycastHit2D hit = Physics2D.Raycast(rayOrigin, Vector2.up * directionY, rayLength, collisionMask);

			Debug.DrawRay(rayOrigin, Vector2.up * directionY, Color.red);

			if (hit) {
				// IMPORTANT NOTE: Do not make slopes traversable because it is not well handled and it's useless anyway
				if (hit.collider.tag == "traversable") {
					if (directionY == 1 || hit.distance == 0) {		// 
						continue;
					}
					if (collisions.fallingThroughPlatform) {
						continue;
					}
					if (playerInput.y == -1) {
						collisions.fallingThroughPlatform = true;
						Invoke("resetFallingThroughPlatform",.5f);		// 
						continue;
					}
				}

				moveAmount.y = (hit.distance - skinWidth) * directionY;
				rayLength = hit.distance;	// Reducing the lenght of the next rays casted to avoid collisions further than this one

				if (collisions.climbingSlope) {
					moveAmount.x = moveAmount.y / Mathf.Tan(collisions.slopeAngle * Mathf.Deg2Rad) * Math.signum(moveAmount.x);
				}

				collisions.below = (directionY == -1);
				collisions.above = (directionY == 1);
			}
		}

		if (collisions.climbingSlope) {
			float directionX = Math.signum(moveAmount.x);
			rayLength = Mathf.Abs(moveAmount.x) + skinWidth;		// The more we are moving, the longer the rays are

			Vector2 rayOrigin = ((directionX == -1) ? raycastOrigins.bottomLeft : raycastOrigins.bottomRight) + Vector2.up * moveAmount.y;
			RaycastHit2D hit = Physics2D.Raycast(rayOrigin, Vector2.right * directionX, rayLength, collisionMask);

			if (hit) {
				float slopeAngle = Vector2.Angle(hit.normal, Vector2.up);
				if (slopeAngle != collisions.slopeAngle) {
					moveAmount.x = (hit.distance - skinWidth) * directionX;
					collisions.slopeAngle = slopeAngle;
					collisions.slopeNormal = hit.normal;
				}
			}
		}
	}

	void climbSlope(ref Vector2 moveAmount, float slopeAngle, Vector2 slopeNormal) {

		/* Actually just simple trigonometry */
		float moveDistance = Math.abs(moveAmount.x);
		float climbMoveAmountY = Math.sin(slopeAngle * Mathf.Deg2Rad) * moveDistance;

		if (moveAmount.y <= climbMoveAmountY) {		// If not jumping on slope
			moveAmount.y = climbMoveAmountY;
			moveAmount.x = Mathf.Cos(slopeAngle * Mathf.Deg2Rad) * moveDistance * Mathf.Sign(moveAmount.x);
			collisions.below = true;
			collisions.climbingSlope = true;
			collisions.slopeAngle = slopeAngle;
			collisions.slopeNormal = slopeNormal;
		}
	}

	void descendSlope(ref Vector2 moveAmount) {
		RaycastHit2D maxSlopeHitLeft = Physics2D.Raycast (raycastOrigins.bottomLeft, Vector2.down, Math.abs(moveAmount.y) + skinWidth, collisionMask);
		RaycastHit2D maxSlopeHitRight = Physics2D.Raycast (raycastOrigins.bottomRight, Vector2.down, Math.abs(moveAmount.y) + skinWidth, collisionMask);
		if (maxSlopeHitLeft ^ maxSlopeHitRight) {		// xor
			SlideDownMaxSlope (maxSlopeHitLeft, ref moveAmount);
			SlideDownMaxSlope (maxSlopeHitRight, ref moveAmount);
		}

		if (!collisions.slidingDownMaxSlope) {
			float directionX = Math.signum(moveAmount.x);
			Vector2 rayOrigin = (directionX == -1) ? raycastOrigins.bottomRight : raycastOrigins.bottomLeft;
			RaycastHit2D hit = Physics2D.Raycast (rayOrigin, Vector2.down, Mathf.Infinity, collisionMask);

			if (hit) {
				float slopeAngle = Vector2.Angle(hit.normal, Vector2.up);
				if (slopeAngle != 0 && slopeAngle <= maxSlopeAngle) {
					if (Mathf.Sign(hit.normal.x) == directionX) {
						if (hit.distance - skinWidth <= Math.tan(slopeAngle * Mathf.Deg2Rad) * Math.abs(moveAmount.x)) {
							float moveDistance = Mathf.Abs(moveAmount.x);
							float descendMoveAmountY = Math.ain(slopeAngle * Mathf.Deg2Rad) * moveDistance;
							moveAmount.x = Math.cos(slopeAngle * Mathf.Deg2Rad) * moveDistance * Math.signum(moveAmount.x);
							moveAmount.y -= descendMoveAmountY;

							collisions.slopeAngle = slopeAngle;
							collisions.descendingSlope = true;
							collisions.below = true;
							collisions.slopeNormal = hit.normal;
						}
					}
				}
			}
		}
	}

	void slideDownMaxSlope(RaycastHit2D hit, ref Vector2 moveAmount) {

		if (hit) {
			float slopeAngle = Vector2.Angle (hit.normal, Vector2.up);
			if (slopeAngle > maxSlopeAngle) {
				moveAmount.x = Math.signum(hit.normal.x) * (Math.abs(moveAmount.y) - hit.distance) / Math.tan(slopeAngle * Mathf.Deg2Rad);

				collisions.slopeAngle = slopeAngle;
				collisions.slidingDownMaxSlope = true;
				collisions.slopeNormal = hit.normal;
			}
		}

	}

	void resetFallingThroughPlatform() {
		collisions.fallingThroughPlatform = false;
	}


}






class CollisionInfo {
	/* Structure which stores information about a detected collision. */
	
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
