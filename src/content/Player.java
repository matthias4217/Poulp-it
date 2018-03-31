package content;

import java.util.LinkedList;
<<<<<<< HEAD
=======

import core.scripts.Controller;
import core.scripts.PlayerScript;
import core.util.Vector2;
>>>>>>> branch 'develop' of https://github.com/matthias4217/hardcore-rodeo-96
import javafx.scene.image.Image;
import core.scripts.Controller;
import core.scripts.PlayerScript;
import core.util.Vector2;

/**
 * This class represents a Player.
 *
 * @author matthias
 * @author Raph
 *
 */
public class Player extends GameObject {

	static final String SPRITE_PATH = "resources/graphic/ball-of-goo-2018-02-03.png";



	private int hp;				// The current amount of HP the player has
	public int getHp() {
		return hp;
	}

	public int maxHP;			// The maximum amount of HP the player can have; also the initial HP
	Weapon weapon;

	LinkedList<State> states;	// List of states affecting the Player



	public Player(Vector2 position, int maxHP, Weapon weapon) {
		super(position, new Image(SPRITE_PATH, Tile.TILE_SIZE, Tile.TILE_SIZE, false, false));
		this.maxHP = maxHP;
		this.hp = maxHP;
		this.weapon = weapon;
<<<<<<< HEAD
		this.scripts.add(new PlayerScript());
		this.scripts.add(new Controller());
=======
		scripts.add(new Controller());
		scripts.add(new PlayerScript());
>>>>>>> branch 'develop' of https://github.com/matthias4217/hardcore-rodeo-96
	}


<<<<<<< HEAD
=======

>>>>>>> branch 'develop' of https://github.com/matthias4217/hardcore-rodeo-96
	public void reduceHP() {
		hp--;
	}



	@Override
	public String toString() {
		return "Player {HP: " + Integer.toString(hp) + "; Position: " + position.toString() + "}";
	}

}
