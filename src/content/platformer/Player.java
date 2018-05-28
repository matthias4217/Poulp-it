package content.platformer;

import javafx.scene.image.Image;
import core.GameEngine;
import core.util.BoxCollider;
import core.util.Vector2;
import content.GameObject;
import content.Layer;
import content.Tag;
import content.platformer.scripts.Controller;
import content.platformer.scripts.PlayerScript;


/**
 * This class represents a Player.
 *
 * @author matthias, Raph
 *
 */
public class Player extends GameObject {

	static final String SPRITE_PATH = "resources/graphic/sophie.png";


	public int maxHP;			// The maximum amount of HP the player can have; also the initial HP

	private int hp;				// The current amount of HP the player has

	public int getHp() {
		return hp;
	}




	public Player(Vector2 position, int maxHP, GameEngine gameEngine) {
		super(position,
				new Image(SPRITE_PATH, 64, 64, false, false),
				Layer.DEFAULT,
				Tag.DEFAULT,
				new BoxCollider(64, 64),		//
				gameEngine,

				new PlayerScript(),
				new Controller());

		this.maxHP = maxHP;
		this.hp = maxHP;
	}



	public void reduceHP() {
		hp--;
	}



	@Override public String toString() {
		return "Player [HP: " + hp + "; Position: " + position + "; " + collider + "]";
	}

}
