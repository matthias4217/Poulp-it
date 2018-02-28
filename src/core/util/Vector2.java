package core.util;

/**
 * Simple structure which represent a 2-dimensional vector.
 * Contains:
 * 		classic functions on vectors
 * 		convenient static variables which can be used
 * 
 * @author Raph
 * 
 */
public final class Vector2 {

	public float x;
	public float y;

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
		
	
	public Vector2 add(Vector2 vectorToAdd) {
		/* Add two vectors. 
		 * NOT IN PLACE
		 */
		return new Vector2(this.x + vectorToAdd.x, this.y + vectorToAdd.y);
	}
	
	public Vector2 multiply(float factor) {
		/* Return the product of this vector by the scalar factor. 
		 * NOT IN PLACE
		 */
		return new Vector2(factor * x, factor * y);
	}
	
	
	
	public static Vector2 zero	= new Vector2(0, 0);
	public static Vector2 up	= new Vector2(0, 1);
	public static Vector2 down	= new Vector2(0, -1);
	public static Vector2 left	= new Vector2(-1, 0);
	public static Vector2 right	= new Vector2(1, 0);
	
	
	
	
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
