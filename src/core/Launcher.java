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

import content.maze.Maze;
import core.exceptions.InvalidArgumentsException;
import core.exceptions.MultipleGameEngineException;
import core.util.Annex;
import core.util.GameInformation;
import core.util.Vector2;

/**
 * This is the starting point of the program.
 * Launcher extends Application and thus has a start method called.
 * This class should stay relatively clean and only call other methods.
 *
 * @author Raph, matthias
 *
 */
public class Launcher extends Application {

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();		// Problems may happen in case of multi-monitors.

	static final double SCALE = 0.9f;
	public static double WINDOW_WIDTH = SCALE * screenSize.getWidth();
	public static double WINDOW_HEIGHT = SCALE * screenSize.getHeight();


	private static double K;
	private static double L;


	/**
	 * The game that will be loaded
	 */
	static Game game = Game.RHYTHM_GAME;


	PlayerInput previousPlayerInput;



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
		stage.setTitle(game.windowTitle);
		stage.setResizable(true);
		Group group0 = new Group();
		stage.setScene(new Scene(group0));
		Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		group0.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image background = new Image("resources/graphic/backgrounds/rideau.jpg", WINDOW_WIDTH, WINDOW_HEIGHT, true, true);
		stage.show();
		
		
		// XXX
		K = stage.getWidth() - WINDOW_WIDTH;
		L = stage.getHeight() - WINDOW_HEIGHT;


		// Initialization of the game
		GameEngine gameEngine = new GameEngine();
		GraphicManager graphicManager = new GraphicManager();



		switch (game) {
		case HOOK_BATTLE:
			int nbPlayers = 1;
			String level0 = "level0";
			gameEngine.initPlatformer(nbPlayers, level0);
			break;
		case SHOOTER:
			String level1 = "level0";
			gameEngine.init2(level1);
			break;
		case MAZE:
			gameEngine.initMazeGame(38, 17, true);
			break;
		case ALIEN:

			break;
		case RHYTHM_GAME:
			String level = "rhythmgame";
			gameEngine.initRhythmGame(level);
			break;
		default:

			break;
		}

		PlayerInput playerInput = new PlayerInput();
		previousPlayerInput = new PlayerInput();

		/* 
		 * gameInformation contains the information which is sent to the client each frame.
		 * It is updated each frame by the GameEngine.
		 */
		GameInformation gameInformation = new GameInformation();

		stage.getScene().setOnKeyPressed(playerInput.eventHandler);		// getting the player input.
		stage.getScene().setOnMousePressed(playerInput.mouseEventHandler);
		stage.getScene().setOnKeyReleased(playerInput.eventHandlerReleased);

		/*
		 * An AnimationTimer used for testing purpose. 
		 */
		AnimationTimer timerTest1 = new AnimationTimer() {
			@Override public void handle(long now) {

				// Setting axes
				gc.setStroke(Color.BLACK);
				gc.strokeLine(WINDOW_WIDTH/2, 0, WINDOW_WIDTH/2, WINDOW_HEIGHT);
				gc.strokeLine(0, WINDOW_HEIGHT/2, WINDOW_WIDTH, WINDOW_HEIGHT/2);

				Vector2 A = new Vector2(0, 0);
				Vector2 B = new Vector2(1, 1);
				Vector2 R = new Vector2(0, 1);

				Vector2 n = Annex.normal(A, B, R);
				System.out.println("A: " + A);
				System.out.println("B: " + B);
				System.out.println("R: " + R);
				System.out.println("Orientation RAB: " + Annex.orientation(R, A, B));
				System.out.println();
				System.out.println("n: " + n);

				gc.setStroke(Color.BLUE);
				gc.strokeLine(A.x, A.y, B.x, B.y);


			}
		};

		/*
		 * Another one 
		 */
		Maze maze = new Maze(38, 17, true);
		AnimationTimer timerTest2 = new AnimationTimer() {
			@Override public void handle(long now) {
				maze.render(gc, stage.getWidth(), stage.getHeight());


			}
		};
		
		



		AnimationTimer timer = new AnimationTimer() {
			long oldNow = System.nanoTime();
			float timeToFramerateDisplay = 0f;
			int framerate;
			@Override public void handle(long now) {
				/* handle is called in each frame while the timer is active. */


				System.out.print(System.lineSeparator());		// To differentiate the different frames in the console

				gc.clearRect(0, 0, stage.getWidth(), stage.getHeight());	// Clear the window
//				gc.drawImage(background, 0, 0);


				System.out.println(playerInput);

				float deltaTime = (now - oldNow) * 0.000000001f;
				System.out.println("Time elapsed since the last frame: " + deltaTime + "s");
				oldNow = now;

				try {
					gameEngine.update(deltaTime, playerInput, previousPlayerInput, gameInformation);
				} catch (InvalidArgumentsException e) {					
					e.printStackTrace();
				}

				previousPlayerInput = playerInput.copy();

				System.out.println("Rendering...");
				graphicManager.render(gc, stage.getWidth() - K, stage.getHeight() - L);

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
