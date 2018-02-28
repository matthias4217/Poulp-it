package core.util.specific;

import core.util.Vector2;

/**
 * Class which represents the focus area of the camera around its target.
 * Used in the script CameraFollow. 
 * 
 * @author Raph
 *
 */
public class FocusArea {

	public Vector2 center;			// The center of the area
	public Vector2 velocity;		// @@@
	float left, right;				// @@@
	float top, bottom;				// @@@


	public FocusArea(Bounds targetBounds, Vector2 size) {
		left = targetBounds.center.x - size.x/2;
		right = targetBounds.center.x + size.x/2;
		bottom = targetBounds.min.y;
		top = targetBounds.min.y + size.y;

		velocity = Vector2.zero;
		center = new Vector2(targetBounds.center.x, (top+bottom)/2);
	}

	public void Update(Bounds targetBounds) {
		float shiftX = 0;
		if (targetBounds.min.x < left) {
			shiftX = targetBounds.min.x - left;
		} else if (targetBounds.max.x > right) {
			shiftX = targetBounds.max.x - right;
		}
		left += shiftX;
		right += shiftX;

		float shiftY = 0;
		if (targetBounds.min.y < bottom) {
			shiftY = targetBounds.min.y - bottom;
		} else if (targetBounds.max.y > top) {
			shiftY = targetBounds.max.y - top;
		}
		top += shiftY;
		bottom += shiftY;
		center = new Vector2((left+right)/2,(top+bottom)/2);
		velocity = new Vector2 (shiftX, shiftY);
	}
}