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
	public boolean spacePressed;
	
	public MouseEvent mouse;
	
	//XXX The position is updated only after a mouse event
	public Vector2 mousePosition = Vector2.ZERO();
	public boolean mouseLeftPressed;
	public boolean mouseRightPressed;
	


	//KeyEvent.getEventType returns the event type

	public EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
		public void handle(KeyEvent event) {
			spacePressed = false;
			directionalInput = Vector2.ZERO();
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
				spacePressed = true;
				break;
			default:
				directionalInput = Vector2.ZERO();
				break;
			}
		}
	};

	
	public EventHandler<MouseEvent> mouseEventHandler = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			mouse = event;
			mousePosition.x = (float) event.getX();
			mousePosition.y = (float) event.getY();
			MouseButton button = event.getButton();
			if (button == MouseButton.NONE) {
				mouseLeftPressed = false;
				mouseRightPressed = false;
			} else if (button == MouseButton.PRIMARY) {
				mouseLeftPressed = true;
			} else if (button == MouseButton.SECONDARY) {
				mouseRightPressed = true;
			}
		}
	};

	
	public PlayerInput copy() {
		PlayerInput result = new PlayerInput();
		result.directionalInput = this.directionalInput;
		result.mouse = this.mouse;
		result.mousePosition = this.mousePosition;
		result.mouseLeftPressed = this.mouseLeftPressed;
		result.mouseRightPressed = this.mouseRightPressed;
		return result;
	}

	@Override public String toString() {
		return "PlayerInput [directionalInput=" + directionalInput + "; eventHandler=" + 
	eventHandler + "; mouse=(" + mousePosition.x + "," + mousePosition.y + ")]";
	}

}
