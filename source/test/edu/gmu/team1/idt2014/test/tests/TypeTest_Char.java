package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.predicates.Equals;


public class TypeTest_Char extends TypeTest {

	@Override
	public void execute() {
		Equals nullTest = new Equals(null);
		Equals minTest = new Equals('a');
		Equals zeroTest = new Equals((char)0);
		Equals maxTest = new Equals('z');
		Equals charInt = new Equals("s");
		
		
		log(nullTest.evaluate(null), "char [null == null]");
		log(minTest.evaluate('a'), "char [a == a]");
		log(zeroTest.evaluate((char)0), "char [0 == 0]");
		log(maxTest.evaluate('z'), "char [z == z]");
		log(!charInt.evaluate('a'), "char ['a' != \"a\"]");
	}

}
