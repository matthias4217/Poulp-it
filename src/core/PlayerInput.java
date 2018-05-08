package core;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
	
	public MouseButton mouse = MouseButton.NONE;
	
	//XXX The position is updated only after a mouse event
	public Vector2 mousePosition = Vector2.ZERO();
	


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

	
	public EventHandler<MouseEvent> mouseEventHandler = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			mouse = event.getButton();
			mousePosition.x = (float) event.getX();
			mousePosition.y = (float) event.getY();
		}
	};


	@Override public String toString() {
		return "PlayerInput [directionalInput=" + directionalInput + "; eventHandler=" + 
	eventHandler + "; mouse=(" + mousePosition.x + "," + mousePosition.y + ")]";
	}

}
