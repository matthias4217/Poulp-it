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

		Vector2 test = new Vector2(1, 1);
		System.out.println(test.norm());
		Vector2 test2 = test.normalize();
		System.out.println(test2);
		System.out.println(test2.norm());

		Vector2[] truc_t = new Vector2[2];
		truc_t[0] = test;
		truc_t[1] = test2;

		CollisionBounds truc = new CollisionBounds(truc_t);
		System.out.println(truc);



	}








}

