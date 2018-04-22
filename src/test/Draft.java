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

		Carrelage mabite1 = new Carrelage(2, 3);
		Carrelage mabite2 = new Carrelage(2, 3);
		Map<Carrelage, LinkedList<GameObject>> mep = new HashMap<Carrelage, LinkedList<GameObject>>();
		
		mep.put(mabite1, null);
		System.out.println("mabite1 dans mep : " + mep.containsKey(mabite1));
		System.out.println("mabite2 dans mep : " + mep.containsKey(mabite2));

	}

	public static int test(int... entiers) {
		int result = 0;
		for (int x: entiers) {
			result += x;
		}
		return result;
	}
	
	
	
	
	
	public static void testIntersection() {
		Vector2 A = new Vector2(2, 2);
		Vector2 B = new Vector2(4, 2);
		Vector2 C = new Vector2(3, -1);
		Vector2 D = new Vector2(3, 3);

		if (Annex.checkSegmentIntersection(A, B, C, D) != null ) {
			System.out.println("Intersection");
		} else {
			System.out.println("Nope");
		}
	}
	
	
	public static void testAngle() {
		
		Vector2 A = new Vector2(1, 0);
		Vector2 B = new Vector2(0, 1);
		
		System.out.println(Vector2.angle(A, B));
		
	}
	



}


