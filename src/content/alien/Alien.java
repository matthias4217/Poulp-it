/**
 * 
 */
package content.alien;

import content.GameObject;
import content.Layer;
import content.Tag;
import content.alien.scripts.AlienController;
import core.GameEngine;
import core.util.BoxCollider;
import core.util.Vector2;
import javafx.scene.image.Image;

/**
 * @author matthias
 *
 */
public class Alien extends GameObject {


	public static float width = 62;
	public static float height = 36;
	
	
	public Alien(Vector2 position, Layer layer, Tag tag, GameEngine gameEngine) {
		super(position,
				new Image("resources/graphic/alien/alien.png", 62, 36, false, false),
				layer, tag,
				new BoxCollider(62, 36),
				gameEngine,
				new AlienController());
	}

}