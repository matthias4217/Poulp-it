package core.util.specific;

import core.util.Vector2;

/**
 * This structure contains the four origins points of the rays casted from a GameObject (Player).
 * Used in RaycastController.
 * 
 * @author Raph
 *
 */
public class RaycastOrigins {
	public Vector2 topLeft, topRight;
	public Vector2 bottomLeft, bottomRight;
}