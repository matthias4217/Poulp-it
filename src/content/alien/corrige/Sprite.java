package content.alien.corrige;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Sprite {

	private Image image;
	private double x;
	private double y;
	private double xSpeed;
	private double ySpeed;
	private double width;
	private double height;
	private double maxX;
	private double maxY;



	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}



	public Sprite(String path, double width, double height, double maxX, double maxY) {
		image = new Image(path, width, height, false, false);
		this.width = width;
		this.height = height;
		this.maxX = maxX;
		this.maxY = maxY;
	}



	public void validatePosition() {
		if (x + width >= maxX) {
			x = maxX - width;
			xSpeed *= -1;
		} else if (x < 0) {
			x = 0;
			xSpeed *= -1;
		}

		if (y + height >= maxY) {
			y = maxY - height;
			ySpeed *= -1;
		} else if (y < 0) {
			y = 0;
			ySpeed *= -1;
		}
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		validatePosition();
	}

	public void setSpeed(double xSpeed, double ySpeed) {
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}

	public void changeSpeed(KeyCode code) {
		switch(code) {
		case LEFT:  xSpeed--; break;
		case RIGHT: xSpeed++; break;
		case UP:    ySpeed--; break;
		case DOWN:  ySpeed++; break;
		default:
		}
	}

	public void updatePosition() {
		x += xSpeed;
		y += ySpeed;
		validatePosition();
	}


	public boolean intersects(Sprite other) {
		return ((x >= other.x && x <= other.x + other.width) ||
				(other.x >= x && other.x <= x + width)) &&
				((y >= other.y && y <= other.y + other.height) ||
						(other.y >= y && other.y <= y + height));
	}


	public void render(GraphicsContext gc) {
		gc.drawImage(image, x, y);
	}



	@Override public String toString() {
		return "Sprite<" + x + ", " + y + ">";
	}

}
