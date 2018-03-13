package core.util;

import content.GameObject;

/**
 * Information returned about an object detected by a raycast.
 * 
 * @author Raph
 *
 */
public class RayCastHit {
	
	private GameObject gameObjectHit;	// A reference to the GameObject whose collider was hit
	private float distance;				// The distance from the ray origin to the impact point
	private Vector2 normal;				// The normal Vector2 of the line hit
	//? Vector2 impactPoint;
	
	public GameObject getGameObjectHit() {
		return gameObjectHit;
	}
	public float getDistance() {
		return distance;
	}
	public Vector2 getNormal() {
		return normal;
	}
	
	
	
	public RayCastHit() {
		// @@@ TODO : Constructor
	}
	
	
	
}
