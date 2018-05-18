package core.util;

import core.annotations.Unused;

/**
 * This class stores diverse constants and methods such as math ones.
 * 
 * @author Raph
 *
 */
public final class Annex {

	/* CONSTANTS */

	public static final float DEG2RAD = (float) Math.PI / 180;		// Multiplicative factor to convert degree to radian




	/* METHODS */

	/**
	 * Clamp a value between a minimum float and a maximum float value. 
	 *
	 * @param value
	 * @param a
	 * @param b
	 * @return value clamped between a and b
	 */
	public static float clamp(float value, float a, float b) {
		return Math.max(a, Math.min(b, value));	
	}



	// https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

	/**
	 * Given three colinear Vector2s A, B, C, the function checks if
	 * Vector2 B lies on line segment [A; C]
	 */
	@Unused
	public static boolean onSegment(Vector2 A, Vector2 B, Vector2 C) {
		return (B.x <= Math.max(A.x, C.x) && B.x >= Math.min(A.x, C.x) &&
				B.y <= Math.max(A.y, C.y) && B.y >= Math.min(A.y, C.y));
	}


	/**
	 * To find orientation of ordered triplet (A, B, C).
	 * The function returns following values
	 * 0 	-> A, B and C are colinear
	 * 1 	-> Clockwise
	 * -1 	-> Counterclockwise
	 */
	public static float orientation(Vector2 A, Vector2 B, Vector2 C) {
		/* See https://www.geeksforgeeks.org/orientation-3-ordered-points for details on the formula */
		return Math.signum((B.y - A.y)*(C.x - B.x) - (B.x - A.x)*(C.y - B.y));
	}

	/**
	 * Indicates if the line segments [A; B] and [C; D] intersect when there is no collinearity
	 * This method return the intersection point if there is one, null otherwise
	 * 
	 * Source: https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect
	 * 
	 * @param A
	 * @param B
	 * @param C
	 * @param D
	 * @return	the intersection point between [A; B] and [C; D], or null
	 */
	public static Vector2 segmentsIntersection(Vector2 A, Vector2 B, Vector2 C, Vector2 D) {
		// Finding the four orientations needed for general and special cases
		float o1 = orientation(A, B, C);
		float o2 = orientation(A, B, D);
		float o3 = orientation(C, D, A);
		float o4 = orientation(C, D, B);

		if ((o1 != o2) && (o3 != o4)) {		// if there is intersection

			float q = A.x*B.y - A.y*B.x;
			float r = C.x*D.y - C.y*D.x;
			float denom = (A.x - B.x)*(C.y - D.y) - (A.y - B.y)*(C.x - D.x);

			return new Vector2(
					(q*(C.x - D.x) - r*(A.x - B.x)) / denom,
					(q*(C.y - D.y) - r*(A.y - B.y)) / denom);
		}
		return null;
	}


	/**
	 * Calculate a normal Vector to a line (AB) pointing toward the side of R.
	 * Points A and B must be distinct and R must not be in (AB).
	 * This is used for raycast.
	 * @@@ FIXME @@@ !!! !!! !!! !!!
	 * 
	 * @param A
	 * @param B
	 * @param R
	 * @return	- a normal vector to the line (AB) (not normalized) pointing toward the side R is
	 */
	public static Vector2 normal(Vector2 A, Vector2 B, Vector2 R) {
		if (A.x == B.x) {		// (AB) is vertical
			return (R.x < A.x) ? Vector2.LEFT() : Vector2.RIGHT();
		} else if (A.y == B.y) {		// (AB) is horizontal
			return (R.y < A.y) ? Vector2.DOWN() : Vector2.UP();
		} else {
			return (new Vector2((A.y - B.y) / (B.x - A.x), 1)).normalize();
		}
	}



	/**
	 * https://en.wikipedia.org/wiki/Smoothstep
	 */
	public static float smoothStep(float x) {
		x = clamp(x, 0, 1);
		return x * x * (3 - 2*x);
	}

	public static float smootherStep(float x) {
		x = clamp(x, 0, 1);
		return x * x * x * (x * (6*x - 15) + 10);
	}


	/**
	 * Gradually changes a value over time.
	 *
	 * @param current			- the current state of the value
	 * @param target			- the target value
	 * @param currentVelocity	- a mutable float keeping track of the current progression
	 * @param smoothTime		- the approximative time it will take to reach the target
	 * @param deltaTime			- the time since the last call of this function
	 * 
	 * @author Not me, taken from the Unity Engine
	 */
	public static float SmoothDamp (float current, float target, MutableFloat currentVelocity,
			float smoothTime, float deltaTime) {

		//System.out.println("Target " + target);
		if (smoothTime == 0) {		// If no damping
			return target;
		}
		float num = 2f / smoothTime;
		float num2 = num * deltaTime;
		float num3 = 1f / (1f + num2 + 0.48f * num2 * num2 + 0.235f * num2 * num2 * num2);
		float num4 = current - target;
		target = current - num4;
		float num5 = (currentVelocity.value + num * num4) * deltaTime;
		currentVelocity.value = (currentVelocity.value - num * num5) * num3;
		float num6 = target + (num4 + num5) * num3;

		if ((target > current) == (num6 > target)) {
			currentVelocity.value = 0f;
			return target;
		}
		return num6;
	}








	public enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}





	/*
	 * Indicates if the line segments [A; B] and [C; D] do intersect in the case ABC are not aligned.
	 * This method is used  
	 * 
	 * @param A
	 * @param B
	 * @param C
	 * @param D
	 * @return	- the intersection point le cas échéant, null if there is no intersection or if C is in (AB)
	 */
	@Deprecated
	public static Vector2 checkSegmentsIntersection(Vector2 A, Vector2 B, Vector2 C, Vector2 D) {
		try {
			/* Explication:
			 * When ABC not aligned, if CD = w1.CA + w2.CB, then
			 * there is intersection iff ((w1+w2 >= 1) && (w1, w2 >= 0))
			 */
			float w1 = (C.x*(A.y - C.y) + (D.y - C.y)*(A.x - C.x) - D.x*(A.y - C.y)) /
					((B.y - C.y)*(A.x - C.x) - (B.x - C.x)*(A.y - C.y));
			float w2 = (D.y - C.y - w1*(B.y - C.y)) / (A.y - C.y);

			if (w1>=0 && w2>=0 && w1+w2>=1) {
				// In the case of intersection, return the intersection point
				float q = A.x*B.y - A.y*B.x;
				float r = C.x*D.y - C.y*D.x;
				float denom = (A.x - B.x)*(C.y - D.y) - (A.y - B.y)*(C.x - D.x);

				return new Vector2(
						(q*(C.x - D.x) - r*(A.x - B.x)) / denom,
						(q*(C.y - D.y) - r*(A.y - B.y)) / denom);
			}
			return null;
		} catch (ArithmeticException e) {		// Happens when ABC are colinear
			return null;
		}
	}

}
