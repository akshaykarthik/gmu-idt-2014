package edu.gmu.team1.idt2014;

import java.util.concurrent.ConcurrentHashMap;

import edu.gmu.team1.idt2014.predicates.Predicate;
import edu.gmu.team1.idt2014.reportwriters.IReportWriter;
import edu.gmu.team1.idt2014.reportwriters.ReportWriter;

public class GMUT extends Thread {
	private IReportWriter _report;
	private ConcurrentHashMap<String, TestStructure> test;

	public static boolean enabled = true;
	
	private GMUT() {
		this._report = new ReportWriter();
		this.test = new ConcurrentHashMap<String, TestStructure>();
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

	public static ITestBuilder addTest() {
		String tclass = TestUtils.getClassName(2);
		String tmethod = TestUtils.getMethodName(2);
		String testName = tclass + "." + tmethod;
		if (getInstance().test.containsKey(testName)) {
			return new NullTestBuilder();
		}

		return new TestBuilder(testName);
	}

	public static void test(Object output, int branch, Object... inputs) {
		if(enabled){
			String tclass = TestUtils.getClassName(2);
			String tmethod = TestUtils.getMethodName(2);
			String testName = tclass + "." + tmethod;
			if (getInstance().test.containsKey(testName)) { // if tests exist for given class/method
				TestStructure tests = getInstance().test.get(testName); 
				for (Predicate p : tests.testMap.keySet()) { // for individual test in tests
					if (p.evaluate(inputs)) { // if input is valid input
						System.out.println(tests.notesMap.get(p));
						
						boolean passed = tests.testMap.get(p).evaluate(output); // did the output evaluate true?
						System.out.println(tests.name + " "	+ (passed ? "pass" : "fail"));
	
						getReportWriter().logTest(tclass, tmethod, passed, branch,
								tests.branches, output, inputs, tests.notesMap.get(p));
					}
				}
			}
		}
	}

	public static void buildTest(String tName, TestStructure tStructure) {
		getInstance().test.put(tName, tStructure);
	}

	public static IReportWriter getReportWriter() {
		return getInstance()._report;
	}

	public static void setReportWriter(IReportWriter _report) {
		getInstance()._report = _report;
	}

}
