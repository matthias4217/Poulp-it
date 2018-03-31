package core.exceptions;

/**
 * Exception raised when trying to instanciate several GameEngines.
 * (actually probably useless =/)
 * 
 * @author Raph
 * @see GameEngine
 *
 */

@SuppressWarnings("serial")
public class MultipleGameEngineException extends Exception {
	
	public MultipleGameEngineException() {
		super("One instance of GameEngine already exists");
	}

}
