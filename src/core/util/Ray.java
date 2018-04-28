package core.util;

import core.Renderable;
import core.exceptions.InvalidArgumentsException;
import core.util.Annex.Direction;
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

	public static final Color RENDER_COLOR = Color.RED;			// The color rays are drawn on screen
	public static final float RENDER_LENGTH_MULTIPLIER = 10;		// The lenght multiplier for screen rendering 



	private Vector2 originPoint;
	private Vector2 endingPoint;
	private float length;

	private boolean hit = false;		// Indicates if something was hit by the ray


	public Vector2 getEndingPoint() {
		return endingPoint;
	}

	public float getLength() {
		return length;
	}



	/**
	 * @param originPoint
	 * @param direction
	 * @param length
	 * 
	 * @throws InvalidArgumentsException if direction is null
	 */
	public Ray(Vector2 originPoint, Direction direction, float length) {
		this.originPoint = originPoint;
		switch (direction) {
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
	 * @@@ TODO TODO
	 * If this is the case, updates this ray by setting its endingPoint to the intersection point.
	 * 
	 * @param collider	- The Collider to check collision with
	 * @return	the NORMAL between this ray and the collider, null if there is no intersection FIXME
	 */
	public Vector2 collision(Collider collider, Vector2 colliderOrigin) {
		if (collider == null) {
			return null;
		}
		Vector2 result = null;
		
		int n = collider.getNbPoints();
		for (int i = 0; i < n; i++) {

			Vector2 collider_I = colliderOrigin.add(collider.getPoint(i));
			Vector2 collider_IPlusOne = colliderOrigin.add(collider.getPoint((i+1) % n));
			
			Vector2 intersectionPoint =	Annex.segmentsIntersection(
					originPoint, endingPoint, collider_I, collider_IPlusOne);

			if (intersectionPoint != null) {		// if there was intersection between the lines
				float dist = Vector2.distance(originPoint, intersectionPoint);
				
				if (dist < length) {
					this.endingPoint = intersectionPoint;		// Reducing the ray
					length = dist;		// Updating lenght
					result = Annex.normal(collider_I, collider_IPlusOne, originPoint);
				}
			}
		}
		return result;
	}



	/**
	 * Render this ray in the GraphicContext gc
	 */
	@Override public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		gc.setStroke(RENDER_COLOR);
		gc.strokeLine(originPoint.x,
				windowHeight - originPoint.y,
				originPoint.x + RENDER_LENGTH_MULTIPLIER * (endingPoint.x - originPoint.x),
				windowHeight - (originPoint.y + RENDER_LENGTH_MULTIPLIER * (endingPoint.y - originPoint.y)));
	}





}
