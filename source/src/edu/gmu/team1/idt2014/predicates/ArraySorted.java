package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

import edu.gmu.team1.idt2014.TestUtils;

/**
 * This predicate returns whether or not a given array is sorted. It relies on
 * Arrays.sort. This is an example of a unary predicate, wherein only the object
 * evaluated matters.
 * 
 * As a result, this can be used as a singleton.
 */
public class ArraySorted implements Predicate {
	public static ArraySorted INSTANCE = new ArraySorted();
	@Override
	public boolean evaluate(Object... inputs) {
		Object o = inputs[0];
		if (o.getClass().isArray()) {
			Object[] ar = TestUtils.toObjectArray(o);
			Object[] ar2 = ar.clone();
			Arrays.sort(ar2);
			return Arrays.equals(ar, ar2);
		}
		return false;
	}

}
