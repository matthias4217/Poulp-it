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

	public LinkedList<MonoBehavior> scripts;

	// Collection<components> ?


	public GameObject(Vector2 position, Image sprite) {
		this.position = position;
		this.sprite = sprite;
		scripts = new LinkedList<MonoBehavior>();
	}
	public GameObject() {
		this(null, null);
	}



	public void render(GraphicsContext gc) {
		/* Render this Sprite on the GraphicsContext gc. */
		gc.drawImage(sprite, position.x, position.y);
	}

	public String toString() {
		return this.position.toString() + collider.toString();
	}


}
