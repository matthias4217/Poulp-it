package content;

import java.util.LinkedList;

/**
 * @@@
 * 
 * @author matthias
 * @author Raph
 * 
 */
public class Player extends GameObject {
	
	private int hp;
	public int getHp() {
		return hp;
	}

	public int maxHP;
	
	Weapon weapon;
	
	LinkedList<State> states;		// List of states affecting the Player
	
	
	
	public Player(int maxHP, Weapon weapon) {
		super();
		this.hp = maxHP;
		this.maxHP = maxHP;
		this.weapon = weapon;
	}
	
	
	public void reduceHP() {
		hp--;
	}
	
	
	
	

}
