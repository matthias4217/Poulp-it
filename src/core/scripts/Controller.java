package core.scripts;

import core.GameEngine;
import content.Layer;
import content.Tag;
import core.util.*;
import core.util.Ray.Direction;
import core.exceptions.InvalidArgumentsException;

/**
 * TODO Doc
 *
 * @author Sebastian Lague, arranged by Raph
 *
 */
public class Controller extends RaycastController {

	public Layer collisionMask;

	public static float maxSlopeAngle;

	public CollisionInfo collisions;
	public Vector2 playerInput;

	/* Constructor */
	public Controller() {
		collisions = new CollisionInfo();
	}

	@Override
	public void start() {
		super.start();
		collisions.faceDir = 1;
	}

	public void move(Vector2 moveAmount, boolean standingOnPlatform) {		// Called when not related to some inputs
		move(moveAmount, Vector2.ZERO(), standingOnPlatform);
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

		horizontalCollisions(moveAmount);
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
			Vector2 rayOrigin = support.position.add(
					(directionX == -1) ? raycastOrigins.topLeft : raycastOrigins.topRight);
			rayOrigin.translate(Vector2.DOWN().multiply(horizontalRaySpacing * i));
			RaycastHit hit = GameEngine.raycast(rayOrigin, (directionX == -1) ? Direction.LEFT : Direction.RIGHT,
					rayLength, collisionMask);



			//Debug.DrawRay(rayOrigin, Vector2.right * directionX, Color.red);

			if (hit != null) {		// If something was hit

				//@Deprecated: we can't be in an obstacle
				if (hit.getDistance() == 0) {	// If we're actually IN an obstacle; @@@ BTW has to be changed in order to crush the character.
					continue;
				}

				float slopeAngle = Vector2.angle(hit.getNormal(), Vector2.UP());

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
					moveAmount.x = (hit.getDistance() - skinWidth) * directionX;
					rayLength = hit.getDistance();		// Reducing the lenght of the next rays casted to avoid collisions further than this one

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

			Vector2 rayOrigin = support.position.add(
					(directionY == 1) ? raycastOrigins.topLeft : raycastOrigins.bottomLeft);
			rayOrigin.translate(Vector2.RIGHT().multiply(verticalRaySpacing * i + moveAmount.x));
			RaycastHit hit = GameEngine.raycast(rayOrigin, (directionY == 1) ? Direction.UP : Direction.DOWN,
					rayLength, collisionMask);

			// Debug.DrawRay(rayOrigin, Vector2.up * directionY, Color.red);

			if (hit != null) {		// If something was hit
				// NOTE: Do not make slopes traversable because it is not well handled and it's useless anyway.
				if (hit.getGameObjectHit().tag == Tag.TRAVERSABLE) {
					if (directionY == 1 || hit.getDistance() == 0) {		//
						continue;
					}
					if (collisions.fallingThroughPlatform) {
						continue;
					}
					if (playerInput.y == -1) {
						collisions.fallingThroughPlatform = true;
						//						Invoke("resetFallingThroughPlatform",.5f);		//
						continue;
					}
				}

				moveAmount.y = (hit.getDistance() - skinWidth) * directionY;
				rayLength = hit.getDistance();	// Reducing the lenght of the next rays casted to avoid collisions further than this one

				if (collisions.climbingSlope) {
					moveAmount.x = (float) (moveAmount.y / Math.tan(collisions.slopeAngle * Annex.DEG2RAD) *
							Math.signum(moveAmount.x));
				}

				collisions.below = (directionY == -1);
				collisions.above = (directionY == 1);
			}
		}

		if (collisions.climbingSlope) {
			float directionX = Math.signum(moveAmount.x);
			rayLength = Math.abs(moveAmount.x) + skinWidth;		// The more we are moving, the longer the rays are

			
			Vector2 rayOrigin = support.position.add(
					((directionX == -1) ? raycastOrigins.bottomLeft : raycastOrigins.bottomRight));
			rayOrigin.translate(Vector2.UP().multiply(moveAmount.y));
			RaycastHit hit = GameEngine.raycast(rayOrigin, (directionX == -1) ? Direction.LEFT : Direction.RIGHT,
					rayLength, collisionMask);

			if (hit != null) {
				float slopeAngle = Vector2.angle(hit.getNormal(), Vector2.UP());
				if (slopeAngle != collisions.slopeAngle) {
					moveAmount.x = (hit.getDistance() - skinWidth) * directionX;
					collisions.slopeAngle = slopeAngle;
					collisions.slopeNormal = hit.getNormal();
				}
			}
		}
	}

