package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.predicates.Equals;
import edu.gmu.team1.idt2014.test.InternalTest;

public class APITest_Integration extends InternalTest {

	@Override
	public void execute() {
		System.out.println("Testing isOdd with 2");
		log(isOdd(2),"isOdd? 2");
		System.out.println("Testing isOdd with 3");
		log(isOdd(3),"isOdd? 3");
		System.out.println("Testing isOdd with 4");
		log(isOdd(4),"isOdd? 4");
		System.out.println("Testing isOdd with 10");
		log(isOdd(10),"isOdd? 10");
		System.out.println("Testing isOdd with 11");
		log(isOdd(11),"isOdd? 11");
	}

	public boolean isOdd(int a){
		note("Building the test structure for input "+a);
		GMUT.addTest()
		.branches(2)
		.testNote("test evens", new Equals(2), new Equals(false))
		.testNote("test odds", new Equals(3), new Equals(true))
		.test(new Equals(4), new Equals(false))
		.testNote("test evens", new Equals(10), new Equals(false))
		.testNote("test odds", new Equals(11), new Equals(true))
		.build();
		if (a % 2 == 0) {
			note("Testing in branch 1 with input "+a);
			GMUT.test(true, 1, a);
			return false;
		}
		note("Testing in branch 2 with input "+a);
		GMUT.test(false, 2,  a);
		return true;
	}

}
