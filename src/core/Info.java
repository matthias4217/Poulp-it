package core;

import core.util.Vector2;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Info class :
 * Collect the data from the Launcher to pass them to the GameEngine
 *
 * @author matthias
 *
 */

public class Info {


	public Vector2 playerInput = new Vector2(0,0);

	public EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
		public void handle(KeyEvent event) {
			switch(event.getCode()) {
			case LEFT:
				playerInput.x = -1;
				playerInput.y = 0;
				break;
			case RIGHT:
				playerInput.x = 1;
				playerInput.y = 0;
				break;
			case UP:
				playerInput.x = 0;
				playerInput.y = 1;
				break;
			case DOWN:
				playerInput.x = 0;
				playerInput.y = -1;
				break;
			case SPACE:
				playerInput.x = -1;
				playerInput.y = 0;
				break;
			default:	break;
			}
		}
	};

	@Override
	public String toString() {
		return "Info [playerInput=" + playerInput + ", eventHandler=" + eventHandler + "]";
	}
}
