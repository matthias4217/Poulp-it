package core;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import core.util.Vector2;

/**
 * This class represents the input of a player sent by a client to the server.
 *
 * @author matthias
 *
 */
public class PlayerInput {

	/**
	 * The inputs relative to the direction
	 */
	public Vector2 directionnalInput = Vector2.ZERO();

	//KeyEvent.getEventType returns the event type

	public EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
		public void handle(KeyEvent event) {
			switch(event.getCode()) {
			case LEFT:
				directionnalInput = Vector2.LEFT();
				break;
			case RIGHT:
				directionnalInput = Vector2.RIGHT();
				break;
			case UP:
				directionnalInput = Vector2.UP();
				break;
			case DOWN:
				directionnalInput = Vector2.DOWN();
				break;
			case SPACE:

				break;
			default:
				directionnalInput = Vector2.ZERO();
				break;
			}
		}
	};



	@Override public String toString() {
		return "PlayerInput [directionalInput=" + directionnalInput + "; eventHandler=" + eventHandler + "]";
	}

}
