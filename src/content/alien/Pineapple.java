/**
 * 
 */
package content.alien;

import content.GameObject;
import content.Layer;
import content.Tag;
import content.alien.scripts.PineappleController;
import core.GameEngine;
import core.util.BoxCollider;
import core.util.Vector2;
import javafx.scene.image.Image;

/**
 * @author matthias
 *
 */
public class Pineapple extends GameObject {

	

	public static float width = 19;
	public static float height = 36;
	
	public Pineapple(Vector2 position, Layer layer, Tag tag, GameEngine gameEngine) {
		super(position,
				new Image("resources/graphic/alien/pinapple.png", 19, 36, false, false),
				layer, tag, new BoxCollider(19, 36),
				gameEngine,
				new PineappleController());
	}
	
	

}
