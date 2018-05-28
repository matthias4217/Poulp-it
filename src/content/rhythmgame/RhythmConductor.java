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
		if (controller.currentLetters[0]) {
			gc.fillText("A", 200, 300);
		}

		if (controller.currentLetters[1]) {
			gc.fillText("Z", 250, 300);
		}

		if (controller.currentLetters[2]) {
			gc.fillText("E", 300, 300);
		}

		if (controller.currentLetters[3]) {
			gc.fillText("R", 350, 300);
		}
		gc.fillText(String.valueOf(controller.score), 100, 100);
	}
}
