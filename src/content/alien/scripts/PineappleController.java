/**
 * 
 */
package content.alien.scripts;

import content.MonoBehaviour;
import content.alien.Alien;
import core.Launcher;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;
import core.util.Vector2;

/**
 * @author matthias
 *
 */
public class PineappleController extends MonoBehaviour {

	Vector2 speed = Vector2.ZERO();
	float speedFactor = 1.3f;
	private float maxX = (float) Launcher.WINDOW_WIDTH;;
	private float maxY = (float) Launcher.WINDOW_HEIGHT;;
	
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput, float maxX, float maxY) throws InvalidArgumentsException {
		this.maxX = maxX;
		this.maxY= maxY;
		update(deltaTime, playerInput, previousPlayerInput);
	}
	
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput) throws InvalidArgumentsException {
		if(Math.random() > 0.995) {
			changeSpeed();
		}
		support.position.x += speed.x;
		support.position.y += speed.y;
		validatePosition(maxX, maxY);	
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
	
	public void changeSpeed() {
		int max = 5;
		float x = max * (float) Math.random() - max/2;
		float y = max * (float) Math.random() - max/2;
		speed = new Vector2(x, y).multiply(speedFactor);
	}
}
