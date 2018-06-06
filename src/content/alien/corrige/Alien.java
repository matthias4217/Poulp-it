package content.alien.corrige;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Alien extends Application {

	public static int WIDTH = 694;
	public static int HEIGHT = 520;
	public static int NB_PINAPPLES = 30;


	private int score;



	public static void main(String[] args) {
		launch(args);
	}


	public void start(Stage stage) {

		stage.setTitle("Alien vs Pinapples");
		stage.setResizable(false);

		Group root = new Group();
		Scene scene = new Scene(root);
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
		gc.setFill(Color.BISQUE);
		gc.setStroke(Color.RED);
		gc.setLineWidth(1);

		Image space = new Image("resources/graphic/alien/space.jpg", WIDTH, HEIGHT, false, false);

		Sprite spaceship = new Sprite("resources/graphic/alien/alien.png", 62, 36, WIDTH, HEIGHT);
		spaceship.setPosition(200, 100);

		Collection<Sprite> pinapples = new ArrayList<Sprite>();

		for(int i = 0; i < NB_PINAPPLES; i++) {
			Sprite pinapple = new Sprite("resources/graphic/alien/pinapple.png", 19, 36, WIDTH, HEIGHT);
			pinapple.setPosition(WIDTH * Math.random(), HEIGHT * Math.random());
			changeSpeed(pinapple);
			pinapples.add(pinapple);
		}

		stage.setScene(scene);
		stage.show();


		EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				spaceship.setSpeed(0, 0);
				spaceship.setPosition(e.getX() - spaceship.getWidth()/2, e.getY() - spaceship.getHeight()/2);
			}
		};

		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				spaceship.changeSpeed(e.getCode());
			}
		});


		new AnimationTimer() {
			public void handle(long now) {

				gc.drawImage(space, 0, 0);

				spaceship.updatePosition();

				Iterator<Sprite> it = pinapples.iterator();
				while(it.hasNext()) {
					Sprite pinapple = it.next();
					pinapple.updatePosition();
					if(pinapple.intersects(spaceship)) {
						it.remove();
						score += 100;
					} else {
						pinapple.render(gc);
						if(Math.random() > 0.995)
							changeSpeed(pinapple);
					}
				}

				spaceship.render(gc);

				String txt = "Score: " + score;
				gc.fillText(txt, 540, 36);
				gc.strokeText(txt, 540, 36); 
			}
		}.start();
	}



	private void changeSpeed(Sprite pinapple) {
		int max = 5;
		pinapple.setSpeed(max * (Math.random() - 0.5), max * (Math.random() - 0.5));
	}

}
