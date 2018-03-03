package content;

import java.util.LinkedList;
import core.util.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import core.scripts.MonoBehavior;

/**
 * Superclass for any game element in a scene which has a spatial position and is renderable.
 *
 * @author Raph
 *
 */
public class GameObject {

	public Vector2 position;
	public Collider collider;
	public Image sprite;
	public Layer layer;

	public LinkedList<MonoBehavior> scripts;

	
	
	public GameObject(Vector2 position, Image sprite) {
		this(position, sprite, Layer.DEFAULT);
	}
	
	public GameObject(Vector2 position, Image sprite, Layer layer) {
		this.position = position;
		this.sprite = sprite;
		scripts = new LinkedList<MonoBehavior>();
	}


	
	/**
	 * Render this Sprite on the GraphicsContext gc.
	 */
	public void render(GraphicsContext gc) {
		gc.drawImage(sprite, position.x, position.y);
	}

	/**
	 * The update method called by the GameEngine.
	 * By default, it updates all the scripts attached to this.
	 * For a different behavior, this method has to be overriden.
	 */
	public void update() {
		updateAllScripts();
	}
	
	protected final void updateAllScripts() {
		for (MonoBehavior script: scripts) {
			script.update();
		}
	}
	
	
	
	@Override
	public String toString() {
		return this.position.toString() + collider.toString();
	}

}
