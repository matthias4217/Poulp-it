package core.scripts;

import core.util.*;

/**
 * @@@
 * 
 * @author Raph
 *
 */
//@TODO: make traversable platform more logical (currently, they are traversable but have side walls)
public class Controller extends RayCastController {

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
		updateRayCastOrigins();

		collisions.reset();
		collisions.moveAmountOld = moveAmount;
		playerInput = input;

		if (moveAmount.y < 0) {
			descendSlope(moveAmount);
		}

		if (moveAmount.x != 0) {
			collisions.faceDir = (int) Math.signum(moveAmount.x);
		}

		horizontalCollisions (moveAmount);
		if (moveAmount.y != 0) {
			verticalCollisions(moveAmount);
		}

		// Moving
		support.position.translate(moveAmount);

		if (standingOnPlatform) {
			collisions.below = true;
		}
	}

	void horizontalCollisions(Vector2 moveAmount) {
		float directionX = collisions.faceDir;
		float rayLength = Math.abs(moveAmount.x) + skinWidth;		// The more we are moving, the longer the rays are

		if (Math.abs(moveAmount.x) < skinWidth) {
			rayLength = 2*skinWidth;
		}

		for (int i = 0; i < horizontalRayCount; i++) {
			Vector2 rayOrigin = (directionX == -1) ? raycastOrigins.bottomLeft : raycastOrigins.bottomRight;
			rayOrigin.translate(Vector2.up.multiply(horizontalRaySpacing * i));
			RaycastHit hit = Ray.rayCast(rayOrigin, Vector2.right.multiply(directionX), rayLength, collisionMask);

			//Debug.DrawRay(rayOrigin, Vector2.right * directionX, Color.red);

			if (hit != null) {		// If something was hit

				//@Deprecated: we can't be in an obstacle
				if (hit.getDistance() == 0) {	// If we're actually IN an obstacle; @@@ BTW has to be changed in order to crush the character.
					continue;
				}

				float slopeAngle = Vector2.angle(hit.getNormal(), Vector2.up);

				if (i == 0 && slopeAngle <= maxSlopeAngle) {
					if (collisions.descendingSlope) {
						collisions.descendingSlope = false;
						moveAmount = collisions.moveAmountOld;
					}
					float distanceToSlopeStart = 0;
					if (slopeAngle != collisions.slopeAngleOld) {
						distanceToSlopeStart = hit.getDistance() - skinWidth;
						moveAmount.x -= distanceToSlopeStart * directionX;
					}
					climbSlope(moveAmount, slopeAngle, hit.getNormal());
					moveAmount.x += distanceToSlopeStart * directionX;
				}

				if (!collisions.climbingSlope || slopeAngle > maxSlopeAngle) {
					moveAmount.x = (hit.distance - skinWidth) * directionX;
					rayLength = hit.distance;		// Reducing the lenght of the next rays casted to avoid collisions further than this one

					if (collisions.climbingSlope) {
						moveAmount.y = (float) (Math.tan(collisions.slopeAngle * Annex.DEG2RAD) * Math.abs(moveAmount.x));
					}

					collisions.left = (directionX == -1);
					collisions.right = (directionX == 1);
				}
			}
		}
	}

	void verticalCollisions(Vector2 moveAmount) {
		float directionY = Math.signum(moveAmount.y);
		float rayLength = Math.abs(moveAmount.y) + skinWidth;		// The more we are moving, the longer the rays are

		for (int i = 0; i < verticalRayCount; i++) {
 
			Vector2 rayOrigin = (directionY == -1) ? raycastOrigins.bottomLeft : raycastOrigins.topLeft;
			rayOrigin.translate(Vector2.right.multiply(verticalRaySpacing * i + moveAmount.x));
			rayCastHit2D hit = GameEngine.rayCast(rayOrigin, Vector2.up.multiply(directionY), rayLength, collisionMask);

			//Debug.DrawRay(rayOrigin, Vector2.up * directionY, Color.red);

			if (hit) {
				// NOTE: Do not make slopes traversable because it is not well handled and it's useless anyway.
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
					moveAmount.x = moveAmount.y / Math.tan(collisions.slopeAngle * Annex.DEG2RAD) * Math.signum(moveAmount.x);
				}

				collisions.below = (directionY == -1);
				collisions.above = (directionY == 1);
			}
		}

		if (collisions.climbingSlope) {
			float directionX = Math.signum(moveAmount.x);
			rayLength = Math.abs(moveAmount.x) + skinWidth;		// The more we are moving, the longer the rays are

			Vector2 rayOrigin = ((directionX == -1) ? raycastOrigins.bottomLeft : raycastOrigins.bottomRight) + Vector2.up * moveAmount.y;
			rayCastHit2D hit = GameEngine.rayCast(rayOrigin, Vector2.right * directionX, rayLength, collisionMask);

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

	void climbSlope(Vector2 moveAmount, float slopeAngle, Vector2 slopeNormal) {

		/* Actually just simple trigonometry */
		float moveDistance = Math.abs(moveAmount.x);
		float climbMoveAmountY = Math.sin(slopeAngle * Annex.DEG2RAD) * moveDistance;

		if (moveAmount.y <= climbMoveAmountY) {		// If not jumping on slope
			moveAmount.y = climbMoveAmountY;
			moveAmount.x = Math.cos(slopeAngle * Annex.DEG2RAD) * moveDistance * Math.signum(moveAmount.x);
			collisions.below = true;
			collisions.climbingSlope = true;
			collisions.slopeAngle = slopeAngle;
			collisions.slopeNormal = slopeNormal;
		}
	}

	void descendSlope(Vector2 moveAmount) {
		rayCastHit2D maxSlopeHitLeft = GameEngine.rayCast (raycastOrigins.bottomLeft, Vector2.down, Math.abs(moveAmount.y) + skinWidth, collisionMask);
		rayCastHit2D maxSlopeHitRight = GameEngine.rayCast (raycastOrigins.bottomRight, Vector2.down, Math.abs(moveAmount.y) + skinWidth, collisionMask);
		if (maxSlopeHitLeft ^ maxSlopeHitRight) {		// xor
			SlideDownMaxSlope (maxSlopeHitLeft, moveAmount);
			SlideDownMaxSlope (maxSlopeHitRight, moveAmount);
		}

		if (!collisions.slidingDownMaxSlope) {
			float directionX = Math.signum(moveAmount.x);
			Vector2 rayOrigin = (directionX == -1) ? raycastOrigins.bottomRight : raycastOrigins.bottomLeft;
			rayCastHit2D hit = GameEngine.rayCast (rayOrigin, Vector2.down, Float.POSITIVE_INFINITY, collisionMask);

			if (hit) {
				float slopeAngle = Vector2.Angle(hit.normal, Vector2.up);
				if (slopeAngle != 0 && slopeAngle <= maxSlopeAngle) {
					if (Math.signum(hit.normal.x) == directionX) {
						if (hit.distance - skinWidth <= Math.tan(slopeAngle * Annex.DEG2RAD) * Math.abs(moveAmount.x)) {
							float moveDistance = Math.abs(moveAmount.x);
							float descendMoveAmountY = Math.ain(slopeAngle * Annex.DEG2RAD) * moveDistance;
							moveAmount.x = Math.cos(slopeAngle * Annex.DEG2RAD) * moveDistance * Math.signum(moveAmount.x);
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

	void slideDownMaxSlope(rayCastHit hit, Vector2 moveAmount) {

		if (hit) {
			float slopeAngle = Vector2.Angle (hit.normal, Vector2.up);
			if (slopeAngle > maxSlopeAngle) {
				moveAmount.x = Math.signum(hit.normal.x) * (Math.abs(moveAmount.y) - hit.distance) / Math.tan(slopeAngle * Annex.DEG2RAD);

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
