/**
 * 
 */
package content.test.scripts;

import content.MonoBehaviour;
import core.PlayerInput;
import core.GameEngine;

/**
 * @author matthias
 *
 */
public class MouseController extends MonoBehaviour {

	/**
	 * 
	 */
	public MouseController() {
		// TODO Auto-generated constructor stub
	}
	
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput) {
		support.position = playerInput.mousePosition;
	}

}
