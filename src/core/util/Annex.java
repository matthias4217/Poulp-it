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
	
	public static float clamp(float value, float a, float b) {
		/* Return value clamped between a and b. */
		return Math.max(a, Math.min(b, value));	
	} 
	
	
	
	

}
