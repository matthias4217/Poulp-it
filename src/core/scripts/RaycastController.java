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
	
	public String collisionLayer;					// Indicates on which layer the Rays will detect things

	public final float PERCENT_SKIN = 0.015f;		// Percentage of the object bounds that is skin
	protected float skinWidth;

	protected int horizontalRayCount;				// Number of horizontal ray
	protected int verticalRayCount;					// Number of vertical ray
	protected float horizontalRaySpacing;			// 
	protected float verticalRaySpacing;				// 

	public CollisionBox collider;
	public RaycastOrigins raycastOrigins;

	@Override
	public void awake() throws Exception {		// Awake in order to get the BoxCollider2D component before CameraFollow
		try {
			collider = (CollisionBox) support.collisionBounds;
		}
		catch (CastException e) {
			throw new InvalidBoxColliderException("RaycastController on a non rectangular GameObject");
		}
	}

	@Override
	public void start() {
		// Calculating rays
		skinWidth = PERCENT_SKIN * Math.min(collider.getWidth(), collider.getHeight());
		horizontalRaySpacing = (collider.getHeight() - 2*skinWidth) / (horizontalRayCount - 1);
		verticalRaySpacing = (collider.getWidth() - 2*skinWidth) / (verticalRayCount - 1);
	}
	
	public void updateRaycastOrigins() {
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
