package gameobjects;

import java.util.LinkedList;

/**
 * 
 * @author matthias
 *
 */
public class Player {
	
	/**
	 * Health of the Player
	 */
	int hp;
	/**
	 * Weapon of the Player
	 */
	Weapon weapon;
	/**
	 * List of States of the Player
	 */
	LinkedList<State> states;
	/**
	 * Boolean : true for debug state ; false for normal use
	 */
	boolean debug;
}
