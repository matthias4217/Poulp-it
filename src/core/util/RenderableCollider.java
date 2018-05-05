package core.util;

import core.Renderable;
import core.exceptions.InvalidArgumentsException;
import javafx.scene.canvas.GraphicsContext;

public class RenderableCollider extends Collider implements Renderable {

	Vector2 position;



	
	
	public RenderableCollider() {
		super();
	}

	public RenderableCollider(Vector2[] pointsArray, Vector2 position) throws InvalidArgumentsException {
		super(pointsArray);
		this.position = position;
	}

	public RenderableCollider(Collider collider, Vector2 position) throws InvalidArgumentsException {
		super(collider.pointsArray);
		this.position = position;
	}

	
	


	@Override
	public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		render(gc, position);
	}



}
