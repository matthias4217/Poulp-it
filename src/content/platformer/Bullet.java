package content.platformer;

import content.GameObject;
import content.Layer;
import content.Tag;
import content.platformer.scripts.BulletController;
import core.util.BoxCollider;
import core.util.Vector2;
import javafx.scene.image.Image;

/**
 * This GameObject represents a bullet which can be "shot" simply by instanciating one.
 * 
 * @author matthias
 *
 */
public class Bullet extends GameObject {

	/* Behaviour :
	 *   - Shot along the playerInput.directionalInput, or along a direction if shot by an NPC
	 *   - Constant speed ? Slightly decreasing ?
	 *   - If collides, disappear
	 *   - If hits player...
	 */

	Vector2 directionShot;		// must be a normalized Vector2
	float velocityFactor;



	public Bullet(Vector2 position, Layer layer, Tag tag, Vector2 directionShot, float velocityFactor) {
		super(position,
				new Image("resources/graphic/bullet.png"),
				layer,
				tag,
				new BoxCollider(2, 2),		//

				new BulletController(velocityFactor, directionShot));

		this.directionShot = directionShot;
		this.velocityFactor = velocityFactor;
	}



}
