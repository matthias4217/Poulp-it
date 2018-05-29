/**
 * 
 */
package content.alien.scripts;

import content.GameObject;
import content.MonoBehaviour;
import content.alien.Alien;
import content.alien.Pineapple;
import core.GameEngine;
import core.Launcher;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;
import core.util.Vector2;

/**
 * @author matthias
 *
 */
public class AlienController extends MonoBehaviour {
	
	
	Vector2 speed = Vector2.ZERO();
	private float maxX = (float) Launcher.WINDOW_WIDTH;
	private float maxY = (float) Launcher.WINDOW_HEIGHT;
	private float speedFactor = .5f;
	
	
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput, float maxX, float maxY) throws InvalidArgumentsException {
		this.maxX = maxX;
		this.maxY= maxY;
		update(deltaTime, playerInput, previousPlayerInput);
	}
	
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput) throws InvalidArgumentsException {
		changeSpeed(playerInput.directionalInput);
		support.position.x += speed.x;
		support.position.y += speed.y;
		validatePosition(maxX, maxY);
		// Check collisions
		for (GameObject gameObject: GameEngine.allGameObjects) {
			try {
				Pineapple pineapple = (Pineapple) gameObject;
				if (intersects(pineapple)) {
					support.gameEngine.allGameObjects.remove(pineapple);
				}
				
			} catch (Exception e) {
				
			}
		}
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
	
	public void changeSpeed(Vector2 directionalInput) {
		speed.x  += directionalInput.x * speedFactor;
		speed.y  += directionalInput.y * speedFactor;
	}
	
	public boolean intersects(Pineapple other) {
		float x = support.position.x;
		float y = support.position.y;
		
		return ((x >= other.position.x && x <= other.position.x + other.width) ||
				(other.position.x >= x && other.position.x <= x + Alien.width)) &&
				((y >= other.position.y && y <= other.position.y + other.height) ||
						(other.position.y >= y && other.position.y <= y + Alien.height));
	}
}
