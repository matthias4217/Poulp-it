package content.game_elements;

import java.util.LinkedList;

import content.GameObject;

/**
 * 
 * @author matthias, raph
 *
 */
public class Player extends GameObject {
	
	int healthPoints;
	public int getHp() {
		return healthPoints;
	}

	public int maxHealthPoints;
	
	Weapon weapon;
	
	LinkedList<State> states;		// List of states affecting the Player
	
	
	public Player(int maxHealthPoints, Weapon weapon) {
		super();
		this.healthPoints = maxHealthPoints;
		this.maxHealthPoints = maxHealthPoints;
		this.weapon = weapon;
	}
	
	public void reduceHP() {
		healthPoints--;
	}
	
	
	
	

}
