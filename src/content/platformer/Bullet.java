package content.platformer;

import content.GameObject;
import content.Layer;
import content.MonoBehaviour;
import content.Tag;
import content.platformer.scripts.BulletController;
import core.GameEngine;
import core.util.BoxCollider;
import core.util.Collider;
import core.util.Vector2;
import javafx.scene.image.Image;

/**
 * @author matthias
 *
 */
public class Bullet extends GameObject {

	/* Behaviour :
	 *   Shot along the playerInput.directionalInput, or along a direction if by an NPC
	 *   constant speed ? Slightly decreasing ?
	 *   If collides, disappear
	 *   If hits player...
	 */
	Vector2 directionShot; //must be a normalized Vector2
	float velocityFactor;
	
	public Bullet(Vector2 position, Layer layer, Tag tag, Vector2 directionShot, float velocityFactor, GameEngine gameEngine) {
		super(position,
				new Image("resources/graphic/bullet.png"),
				Layer.DEFAULT,
				Tag.SOLID,
				new BoxCollider(2, 2),
				gameEngine,
				new BulletController(velocityFactor, directionShot));
		// TODO Auto-generated constructor stub
		this.directionShot = directionShot;
		this.velocityFactor = velocityFactor;
	}
}
