package content.rhythm_game;

/**
 * This enumeration lists all states possible for the Rythm game.
 * 
 * @author Raph
 *
 */
public enum State {
	/**
	 * The default state.
	 */
	NORMAL,
	
	/**
	 * The state for when the player just succeeded this round
	 */
	WIN,
	
	/**
	 * The state for when the player just failed this round.
	 */
	FAIL

}
