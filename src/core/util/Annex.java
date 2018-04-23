package core.util;

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
	public static Vector2 checkSegmentIntersection(Vector2 A, Vector2 B, Vector2 C, Vector2 D) {
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


	// https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/

	// Given three colinear Vector2s p, q, r, the function checks if
	// Vector2 q lies on line segment 'pr'
	boolean onSegment(Vector2 a, Vector2 b, Vector2 p) {
		return (b.x <= Math.max(a.x, p.x) && b.x >= Math.min(a.x, p.x) &&
				b.y <= Math.max(a.y, p.y) && b.y >= Math.min(a.y, p.y));
	}

	// To find orientation of ordered triplet (p, q, r).
	// The function returns following values
	// 0 --> p, q and r are colinear
	// 1 --> Clockwise
	// -1 --> Counterclockwise
	float orientation(Vector2 p, Vector2 q, Vector2 r) {
		// See https://www.geeksforgeeks.org/orientation-3-ordered-Vector2s/
		// for details of below formula.
		return Math.signum((q.y - p.y)*(r.x - q.x) - (q.x - p.x)*(r.y - q.y));
	}

	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	boolean doIntersect(Vector2 p1, Vector2 q1, Vector2 p2, Vector2 q2) {
		// Find the four orientations needed for general and
		// special cases
		float o1 = orientation(p1, q1, p2);
		float o2 = orientation(p1, q1, q2);
		float o3 = orientation(p2, q2, p1);
		float o4 = orientation(p2, q2, q1);

		// General case
		if (o1 != o2 && o3 != o4)
			return true;

		// Special Cases
		// p1, q1 and p2 are colinear and p2 lies on segment p1q1
		if (o1 == 0 && onSegment(p1, p2, q1)) return true;

		// p1, q1 and p2 are colinear and q2 lies on segment p1q1
		if (o2 == 0 && onSegment(p1, q2, q1)) return true;

		// p2, q2 and p1 are colinear and p1 lies on segment p2q2
		if (o3 == 0 && onSegment(p2, p1, q2)) return true;

		// p2, q2 and q1 are colinear and q1 lies on segment p2q2
		if (o4 == 0 && onSegment(p2, q1, q2)) return true;

		return false; // Doesn't fall in any of the above cases
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
			num6 = target;
			currentVelocity.value = 0f;
		}
		return num6;
	}










}
