package core.util;

/**
 * This class represents a 2-dimensional vector.
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
	
	public float norm() {
		/* Return the norm of this Vector2 */
		return (float)Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
	}
	
	public Vector2 normalize() {
		/* Return the normalized Vector2 associated to this one.
		 * NOT IN PLACE
		 */
		float norm = this.norm();
		return new Vector2(this.x / norm, this.y / norm);
	}
	
	
	
	public static Vector2 zero	= new Vector2(0, 0);
	public static Vector2 up	= new Vector2(0, 1);
	public static Vector2 down	= new Vector2(0, -1);
	public static Vector2 left	= new Vector2(-1, 0);
	public static Vector2 right	= new Vector2(1, 0);
	
	
	
	
	@Override
	public boolean equals(Object toCompare) {
		Vector2 toCompareVector = (Vector2) toCompare;
		return (toCompareVector.x == this.x && toCompareVector.y == this.y);
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
