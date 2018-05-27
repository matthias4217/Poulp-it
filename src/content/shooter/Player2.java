package content.shooter;

import content.GameObject;
import content.Layer;
import content.MonoBehaviour;
import content.Tag;
import content.shooter.scripts.Controller;
import core.GameEngine;
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

	static final String SPRITE_PATH = "resources/graphic/sophie.png";



	public Player2(Vector2 position, GameEngine gameEngine) {
		super(position,
				new Image(SPRITE_PATH /*, 64, 64, false, false*/),
				Layer.DEFAULT,
				Tag.DEFAULT,
				null,
				gameEngine,

				new Controller());

	}



	public Player2(Vector2 position, Image sprite, Layer layer, Tag tag, Collider collider,  GameEngine gameEngine, MonoBehaviour[] scripts) {
		super(position, sprite, layer, tag, collider, gameEngine, scripts);
	}

}
