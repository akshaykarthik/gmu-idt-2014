package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

/**
 * This Predicate evaluates whether the first object of the array equals the
 * constructor value.
 */
public class IntArrayEquals extends Predicate {

	private Object initial;

	public IntArrayEquals(Object input) {
		initial = input;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		return (initial != null) ? Arrays.equals((int[])initial, (int[])inputs[0]) : inputs[0] == null;
	}

}
