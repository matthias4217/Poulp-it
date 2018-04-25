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

		LinkedList<Vector2> pointsList = new LinkedList<Vector2>();
		pointsList.add(new Vector2(0, 0));					// Top left
		pointsList.add(new Vector2(5, 0)); 			// Top right
		pointsList.add(new Vector2(5, -5));	// Bottom right
		pointsList.add(new Vector2(0, -5));			// Bottom left
		
		System.out.println(pointsList);
		
		Vector2[] maBite = new Vector2[pointsList.size()];
		
		pointsList.toArray(maBite);
		System.out.println();
		for (Vector2 x: maBite) {
			System.out.println(x);
		}
		
		
		
		Vector2[] maBite2 = (Vector2[]) pointsList.toArray();
		System.out.println();
		for (Vector2 x: maBite2) {
			System.out.println(x);
		}
		

	}





	public static void testIntersection() {

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

		Vector2 intersection = Annex.segmentsIntersection(A, B, C, D);

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


