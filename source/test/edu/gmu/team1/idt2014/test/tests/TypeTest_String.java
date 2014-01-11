package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.predicates.Equals;

public class TypeTest_String extends TypeTest {

	@Override
	public void execute() {
		Equals nullTest = new Equals(null);
		Equals emptyTest = new Equals("");
		Equals caseTest = new Equals("abcdefg");
		Equals correctEquality = new Equals(
				"the quick brown fox jumps over the lazy dog");

		log(nullTest.evaluate(null), "string [null == null]");
		log(!caseTest.evaluate(""), "string [\"\" == \"\"]");
		log(correctEquality.evaluate("the quick brown fox jumps over the lazy dog"),
				"String [\"the quick brown fox jumps over the lazy dog\" == \"the quick brown fox jumps over the lazy dog\"]");
	}

}
