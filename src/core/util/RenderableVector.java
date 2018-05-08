package core.util;

import core.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Class used to render a vector as an arrow starting from a specific point.
 * 
 * @author Raph
 *
 */
public class RenderableVector extends Vector2 implements Renderable {

	public static final Color RENDER_COLOR = Color.MAGENTA;			// The color rays are drawn on screen
	public static final float RENDER_LENGTH_MULTIPLIER = 5f;		// The length multiplier for screen rendering



	Vector2 origin;



	public RenderableVector(float x, float y, Vector2 origin) {
		super(x, y);
		this.origin = origin;
	}

	public RenderableVector(Vector2 vector, Vector2 origin) {
		this(vector.x, vector.y, origin);
	} 



	@Override
	public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		System.out.println("Rendering normal vector: (" + x + ", " + y + ") from " + origin);
		gc.setStroke(RENDER_COLOR);
		gc.strokeLine(origin.x, windowWidth - origin.y,
				origin.x + RENDER_LENGTH_MULTIPLIER * x, windowWidth - (origin.y + RENDER_LENGTH_MULTIPLIER * y));
	}

}
