package core;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


import core.util.Vector2;
import core.Launcher;

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
	
	// XXX The position is updated only after a mouse event
	public Vector2 mousePosition = Vector2.ZERO();
	public boolean mouseLeftPressed;
	public boolean mouseRightPressed;
	
	public boolean leftPressed = false;
	public boolean rightPressed = false;
	public boolean upPressed = false;
	public boolean downPressed = false;
	
	public boolean aPressed = false;
	public boolean zPressed = false;
	public boolean ePressed = false;
	public boolean rPressed = false;
	

	// KeyEvent.getEventType returns the event type

	public EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
		@Override public void handle(KeyEvent event) {
			directionalInput = Vector2.ZERO();
			switch (event.getCode()) {
				case LEFT:	leftPressed = true;
					break;
				case RIGHT:	rightPressed = true;
					break;
				case DOWN: downPressed = true;
					break;
				case UP: upPressed = true;
					break;
				case SPACE: spacePressed = true;
					break;
				case A: aPressed = true;
					break;
				case Z: zPressed = true;
					break;
				case E: ePressed = true;
					break;
				case R: rPressed = true;
					break;
				default: break;
			}
			if (leftPressed) {
				directionalInput.x = -1;
			}
			if (rightPressed) {
				directionalInput.x = 1;
			}
			if (leftPressed && rightPressed) {
				directionalInput.x = 0;
			}
			if (upPressed) {
				directionalInput.y = 1;
			}
			if (downPressed) {
				directionalInput.y = -1;
			}
			if (upPressed && downPressed) {
				directionalInput.y = 0;
			}
			directionalInput.normalize();
		}
	};

	public EventHandler<KeyEvent> eventHandlerReleased = new EventHandler<KeyEvent>() {
		@Override public void handle(KeyEvent event) {
			directionalInput = Vector2.ZERO();
			spacePressed = false;
			switch (event.getCode()) {
			case LEFT:	leftPressed = false;
				break;
			case RIGHT:	rightPressed = false;
				break;
			case DOWN: downPressed = false;
				break;
			case UP: upPressed = false;
				break;
			case SPACE: spacePressed = false;
				break;
			case A: aPressed = false;
				break;
			case Z: zPressed = false;
				break;
			case E: ePressed = false;
				break;
			case R: rPressed = false;
				break;
			default:
				break;
		}
			}
	};
	
	
	
	public EventHandler<MouseEvent> mouseEventHandler = new EventHandler<MouseEvent>() {
		@Override public void handle(MouseEvent event) {
			mouse = event;
			mousePosition.x = (float) event.getX();
			mousePosition.y = (float) event.getY();
			mousePosition = GameEngine.screenToWindow(mousePosition);
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

	
	/**
	 * Makes a copy of a PlayerInput object
	 */
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
