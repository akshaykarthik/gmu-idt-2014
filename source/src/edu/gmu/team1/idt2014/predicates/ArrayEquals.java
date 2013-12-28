package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

import edu.gmu.team1.idt2014.TestUtils;

/**
 * This Predicate evaluates whether the first object of the array equals the
 * constructor array.
 */
public class ArrayEquals extends Predicate {

	private Object[] initial;

	public ArrayEquals(Object... input) {
		initial = input;
	}

	@Override
	public boolean evaluate(Object... inputs) {

		Object[] objectArray = null;

		if (inputs[0].getClass() == int[].class)
			objectArray = TestUtils.toIntegerArray((int[]) inputs[0]);
		else if (inputs[0].getClass() == float[].class)
			objectArray = TestUtils.toFloatArray((float[]) inputs[0]);
		else if (inputs[0].getClass() == double[].class)
			objectArray = TestUtils.toDoubleArray((double[]) inputs[0]);
		else {
			try {
				objectArray = (Object[]) inputs[0];
			} catch (Exception ex) {
				return false;
			}
		}

		return Arrays.equals(initial, objectArray);

	}
}
