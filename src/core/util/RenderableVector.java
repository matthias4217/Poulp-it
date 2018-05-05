package core.util;

import core.Renderable;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class used to render a vector as an arrow starting from a specific point.
 * 
 * @author Raph
 *
 */
public class RenderableVector extends Vector2 implements Renderable {

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
		gc.strokeLine(origin.x, origin.y, origin.x + x, origin.y + y);
	}

}
