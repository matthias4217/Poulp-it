package core.util;

import content.GameObject;

/**
 * Information returned about an object detected by a raycast.
 *
 * @author Raph
 *
 */
public class RaycastHit {

	/**
	 * A reference to the GameObject which was hit; null if what was hit isn't a GameObject
	 */
	private GameObject gameObjectHit;

	/**
	 * The distance from the ray origin to the impact point
	 */
	private float distance;

	/**
	 * The normal Vector2 of the line hit
	 */
	private Vector2 normal;


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

	public void setGameObjectHit(GameObject gameObjectHit) {
		this.gameObjectHit = gameObjectHit;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public void setNormal(Vector2 normal) {
		this.normal = normal;
	}



	public RaycastHit(GameObject gameObjectHit, float distance, Vector2 normal) {
		this.gameObjectHit = gameObjectHit;
		this.distance = distance;
		this.normal = normal;
	}




}
