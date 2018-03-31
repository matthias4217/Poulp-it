package core.scripts;

import content.GameObject;
import core.Info;
import core.exceptions.InvalidArgumentsException;
import core.exceptions.InvalidBoxColliderException;

/**
 * Superclass for all scripts which can be added to a GameObject.
 * A script describes the behavior of a GameObject and is updated each frame
 *
 * @author Raph
 * Inspired by the functionning of the Unity Engine.
 *
 */
public abstract class MonoBehavior {

	/**
	 * The GameObject to which the script is attached
	 */

	private GameObject support;

	public GameObject getSupport() {
		return support;
	}
	
	
	
	public MonoBehavior(GameObject support) throws InvalidBoxColliderException {
		/* Constructor */
		this.support = support;
		start();
	}


	/*
	 * awake is called once when the script instance is being loaded.
	 *
	 * awake is called before start.
	 */
	public void awake() throws InvalidBoxColliderException {}

	/**
	 * Is called after awake(), once when the script instance is being loaded.
	 *
	 * @throws InvalidBoxColliderException
	 */
	public void start() throws InvalidBoxColliderException {}

	/**
	 * Is called every frame the support GameObject is active.
	 *
	 * @param deltaTime		The timestamp of the current frame given in nanoseconds
	 * @throws InvalidArgumentsException 
	 */
	public void update(long deltaTime, Info info) throws InvalidArgumentsException {}

	/**
	 * Same as update but the lateUpdate methods are called after all updates are done.
	 *
	 * @param deltaTime		The timestamp of the current frame given in nanoseconds
	 */
	public void lateUpdate(long deltaTime, Info info) {}



}
