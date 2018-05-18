package content;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import content.scripts.rhythmgame.Controller;
import core.util.Vector2;

public class RhythmConductor extends GameObject {
	
	//interval between each apparition of a new input
	public float INTERVAL = 1f;
	public float timeIntervalSpent = 0f;
	
	public String currentLetter;
	Map<Integer, String> map = new HashMap<Integer, String>();
	String temp;
	temp = map.put(1, "A");
	temp = map.put(2, "Z");
	temp = map.put(3, "E");
	temp = map.put(4, "R");
	

	public RhythmConductor() {
		super(Vector2.ZERO(),
				null,
				Layer.DEFAULT,
				Tag.DEFAULT,
				null,
				new Controller());
		currentLetter = map.get(ThreadLocalRandom.current().nextInt(1, 5));
	}
}
