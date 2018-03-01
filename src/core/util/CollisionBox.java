/**
 * 
 */
package core.util;

/**
 * This is a particular case of CollisionBounds where the bounds are a rectangle.
 * This is what you'll want to use in most cases.
 * 
 * @author Raph
 *
 */
public class CollisionBox extends CollisionBounds {
	
	
	@Override
	public int getNbPoints () {
		return 4;
	}
	
	
	
	
	public CollisionBox(Vector2 originPoint, float width, float height) {
		pointsArray = new Vector2[4];
		pointsArray[0] = originPoint;
		pointsArray[1] = originPoint.add(Vector2.up.multiply(height)); 
		pointsArray[2] = originPoint.add(new Vector2(width, height));
		pointsArray[3] = originPoint.add(Vector2.right.multiply(width));
		super(pointsArray);
	}
}
