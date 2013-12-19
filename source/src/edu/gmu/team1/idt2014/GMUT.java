package edu.gmu.team1.idt2014;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import edu.gmu.team1.idt2014.predicates.Predicate;

public class GMUT extends Thread {
	private ReportWriter _report;
	private ConcurrentHashMap<String, TestStructure> test;

	private GMUT() {
		_report = new ReportWriter();
		test = new ConcurrentHashMap<String, TestStructure>();
		this.start();
	}

	private static class GMUTHolder {
		private static final GMUT INSTANCE = new GMUT();
	}

	public static GMUT tester() {
		return GMUTHolder.INSTANCE;
	}

	public static GMUT getInstance() {
		return GMUTHolder.INSTANCE;
	}

	public TestBuilder addTest() {
		String tclass = TestUtils.getClassName();
		String tmethod = TestUtils.getMethodName();
		String testName = tclass + "." + tmethod;
		if (test.containsKey(testName)) {
			// test exists
		}
		return new TestBuilder(testName);
	}


	public void test(Object output, int branch, Object... inputs) {
		String tclass = TestUtils.getClassName();
		String tmethod = TestUtils.getMethodName();
		String testName = tclass + "." + tmethod;
		if (test.containsKey(testName)) {
			TestStructure tests = test.get(testName);
			for(Predicate p : tests.testMap.keySet()){
				if(p.evaluate(inputs)){
					boolean passed = tests.testMap.get(p).evaluate(output);
					System.out.println("test: " + inputs.toString() + " " + (passed?"pass":"fail"));
				
					/* TODO:
					 * Report whether test passed or failed
					 * 
					 */
				}
			}
		}
	}

	public void buildTest(String tName, TestStructure tStructure) {
		test.put(tName, tStructure);
	}

}
