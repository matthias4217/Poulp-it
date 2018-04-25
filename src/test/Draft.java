package test;

import core.util.*;
import java.util.*;
import content.*;
import core.exceptions.InvalidArgumentsException;

/**
 * This class is simply used as a draft to try out various things.
 *
 * @author Raph
 *
 */
public class Draft {
	public static void main(String[] args) throws InvalidArgumentsException {



		// Seems to be working =)

		Vector2 A = new Vector2(0, 2);
		Vector2 B = new Vector2(2, 0);
		Vector2 C = new Vector2(1, 1);
		Vector2 D = new Vector2(2, 2);

		System.out.println("A: " + A);
		System.out.println("B: " + B);
		System.out.println("C: " + C);
		System.out.println("D: " + D);
		System.out.println();

		Vector2 intersection = Annex.doIntersect(A, B, C, D);

		if (intersection == null) {
			System.out.println("null");
		} else {
			System.out.println("Intersection at " + intersection);
		}

	}






	public static void testAngle() {

		Vector2 A = new Vector2(1, 0);
		Vector2 B = new Vector2(0, 1);

		System.out.println(Vector2.angle(A, B));

	}




}


