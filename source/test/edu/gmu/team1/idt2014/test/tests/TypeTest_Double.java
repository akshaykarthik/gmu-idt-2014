package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.predicates.Equals;


public class TypeTest_Double extends TypeTest {

	@Override
	public void execute() {
		//Equals nullTest = new Equals(null);
		Equals minTest = new Equals(Double.MIN_VALUE);
		Equals zeroTest = new Equals((double)0);
		Equals maxTest = new Equals(Double.MAX_VALUE);
		Equals doubleInt = new Equals((int)5);
		Equals doubleFloat = new Equals((float)5);
		
		//log(nullTest.evaluate(null), "double [null == null]");
		log(minTest.evaluate(Double.MIN_VALUE), "double [min == min]");
		log(zeroTest.evaluate((double)0), "double [0.0 == 0.0]");
		log(maxTest.evaluate(Double.MAX_VALUE), "double [max == max]");
		log(!doubleInt.evaluate((double)5), "double [5.0 != 5]");
		log(!doubleFloat.evaluate((double)5), "double [5.0 != 5f]");
	}

}
