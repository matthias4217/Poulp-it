package content;

/**
 * Interface implemented by any object which does something in the game without being a graphical element.
 * Any such object must override the update method, which will be called each frame by the GameEngine.
 * For example, the object which manage the spawn of collectables .
 * 
 * @author Raph
 *
 */
public interface GameManager {
	public void apply();
	/* Is called every frame */
	
}
