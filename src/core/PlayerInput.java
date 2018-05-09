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
	public Vector2 directionalInput = Vector2.ZERO();
	


	//KeyEvent.getEventType returns the event type

	public EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
		public void handle(KeyEvent event) {
			switch(event.getCode()) {
			case LEFT:
				directionalInput = Vector2.LEFT();
				break;
			case RIGHT:
				directionalInput = Vector2.RIGHT();
				break;
			case UP:
				directionalInput = Vector2.UP();
				break;
			case DOWN:
				directionalInput = Vector2.DOWN();
				break;
			case SPACE:
				directionalInput = Vector2.ZERO();
				break;
			default:
				directionalInput = Vector2.ZERO();
				break;
			}
		}
	};



	@Override public String toString() {
		return "PlayerInput [directionalInput=" + directionalInput + "; eventHandler=" + eventHandler + "]";
	}

}
