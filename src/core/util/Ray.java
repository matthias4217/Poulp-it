package core.util;

import content.GameObject;
import core.Renderable;
import core.exceptions.InvalidArgumentsException;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class represents a Ray, which is used to detect collisions with GameObjects.
 * A Ray is always a pure vertical or pure horizontal line.
 * In practice, what is stored are simply two points.
 * 
 * @author Raph
 * 
 */
public class Ray implements Renderable {
	
	public static final Color renderColor = Color.RED;
	
	
	private Vector2 originPoint;
	private Vector2 endingPoint;

	public Vector2 getEndingPoint() {
		return endingPoint;
	}


	/**
	 * @param originPoint
	 * @param direction
	 * @param length
	 * 
	 * @throws InvalidArgumentsException if direction is null
	 */
	public Ray(Vector2 originPoint, Direction dir, float length) {
		this.originPoint = originPoint;
		switch (dir) {
		case UP:
			this.endingPoint = originPoint.add(new Vector2(0, length));
			break;
		case DOWN:
			this.endingPoint = originPoint.add(new Vector2(0, -length));
			break;
		case LEFT:
			this.endingPoint = originPoint.add(new Vector2(-length, 0));
			break;
		case RIGHT:
			this.endingPoint = originPoint.add(new Vector2(length, 0));
			break;
		default:
			break;
		}
		
	}


	/**
	 * Check if there is a collision between this ray and a GameObject.
	 * If this is the case, updates this ray by setting its endingPoint to the intersection point.
	 * 
	 * @param gameObject	- The gameObject to check collision with
	 */
	public void collision(GameObject gameObject) {
		Collider collider = gameObject.collider;
		if (collider == null) {
			return;
		}
		for (int i = 0; i < collider.getNbPoints(); i++) {
			//
			//Annex.checkcollider.getPoint(i)
			
			
		}

		
		
	}
	
	/**
	 * Render this ray in the GraphicContext gc
	 */
	@Override public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		gc.setStroke(renderColor);
		gc.strokeLine(originPoint.x, windowHeight - originPoint.y, endingPoint.x, windowHeight - endingPoint.y);
	}





	public enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}

}
