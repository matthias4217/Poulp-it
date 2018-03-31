package core.scripts;

import content.GameObject;
import core.exceptions.InvalidBoxColliderException;
import core.util.*;

/**
 * This script must be attached to a RECTANGULAR object
 *
 * @author Sebastian Lague, arranged by Raph
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

	public BoxCollider collider;
	public RaycastOrigins raycastOrigins;



	public RaycastController(GameObject support) throws InvalidBoxColliderException {
		super(support);
		collider = new BoxCollider(new Vector2(0,0), PERCENT_SKIN, PERCENT_SKIN);
		raycastOrigins = new RaycastOrigins();
	}

	@Override
	public void start() throws InvalidBoxColliderException {
		try {
			collider = (BoxCollider) getSupport().collider;
		}
		catch (ClassCastException exception) {
			throw new InvalidBoxColliderException("RaycastController on a non rectangular GameObject");
		}

		//Calculating rays
		skinWidth = PERCENT_SKIN * Math.min(collider.getWidth(), collider.getHeight());
		horizontalRaySpacing = (collider.getHeight() - 2*skinWidth) / (horizontalRayCount - 1);
		verticalRaySpacing = (collider.getWidth() - 2*skinWidth) / (verticalRayCount - 1);
	}

	public void updateRayCastOrigins() {
		// Code pas super lisible avec Java, mais en réalité pas compliqué...
		// "Not very readable"... you bet !
		Vector2 shift1 = new Vector2(skinWidth, skinWidth);
		Vector2 shift2 = new Vector2(skinWidth, -skinWidth);
		System.out.println("collider " + collider);
		raycastOrigins.bottomLeft = collider.getBottomLeft().add(shift1);;
		raycastOrigins.bottomRight = collider.getBottomRight().add(shift2.reverse());
		raycastOrigins.topLeft = collider.getTopLeft().add(shift2);
		raycastOrigins.topRight = collider.getTopRight().add(shift1.reverse());
	}

}



class RaycastOrigins {
	public Vector2 topLeft, topRight;
	public Vector2 bottomLeft, bottomRight;


	public RaycastOrigins() {
		super();
		topLeft = new Vector2(0,0);
		topRight= new Vector2(0,0);
		bottomLeft = new Vector2(0,0);
		bottomRight = new Vector2(0,0);
	}

}
