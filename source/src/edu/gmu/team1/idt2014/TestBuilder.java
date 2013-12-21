package edu.gmu.team1.idt2014;

import edu.gmu.team1.idt2014.predicates.Predicate;

public class TestBuilder implements ITestBuilder {
	private String tName;
	
	private TestStructure tStructure = new TestStructure();
	
	
	public TestBuilder(String tName) {
		this.tName = tName;
		tStructure = new TestStructure();
	}

	
	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#branches(int)
	 */
	@Override
	public ITestBuilder branches(int br){
		tStructure.branches = br;
		return this;
	}

	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#test(edu.gmu.team1.idt2014.predicates.Predicate, edu.gmu.team1.idt2014.predicates.Predicate)
	 */
	@Override
	public ITestBuilder test(Predicate b, Predicate c) {
		tStructure.testMap.put(b,  c);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see edu.gmu.team1.idt2014.ITestBuilder#build()
	 */
	@Override
	public void build(){
		GMUT.tester().buildTest(tName, tStructure);
	}

}
