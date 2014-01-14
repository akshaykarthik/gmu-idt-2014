package edu.gmu.team1.idt2014.predicates;

/**
 * This predicate always returns true. <br>
 * This is mostly for functions where you want to test every input so the input
 * predicate is this. <br>
 * If possible, you should use AlwaysPredicate.INSTANCE so that only one class
 * is ever instantiated.
 */
public class AlwaysPredicate implements Predicate {

	public static AlwaysPredicate INSTANCE = new AlwaysPredicate();

	public boolean evaluate(Object... inputs) {
		return true;
	}

}
