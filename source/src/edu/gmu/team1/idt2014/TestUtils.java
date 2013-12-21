package edu.gmu.team1.idt2014;

public class TestUtils {
	/**
	 * @param depth
	 * @return
	 */
	public static String getMethodName(int depth) {
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();
		return stackTraceElements[depth + 1].getMethodName();
	}

	public static String getClassName(int depth) {
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();
		return stackTraceElements[depth + 1].getClassName();
	}
}
