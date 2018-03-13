package core;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Controller class managing the inputs
 *
 * @author matthias
 *
 */
public class Controller {

	
	EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {

		// @@@ TODO
		
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
