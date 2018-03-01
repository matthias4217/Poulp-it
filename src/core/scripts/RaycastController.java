package core.scripts;

import core.exceptions.InvalidBoxColliderException;
import core.util.*;

/**
 * This script is attached to a RECTANGULAR object
 * 
 * @author Raph
 * 
 */
public class RaycastController extends MonoBehavior {
	
	public String collisionLayer;					// Indicates which cast is 

	public final float PERCENT_SKIN = 0.015f;		// Percentage of the object bounds that is skin
	protected float skinWidth;

	public int horizontalRayCount;					// Number of horizontal ray
	public int verticalRayCount;					// Number of vertical ray

	protected float horizontalRaySpacing;
	protected float verticalRaySpacing;
	
	public CollisionBounds collider;
	public RaycastOrigins raycastOrigins;

	@Override
	public void awake() throws Exception {		// Awake in order to get the BoxCollider2D component before CameraFollow
		collider = support.collisionBounds;
		if (collider.getNbPoints() != 4) {
			throw new InvalidBoxColliderException("RaycastController on a non rectangular GameObject");
		}
	}

	@Override
	public void start() {
		calculateRay();
	}
		
	public void calculateRay() {
		Bounds bounds = collider.bounds;
		skinWidth = PERCENT_SKIN * Math.min(bounds.size.x, bounds.size.y);

		bounds.Expand (skinWidth * -2);
		horizontalRaySpacing = bounds.size.y / (horizontalRayCount - 1);
		verticalRaySpacing = bounds.size.x / (verticalRayCount - 1);
	}
	
	public void updateRaycastOrigins() {
		Bounds bounds = collider.bounds;
		bounds.Expand (skinWidth * -2);		// Reduce the bounds

		raycastOrigins.bottomLeft = new Vector2 (bounds.min.x, bounds.min.y);
		raycastOrigins.bottomRight = new Vector2 (bounds.max.x, bounds.min.y);
		raycastOrigins.topLeft = new Vector2 (bounds.min.x, bounds.max.y);
		raycastOrigins.topRight = new Vector2 (bounds.max.x, bounds.max.y);
	}

}




class RaycastOrigins {
	public Vector2 topLeft, topRight;
	public Vector2 bottomLeft, bottomRight;
}
