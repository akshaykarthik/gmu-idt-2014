package edu.gmu.team1.idt2014;

import edu.gmu.team1.idt2014.predicates.Predicate;

public class NullTestBuilder implements ITestBuilder {

	@Override
	public ITestBuilder branches(int br) {

		return this;
	}

	@Override
	public ITestBuilder test(Predicate b, Predicate c) {
		return this;
	}

	@Override
	public void build() {

	}

	@Override
	public String getName() {
		return null;
	}

}
