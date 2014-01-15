package edu.gmu.team1.idt2014.predicates;

/**
 * This predicate performs a logical and between many predicates. <br>
 * i.e : <br>
 * <code> (Pa(i)->boolean) && (Pb(i)->boolean && Pn(i)) -> boolean </code>
 */
public class LAnds implements Predicate {

	Predicate[] list;

	/**
	 * The first and second predicates. This predicate evaluates true when either predicate evaluates true.
	 * @param a
	 * @param b
	 */
	public LAnds(Predicate ...a) {
		this.list = a;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		boolean ret = true;
		for(Predicate a : list){
			ret = ret && a.evaluate(inputs);
		}
		return ret;		
	}

}
