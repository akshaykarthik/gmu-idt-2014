package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

/**
 * This Predicate evaluates whether the first object of the array equals the
 * constructor array.
 */
public class ArrayEquals extends Predicate {

	private Object[] initial;
	private boolean first;

	public ArrayEquals(boolean firstElement, Object... input) {
		first = firstElement;
		initial = input;
	}

	public ArrayEquals(Object... input) {
		this(false, input);
	}

	@Override
	public boolean evaluate(Object ... inputs) {
		Object[] test = (Object[]) (first?inputs[0]:inputs);
		
		if(initial.length != test.length)
			return false;
		
		return Arrays.equals(initial, test);
	}
}
