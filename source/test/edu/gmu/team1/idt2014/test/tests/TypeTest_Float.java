package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.predicates.Equals;
import edu.gmu.team1.idt2014.test.InternalTest;

public class TypeTest_Float extends TypeTest{

	@Override
	public void execute() {
		Equals nullTest = new Equals(null);
		Equals minTest = new Equals(Float.MIN_VALUE);
		Equals zeroTest = new Equals((float)0);
		Equals maxTest = new Equals(Float.MAX_VALUE);
		Equals floatInt = new Equals((int)5);
		Equals floatFloat = new Equals((double)5);
		
		log(nullTest.evaluate(null), "float [null == null]");
		log(minTest.evaluate(Float.MIN_VALUE), "float [min == min]");
		log(zeroTest.evaluate((float)0), "float [0.0 == 0.0]");
		log(maxTest.evaluate(Float.MAX_VALUE), "float [max == max]");
		log(!floatInt.evaluate((float)5), "float [5.0f != 5]");
		log(!floatFloat.evaluate((float)5), "float [5.0(double) != 5f]");	}

}
