package test;

import core.util.*;
import core.exceptions.InvalidArgumentsException;

/**
 * This class is simply used as a draft to try out various things.
 *
 * @author Raph
 *
 */
public class Draft {
	public static void main(String[] args) throws InvalidArgumentsException {

		Vector2 A = new Vector2(2, 2);
		Vector2 B = new Vector2(4, 2);
		Vector2 C = new Vector2(3, -1);
		Vector2 D = new Vector2(3, 3);

		if (Annex.checkSegmentIntersection(A, B, C, D) ) {
			System.out.println("Intersection");
		} else {
			System.out.println("Nope");
		}

	}

	
	
	
}
