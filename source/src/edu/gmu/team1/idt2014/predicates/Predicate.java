package edu.gmu.team1.idt2014.predicates;

/**
 * The Core of the Logic Framework
 * <p>
 * Contains one function <code>evaluate</code>.
 * <p>
 * Extend this abstract class in order to create your own predicate functions.
 *
 */
public abstract class Predicate {
	
	/**
	 * The evaluation takes a set of inputs and returns a boolean evaluation.
	 * @param inputs - An Object Array as variable length arguments
	 * @return boolean - An evaluation of the inputs.
	 */
	public abstract boolean evaluate(Object... inputs);

}
