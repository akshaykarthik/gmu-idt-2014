package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.predicates.Equals;

public class TypeTest_Int extends TypeTest {

	@Override
	public void execute() {
		//Equals nullTest = new Equals(null);
		Equals minTest = new Equals(Integer.MIN_VALUE);
		Equals zeroTest = new Equals((int)0);
		Equals maxTest = new Equals(Integer.MAX_VALUE);
		Equals intInt = new Equals((double)5);
		Equals intFloat = new Equals((float)5);
		
		//log(nullTest.evaluate(null), "int [null == null]");
		log(minTest.evaluate(Integer.MIN_VALUE), "int [min == min]");
		log(zeroTest.evaluate((int)0), "int [0.0 == 0.0]");
		log(maxTest.evaluate(Integer.MAX_VALUE), "int [max == max]");
		log(!intInt.evaluate((int)5), "int [5 != 5.0]");
		log(!intFloat.evaluate((int)5), "int [5 != 5f]");

	}

}
