package edu.gmu.team1.idt2014;

import java.util.concurrent.ConcurrentHashMap;

import edu.gmu.team1.idt2014.predicates.Predicate;
import edu.gmu.team1.idt2014.reportwriters.AbstractReportWriter;
import edu.gmu.team1.idt2014.reportwriters.FileReportWriter;

/**
 * GMUT is the primary access point to the testing API. <br>
 * It is a thread-safe singleton class that is instantiated when the api is
 * first called. <br>
 * Key access to GMUT is through two methods. <code>addTest()</code> and
 * <code>test()</code> <br><br>
 */
public class GMUT extends Thread {
	// Private Constructor Prevents Initialization
	private GMUT() {
		this._report = new FileReportWriter();
		this._test = new ConcurrentHashMap<String, TestStructure>();
		this.start();
	}

	// Private static class holds GMUT instance and since it is inside GMUT, it
	// has access to the private constructor.
	private static class GMUTHolder {
		private static final GMUT INSTANCE = new GMUT();
	}

	/**
	 * Returns the actual instance of GMUT, should be unnecessary as methods are static.
	 * @return GMUT Instance
	 */
	public static GMUT getInstance() {
		return GMUTHolder.INSTANCE;
	}
	
	
	private AbstractReportWriter _report;
	private ConcurrentHashMap<String, TestStructure> _test;

	private static boolean enabled = true;	

	/**
	 * Returns <code>true</code> if enabled, <code>false</code> otherwise.
	 * @return 
	 */
	public static boolean isEnabled() {
		return enabled;
	}

	/**
	 * Enables or disables the testing system.
	 * @param enabled
	 */
	public static void setEnabled(boolean enabled) {
		GMUT.enabled = enabled;
	}

	/**
	 * This method returns an ITestBuilder that creates a test structure.
	 * @see edu.gmu.team1.idt2014.ITestBuilder
	 * @return ITestBuilder
	 */
	public static ITestBuilder addTest() {
		// A depth of 2 is used as 0 is getClassName
		// and 1 is addTest()
		String tclass = TestUtils.getClassName(2);
		String tmethod = TestUtils.getMethodName(2);
		String testName = tclass + "." + tmethod;
		if (getInstance()._test.containsKey(testName)) {
			return NullTestBuilder.getInstance();
		}

		return new MainTestBuilder(testName);
	}

	/**
	 * This method performs the actual testing. It evaluates a given method for its input predicate.
	 * Then, if the input predicate returns true, it evaluates the output predicate for testing whether
	 * or not the test passed or failed.
	 * @param output
	 * @param branch
	 * @param inputs
	 */
	public static void test(Object output, int branch, Object... inputs) {
		if(GMUT.enabled){
			// A depth of 2 is used as 0 is getClassName
			// and 1 is addTest()
			String tclass = TestUtils.getClassName(2);
			String tmethod = TestUtils.getMethodName(2);
			String testName = tclass + "." + tmethod;
			
			// if tests exist for given class/method
			if (getInstance()._test.containsKey(testName)) { 
				// aquire the tests strucure
				TestStructure tests = getInstance()._test.get(testName); 
				 // for individual test in that test structure
				for (Predicate p : tests.testMap.keySet()) {
					// evaluate the input
					if (p.evaluate(inputs)) {
						
						// when the input is valid, evaluate the output predicate.
						boolean passed = tests.testMap.get(p).evaluate(output);
						//System.out.println(tests.name + " "	+ (passed ? "pass" : "fail"));
						
						// log the test case;
						getReportWriter().logTest(tclass, tmethod, passed, branch,
								tests.branches, output, inputs, tests.notesMap.get(p));
					}
				}
			}
		}
	}

	/**
	 * This method builds and actual testcase for a given class+method and testStructure.
	 * Ideally this is not accessed as TestBuilder builds these test for you.
	 * @param tName
	 * @param tStructure
	 */
	public static void buildTest(String tName, TestStructure tStructure) {
		getInstance()._test.put(tName, tStructure);
	}

	/**
	 * This returns the current IReportWriter that GMUT is using.
	 * @return
	 */
	public static AbstractReportWriter getReportWriter() {
		return getInstance()._report;
	}

	/**
	 * This sets the current IReportWriter that GMUT is using.
	 * @param _report
	 */
	public static void setReportWriter(AbstractReportWriter _report) {
		getInstance()._report = _report;
	}

}
