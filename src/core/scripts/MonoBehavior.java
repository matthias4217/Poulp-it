package core.scripts;

import content.GameObject;
import core.GameInformation;
import core.exceptions.InvalidArgumentsException;

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
	
	public void setSupport(GameObject support) {
		this.support = support;
	}




	/**
	 * Is called after awake(), once when the script instance is being loaded.
	 *
	 * @throws InvalidBoxColliderException
	 */
	public void start() {}

	/**
	 * Is called every frame the support GameObject is active.
	 *
	 * @param deltaTime		The time in seconds it took to complete the last frame
	 * @throws InvalidArgumentsException 
	 */
	public void update(float deltaTime, GameInformation gameInformation) throws InvalidArgumentsException {}

	/**
	 * Same as update but the lateUpdate methods are called after all updates are done.
	 *
	 * @param deltaTime		The time in seconds it took to complete the last frame
	 */
	public void lateUpdate(long deltaTime, GameInformation gameInformation) {}



}
