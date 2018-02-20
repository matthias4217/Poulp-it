package core;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author matthias
 *
 * Controller class manging the inputs
 */
public class Controller {

	/**
	 *
	 */
	EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {

		public void handle(KeyEvent event) {
			switch(event.getCode()) {
			case LEFT:   break;
			case RIGHT:  break;
			case UP:     break;
			case DOWN:   break;
			case SPACE:  break;
			default:;
			}
		}
	};
}