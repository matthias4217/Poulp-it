/**
 * Starting point for the program
 */
package core;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 * @author Raph
 *
 */
public class Main extends Application {
	
	public static double WINDOWS_WIDTH = 1800;
	public static double WINDOWS_HEIGHT = 1000;
	
	
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
	public void init() {
		
	}
	
	@Override
	public void start(Stage stage) {
		// Initialisation of the window
		stage.setTitle("Hardcore Rodeo 96");
		stage.setResizable(false);
		Group group0 = new Group();
		stage.setScene(new Scene(group0));
		Canvas canvas = new Canvas(WINDOWS_WIDTH, WINDOWS_HEIGHT);
		group0.getChildren().add(canvas);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		//Image background = new Image("space2.jpg", WINDOWS_WIDTH, WINDOWS_HEIGHT, false, true);
		stage.show();

		// Si on met un menu, ce sera par ici je pense...
		
		
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				/* Is called in each frame while the timer is active */
				
			}
		};
		timer.start();
	}
	
	@Override
	public void stop() {
		
	}
	
	
}
