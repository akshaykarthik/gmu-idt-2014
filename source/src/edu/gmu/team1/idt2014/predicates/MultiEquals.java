package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

import edu.gmu.team1.idt2014.TestUtils;

/**
 * This Predicate evaluates whether a set of objects are equal to another set of
 * objects. This is different from ArrayEquals because it attempts to ignore the
 * datatype of the initial object. <br>
 * <br>
 * i.e, 5(int) == 5(byte), etc... <br>
 * This also works on a set of objects, each a different type.
 */
public class MultiEquals extends Predicate {

	private Object[] initial;

	public MultiEquals(Object... input) {
		initial = input;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		Object[] objectArray = TestUtils.toObjectArray(inputs);
		return Arrays.deepToString(initial).equals(
				Arrays.deepToString(objectArray));
	}
}
