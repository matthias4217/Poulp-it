package content;

import core.PlayerInput;

/**
 * Interface implemented by any object which does something in the game without being a graphical element.
 * Any such object must override the apply() method, which will be called each frame by the GameEngine.
 * Example: the object which manages the spawn of collectables.
 *
 * @author Raph
 *
 */
public interface GameManager {

	/**
	 * Called every frame
	 *
	 * @param deltaTime - the time in seconds it took to complete the last frame
	 * @param gameInformation
	 * @param previousPlayerInput 
	 */
	public void apply(float deltaTime, PlayerInput[] gameInformation, PlayerInput[] previousPlayerInput);


}
