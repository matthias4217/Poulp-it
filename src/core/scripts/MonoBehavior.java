package core.scripts;

import content.GameObject;
import core.exceptions.InvalidBoxColliderException;

/**
 * Superclass for all scripts which can be added to a GameObject.
 * A script describes the behavior of a GameObject and is updated each frame
 * 
 * @author Raph
 * Inspired by the functionning of the Unity Engine.
 *
 */
// TODO document this class
public abstract class MonoBehavior {
	
	GameObject support;			// The GameObject to which the script is attached
	
	
	/*
	 * awake is called when the script instance is being loaded.
	 * 
	 * awake is called before start. 
	 */
	public void awake() throws InvalidBoxColliderException {}
	
	public void start() throws InvalidBoxColliderException {}
	
	public void update(long deltaTime) {}
	
	public void lateUpdate() {}
	
	
}
