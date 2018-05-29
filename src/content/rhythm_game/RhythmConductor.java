package content.rhythm_game;

import content.GameObject;
import content.Layer;
import content.Tag;
import content.rhythm_game.scripts.ControllerRhythm;
import core.util.Collider;
import core.util.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The main GameObject of the Rhythm game.
 * This manages the rendering of the game and has a controller script which manages the game flow.
 *
 * @author matthias
 *
 */
public class RhythmConductor extends GameObject {

	public static Color LETTER_COLOR_NORMAL = Color.WHITE;
	public static Color LETTER_COLOR_WIN = Color.LIMEGREEN;
	public static Color LETTER_COLOR_FAIL = Color.RED;
	public static Color SCORE_COLOR = Color.YELLOW;
	
	
	ControllerRhythm controller;		// A reference to this' Controller script



	public RhythmConductor() {
		super(Vector2.ZERO(),
				null,
				Layer.DEFAULT,
				Tag.DEFAULT,
				Collider.NO_COLLIDER(),

				new ControllerRhythm());

		controller = (ControllerRhythm) scripts.get(0);		// =/
	}






	@Override
	public void render(GraphicsContext gc, double windowWidth, double windowHeight) {

		
		
		gc.setFill(LETTER_COLOR_NORMAL);
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
		gc.setFill(SCORE_COLOR);
		gc.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 80));
		gc.fillText(String.valueOf(controller.score), 0.48 * windowWidth, 0.3 * windowHeight);

	}

}
