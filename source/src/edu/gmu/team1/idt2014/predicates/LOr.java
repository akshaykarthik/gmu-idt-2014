package edu.gmu.team1.idt2014.predicates;

/**
 * This predicate performs a logical or between two predicates. <br>
 * i.e : <br>
 * <code> (Pa(i)->boolean) || (Pb(i)->boolean) -> boolean </code>
 */
public class LOr implements Predicate {

	Predicate a, b;

	/**
	 * The first and second predicates. This predicate evaluates true when either predicate evaluates true.
	 * @param a
	 * @param b
	 */
	public LOr(Predicate a, Predicate b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		// TODO Auto-generated method stub
		return a.evaluate(inputs) || b.evaluate(inputs);
	}

}
