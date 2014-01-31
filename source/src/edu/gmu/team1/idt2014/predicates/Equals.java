package edu.gmu.team1.idt2014.predicates;

/**
 * This Predicate evaluates whether the the first of the evaluation arguments is
 * equivalent to the initial object.
 */
public class Equals implements Predicate {

	private Object initial;

	public Equals(Object input) {
		initial = input;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		if (inputs == null && initial == null)
			return true;
		
		if (inputs == null || initial == null)
			return false;
		
		return initial.equals(inputs[0]);
	}

}
