package content;

import core.PlayerInput;
import core.annotations.Unused;
import core.exceptions.InvalidArgumentsException;

/**
 * Superclass for all scripts which can be added to a GameObject.
 * A script describes the behaviour of a GameObject and is updated each frame.
 *
 * @author Raph, inspired by the functionning of the Unity Engine.
 *
 */
public abstract class MonoBehaviour {

	/**
	 * The GameObject to which the script is attached
	 */
	public GameObject support;




	/**
	 * Is called once when a GameObject with this script is instanciated.
	 * 
	 */
	public void start() {}


	/**
	 * Is called every frame the support GameObject is active.
	 *
	 * @param deltaTime - the time in seconds it took to complete the last frame
	 * @param previousPlayerInput 
	 * @throws InvalidArgumentsException 
	 */
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput) throws InvalidArgumentsException {}


	/**
	 * Same as update but the lateUpdate methods are called after all updates are done.
	 *
	 * @param deltaTime- the time in seconds it took to complete the last frame
	 */
	@Unused
	public void lateUpdate(long deltaTime, PlayerInput playerInput) {}


}
