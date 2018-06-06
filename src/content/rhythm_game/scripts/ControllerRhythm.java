package content.rhythm_game.scripts;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import content.MonoBehaviour;
import content.rhythm_game.State;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;
import core.util.Annex;
import javafx.scene.media.AudioClip;


/**
 * This script manages the flow of the Rythm game.
 *
 * @author matthias
 *
 */
public class ControllerRhythm extends MonoBehaviour {

	/**
	 * The maximum amount of time for a round; correspond to the easiest difficulty
	 */
	public static float INTERVAL_MAX = 1f;

	/**
	 * The minimum amount of time for a round; correspond to the hardest difficulty
	 */
	public static float INTERVAL_MIN = 0.45f;

	/**
	 * The duration of the feedback period (when the letters are colored after a win or fail).
	 * Denoted as a percentage of interval.
	 */
	public static float PERCENT_FEEDBACK = 0.31f;

	/**
	 * The initial score.
	 */
	public static int INITIAL_SCORE = 10;

	/**
	 * The maximum score after which the difficulty stops increasing
	 */
	public static int SCORE_MAX_DIFFICULTY = 100;

	/**
	 * The sound effect played when a round is won
	 */
	public static final AudioClip WIN_SOUND = new AudioClip("file:///C:/Users/Raphaël/git/hardcore-rodeo-96/bin/resources/audio/sound_effects/CasualGameSounds/"
			+ "DM-CGS-45.wav");

	/**
	 * The sound effect played when a round is failed 
	 */
	public static final AudioClip FAIL_SOUND = new AudioClip("file:///C:/Users/Raphaël/git/hardcore-rodeo-96/bin/resources/audio/sound_effects/CasualGameSounds/"
			+ "DM-CGS-02.wav");

	/**
	 * The volume of the sound effects
	 */
	public static double volume = 1.0;




	/**
	 * The interval between each apparition of a new row of letters.
	 */
	public float interval;
	/**
	 * The current score
	 */
	public int score;

	/**
	 * The current state:
	 * 	- NORMAL	
	 *  - WIN		
	 *  - LOSE		
	 */
	public State state;

	/**
	 * The amount of time currently spent in this phase
	 */
	private float timeIntervalSpent = 0f;


	Random random = new Random();
	public boolean[] currentLetters = new boolean[4];		// 0 -> A; 1 -> Z; 2 -> E; 3 -> R
	public boolean[] lettersPressed = new boolean[4];
	Map<Integer, String> numMap = new HashMap<>();
	Map<Integer, String> mapLetters = new HashMap<>();



	@Override
	public void start() {
		score = INITIAL_SCORE;
		calculateInterval(score);
		state = State.NORMAL;
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
					WIN_SOUND.play(volume/2);
					score++;
					state = State.WIN;
				} else {
					System.out.println("Fail!");
					FAIL_SOUND.play(volume);
					score--;
					state = State.FAIL;
				}
				timeIntervalSpent = 0;

			}

			break;
		case WIN:
		case FAIL:

			if (timeIntervalSpent >= PERCENT_FEEDBACK * interval) {
				generateRow();
				state = State.NORMAL;
				timeIntervalSpent = 0;

				calculateInterval(Annex.clamp(score, 0, SCORE_MAX_DIFFICULTY));
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

	void calculateInterval(int score) {
		interval = INTERVAL_MAX + (INTERVAL_MIN - INTERVAL_MAX) * score / SCORE_MAX_DIFFICULTY;
	}

}
