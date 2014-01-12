package edu.gmu.team1.idt2014;

import edu.gmu.team1.idt2014.predicates.Predicate;

/**
 * NullTestBuilder does nothing. It is a singleton so only one instance is ever instantiated and used;
 */
public class NullTestBuilder implements ITestBuilder {

	private NullTestBuilder(){
		
	}
	
	private static class NullTestBuilderHolder {
		private static final NullTestBuilder INSTANCE = new NullTestBuilder();
	}
	
	public static NullTestBuilder getInstance(){
		return NullTestBuilderHolder.INSTANCE;
	}
	
	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#getName()
	 */
	@Override
	public String getName() {
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#branches(int)
	 */
	@Override
	public ITestBuilder branches(int br) {
		return NullTestBuilderHolder.INSTANCE;
	}

	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#test(edu.gmu.team1.idt2014.predicates.Predicate, edu.gmu.team1.idt2014.predicates.Predicate)
	 */
	@Override
	public ITestBuilder test(Predicate b, Predicate c) {
		return NullTestBuilderHolder.INSTANCE;
	}

	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#testNote(java.lang.String, edu.gmu.team1.idt2014.predicates.Predicate, edu.gmu.team1.idt2014.predicates.Predicate)
	 */
	@Override
	public ITestBuilder testNote(String note, Predicate b, Predicate c) {
		return NullTestBuilderHolder.INSTANCE;
	}

	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#build()
	 */
	@Override
	public void build() {

	}

}
