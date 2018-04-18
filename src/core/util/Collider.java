package core.util;

import java.util.Arrays;

import core.Launcher;
import core.Renderable;
import core.exceptions.InvalidArgumentsException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class represents a collision mask.
 * In practice, this is simply a list of points which defines a polygon.
 * If you want rectangular bounds, use the subclass BoxCollider.
 * The user is trusted to create valid forms.
 *
 * Note: A line (two points) is valid.
 *
 * @author Raph
 *
 */
// Not sure if necessary =/
public class Collider /*implements Renderable*/ {

	protected Vector2[] pointsArray;
	protected int nbPoints;



	public Vector2 getPoint(int i) {
		return pointsArray[i];
	}

	protected void setPointsArray(Vector2[] pointsArray) {
		/* Used by subclass BoxCollider */
		this.pointsArray = pointsArray;
	}

	public int getNbPoints() {
		return nbPoints;
	}



	/* Constructor */
	public Collider(Vector2[] pointsArray) throws InvalidArgumentsException {
		if (pointsArray.length < 2) {
			throw new InvalidArgumentsException("The array of points for a Collider must be at least two");
		}

		this.pointsArray = pointsArray;
		nbPoints = pointsArray.length;
	}

	protected Collider() {
		/* This constructor is only used by the subclass BoxCollider. */
	}





	/**
	 * Render this collider on the GraphicContext gc.
	 * 
	 * @param gc		- The GraphicContext on which this will be rendered
	 * @param center	- 
	 */
	public void render(GraphicsContext gc, Vector2 center) {
		double[] xPoints = new double[nbPoints];
		double[] yPoints = new double[nbPoints]; 
		for (int i = 0; i < nbPoints; i++) {
			xPoints[i] = center.x + pointsArray[i].x;
			yPoints[i] = Launcher.WINDOW_HEIGHT - center.y - pointsArray[i].y;
		}

		gc.setStroke(Color.GREEN);
		gc.strokePolygon(xPoints, yPoints, nbPoints);
	}



	@Override public String toString() {
		return "Collider[" + nbPoints + " points: "	+ Arrays.toString(pointsArray) + "]";
	}


}
