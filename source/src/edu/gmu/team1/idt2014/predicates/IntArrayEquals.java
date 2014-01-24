package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

/**
 * This Predicate evaluates whether the initial integer array is equvalent to
 * the integer array evaluated. This specifies int array vs ArrayEquals because
 * ArrayEquals works for objects but not primitives.
 */
public class IntArrayEquals implements Predicate {

	private Object initial;

	public IntArrayEquals(Object input) {
		initial = input;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		
		if(inputs == null && initial == null)
			return true;
		else if(inputs == null)
			return false;
		
		boolean value = Arrays.equals((int[]) initial, (int[]) inputs[0]);
//		 System.out.println(value);

		return value;
	}

}
