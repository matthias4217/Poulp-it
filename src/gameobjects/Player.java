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
	 * Maximum health the player can have.
	 * It should be read from a configuration file.
	 */
	int max_hp;
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
	
	/**
	 * Constructor for the Player class
	 * <p>
	 * @param hp 		int Life of the player
	 * @param max_hp	int Maximum life of the player
	 * @param weapon	Weapon Weapon of the player
	 * @param states	LinkedList<State> States in which the player is.
	 * @param debug		Boolean enabling debug mode.
	 */
	public Player(int hp, int max_hp, Weapon weapon, LinkedList<State> states, boolean debug) {
		this.hp = hp;
		this.max_hp = max_hp;
		this.weapon = weapon;
		this.states = states;
		this.debug = debug;
		
	}
}
