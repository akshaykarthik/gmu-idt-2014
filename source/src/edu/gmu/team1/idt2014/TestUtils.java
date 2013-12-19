package edu.gmu.team1.idt2014;

public class TestUtils {
	public static String getMethodName() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		return stackTraceElements[2].getMethodName();
	}
	
	public static String getClassName() {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		return stackTraceElements[2].getClassName();
	}
}
