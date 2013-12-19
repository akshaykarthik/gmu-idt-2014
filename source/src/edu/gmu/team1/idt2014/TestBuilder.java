package edu.gmu.team1.idt2014;

import edu.gmu.team1.idt2014.predicates.Predicate;

public class TestBuilder {
	private String tName;
	
	private TestStructure tStructure = new TestStructure();
	
	
	public TestBuilder(String tName) {
		this.tName = tName;
		tStructure = new TestStructure();
	}

	
	public TestBuilder branches(int br){
		tStructure.branches = br;
		return this;
	}

	public TestBuilder test(Predicate b, Predicate c) {
		tStructure.testMap.put(b,  c);
		return this;
	}
	
	public void build(){
		GMUT.tester().buildTest(tName, tStructure);
	}

}
