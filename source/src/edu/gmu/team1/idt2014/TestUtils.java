package edu.gmu.team1.idt2014;

import java.util.Arrays;

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

	public static Object[] toObjectArray(Object input) {
		if (input instanceof int[]) {
			return toIntegerArray((int[])input);
		} else if (input instanceof double[]) {
			return toDoubleArray((double[])input);
		} else if (input instanceof float[]) {
			return toFloatArray((float[])input);
		} else if (input instanceof byte[]) {
			return toByteArray((byte[])input);
		} else if (input instanceof short[]) {
			return toShortArray((short[])input);
		} else if (input instanceof boolean[]) {
			return toBoolArray((boolean[])input);
		} else {
			return (Object[])input;
		}
	}

	public static Integer[] toIntegerArray(int[] input) {
		Integer[] returnVal = new Integer[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Integer(input[i]);
		return returnVal;
	}

	public static Double[] toDoubleArray(double[] inputs) {
		Double[] returnVal = new Double[inputs.length];
		for (int i = 0; i < inputs.length; i++)
			returnVal[i] = new Double(inputs[i]);
		return returnVal;
	}

	public static Float[] toFloatArray(float[] fs) {
		Float[] returnVal = new Float[fs.length];
		for (int i = 0; i < fs.length; i++)
			returnVal[i] = new Float(fs[i]);
		return returnVal;
	}

	public static Byte[] toByteArray(byte[] input) {
		Byte[] returnVal = new Byte[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Byte(input[i]);
		return returnVal;
	}

	public static Short[] toShortArray(short[] input) {
		Short[] returnVal = new Short[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Short(input[i]);
		return returnVal;
	}

	public static Boolean[] toBoolArray(boolean[] input) {
		Boolean[] returnVal = new Boolean[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Boolean(input[i]);
		return returnVal;
	}
	
	
}
