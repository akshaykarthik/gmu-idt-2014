package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

import edu.gmu.team1.idt2014.TestUtils;

/**
 * This predicate returns whether or not a given array is sorted. It relies on
 * Arrays.sort.
 */
public class ArraySorted implements Predicate {
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
