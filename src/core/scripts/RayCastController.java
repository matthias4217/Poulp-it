package core.scripts;

import core.exceptions.InvalidBoxColliderException;
import core.util.*;

/**
 * This script is attached to a RECTANGULAR object
 * 
 * @author Raph
 * 
 */
public class RayCastController extends MonoBehavior {
	
	public String collisionLayer;					// Indicates on which layer the Rays will detect things

	public final float PERCENT_SKIN = 0.015f;		// Percentage of the object bounds that is skin
	protected float skinWidth;

	protected int horizontalRayCount;				// Number of horizontal ray
	protected int verticalRayCount;					// Number of vertical ray
	protected float horizontalRaySpacing;			// 
	protected float verticalRaySpacing;				// 

	public BoxCollider collider;
	public RaycastOrigins raycastOrigins;

	@Override
	public void awake() throws InvalidBoxColliderException {		// @@@ awake() instead of start() to get the Collider before CameraFollow
		try {
			collider = (BoxCollider) support.collider;
		}
		catch (ClassCastException exception) {
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
	
	public void updateRayCastOrigins() {
		// Code pas super lisible, mais en réalité pas compliqué...
		Vector2 shift1 = new Vector2(skinWidth, skinWidth);
		Vector2 shift2 = new Vector2(skinWidth, -skinWidth);
		raycastOrigins.bottomLeft = collider.getBottomLeft().add(shift1);;
		raycastOrigins.bottomRight = collider.getBottomRight().add(shift2.reverse());
		raycastOrigins.topLeft = collider.getTopLeft().add(shift2);
		raycastOrigins.topRight = collider.getTopRight().add(shift1.reverse());
	}

}



class RaycastOrigins {
	public Vector2 topLeft, topRight;
	public Vector2 bottomLeft, bottomRight;
}
