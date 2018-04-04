package content;

import javafx.scene.image.Image;
import core.util.BoxCollider;
import core.util.Vector2;
import core.scripts.PlayerScript;
import core.scripts.Controller;


/**
 * This class represents a Player.
 *
 * @author matthias
 * @author Raph
 *
 */
public class Player extends GameObject {

	static final String SPRITE_PATH = "resources/graphic/ball-of-goo-2018-02-03.png";
	

	public int maxHP;			// The maximum amount of HP the player can have; also the initial HP
	
	private int hp;				// The current amount of HP the player has
	
	public int getHp() {
		return hp;
	}




	public Player(Vector2 position, int maxHP) {
		super(position,
				new Image(SPRITE_PATH, Tile.TILE_SIZE, Tile.TILE_SIZE, false, false),
				Layer.DEFAULT,
				Tag.DEFAULT,
				new BoxCollider(5, 5),		//

				new PlayerScript(),
				new Controller());

		this.maxHP = maxHP;
		this.hp = maxHP;
	}



	public void reduceHP() {
		hp--;
	}



	@Override public String toString() {
		return "Player [HP: " + hp + "; Position: " + position + "; Collider " + collider + "]";
	}

}
