package core;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import core.exceptions.InvalidArgumentsException;
import core.exceptions.MultipleGameEngineException;
import core.util.GameInformation;
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

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();		// Problems may happen in case of multi-monitors.

	/**
	 * SCALE is the variable which may need some adjustments.	@@@ XXX
	 */
	static final double SCALE = 0.8f;
	public static double WINDOW_WIDTH = SCALE * screenSize.getWidth();
	public static double WINDOW_HEIGHT = SCALE * screenSize.getHeight();

	static final String WINDOW_TITLE = "Hook Battle";



	public static void main(String[] args) {
		/**
		 * When the Application is launched,
		 * - init() is called
		 * - start() is called
		 * - waiting for Platform.exit() or last window closed
		 * - stop() is called
		 */
		System.out.println("Starting the program");
		launch(args);
	}


	@Override
	public void start(Stage stage) throws MultipleGameEngineException, IOException, InvalidArgumentsException {

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
		String level = "level0";
		gameEngine.init(nbPlayers, level);

		PlayerInput playerInput = new PlayerInput();

		/* 
		 * gameInformation contains the information which is sent top the clientd each frame.
		 * It is updated each frame by the GameEngine.
		 */
		GameInformation gameInformation = new GameInformation();


		/*
		 * An AnimationTimer used for testing purpose. 
		 */
		AnimationTimer timerTest = new AnimationTimer() {
			@Override public void handle(long now) {
				gc.clearRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
				double[] xPoints = {50.0, 75.0, 100.0};
				double[] yPoints = {50.0, 200.0, 50.0};
				gc.strokePolyline(xPoints, yPoints, 3);
			}
		};






		AnimationTimer timer = new AnimationTimer() {
			long oldNow = System.nanoTime();
			float timeToFramerateDisplay = 0f;
			int framerate;
			@Override public void handle(long now) {
				/* handle is called in each frame while the timer is active. */

				System.out.print(System.lineSeparator());		// To differentiate the different frames in the console

				
				gc.drawImage(background, 0, 0);
				stage.getScene().setOnKeyPressed(playerInput.eventHandler);		// getting the player input.
				System.out.println(playerInput);

				float deltaTime = (now - oldNow) * 0.000000001f;
				System.out.println("Time elapsed since the last frame: " + deltaTime + "s");
				oldNow = now;

				try {
					gameEngine.update(deltaTime, playerInput, gameInformation);
				} catch (InvalidArgumentsException e) {					
					e.printStackTrace();
				}

				playerInput.directionalInput = Vector2.ZERO(); //XXX if placed just **before**
				// setOnKeyPressed, then it doesn't work ?!?
				
				System.out.println("Rendering...");
				graphicManager.render(gc, WINDOW_WIDTH, WINDOW_HEIGHT);

				timeToFramerateDisplay -= deltaTime;
				if (timeToFramerateDisplay <= 0) {
					framerate = Math.min(60, (int) (1 / deltaTime)); 
					timeToFramerateDisplay = 0.1f;
				}
				gc.setFont(Font.font("Helvetica", FontWeight.SEMI_BOLD, 12));
				gc.setFill(Color.LIME);
				gc.fillText(Integer.toString(framerate), 5, 15);
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
