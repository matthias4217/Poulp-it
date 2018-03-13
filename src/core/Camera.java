package core;

import core.util.Vector2;

/**
 * This class manages the game camera.
 * It has a position which is updated each frame.
 * 
 * @author Raph
 *
 */
public class Camera {

	private Vector2 currentPosition;
	
	
	
	public Vector2 update() {
		
		Vector2 newPosition = new Vector2(0, 0);		//
		
		
		
		
		
		
		
		return newPosition;
	}
	
	
	
	
}


/*


public Controller target;
	public float verticalOffset;
	public float lookAheadDstX;
	public float lookSmoothTimeX;
	public float verticalSmoothTime;
	public Vector2 focusAreaSize;

	FocusArea focusArea;

	private float currentLookAheadX;
	private float targetLookAheadX;
	private float lookAheadDirX;
	private float smoothLookVelocityX;
	private float smoothVelocityY;

	private boolean lookAheadStopped;

	@Override
	public void start() {
		focusArea = new FocusArea(target.collider.bounds, focusAreaSize);
	}

	@Override
	public void lateUpdate() {
		focusArea.update(target.collider.bounds);

		Vector2 focusPosition = focusArea.center.add(Vector2.up.multiply(verticalOffset));

		if (focusArea.velocity.x != 0) {
			lookAheadDirX = Math.signum(focusArea.velocity.x);
			if (Math.signum(target.playerInput.x) == Math.signum(focusArea.velocity.x) && target.playerInput.x != 0) {
				lookAheadStopped = false;
				targetLookAheadX = lookAheadDirX * lookAheadDstX;
			}
			else {
				if (!lookAheadStopped) {
					lookAheadStopped = true;
					targetLookAheadX = currentLookAheadX + (lookAheadDirX * lookAheadDstX - currentLookAheadX)/4f;
				}
			}
		}


		currentLookAheadX = Mathf.SmoothDamp (currentLookAheadX, targetLookAheadX, ref smoothLookVelocityX, lookSmoothTimeX);

		focusPosition.y = Mathf.SmoothDamp (support.position.y, focusPosition.y, ref smoothVelocityY, verticalSmoothTime);
		focusPosition = focusPosition.add(Vector2.right.multiply(currentLookAheadX)));
		support.position = focusPosition + Vector3.forward * -10;
	}

}





class FocusArea {
	/* Class which represents the focus area of the camera around its target. 

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

	public void update(Bounds targetBounds) {
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





*/