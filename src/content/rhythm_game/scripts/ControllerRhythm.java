package content.rhythm_game.scripts;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import content.MonoBehaviour;
import content.rhythm_game.State;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;


/**
 * This script manages the flow of the Rythm game.
 *
 * @author matthias
 *
 */
public class ControllerRhythm extends MonoBehaviour {

	/**
	 * The interval between each apparition of a new row of letters.
	 * Adjust this to control the difficulty.
	 */
	public static float interval = 0.5f;
	
	/**
	 * The duration of the feedback period (when the letters are colored after a win or fail).
	 * Denoted as a percentage of interval.
	 */
	public static float percentFeedback = 0.35f;



	/**
	 * The current score
	 */
	public int score;

	/**
	 * The current state:
	 */
	public State state;


	private float timeIntervalSpent = 0f;


	Random random = new Random();
	public boolean[] currentLetters = new boolean[4];		// 0 -> A; 1 -> Z; 2 -> E; 3 -> R
	public boolean[] lettersPressed = new boolean[4];
	Map<Integer, String> numMap = new HashMap<>();
	Map<Integer, String> mapLetters = new HashMap<>();



	@Override
	public void start() {
		state = State.NORMAL;
		score = 0;
		generateRow();
	};

	@Override
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput)
			throws InvalidArgumentsException {

		timeIntervalSpent += deltaTime;
		
		switch (state) {
		case NORMAL:
			
			if (timeIntervalSpent >= interval) {		// if the time interval is finished
				if (checkEquality(playerInput)) {
					System.out.println("Win!");
					score++;
					state = State.WIN;
				} else {
					System.out.println("Fail!");
					score--;
					state = State.FAIL;
				}
				timeIntervalSpent = 0;

			}

			break;
		case WIN:
		case FAIL:
			
			if (timeIntervalSpent >= percentFeedback * interval) {
				generateRow();
				state = State.NORMAL;
				timeIntervalSpent = 0;
			}
			break;

		default:
			break;
		}

	}


	/**
	 * Generate a new currentLetters table
	 */
	public void generateRow() {
		for (int i = 0; i < 4; i++) {
			currentLetters[i] = random.nextBoolean();
		}
	}

	/**
	 * Check the equality between currentLetters and lettersPressed
	 * 
	 * @return true if currentLetters equals lettersPressed, false otherwise
	 */
	public boolean checkEquality(PlayerInput playerInput) {
		return playerInput.aPressed == currentLetters[0] &&
				playerInput.zPressed == currentLetters[1] &&
				playerInput.ePressed == currentLetters[2] &&
				playerInput.rPressed == currentLetters[3];
	}

}
