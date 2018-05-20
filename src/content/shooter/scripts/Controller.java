package content.shooter.scripts;

import content.MonoBehaviour;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;
import core.util.Annex;
import core.util.MutableFloat;
import core.util.Vector2;

/**
 * The controller script which manages the movement of the GameObject
 * 
 * @author Raph
 *
 */
public class Controller extends MonoBehaviour {

	/**
	 * The maximum horizontal move speed of the player  
	 */
	public static float moveSpeed = 500f;

	/**
	 * The time required to reach the target velocity when starting with a null velocity
	 * (set 0 for no inertia)
	 */
	public static float accelerationTime = 0.1f;

	
	Vector2 velocity = Vector2.ZERO();
	MutableFloat velocityXSmoothing = new MutableFloat(0f);		// Used for the smoothing of the horizontal velocity
	MutableFloat velocityYSmoothing = new MutableFloat(0f);		// Used for the smoothing of the vertical velocity



	/* Constructor */
	public Controller() {}



	@Override
	public void start() {

	}

	@Override
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput) throws InvalidArgumentsException {
		
		calculateVelocity(deltaTime, playerInput.directionalInput);

		// Moving
		support.position.translate(velocity.multiply(deltaTime));
	}


	/**
	 * Calculate the velocity of the player for this frame according to its input.
	 */
	void calculateVelocity(float deltaTime, Vector2 directionalInput) {
		float targetVelocityX = moveSpeed * directionalInput.x;
		float targetVelocityY = moveSpeed * directionalInput.y;
		velocity.x = Annex.SmoothDamp(velocity.x, targetVelocityX, velocityXSmoothing, accelerationTime, deltaTime);
		velocity.y = Annex.SmoothDamp(velocity.y, targetVelocityY, velocityYSmoothing, accelerationTime, deltaTime);
	}




}
