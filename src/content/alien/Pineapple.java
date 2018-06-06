package content.alien;

import content.GameObject;
import content.Layer;
import content.Tag;
import content.alien.scripts.PineappleController;
import core.util.BoxCollider;
import core.util.Vector2;
import javafx.scene.image.Image;

/**
 * ---
 * 
 * @author matthias
 *
 */
public class Pineapple extends GameObject {

	static final String SPRITE_PATH = "resources/graphic/alien/pineapple.png";
	
	public static float width = 19;
	public static float height = 36;



	public Pineapple(Vector2 position, Layer layer, Tag tag) {
		super(position,
				new Image(SPRITE_PATH, width, height, false, false),
				layer,
				tag,
				new BoxCollider(width, height),

				new PineappleController());
	}



}
