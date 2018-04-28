package content;

import java.util.LinkedList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import core.GameEngine;
import core.GraphicManager;
import core.Launcher;
import core.PlayerInput;
import core.Renderable;
import core.annotations.Unused;
import core.exceptions.InvalidArgumentsException;
import core.scripts.MonoBehaviour;
import core.util.*;

/**
 * Superclass for any game element in a scene which has a spatial position and is renderable.
 * Example: Player
 *
 * @author Raph
 *
 */
public abstract class GameObject implements Renderable {

	/**
	 * The current position in global coordinates of this GameObject
	 */
	public Vector2 position;

	/**
	 * The sprite used for rendering this GameObject
	 */
	public Image sprite;

	@Unused
	public Layer layer;

	/**
	 * A tag associated with this GameObject; used for convenient references between the different objects
	 */
	public Tag tag;

	/**
	 * The collider used to detect collisions against this GameObject; null if this GameObject can't be collided with
	 */
	public Collider collider;

	/**
	 * A list of scripts attached to this GameObject, which describe its behaviour
	 */
	public LinkedList<MonoBehaviour> scripts;


	/**
	 * This method generates the lists of scripts which will be attached to a GameObject.
	 * A GameObject must override this method in order to set its own scripts.
	 * We use such an implementation to bypass the (annoying) fact that Java won't allow any instruction before super() in a constructor.
	 *
	 * @return		an empty list by default (if not overridden)
	 */
	 @Deprecated
	protected static LinkedList<MonoBehaviour> generateScriptsList() {
		return new LinkedList<MonoBehaviour>();
	}



	/**
	 * Standard constructor for a GameObject.
	 * All scripts are initialized at the end of this instanciation.
	 *
	 * @param position	- the position in global coordinates where the GameObject will spawn
	 * @param sprite	- the sprite which will represent the GameObject
	 * @param layer
	 * @param tag
	 * @param collider	- the Collider of the GameObject; set null if the GameObject can't be collided with
	 * @param scripts	- the scripts attached to this GameObject
	 */
	public GameObject(Vector2 position, Image sprite, Layer layer, Tag tag, Collider collider, MonoBehaviour... scripts) {
		this.position = position;
		this.sprite = sprite;
		this.layer = layer;
		this.tag = tag;
		this.collider = collider;

		this.scripts = new LinkedList<MonoBehaviour>();
		for (MonoBehaviour script: scripts) {
			script.support = this;		// Setting the right support reference for the script
			this.scripts.add(script);
		}

		// Starting all scripts
		for (MonoBehaviour script: this.scripts) {		// NOTE: it's important that this loop comes AFTER the previous one
			script.start();
		}

	}



	/**
	 * The update method called by the GameEngine.
	 * By default, it simply updates all the scripts attached to this.
	 * This method can be overriden for a more specific behaviour.
	 *
	 * @param deltaTime
	 * @param gameInformation
	 */
	public void update(float deltaTime, PlayerInput gameInformation) {
		updateAllScripts(deltaTime, gameInformation);
	}

	/**
	 *
	 * @param deltaTime			- the time in seconds it took to complete the last frame
	 * @param playerInput 	- Info that the Launcher sends to the GameManager
	 */
	protected final void updateAllScripts(float deltaTime, PlayerInput playerInput) {
		for (MonoBehaviour script: scripts) {
			script.update(deltaTime, playerInput);
		}
	}


	/**
	 * Render this Sprite on the GraphicsContext gc.
	 */
	@Override public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		gc.drawImage(sprite, position.x, windowHeight - position.y);
		if (GraphicManager.debugVisible) {
			collider.render(gc, position);
		}
	}



	@Override public String toString() {
		return "GameObject [Position " + this.position + "; Collider " + collider + "]";
	}


}
