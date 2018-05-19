package content.rythmgame;


import content.GameObject;
import content.Layer;
import content.Tag;
import content.scripts.rhythmgame.Controller;
import core.util.Vector2;
import javafx.scene.canvas.GraphicsContext;


/**
*
* @author matthias
*
*/
public class RhythmConductor extends GameObject {
	


	public Controller controller;

	public RhythmConductor() {
		super(Vector2.ZERO(),
				null,
				Layer.DEFAULT,
				Tag.DEFAULT,
				null,
				new Controller());
		controller = (Controller) scripts.get(0);
	}
	
	@Override public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		//We'll just print the letter at the center of the screen
		gc.fillText(controller.currentLetter, Math.round(windowWidth/2), Math.round(windowHeight/2));//windowWidth/2, windowHeight/2);
		gc.fillText(String.valueOf(controller.score), 100, 100);
	}
}
