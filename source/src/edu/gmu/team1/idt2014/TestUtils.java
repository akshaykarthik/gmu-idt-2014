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
	
	public static Integer[] toIntegerArray(int[] input){
		Integer[] returnVal = new Integer[input.length];
		for(int i = 0; i < input.length; i++)
			returnVal[i] = new Integer(input[i]);
		return returnVal;
	}

	public static Float[] toFloatArray(float[] fs) {
		Float[] returnVal = new Float[fs.length];
		for(int i = 0; i < fs.length; i++)
			returnVal[i] = new Float(fs[i]);
		return returnVal;
	}
	
	public static Double[] toDoubleArray(double[] inputs) {
		Double[] returnVal = new Double[inputs.length];
		for(int i = 0; i < inputs.length; i++)
			returnVal[i] = new Double(inputs[i]);
		return returnVal;
	}
}
