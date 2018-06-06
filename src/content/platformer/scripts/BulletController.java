package content.platformer.scripts;

import content.MonoBehaviour;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;
import core.util.Vector2;

/**
 * ---
 * 
 * @author matthias
 *
 */
public class BulletController extends MonoBehaviour {

	float velocityFactor;
	float currentVelocity;
	Vector2 directionShot;



	public BulletController(float velocityFactor, Vector2 directionShot) {
		super();
		this.velocityFactor = velocityFactor;
		this.directionShot = directionShot;
		currentVelocity = 0;
	}



	@Override
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput)
			throws InvalidArgumentsException {
		/*
		 * Here we update the speed, throwing a ray first, and if no
		 * obstacle, with support.velocityFactor * directionShot
		 */
		support.position.x += velocityFactor * directionShot.x;
		support.position.y += velocityFactor * directionShot.y;
	}


}
