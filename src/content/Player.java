package content;

import java.util.LinkedList;

import core.util.Vector2;
import javafx.scene.image.Image;

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



	public Player(Vector2 position, int maxHP, Weapon weapon) {
		super(position, new Image("resources/ball-of-goo-2018-02-03.png"));
		this.hp = maxHP;
		this.maxHP = maxHP;
		this.weapon = weapon;
	}


	public void reduceHP() {
		hp--;
	}

	@Override
	public String toString() {
		return "Player {hp: " + Integer.toString(hp) + position.toString() + "}";
	}





}
