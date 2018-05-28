package content.rhythmgame;


import content.GameObject;
import content.Layer;
import content.Tag;
import content.rhythmgame.scripts.Controller;
import core.GameEngine;
import core.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


/**
*
* @author matthias
*
*/
public class RhythmConductor extends GameObject {

	Controller controller;

	public RhythmConductor(GameEngine gameEngine) {
		super(Vector2.ZERO(),
				null,
				Layer.DEFAULT,
				Tag.DEFAULT,
				null,
				gameEngine,
				new Controller());
		controller = (Controller) scripts.get(0);
	}
	
	@Override public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		//We'll just print the letter at the center of the screen
		gc.setFill(Color.RED);
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 40));
		gc.fillText(controller.currentLetter, Math.round(windowWidth/2), Math.round(windowHeight/2));//windowWidth/2, windowHeight/2);
		gc.fillText(String.valueOf(controller.score), 100, 100);
	}
}
