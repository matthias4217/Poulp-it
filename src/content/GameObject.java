package content;

import java.util.LinkedList;
import core.util.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import core.GameInformation;
import core.annotations.Unused;
import core.exceptions.InvalidArgumentsException;
import core.scripts.MonoBehaviour;

/**
 * Superclass for any game element in a scene which has a spatial position and is renderable.
 * Example: Player
 *
 * @author Raph
 *
 */
public abstract class GameObject {

	/**
	 * The current position in absolute coordinates of this GameObject
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
	 * The collider used to detect collisions against this GameObject; null if this can't be collided with
	 */
	public Collider collider;

	/**
	 * The list of scripts attached to the GameObject which describes its behavior
	 */
	public LinkedList<MonoBehaviour> scripts;


	/**
	 * This method generates the lists of scripts which will be attached to a GameObject.
	 * A GameObject must override this method in order to set its own scripts.
	 * We use such an implementation to bypass the (annoying) fact that Java won't allow any instruction before super() in a constructor.
	 *
	 * @return		an empty list by default (if not overridden)
	 */
	protected static LinkedList<MonoBehaviour> generateScriptsList() {
		return new LinkedList<MonoBehaviour>();
	}



	/**
	 * Standard constructor for a GameObject.
	 * All scripts are initialized at the end of this instanciation.
	 *
	 * @param position		The position in global coordinates where this will spawn
	 * @param sprite
	 * @param layer
	 * @param tag
	 * @param scripts		The list of script attached to this GameObject
	 */
	public GameObject(Vector2 position, Collider collider, Image sprite, Layer layer, Tag tag, LinkedList<MonoBehaviour> scripts) {
		this.position = position;
		this.collider = collider;
		this.sprite = sprite;
		this.layer = layer;
		this.tag = tag;
		this.scripts = scripts;

		// Setting the right support reference for each script
		for (MonoBehaviour script: this.scripts) {
			script.setSupport(this);
		}

		// Starting all scripts
		for (MonoBehaviour script: this.scripts) {
			script.start();
		}
	}



	/**
	 * The update method called by the GameEngine.
	 * By default, it updates all the scripts attached to this.
	 * This method can be overriden for a more specific behavior.
	 *
<<<<<<< HEAD
	 * @param deltaTime		The timestamp of the current frame given in nanoseconds
	 * @param info
	 * @throws InvalidArgumentsException
=======
	 * @param deltaTime
	 * @param gameInformation
	 * @throws InvalidArgumentsException
>>>>>>> Le_perso_bouge_!
	 */
	public void update(float deltaTime, GameInformation gameInformation) throws InvalidArgumentsException {
		updateAllScripts(deltaTime, gameInformation);
	}

	/**
	 *
<<<<<<< HEAD
	 * @param deltaTime : long
	 * @param info : Info that the Launcher sends to the GameManager
	 * @throws InvalidArgumentsException
=======
	 * @param deltaTime		The time in seconds it took to complete the last frame
	 * @param gameInformation : Info that the Launcher sends to the GameManager
	 * @throws InvalidArgumentsException
>>>>>>> Le_perso_bouge_!
	 */
	protected final void updateAllScripts(float deltaTime, GameInformation gameInformation) throws InvalidArgumentsException {
		for (MonoBehaviour script: scripts) {
			script.update(deltaTime, gameInformation);
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




	public enum Tag {
		DEFAULT,
		TRAVERSABLE,



	}


}
