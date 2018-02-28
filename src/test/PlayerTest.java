package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import content.Player;
import content.State;
import content.Weapon;

class PlayerTest extends Player {

	PlayerTest(int hp, int max_hp, Weapon weapon, LinkedList<State> states, boolean debug) {
		super(max_hp, weapon);
	}

	@Test
	void testPlayer() {
		Player playertest = new Player(10, null);
		fail("Not yet implemented");
	}

}
