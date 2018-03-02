package content;

import java.util.LinkedList;
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

	Image sprite = new Image("resources/ball-of-goo-2018-02-03.png");



	public Player(int maxHP, Weapon weapon) {
		super();
		this.hp = maxHP;
		this.maxHP = maxHP;
		this.weapon = weapon;
	}


	public void reduceHP() {
		hp--;
	}

	@Override
	public String toString() {
		return "Player {hp: " +Integer.toString(hp) + position.toString() + "}";
	}





}
