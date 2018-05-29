package content.shooter;

import content.GameObject;
import content.Layer;
import content.MonoBehaviour;
import content.Tag;
import content.shooter.scripts.ControllerShooter;
import core.util.Collider;
import core.util.Vector2;
import javafx.scene.image.Image;

/**
 * Player GameObject for shooter.
 * 
 * @author Raph
 *
 */
public class PlayerShooter extends GameObject {

	static final String SPRITE_PATH = "resources/graphic/sophie.png";



	public PlayerShooter(Vector2 position) {
		super(position,
				new Image(SPRITE_PATH /*, 64, 64, false, false*/),
				Layer.DEFAULT,
				Tag.DEFAULT,
				Collider.NO_COLLIDER(),

				new ControllerShooter());

	}

	public PlayerShooter(Vector2 position, Image sprite, Layer layer, Tag tag, Collider collider, MonoBehaviour[] scripts) {
		super(position, sprite, layer, tag, collider, scripts);
	}

}
