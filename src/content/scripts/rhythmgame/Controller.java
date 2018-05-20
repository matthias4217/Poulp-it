package content.scripts.rhythmgame;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import content.MonoBehaviour;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;


/**
 *
 * @author matthias
 *
 */
public class Controller extends MonoBehaviour {

	//interval between each apparition of a new input
	public float INTERVAL = 1f;
	public float timeIntervalSpent = 0f;
	
	public int score;
	
	
	public String currentLetter = "";
	Map<Integer, String> numMap = new HashMap<>();
	Map<Integer, String> mapLetters = new HashMap<>();
	String temp0 = mapLetters.put(1, "A");
	String temp1 = mapLetters.put(2, "Z");
	String temp2 = mapLetters.put(3, "E");
	String temp3 = mapLetters.put(4, "R");

	@Override
	public void start() {

		currentLetter = generateLetter();
		System.out.println("initialLetter : " + currentLetter);
		score = 0;
	};
	
	@Override
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput) throws InvalidArgumentsException {
		timeIntervalSpent += deltaTime;
		if (timeIntervalSpent > INTERVAL) {
			//generate new CurrentLetter
			timeIntervalSpent -= INTERVAL;
			currentLetter = generateLetter();
		}
		switch (currentLetter) {
			case "A": if (playerInput.aPressed) {
				currentLetter = generateLetter();
				score += 1;
			}
				break;
			case "Z": if (playerInput.zPressed) {
				currentLetter = generateLetter();
				score += 1;
			}
				break;
			case "E": if (playerInput.ePressed) {
				currentLetter = generateLetter();
				score += 1;
			}
				break;
			case "R": if (playerInput.rPressed) {
				currentLetter = generateLetter();
				score += 1;
			}
				break;
		}
	};
	
	public String generateLetter() {
		String tempLetter = "";
		while (tempLetter == currentLetter) {
			tempLetter = mapLetters.get(ThreadLocalRandom.current().nextInt(1, 5));
		}
		return tempLetter;
	}
}
