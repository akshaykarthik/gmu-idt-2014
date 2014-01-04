package edu.gmu.team1.idt2014;

import edu.gmu.team1.idt2014.predicates.Predicate;

public class TestBuilder implements ITestBuilder {
	private String tName;
	
	private TestStructure tStructure;// = new TestStructure();
	
	
	public TestBuilder(String tName) {
		this.tName = tName;
		tStructure = new TestStructure(tName);
	}

	
	@Override
	public String getName() {
		
		return this.tName;
	}


	@Override
	public ITestBuilder branches(int br){
		tStructure.branches = br;
		return this;
	}

	@Override
	public ITestBuilder test(Predicate b, Predicate c) {
		tStructure.testMap.put(b,  c);
		return this;
	}
	
	@Override
	public ITestBuilder testNote(String note, Predicate b, Predicate c) {
		tStructure.notesMap.put(b, note);
		test(b, c);
		return this;
	}

	@Override
	public void build(){
		GMUT.buildTest(tName, tStructure);
	}

}
