package core;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import core.exceptions.InvalidArgumentsException;
import core.exceptions.MultipleGameEngineException;
import core.util.Vector2;

/**
 * This is the starting point of the program.
 * Launcher extends Application and thus has a start method called.
 * This class should stay relatively clean and only call other methods.
 *
 * @author Raph
 *
 */
public class Launcher extends Application {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();		// Problems may happen in case of multi-monitors.

	/**
	 * SCALE is the variable which may need some adjustments.
	 */
	static final double SCALE = 0.9;
	double WINDOW_WIDTH = SCALE * screenSize.getWidth();
	double WINDOW_HEIGHT = SCALE * screenSize.getHeight();

	static final String WINDOW_TITLE = "Hook Battle";



	public static void main(String[] args) {
		/**
		 * When the Application is launched,
		 * - init() is called
		 * - start() is called
		 * - waiting for Platform.exit() or last window closed
		 * - stop() is called
		 */
		launch(args);
	}

	@Override
	public void start(Stage stage) throws MultipleGameEngineException, IOException {
		// Initialization of the window
		System.out.println(WINDOW_WIDTH + " × " + WINDOW_HEIGHT);
		stage.setTitle(WINDOW_TITLE);
		stage.setResizable(false);
		Group group0 = new Group();
		stage.setScene(new Scene(group0));
		Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		group0.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image background = new Image("resources/graphic/background_dogs.jpg", WINDOW_WIDTH, WINDOW_HEIGHT, true, true);
		stage.show();


		// If we implement a menu, that's probably around here.


		// Initialization of the game
		GameEngine gameEngine = new GameEngine();
		GraphicManager graphicManager = new GraphicManager();
		int nbPlayers = 1;
		gameEngine.init(nbPlayers, "level0");
		gc.drawImage(background, 0, 0);
		GameInformation gameInformation = new GameInformation();


		AnimationTimer timer = new AnimationTimer() {
			long oldNow = System.nanoTime();
			@Override public void handle(long now) {
				/* handle is called in each frame while the timer is active. */
				

				gc.drawImage(background, 0, 0);
				
				gameInformation.playerInput = Vector2.zero;
				stage.getScene().setOnKeyPressed(gameInformation.eventHandler);		// getting the player input
				gameInformation.playerInput = Vector2.zero;
				System.out.println("Etat de input avant d'aller dans les objets : " + gameInformation.playerInput);		// WTF ?!
				
				float deltaTime = (now - oldNow) * 0.000000001f;
				System.out.println("Time elapsed since the last frame: " + deltaTime + "s");
				oldNow = now;

				try {
					gameEngine.update(deltaTime, gameInformation);
				} catch (InvalidArgumentsException e) {}
				graphicManager.render(gc);
				
				System.out.print(System.lineSeparator());
				
			}
		};
		timer.start();
	}



	@Override
	public void stop() {
		/* Is called when the window is closed */
		System.out.println("Game closed =(");

	}

}
