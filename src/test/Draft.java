package test;

import core.exceptions.InvalidArgumentsException;
import core.util.*;

/**
 * This class is simply used as a draft to try out various things.
 *
 * @author Raph
 *
 */
public class Draft {
	public static void main(String[] args) throws InvalidArgumentsException {

		Vector2 test = new Vector2(1, 2);
		changeVector(test);
		System.out.println(test);
		



	}

	
	static void changeVector(Vector2 maBite) {
		maBite.x = 5;
	}
	
	
}
