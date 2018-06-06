package content.test;

import content.GameObject;
import content.Layer;
import content.Tag;
import content.test.scripts.MouseController;
import core.util.Vector2;
import javafx.scene.image.Image;

/**
 * @author matthias
 *
 */
public class Mouse extends GameObject {

	public Mouse() {
		super(Vector2.ZERO(), 
				new Image("resources/graphic/player.png"), 
				Layer.DEFAULT, Tag.DEFAULT, null,
				new MouseController());
		// TODO Auto-generated constructor stub
	}

	
}
