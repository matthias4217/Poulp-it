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

// @TODO Organiser cette classe et la documenter correctement
public class Vector2 {

	public float x;
	public float y;

	/* Constructor */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
		
	
	
	public Vector2 add(Vector2 toAdd) {
		/* Add two vectors.
		 * NOT IN PLACE
		 */
		return new Vector2(this.x + toAdd.x, this.y + toAdd.y);
	}
	
	public Vector2 minus(Vector2 toSubstract) {
		return new Vector2(this.x - toSubstract.x, this.y - toSubstract.y);
	}
	
	public void translate(Vector2 translation) {
		/* Apply translation to this Vector2. */
		this.x += translation.x;
		this.y += translation.y;
	}
	
	public Vector2 multiply(float scalar) {
		/* Return the product of this vector by the scalar scalar. 
		 * NOT IN PLACE
		 */
		return new Vector2(scalar * x, scalar * y);
	}
	
	public Vector2 reverse() {
		/* Return -thisVector2 */
		return new Vector2(-x, -y);
	}
	
	public float norm() {
		/* Return the norm of this Vector2. */
		return (float)Math.sqrt(x*x + y*y);
	}
	
	public Vector2 normalize() {
		/* Return the normalized Vector2 associated to this one.
		 * NOT IN PLACE
		 */
		float norm = this.norm();
		return new Vector2(this.x / norm, this.y / norm);
	}
	
	
	
	public static float distance(Vector2 u, Vector2 v) {
		/* Return the distance between u and v. */
		return (new Vector2(v.x - u.x, v.y - u.y)).norm();
		
	}
	
	public static float dotProduct(Vector2 u, Vector2 v) {
		/* Return the dot product between u and v. */
		return u.x * v.x + u.y * v.y;
	}
	
	public static float angle(Vector2 u, Vector2 v) {
		/* Return the angle in radian between u and v. */
		return (float) Math.acos(Vector2.dotProduct(u, v) / (u.norm() * v.norm()));
	}
	
	
	
	public static final Vector2 zero	= new Vector2(0, 0);
	public static final Vector2 up		= new Vector2(0, 1);
	public static final Vector2 down	= new Vector2(0, -1);
	public static final Vector2 left	= new Vector2(-1, 0);
	public static final Vector2 right	= new Vector2(1, 0);
	
	
	
	
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