	void climbSlope(Vector2 moveAmount, float slopeAngle, Vector2 slopeNormal) {

		/* Actually just simple trigonometry */
		float moveDistance = Math.abs(moveAmount.x);
		float climbMoveAmountY = (float) (Math.sin(slopeAngle * Annex.DEG2RAD) * moveDistance);

		if (moveAmount.y <= climbMoveAmountY) {		// If not jumping on slope
			moveAmount.y = climbMoveAmountY;
			moveAmount.x = (float) (Math.cos(slopeAngle * Annex.DEG2RAD) * moveDistance * Math.signum(moveAmount.x));
			collisions.below = true;
			collisions.climbingSlope = true;
			collisions.slopeAngle = slopeAngle;
			collisions.slopeNormal = slopeNormal;
		}
	}

	void descendSlope(Vector2 moveAmount) {
		RaycastHit maxSlopeHitLeft = GameEngine.raycast(support.position.add(raycastOrigins.bottomLeft),
				Direction.DOWN, Math.abs(moveAmount.y) + skinWidth, collisionMask);
		RaycastHit maxSlopeHitRight = GameEngine.raycast(support.position.add(raycastOrigins.bottomRight),
				Direction.DOWN, Math.abs(moveAmount.y) + skinWidth, collisionMask);

		if ((maxSlopeHitLeft != null) ^ (maxSlopeHitRight != null)) {		// xor
			slideDownMaxSlope(maxSlopeHitLeft, moveAmount);
			slideDownMaxSlope(maxSlopeHitRight, moveAmount);
		}

		if (!collisions.slidingDownMaxSlope) {
			float directionX = Math.signum(moveAmount.x);
			Vector2 rayOrigin = support.position.add(
			(directionX == -1) ? raycastOrigins.bottomRight : raycastOrigins.bottomLeft);

			RaycastHit hit = GameEngine.raycast(rayOrigin, Direction.DOWN, Float.POSITIVE_INFINITY, collisionMask);
			if (hit != null) {

				float slopeAngle = Vector2.angle(hit.getNormal(), Vector2.UP());
				if (slopeAngle != 0 && slopeAngle <= maxSlopeAngle) {
					if (Math.signum(hit.getNormal().x) == directionX) {
						if (hit.getDistance() - skinWidth <= Math.tan(slopeAngle * Annex.DEG2RAD) * Math.abs(moveAmount.x)) {
							float moveDistance = Math.abs(moveAmount.x);
							float descendMoveAmountY = (float) (Math.sin(slopeAngle * Annex.DEG2RAD) * moveDistance);
							moveAmount.x = (float) (Math.cos(slopeAngle * Annex.DEG2RAD) * moveDistance * Math.signum(moveAmount.x));
							moveAmount.y -= descendMoveAmountY;

							collisions.slopeAngle = slopeAngle;
							collisions.descendingSlope = true;
							collisions.below = true;
							collisions.slopeNormal = hit.getNormal();
						}
					}
				}
			}
		}
	}

	void slideDownMaxSlope(RaycastHit hit, Vector2 moveAmount) {

		if (hit != null) {
			float slopeAngle = Vector2.angle (hit.getNormal(), Vector2.UP());
			if (slopeAngle > maxSlopeAngle) {
				moveAmount.x = (float) (Math.signum(hit.getNormal().x) * (Math.abs(moveAmount.y) - hit.getDistance()) /
						Math.tan(slopeAngle * Annex.DEG2RAD));

				collisions.slopeAngle = slopeAngle;
				collisions.slidingDownMaxSlope = true;
				collisions.slopeNormal = hit.getNormal();
			}
		}
	}

	void resetFallingThroughPlatform() {
		collisions.fallingThroughPlatform = false;
	}


}
