package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.predicates.Equals;


public class TypeTest_Boolean extends TypeTest {

	@Override
	public void execute() {
		Equals expectTrue = new Equals(true);
		Equals expectFalse = new Equals(false);
		Equals expectNull = new Equals(null);
		log(expectNull.evaluate(null), "boolean [null == null]");
		log(expectTrue.evaluate(true), "boolean [true == true]");
		log(expectTrue.evaluate(false), "boolean [true != false]");
		log(expectFalse.evaluate(true), "boolean [false == false]");
		log(expectFalse.evaluate(false), "boolean [false != true]");
		
	}

}
