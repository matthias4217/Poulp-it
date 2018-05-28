/**
 * 
 */
package content.alien;

import content.GameObject;
import content.Layer;
import content.Tag;
import core.GameEngine;
import core.util.BoxCollider;
import core.util.Vector2;
import javafx.scene.image.Image;

/**
 * @author inazuma
 *
 */
public class Pineapple extends GameObject {

	public Pineapple(Vector2 position, Layer layer, Tag tag, GameEngine gameEngine) {
		super(position,
				new Image("resources/graphic/alien/pinapple.png", 19, 36, false, false),
				layer, tag, new BoxCollider(19, 36),
				gameEngine);
	}

}
