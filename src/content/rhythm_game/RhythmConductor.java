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

		// We'll just print the letter at the center of the screen
		gc.setFill(Color.WHITE);
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 200));
		if (controller.currentLetters[0]) {
			gc.fillText("A", 320, 700);
		}

		if (controller.currentLetters[1]) {
			gc.fillText("Z", 630, 704);
		}

		if (controller.currentLetters[2]) {
			gc.fillText("E", 949, 700);
		}

		if (controller.currentLetters[3]) {
			gc.fillText("R", 1240, 700);
		}
		
		// Score
		gc.setFill(Color.YELLOW);
		gc.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 80));
		gc.fillText(String.valueOf(controller.score), 0.48 * windowWidth, 0.3 * windowHeight);
	}
}
