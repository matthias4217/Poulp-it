package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import content.game_elements.Player;
import content.game_elements.State;
import content.game_elements.Weapon;

class PlayerTest extends Player {

	PlayerTest(int hp, int max_hp, Weapon weapon, LinkedList<State> states, boolean debug) {
		super(hp, max_hp, weapon, states, debug);
		// TODO Auto-generated constructor stub
	}

	@Test
	void testPlayer() {
		Player playertest = new Player(100, 100, null, null, false);
		fail("Not yet implemented");
	}

}
