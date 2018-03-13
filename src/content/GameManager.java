package content;

/**
 * Interface implemented by any object which does something in the game without being a graphical element.
 * Any such object must override the apply method, which will be called each frame by the GameEngine.
 * For example, the object which manages the spawn of collectables.
 * 
 * @author Raph
 *
 */
public interface GameManager {

	/**
	 * Must be overridden; called every frame.
	 */
	public void apply();
	
	
}
