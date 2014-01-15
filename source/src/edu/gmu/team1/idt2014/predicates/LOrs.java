package edu.gmu.team1.idt2014.predicates;

/**
 * This predicate performs a logical or between many predicates. <br>
 * i.e : <br>
 * <code> (Pa(i)->boolean) || (Pb(i)->boolean || Pn(i)) -> boolean </code>
 */
public class LOrs implements Predicate {

	Predicate[] list;

	/**
	 * The list of predicates, it evalues true when any of the predicates are true.
	 * @param a
	 * @param b
	 */
	public LOrs(Predicate ...a) {
		this.list = a;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		boolean ret = false;
		for(Predicate a : list){
			ret = ret || a.evaluate(inputs);
		}
		return ret;		
	}

}
