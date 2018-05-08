package content.scripts;

import core.PlayerInput;
import core.util.*;
import core.exceptions.InvalidArgumentsException;

/**
 * This script contains all movement parameters of a Player, and describes its general behaviour.
 * It relies on a Controller script which manages the movement.
 *
 * @author Sebastian Lague, arranged by Raph
 *
 */
// TODO: Wall slide not activated when not moving toward the wall (preferably enable design choice)
public class PlayerScript extends MonoBehaviour {

	/**
	 * The maximum horizontal move speed of the player  
	 */
	public static float moveSpeed = 100f;

	/**
	 * The maximum height that can be reached when keeping the jump button pressed
	 */
	public static float maxJumpHeight = 4;

	/**
	 * The minimum height of a jump
	 */
	public static float minJumpHeight = 1;

	/**
	 * The time required to reach the apex of the jump parable
	 */
	public static float timeToJumpApex = .4f;

	/**
	 * The time required to reach the target horizontal velocity while airborne when starting with a null velocity
	 * (set 0 for no inertia)
	 */
	public static float accelerationTimeAirborne = 0.2f;

	/**
	 * The time required to reach the target horizontal velocity while grounded when starting with a null velocity
	 * (set 0 for no inertia)
	 */
	public static float accelerationTimeGrounded = 0.2f;


	/**
	 * The force applied to jump when wall-jumping toward the wall
	 */
	public static Vector2 wallJumpClimb;

	/**
	 * The force applied to jump when wall-jumping with no input
	 */
	public static Vector2 wallJumpOff;

	/**
	 * The force applied to jump when wall-jumping away from the wall
	 */
	public static Vector2 wallLeap;


	/**
	 * The maximum vertical speed that can be reached when sliding down against a wall 
	 */
	public static float wallSlideSpeedMax = 30;

	/**
	 * The amount of ime the player will stay stuck against a wall when inputing away from it;
	 * it is useful to perform wallLeap
	 */
	public static float wallStickTime = .25f;


	/**
	 * The constant of gravity applied to this player. 
	 */
	float gravity;

	/**
	 * The vertical velocity which correspond to the heighest jump possible
	 */
	float maxJumpVelocity;

	/**
	 * The vertical velocity which correspond to the lowest jump possible
	 */
	float minJumpVelocity;

	Vector2 velocity = Vector2.ZERO();
	MutableFloat velocityXSmoothing = new MutableFloat(0f);		// Used for the smoothing of the horizontal velocity

	boolean wallSliding;
	
	/**
	 * The amount of time remaining before unsticking from a wall
	 */
	float timeToWallUnstick;
	
	int wallDirX;	// wall on left or right


	Controller controller;		// A reference to the Controller script of this Player GameObject



	/* Constructor */
	public PlayerScript() {}



	@Override
	public void start() {
		// Setting up references
		controller = (Controller) support.scripts.get(1);		// Moche mais bon...

		// Calculating physics variables according to gameplay parameters
		gravity = (float) (-2 * maxJumpHeight / (timeToJumpApex * timeToJumpApex));
		gravity = 0;		// For testing purpose
		maxJumpVelocity = Math.abs(gravity) * timeToJumpApex;
		minJumpVelocity = (float) Math.sqrt(2 * Math.abs(gravity) * minJumpHeight);
	}

	@Override
	public void update(float deltaTime, PlayerInput playerInput) throws InvalidArgumentsException {
		System.out.println("Input update " + playerInput);
		calculateVelocity(deltaTime, playerInput.directionalInput);
		//handleWallSliding(deltaTime, playerInput.directionnalInput);

		controller.move(velocity.multiply(deltaTime), playerInput.directionalInput);

		if (controller.collisions.above || controller.collisions.below) {
			if (controller.collisions.slidingDownMaxSlope) {
				// Modulation of the vertical acceleration according to the slope
				velocity.y += controller.collisions.slopeNormal.y * -gravity * deltaTime;
			} else {
				velocity.y = 0;		// To avoid "accumulating" gravity
			}
		}
	}


	/**
	 * Calculate the velocity of the player for this frame according to its horizontal input
	 */
	void calculateVelocity(float deltaTime, Vector2 directionalInput) {
		float targetVelocityX = directionalInput.x * moveSpeed;
		System.out.println("trgtVelX " + targetVelocityX);
		velocity.x = Annex.SmoothDamp(velocity.x, targetVelocityX, velocityXSmoothing,
				(controller.collisions.below) ? accelerationTimeGrounded : accelerationTimeAirborne, deltaTime);

		velocity.y += gravity * deltaTime;
	}

	void handleWallSliding(float deltaTime, Vector2 directionalInput) {
		wallDirX = (controller.collisions.left) ? -1 : 1;
		wallSliding = false;
		if ((controller.collisions.left || controller.collisions.right) &&
				!controller.collisions.below && velocity.y < 0) {
			wallSliding = true;

			if (velocity.y < -wallSlideSpeedMax) {		// if wall sliding speed is already maximum 
				velocity.y = -wallSlideSpeedMax;
			}

			if (timeToWallUnstick > 0) {		// if the player is stuck to the wall
				// Nullifying horizontal velocity
				velocityXSmoothing.value = 0;
				velocity.x = 0;

				// if the player is inputing away
				if (directionalInput.x != wallDirX && directionalInput.x != 0) {
					timeToWallUnstick -= deltaTime;
				}
				else {
					timeToWallUnstick = wallStickTime;		// Reset
				}
			}
			else {
				timeToWallUnstick = wallStickTime;		// Reset
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
				// if not jumping AGAINST max slope
				if (directionalInput.x != -Math.signum (controller.collisions.slopeNormal.x)) {
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
