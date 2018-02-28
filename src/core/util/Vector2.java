package core.util;

/**
 * Simple structure which represent a 2-dimensional vector.
 * Contains convenient static variables which can be used.
 * 
 * @author Raph
 * 
 */
public final class Vector2 {		// @@@ final?

	public float x;
	public float y;

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
		
	
	
	public static Vector2 zero	= new Vector2(0, 0);
	public static Vector2 up	= new Vector2(0, 1);
	public static Vector2 down	= new Vector2(0, -1);
	public static Vector2 left	= new Vector2(-1, 0);
	public static Vector2 right	= new Vector2(1, 0);
	
}
