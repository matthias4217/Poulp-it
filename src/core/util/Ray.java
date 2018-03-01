package core.util;

import core.exceptions.InvalidArgumentsException;

/**
 * This class represents a Ray, which is used to detect collisions with GameObjects.
 * A Ray has a finite length.
 * 
 * In practice, what is stored are simply two points.
 * 
 * @author Raph
 * 
 */
public class Ray {
	
	private Vector2 originPoint;
	private Vector2 endingPoint;
	
	
	/**
	 * @param originPoint
	 * @param direction
	 * @param length
	 */
	public Ray(Vector2 originPoint, Vector2 direction, float length) throws InvalidArgumentsException {
		if (direction == Vector2.zero) {
			throw new InvalidArgumentsException("direction vector is null");
		}
		
		this.originPoint = originPoint;
		this.endingPoint = originPoint.add(direction.normalize().multiply(length));
	}
	
	
	
}