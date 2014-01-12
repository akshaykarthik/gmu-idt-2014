package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

/**
 * This Predicate evaluates whether the initial integer array is equvalent to
 * the integer array evaluated. This specifies int array vs ArrayEquals because
 * ArrayEquals works for objects but not primitives.
 */
public class IntArrayEquals extends Predicate {

	private Object initial;

	public IntArrayEquals(Object input) {
		initial = input;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		// System.out.println(Arrays.toString((int[])initial));
		// System.out.println(Arrays.toString((int[])inputs[0]));
		boolean value = Arrays.equals((int[]) initial, (int[]) inputs[0]);
		// System.out.println(value);
		return value;
	}

}
