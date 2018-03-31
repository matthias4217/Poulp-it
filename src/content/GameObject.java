package content;

import java.util.LinkedList;
import core.util.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import core.scripts.MonoBehavior;

/**
 * Superclass for any game element in a scene which has a spatial position and is renderable.
 * Example: Player
 *
 * @author Raph
 *
 */
public class GameObject {

	public Vector2 position;
	public Image sprite;
//	public Layer layer;
	
	public Collider collider;
	public LinkedList<MonoBehavior> scripts;		// The list of scripts attached to the GameObject which describes its behavior

	
	
	public GameObject(Vector2 position, Image sprite) {
		this(position, sprite, Layer.DEFAULT);
	}
	
	public GameObject(Vector2 position, Image sprite, Layer layer) {
		this.position = position;
		this.sprite = sprite;
		scripts = new LinkedList<MonoBehavior>();
	}



	/**
	 * The update method called by the GameEngine.
	 * By default, it updates all the scripts attached to this.
	 * For a more specific behavior, this method has to be overriden.
	 * 
	 * @param deltaTime		The timestamp of the current frame given in nanoseconds
	 */
	public void update(long deltaTime) {
		updateAllScripts(deltaTime);
	}
	
	protected final void updateAllScripts(long deltaTime) {
		for (MonoBehavior script: scripts) {
			script.update(deltaTime);
		}
	}
	
	
	/**
	 * Render this Sprite on the GraphicsContext gc.
	 */
	public void render(GraphicsContext gc) {
		gc.drawImage(sprite, position.x, position.y);
	}
	
	
	
	@Override
	public String toString() {
		return this.position.toString() + collider.toString();		//
	}

}
