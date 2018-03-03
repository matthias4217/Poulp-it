package core.scripts;

import content.GameObject;
import core.exceptions.InvalidBoxColliderException;

/**
 * Superclass for all scripts which can be added to a GameObject. 
 * 
 * @author Raph
 *
 */
public class MonoBehavior {
	
	GameObject support;			// The GameObject to which the script is attached
	
	
	public void awake() throws InvalidBoxColliderException {
	}
	public void start() {
	}
	public void update() {
	}
	public void lateUpdate() {
	}
	
	
}
