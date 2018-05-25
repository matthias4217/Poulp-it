package core.util;

/**
 * This class represents a 2-dimensional vector.
 * Contains:
 * 		classic functions on vectors
 * 		convenient "constants" which can be used
 * 
 * @author Raph
 * 
 */
public class Vector2 {

	public float x;
	public float y;


	/* Constructor */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}



	// Dégueu, mais je vois pas comment faire autrement =/
	public static final Vector2 ZERO()	{	return new Vector2(0, 0);	}
	public static final Vector2 UP()	{	return new Vector2(0, 1);	}
	public static final Vector2 DOWN()	{	return new Vector2(0, -1);	}
	public static final Vector2 LEFT()	{	return new Vector2(-1, 0);	}
	public static final Vector2 RIGHT()	{	return new Vector2(1, 0);	}



	/**
	 * @param toAdd
	 * @return thisVector2 + toAdd 		(out-of-place)
	 */
	public Vector2 add(Vector2 toAdd) {
		return new Vector2(this.x + toAdd.x, this.y + toAdd.y);
	}

	/**
	 * @param toSubstract
	 * @return thisVector2 - toSubstract 		(out-of-place)
	 */
	public Vector2 minus(Vector2 toSubstract) {
		return new Vector2(this.x - toSubstract.x, this.y - toSubstract.y);
	}

	/**
	 * Applies a translation to this Vector2 (in-place).
	 * @param translation - the translation to apply to thisVector2
	 */
	public void translate(Vector2 translation) {
		this.x += translation.x;
		this.y += translation.y;
	}

	/**
	 * @param scalar
	 * @return scalar * thisVector2		(out-of-place)
	 */
	public Vector2 multiply(float scalar) {
		return new Vector2(scalar * x, scalar * y);
	}

	/**
	 * @return -(thisVector2)		(out-of-place)
	 */
	public Vector2 reverse() {
		return new Vector2(-x, -y);
	}

	/**
	 * @return the normalized Vector2 associated to thisVector2		(out-of-place)
	 */
	public Vector2 normalize() {
		float norm = this.norm();
		return new Vector2(this.x / norm, this.y / norm);
	}


	/**
	 * @return the norm of thisVector2
	 */
	public float norm() {
		return (float) Math.sqrt(x*x + y*y);
	}

	/**
	 * @param u
	 * @param v
	 * @return the euclidean distance between Vector2 u and Vector2 v
	 */
	public static float distance(Vector2 u, Vector2 v) {
		return (new Vector2(v.x - u.x, v.y - u.y)).norm();
	}

	/**
	 * @param u
	 * @param v
	 * @return the dot product between Vector2 u and Vector2 v
	 */
	public static float dotProduct(Vector2 u, Vector2 v) {
		return u.x * v.x + u.y * v.y;
	}
	
	/**
	 * @param u
	 * @param v
	 * @return the angle in radian between Vector2 u and Vector2 v
	 */
	public static float angle(Vector2 u, Vector2 v) {
		return (float) Math.acos(Vector2.dotProduct(u, v) / (u.norm() * v.norm()));
	}




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
