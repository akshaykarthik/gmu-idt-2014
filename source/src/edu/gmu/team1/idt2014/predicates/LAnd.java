package edu.gmu.team1.idt2014.predicates;

/**
 * This predicate performs a logical and between two predicates. <br>
 * i.e : <br>
 * <code> (Pa(i)->boolean) && (Pb(i)->boolean) -> boolean </code>
 */
public class LAnd implements Predicate {

	Predicate a, b;

	/**
	 * The first and second predicates. This predicate evaluates true when both predicates evaluate true.
	 * @param a
	 * @param b
	 */
	public LAnd(Predicate a, Predicate b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		return a.evaluate(inputs) && b.evaluate(inputs);
	}

}
