package core;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import core.exceptions.MultipleGameEngineException;

/**
 * This is the starting point of the program.
 * Launcher extends Application and thus has a start method called.
 * This class must stay clean and only call other methods.
 * 
 * @author Raph
 * 
 */
public class Launcher extends Application {

	static final double WINDOW_WIDTH = 1345;
	static final double WINDOW_HEIGHT = 917;
	static final String WINDOW_TITLE = "Hardcore Rodeo 96 !!!";


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
	public void start(Stage stage) throws MultipleGameEngineException {

		// Initialization of the window
		stage.setTitle(WINDOW_TITLE);
		stage.setResizable(false); 
		Group group0 = new Group();
		stage.setScene(new Scene(group0));
		Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		group0.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		Image background = new Image("resources/background_dogs.jpg", WINDOW_WIDTH, WINDOW_HEIGHT, true, true);
		stage.show();

		
		// If we implements a menu, that's probably around here
		
	
		// Initialization of the game
		int nbPlayers = 1;
		GameEngine gameEngine = new GameEngine(nbPlayers);
		GraphicManager graphicManager = new GraphicManager();
		gameEngine.init();


		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				/* Is called in each frame while the timer is active */
				gc.drawImage(background, 0, 0);
				
				gameEngine.update();
				graphicManager.render(gc);
				
			}
		};
		timer.start();
	}

	
	@Override
	public void stop() {
		/* Is called when the window is closed */
		
		
	}

}
