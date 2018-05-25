/**
 * 
 */
package content.shooter;

import content.GameObject;
import content.Layer;
import content.MonoBehaviour;
import content.Tag;
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
	Vector2 directionShot;
	/*
	 * Image sprite;
	 * , Collider collider
	 */
	
	
	public Bullet(Vector2 position, Layer layer, Tag tag, Vector2 directionShot, MonoBehaviour[] scripts) {
		super(position, sprite, layer, tag, collider, scripts);
		// TODO Auto-generated constructor stub
		this.directionShot = directionShot;
	}

}
