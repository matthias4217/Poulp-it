package content.shooter;

import content.GameObject;
import content.Layer;
import content.MonoBehaviour;
import content.Tag;
import content.shooter.scripts.Controller;
import core.util.BoxCollider;
import core.util.Collider;
import core.util.Vector2;
import javafx.scene.image.Image;

/**
 * Player class for shooter
 * 
 * @author Raph
 *
 */
public class PlayerShooter extends GameObject {

	static final String SPRITE_PATH = "resources/graphic/sophie.png";

	/**
	 * The amount of Player objects created 
	 */
	private static int nbPlayers = 0;



	/**
	 * The id of the player. It is used to get the correct inputs. 
	 */
	private int id;



	public PlayerShooter(Vector2 position) {
		super(position,
				new Image(SPRITE_PATH /*, 64, 64, false, false*/),
				Layer.DEFAULT,
				Tag.DEFAULT,
				null,

				new Controller());

		this.id = nbPlayers;
		nbPlayers++;

	}



	public PlayerShooter(Vector2 position, Image sprite, Layer layer, Tag tag, Collider collider, MonoBehaviour[] scripts) {
		super(position, sprite, layer, tag, collider, scripts);
	}

}
