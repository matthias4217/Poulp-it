package core.scripts;

import core.util.*;
import content.Layer;

/**
 * Superclass for all scripts which rely on raycasts to detect collisions.
 * The rays are cast from the edge of the collider of a gameObject.
 * This script must be attached to a RECTANGULAR object (GameObject with a BoxCollider)
 *
 * @author Sebastian Lague, arranged by Raph
 *
 */
public class RaycastController extends MonoBehaviour {

	public Layer layer;					// Indicates on which layer the Rays will detect things

	public static final float PERCENT_SKIN = 0.015f;		// Percentage of the object bounds that is skin
	protected float skinWidth;

	protected int horizontalRayCount = 4;			// Number of horizontal rays
	protected int verticalRayCount = 4;				// Number of vertical rays
	protected float horizontalRaySpacing;			// The distance between two adjacent horizontal rays
	protected float verticalRaySpacing;				// The distance between two adjacent vertical rays

	public BoxCollider collider;
	public RaycastOrigins raycastOrigins;



	public RaycastController() {
		raycastOrigins = new RaycastOrigins();
	}


	@Override
	public void start() {
		try {
			collider = (BoxCollider) support.collider;
		}
		catch (ClassCastException exception) {
			System.out.println("INVALID COLLIDER");
			exception.printStackTrace();
		}

		// Calculating rays
		skinWidth = PERCENT_SKIN * Math.min(collider.getWidth(), collider.getHeight());
		horizontalRaySpacing = (collider.getWidth() - 2*skinWidth) / (horizontalRayCount - 1);
		verticalRaySpacing = (collider.getHeight() - 2*skinWidth) / (verticalRayCount - 1);
	}


	public void updateRayCastOrigins() {
		// Code pas super lisible en Java, mais en réalité pas compliqué...
		// "Not very readable"... you bet !
		Vector2 shift1 = new Vector2(skinWidth, skinWidth);
		Vector2 shift2 = new Vector2(skinWidth, -skinWidth);
		raycastOrigins.topLeft = collider.getTopLeft().add(shift2);
		raycastOrigins.topRight = collider.getTopRight().add(shift1.reverse());
		raycastOrigins.bottomLeft = collider.getBottomLeft().add(shift1);;
		raycastOrigins.bottomRight = collider.getBottomRight().add(shift2.reverse());
		System.out.println(raycastOrigins);
	}

}




class RaycastOrigins {

	public Vector2 topLeft, topRight;
	public Vector2 bottomLeft, bottomRight;

	public RaycastOrigins() {
		topLeft = topRight = Vector2.ZERO();
		bottomLeft = bottomRight = Vector2.ZERO();
	}
	
	
	@Override public String toString() {
		return "RaycastOrigins[topLeft" + topLeft + "; topRight"+ topRight + "; bottomLeft" + bottomLeft + "; bottomRight" + bottomRight + "]";
	}

}
