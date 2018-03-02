package core.util;

/**
 * This class stores diverse constantes and methods such as math ones.
 * 
 * @author Raph
 *
 */
public final class Annex {
	
	/* CONSTANTES */
	
	public static final float DEG2RAD = (float) Math.PI / 180;
	
	
	
	
	/* METHODS */
	
	/**
	 * @return value clamped between a and b
	 */
	public static float clamp(float value, float a, float b) {
		return Math.max(a, Math.min(b, value));	
	}
	
	/**
	 * Indicates if the line segments [A; B] and [C; D] intersect
	 */
	public static boolean checkSegmentIntersection(Vector2 A, Vector2 B, Vector2 C, Vector2 D) {
		/* Explication
		 * When ABC not aligned, if CD = w1.CA + w2.CB, then
		 * it is true iff (w1+w2 >= 1 && w1, w2 >= 0)
		 * */		
		float w1 = (A.x*(C.y - A.y) + (D.y - A.y)*(C.x - A.x) - D.x*(C.y - A.y)) /
				((B.y - A.y)*(C.x - A.x) - (B.x - A.x)*(C.y - A.y));
		System.out.println(w1);
		float w2 = (D.y - A.y - w1*(B.y - A.y)) /
				(C.y - A.y);
		System.out.println(w2);
		return w1>=0 && w2>=0 && w1+w2>=1;
	}
	
	
	
	

}
