package core.scripts;

import content.GameObject;
import core.Info;
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

	/**
	 * The GameObject to which the script is attached
	 */
	GameObject support;




	public MonoBehavior(GameObject support) {
		super();
		this.support = support;
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
	 */
	public void update(long deltaTime, Info info) {}

	/**
	 * Same as update but the lateUpdate methods are called after all updates are done.
	 *
	 * @param deltaTime
	 */
	public void lateUpdate(long deltaTime) {}



}
