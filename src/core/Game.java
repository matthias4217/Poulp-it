package core;

/**
 * The list of games that can be loaded by the Launcher.
 * Each of them encapsulates its name and the title of the window.
 * 
 * @see Launcher
 * 
 * @author Raph
 *
 */
public enum Game {

	HOOK_BATTLE("Hook Battle"),
	MAZE("Maze Game"),
	SHOOTER("Dash"),
	ALIEN("Alien VS Ananas"),
	RHYTHM_GAME("Rythme Game");



	/**
	 * The name of the Game
	 */
	public String name;
	
	/**
	 * The title of the game window
	 */
	public String windowTitle;

	/**
	 * A description of the game
	 */
	public String description = "";



	Game(String name) {
		this(name, name);
	}

	Game(String name, String windowTitle){
		this.name = name;
		this.windowTitle = windowTitle;
	}



	@Override public String toString(){
		return name;
	}

}
