package content;

import java.util.LinkedList;
import javafx.scene.image.Image;
import levels.Tile;
import core.scripts.Controller;
import core.scripts.MonoBehaviour;
import core.scripts.PlayerScript;
import core.util.BoxCollider;
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
	

	public int maxHP;			// The maximum amount of HP the player can have; also the initial HP
	Weapon weapon;
	
	private int hp;				// The current amount of HP the player has
	public int getHp() {
		return hp;
	}



	/**
	 * Private method required to bypass the annoying fact that super() has to be called first in the constructor
	 */
	protected static LinkedList<MonoBehaviour> generateScriptsList() {
		LinkedList<MonoBehaviour> result = new LinkedList<MonoBehaviour>();
		result.add(new PlayerScript());
		result.add(new Controller());
		return result;
	}


	public Player(Vector2 position, int maxHP, Weapon weapon) {
		super(position, new BoxCollider(5, 5), new Image(SPRITE_PATH, Tile.TILE_SIZE, Tile.TILE_SIZE, false, false),
				Layer.DEFAULT, Tag.DEFAULT, generateScriptsList());
		this.maxHP = maxHP;
		this.hp = maxHP;
		this.weapon = weapon;
	}



	public void reduceHP() {
		hp--;
	}



	@Override
	public String toString() {
		return "Player {HP: " + Integer.toString(hp) + "; Position: " + position.toString() + "}";
	}

}
