package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.predicates.Equals;

public class TypeTest_Byte extends TypeTest {

	@Override
	public void execute() {
		//Equals nullTest = new Equals(null);
		Equals minTest = new Equals(Byte.MIN_VALUE);
		Equals zeroTest = new Equals((byte) 0);
		Equals maxTest = new Equals(Byte.MAX_VALUE);
		Equals byteInt = new Equals((byte) 5);

		//log(nullTest.evaluate(null), "byte [null == null]");
		log(minTest.evaluate(Byte.MIN_VALUE), "byte [min == min]");
		log(zeroTest.evaluate((byte) 0), "byte [0x0 == 0x0]");
		log(maxTest.evaluate(Byte.MAX_VALUE), "byte [max == max]");
		log(!byteInt.evaluate((int) 5), "byte [0x5 != 5]");
	}

}
