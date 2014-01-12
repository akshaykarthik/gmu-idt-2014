package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.predicates.Equals;

public class TypeTest_Short extends TypeTest {


	@Override
	public void execute() {
		//Equals nullTest = new Equals(null);
		Equals minTest = new Equals(Short.MIN_VALUE);
		Equals zeroTest = new Equals((short)0);
		Equals maxTest = new Equals(Short.MAX_VALUE);
		Equals shortInt = new Equals((double)5);
		Equals shortFloat = new Equals((float)5);
		
		//log(nullTest.evaluate(null), "short [null == null]");
		log(minTest.evaluate(Short.MIN_VALUE), "short [min == min]");
		log(zeroTest.evaluate((short)0), "short [0.0 == 0.0]");
		log(maxTest.evaluate(Short.MAX_VALUE), "short [max == max]");
		log(!shortInt.evaluate((short)5), "short [5(short) != 5.0]");
		log(!shortFloat.evaluate((short)5), "short [5(short) != 5f]");

	}

}
