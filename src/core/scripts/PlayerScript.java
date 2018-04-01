package core.scripts;

import core.GameInformation;
import core.exceptions.InvalidArgumentsException;
import core.util.*;

/**
 *
 *
 * @author Sebastian Lague, arranged by Raph
 *
 */
//@TODO: Wall slide not activated when not moving toward the wall (preferably enable design choice)
public class PlayerScript extends MonoBehavior {

	public static float moveSpeed = 6f;
	public static float maxJumpHeight = 4;
	public static float minJumpHeight = 1;
	public static float timeToJumpApex = .4f;
	public static float accelerationTimeAirborne = 0f;		// Amount of inertia while airborne (set to 0 for no inertia)
	public static float accelerationTimeGrounded = 0f;		// Amount of inertia while grounded (set to 0 for no inertia)


	public static Vector2 wallJumpClimb;					// Force applied to jump when wall-jumping toward the wall
	public static Vector2 wallJumpOff;						// Force applied to jump when wall-jumping with no input
	public static Vector2 wallLeap;							// Force applied to jump when wall-jumping away from the wall

	public static float wallSlideSpeedMax = 3;
	public static float wallStickTime = .25f;				// Time the player will stay stuck against a wall when inputing away from it; useful to perform wallLeap

	float gravity;
	float maxJumpVelocity;
	float minJumpVelocity;
	Vector2 velocity = Vector2.zero;
	MutableFloat velocityXSmoothing = new MutableFloat(0);

	Vector2 directionalInput;
	boolean wallSliding;
	float timeToWallUnstick;	// The amount of time remaining before unsticking from a wall
	int wallDirX;	// wall on left or right

	Controller controller;

	
	
	public PlayerScript() {
	}


	@Override
	public void start() {
		controller = (Controller) getSupport().scripts.get(1);		// XXX

		gravity = (float) (/*-*/2 * maxJumpHeight / (timeToJumpApex * timeToJumpApex));
		maxJumpVelocity = Math.abs(gravity) * timeToJumpApex;
		minJumpVelocity = (float) Math.sqrt(2 * Math.abs(gravity) * minJumpHeight);
	}

	@Override
	public void update(float deltaTime, GameInformation gameInformation) throws InvalidArgumentsException {
		setDirectionalInput(gameInformation.playerInput);
		calculateVelocity (deltaTime);
		handleWallSliding (deltaTime);
		System.out.println("directionalInput" + directionalInput);

		controller.move(velocity.multiply(deltaTime), directionalInput);

		if (controller.collisions.above || controller.collisions.below) {
			if (controller.collisions.slidingDownMaxSlope) {
				velocity.y += controller.collisions.slopeNormal.y * -gravity * deltaTime;		// modulation of the vertical acceleration according to the slope
			} else {
				velocity.y = 0;		// To avoid "accumulating" gravity
			}
		}
	}

	
	public void setDirectionalInput (Vector2 input) {
		directionalInput = input;
	}

	void calculateVelocity(float deltaTime) {
		System.out.println(directionalInput);
		float targetVelocityX = directionalInput.x * moveSpeed;
		velocity.x = Annex.SmoothDamp(velocity.x, targetVelocityX, velocityXSmoothing, (controller.collisions.below)?accelerationTimeGrounded:accelerationTimeAirborne, deltaTime);
		velocity.y += gravity * deltaTime;
	}

	void handleWallSliding(float deltaTime) {
		System.out.println("truc");
		System.out.println(controller.collisions);
		wallDirX = (controller.collisions.left) ? -1 : 1;
		wallSliding = false;
		if ((controller.collisions.left || controller.collisions.right) && !controller.collisions.below && velocity.y < 0) {
			wallSliding = true;

			if (velocity.y < -wallSlideSpeedMax) {
				velocity.y = -wallSlideSpeedMax;
			}

			if (timeToWallUnstick > 0) {
				velocityXSmoothing.value = 0;
				velocity.x = 0;

				if (directionalInput.x != wallDirX && directionalInput.x != 0) {
					timeToWallUnstick -= deltaTime;
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
		if (controller.collisions.below) {		// If the player is grounded
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
}
