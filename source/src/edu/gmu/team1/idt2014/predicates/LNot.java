package edu.gmu.team1.idt2014.predicates;

/**
 * This predicate performs a logical not of a predicate. <br>
 * i.e : <br>
 * <code> (Pa(i)->boolean) -> !(Pa(i)->boolean) </code>
 */
public class LNot implements Predicate {

	Predicate a;

	/**
	 * This predicate evaluates true when its internal predicate evaluates false;
	 * @param a
	 */
	public LNot(Predicate a) {
		this.a = a;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		return !a.evaluate(inputs);
	}

}
