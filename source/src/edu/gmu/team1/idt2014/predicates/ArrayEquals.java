package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

import edu.gmu.team1.idt2014.TestUtils;

/**
 * This Predicate evaluates if Arrays.DeepEquals evaluates true for a given
 * array.
 */
public class ArrayEquals extends Predicate {

	private Object[] initial;

	public ArrayEquals(Object... input) {
		initial = input;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		Object[] objectArray = TestUtils.toObjectArray(inputs[0]);
		return Arrays.deepEquals(initial, objectArray);
	}
}
