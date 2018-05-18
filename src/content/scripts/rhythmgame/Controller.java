package content.scripts.rhythmgame;

import content.scripts.MonoBehaviour;
import core.PlayerInput;
import core.exceptions.InvalidArgumentsException;

public class Controller extends MonoBehaviour {

	@Override
	public void start() {
		
	};
	
	@Override
	public void update(float deltaTime, PlayerInput playerInput, PlayerInput previousPlayerInput) throws InvalidArgumentsException {
		support.timeIntervalSpent += deltaTime;
		if (support.timeIntervalSpent > support.INTERVAL) {
			// display next letter
		}
	};
}
