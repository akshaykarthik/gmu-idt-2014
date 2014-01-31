package edu.gmu.team1.idt2014;

import edu.gmu.team1.idt2014.predicates.Predicate;

/**
 * This is the actual implementation of {@link ITestBuilder}.<br>
 * It is simply a string name ( {@link #getName()} and a {@link TestStructure} ).
 * <br><br>
 * {@link #test(Predicate, Predicate)} maps a {@link Predicate} to another.
 * <br>
 * A test is a mapping between one predicate and another.
 * <br>
 * test = (a(input) 
 */
public class MainTestBuilder implements ITestBuilder {
	private String tName;
	private TestStructure tStructure;// = new TestStructure();
	
	
	public MainTestBuilder(String tName) {
		this.tName = tName;
		tStructure = new TestStructure(tName);
	}

	
	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#getName()
	 */
	@Override
	public String getName() {
		
		return this.tName;
	}


	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#branches(int)
	 */
	@Override
	public ITestBuilder branches(int br){
		tStructure.setBranches(br);
		return this;
	}

	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#test(edu.gmu.team1.idt2014.predicates.Predicate, edu.gmu.team1.idt2014.predicates.Predicate)
	 */
	@Override
	public ITestBuilder test(Predicate b, Predicate c) {
		tStructure.getTestMap().put(b,  c);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#testNote(java.lang.String, edu.gmu.team1.idt2014.predicates.Predicate, edu.gmu.team1.idt2014.predicates.Predicate)
	 */
	@Override
	public ITestBuilder testNote(String note, Predicate b, Predicate c) {
		tStructure.getNotesMap().put(b, note);
		test(b, c);
		return this;
	}

	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#build()
	 */
	@Override
	public void build(){
		GMUT.buildTest(tName, tStructure);
	}

}
