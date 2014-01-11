package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.predicates.ArrayEquals;


public class TypeTest_IntArr extends TypeTest {

	@Override
	public void execute() {
		ArrayEquals empty = new ArrayEquals();
		ArrayEquals correctEquals = new ArrayEquals(0, 1, 2, 3, 4, 5);
		ArrayEquals lengthMismatch = new ArrayEquals(1,2,3,4,5);
		log(empty.evaluate(new int[]{}), "int[] [{} == {}]");
		log(correctEquals.evaluate(new int[]{0,1,2,3,4,5}), "int[] [{0,1,2,3,4,5} == {0,1,2,3,4,5}]");
		log(!lengthMismatch.evaluate(new int[]{0,1,2,3,4,5}), "int[] [{0,1,2,3,4,5} != {1,2,3,4,5}]");
	}

}
