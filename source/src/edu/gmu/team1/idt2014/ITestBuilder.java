package edu.gmu.team1.idt2014;

import edu.gmu.team1.idt2014.predicates.Predicate;

public interface ITestBuilder {

	public abstract ITestBuilder branches(int br);

	public abstract ITestBuilder test(Predicate b, Predicate c);
	
	public abstract ITestBuilder testNote(String note, Predicate b, Predicate c);

	public abstract void build();
	
	public String getName();

}