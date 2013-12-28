package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

/**
 * This Predicate evaluates whether the first object of the array equals the constructor array.
 */
public class ArrayEquals extends Predicate {

	private Object[] initial;
	
	public ArrayEquals(Object[] input){
		initial = input;
	}
	
	@Override
	public boolean evaluate(Object ... inputs) {
		return Arrays.deepEquals(initial, inputs);
	}

}
