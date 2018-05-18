package content;

import content.scripts.shooter.Controller;
import content.scripts.MonoBehaviour;
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
public class Player2 extends GameObject {

	static final String SPRITE_PATH = "resources/graphic/player.png";



	public Player2(Vector2 position) {
		super(position,
				new Image(SPRITE_PATH /*, 64, 64, false, false*/),
				Layer.DEFAULT,
				Tag.DEFAULT,
				null,

				new Controller());

	}


	
	public Player2(Vector2 position, Image sprite, Layer layer, Tag tag, Collider collider, MonoBehaviour[] scripts) {
		super(position, sprite, layer, tag, collider, scripts);
	}

}
