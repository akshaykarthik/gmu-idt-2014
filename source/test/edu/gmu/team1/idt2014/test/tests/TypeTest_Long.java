package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.predicates.Equals;

public class TypeTest_Long extends TypeTest {

	@Override
	public void execute() {
		//Equals nullTest = new Equals(null);
		Equals minTest = new Equals(Long.MIN_VALUE);
		Equals zeroTest = new Equals((long)0);
		Equals maxTest = new Equals(Long.MAX_VALUE);
		Equals longInt = new Equals((double)5);
		Equals longFloat = new Equals((float)5);
		
		//log(nullTest.evaluate(null), "long [null == null]");
		log(minTest.evaluate(Long.MIN_VALUE), "long [min == min]");
		log(zeroTest.evaluate((long)0), "long [0.0 == 0.0]");
		log(maxTest.evaluate(Long.MAX_VALUE), "long [max == max]");
		log(!longInt.evaluate((long)5), "long [5(long) != 5.0]");
		log(!longFloat.evaluate((long)5), "long [5(long) != 5f]");
	}

}
