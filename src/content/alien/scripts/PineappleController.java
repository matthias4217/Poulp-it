/**
 * 
 */
package content.alien.scripts;

import content.MonoBehaviour;
import content.alien.Alien;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;
import core.util.Vector2;

/**
 * @author matthias
 *
 */
public class PineappleController extends MonoBehaviour {

	Vector2 speed = Vector2.ZERO();
	private float maxX;
	private float maxY;
	
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput) throws InvalidArgumentsException {
		
	}
	
	public void validatePosition(float maxX, float maxY) {
		float x = support.position.x;
		float y = support.position.y;
		
		if (x + Alien.width >= maxX) {
			x = maxX - Alien.width;
			speed.x *= -1;
		} else if (x < 0) {
			x = 0;
			speed.x *= -1;
		}

		if (y + Alien.height >= maxY) {
			y = maxY - Alien.height;
			speed.y *= -1;
		} else if (y < 0) {
			y = 0;
			speed.y *= -1;
		}
	}
}
