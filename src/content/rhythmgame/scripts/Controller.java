package content.rhythmgame.scripts;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import content.MonoBehaviour;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;


/**
 *
 * @author matthias
 *
 */
public class Controller extends MonoBehaviour {

	/**
	 * The interval between each apparition of a new input
	 */
	public static float interval = .8f;
	

	private float timeIntervalSpent = 0f;
	
	public int score;
	
	Random random = new Random();
	public boolean[] currentLetters = new boolean[4]; // 0:a ; 1:z ; 2:e ; 3:r
	public boolean[] lettersPressed = new boolean[4];
	Map<Integer, String> numMap = new HashMap<>();
	Map<Integer, String> mapLetters = new HashMap<>();
	private String temp0 = mapLetters.put(0, "A");
	private String temp1 = mapLetters.put(1, "Z");
	private String temp2 = mapLetters.put(2, "E");
	private String temp3 = mapLetters.put(3, "R");

	@Override
	public void start() {

		generateRow();
		score = 0;
	};
	
	@Override
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput) throws InvalidArgumentsException {
		timeIntervalSpent += deltaTime;
		if (timeIntervalSpent > interval) {
			if (checkEquality()) {
				score += 1;
				System.out.println("truc !");
			} else {
				score -= 1;
			}
			generateRow();
			timeIntervalSpent -= interval;
		}
		lettersPressed[0] = (playerInput.aPressed);
		lettersPressed[1] = (playerInput.zPressed);
		lettersPressed[2] = (playerInput.ePressed);
		lettersPressed[3] = (playerInput.rPressed);
			
		
	}
	
	/**
	 * Generate a new currentLetters table
	 */
	public void generateRow() {
		for (int i = 0; i <4 ; i++) {
			currentLetters[i] = random.nextBoolean();
		}
	}
	
	/**
	 * Check the equality between currentLetters and lettersPressed
	 * 
	 * @return true if currentLetters equals lettersPressed, else false
	 */
	public boolean checkEquality() {
		for (int i = 0; i <4 ; i++) {
			if (!(currentLetters[i] == lettersPressed[i])) {
				return false;
			}
		}
		return true;
	}
}
