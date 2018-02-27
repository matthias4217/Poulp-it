package content.game_elements;

import java.util.LinkedList;

import content.GameObject;

/**
 * 
 * @author matthias
 *
 */
public class Player extends GameObject {
	
	/**
	 * Health of the Player
	 */
	int hp;
	public int getHp() {
		return hp;
	}

	/**
	 * Maximum health the player can have.
	 */
	public int maxHp;
	/**
	 * Weapon of the Player
	 */
	Weapon weapon;
	/**
	 * List of States of the Player
	 */
	LinkedList<State> states;
	
	/**
	 * Constructor for the Player class
	 * <p>
	 * @param max_hp	int Maximum life of the player
	 * @param weapon	Weapon Weapon of the player
	 */
	public Player(int maxHp, Weapon weapon) {
		super();
		this.hp = maxHp;
		this.maxHp = maxHp;
		this.weapon = weapon;
	}
	
	public void reduceHP() {
		hp--;
	}
	
	
	
	

}
