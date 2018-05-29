/**
 * 
 */
package content.alien.scripts;

import content.MonoBehaviour;
import core.util.Vector2;
import javafx.scene.input.KeyCode;

/**
 * @author matthias
 *
 */
public class AlienController extends MonoBehaviour {

	Vector2 speed;
	void update() {
		
	}

	public void validatePosition() {
		float x = support.position.x;
		float y = support.position.y;
		
		if (x + width >= maxX) {
			x = maxX - width;
			speed.x *= -1;
		} else if (x < 0) {
			x = 0;
			speed.x *= -1;
		}

		if (y + height >= maxY) {
			y = maxY - height;
			speed.y *= -1;
		} else if (y < 0) {
			y = 0;
			speed.y *= -1;
		}
	}
	
	public void changeSpeed(Vector2 directionalInput) {
		speed = speed.add(directionalInput);
	}
}
