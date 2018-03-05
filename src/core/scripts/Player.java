package core.scripts;

import core.util.*;

/**
 * @@@
 * 
 * @author Raph
 *
 */
//@TODO: Wall slide not activated when not moving toward the wall (preferably enable design choice)
public class Player extends MonoBehavior {

	public float moveSpeed = 6;
	public float maxJumpHeight = 4;
	public float minJumpHeight = 1;
	public float timeToJumpApex = .4f;
	public float accelerationTimeAirborne = .2f;	// Amount of inertia while airborne (set to 0 for no inertia)
	public float accelerationTimeGrounded = .1f;	// Amount of inertia while grounded (set to 0 for no inertia)

	public Vector2 wallJumpClimb;					// Force applied to jump when wall-jumping toward the wall
	public Vector2 wallJumpOff;						// Force applied to jump when wall-jumping with no input
	public Vector2 wallLeap;						// Force applied to jump when wall-jumping away from the wall

	public float wallSlideSpeedMax = 3;
	public float wallStickTime = .25f;				// Time the player will stay stuck against a wall when inputing away from it; useful to perform wallLeap
	float timeToWallUnstick;

	float gravity;
	float maxJumpVelocity;
	float minJumpVelocity;
	Vector2 velocity;
	float velocityXSmoothing;

	Controller controller;

	Vector2 directionalInput;
	boolean wallSliding;
	int wallDirX;	// wall on left or right

	public void start() {
		controller = GetComponent<Controller2D> ();

		gravity = -2 * maxJumpHeight / Math.pow(timeToJumpApex, 2);
		maxJumpVelocity = Math.abs(gravity) * timeToJumpApex;
		minJumpVelocity = Math.sqrt(2 * Math.abs(gravity) * minJumpHeight);
	}

	@Override
	public void update() {
		calculateVelocity ();
		handleWallSliding ();

		controller.Move (velocity * Time.deltaTime, directionalInput);

		if (controller.collisions.above || controller.collisions.below) {
			if (controller.collisions.slidingDownMaxSlope) {
				velocity.y += controller.collisions.slopeNormal.y * -gravity * Time.deltaTime;		// modulation of the vertical acceleration according to the slope
			} else {
				velocity.y = 0;		// To avoid "accumulating" gravity
			}
		}
	}

	public void setDirectionalInput (Vector2 input) {
		directionalInput = input;
	}

	public void onJumpInputDown() {
		if (wallSliding) {
			if (wallDirX == directionalInput.x) {
				velocity.x = -wallDirX * wallJumpClimb.x;
				velocity.y = wallJumpClimb.y;
			}
			else if (directionalInput.x == 0) {
				velocity.x = -wallDirX * wallJumpOff.x;
				velocity.y = wallJumpOff.y;
			}
			else {
				velocity.x = -wallDirX * wallLeap.x;
				velocity.y = wallLeap.y;
			}
		}
		if (controller.collisions.below) {
			if (controller.collisions.slidingDownMaxSlope) {
				if (directionalInput.x != -Math.signum (controller.collisions.slopeNormal.x)) {		// If not jumping AGAINST max slope
					velocity.y = maxJumpVelocity * controller.collisions.slopeNormal.y;
					velocity.x = maxJumpVelocity * controller.collisions.slopeNormal.x;
				}
			} else {
				velocity.y = maxJumpVelocity;
			}
		}
	}

	public void onJumpInputUp() {
		if (velocity.y > minJumpVelocity) {
			velocity.y = minJumpVelocity;
		}
	}
		

	void handleWallSliding() {
		wallDirX = (controller.collisions.left) ? -1 : 1;
		wallSliding = false;
		if ((controller.collisions.left || controller.collisions.right) && !controller.collisions.below && velocity.y < 0) {
			wallSliding = true;

			if (velocity.y < -wallSlideSpeedMax) {
				velocity.y = -wallSlideSpeedMax;
			}

			if (timeToWallUnstick > 0) {
				velocityXSmoothing = 0;
				velocity.x = 0;

				if (directionalInput.x != wallDirX && directionalInput.x != 0) {
					timeToWallUnstick -= Time.deltaTime;
				}
				else {
					timeToWallUnstick = wallStickTime;	// Reset
				}
			}
			else {
				timeToWallUnstick = wallStickTime;	// Reset
			}

		}

	}

	void calculateVelocity() {
		float targetVelocityX = directionalInput.x * moveSpeed;
		velocity.x = Math.SmoothDamp (velocity.x, targetVelocityX, /*ref*/ velocityXSmoothing, (controller.collisions.below)?accelerationTimeGrounded:accelerationTimeAirborne);
		velocity.y += gravity * Time.deltaTime;
	}
}
