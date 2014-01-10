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

	/**
	 * Converts an object to an Object Array. This is useful when primitive
	 * arrays are passed as converting them to their object equivalents allows
	 * for easy manipulation. This allows us to use java.util.Arrays and
	 * simplifies a lot of type management issues;
	 */
	public static Object[] toObjectArray(Object input) {
		if (input instanceof int[]) {
			return toIntegerArray((int[]) input);
		} else if (input instanceof double[]) {
			return toDoubleArray((double[]) input);
		} else if (input instanceof float[]) {
			return toFloatArray((float[]) input);
		} else if (input instanceof byte[]) {
			return toByteArray((byte[]) input);
		} else if (input instanceof short[]) {
			return toShortArray((short[]) input);
		} else if (input instanceof boolean[]) {
			return toBoolArray((boolean[]) input);
		} else {
			try{
				return (Object[]) input;
			} catch(Exception ex){
				return null;
			}
		}
	}

	/**
	 * Converts and array of Primitive {@link int} to Object {@link Integer}
	 * @param an int array.
	 * @return An Object Array of Integer
	 */
	public static Integer[] toIntegerArray(int[] input) {
		Integer[] returnVal = new Integer[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Integer(input[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive {@link double} to Object {@link Double}
	 * @param a double array.
	 * @return An Object Array of Doubles
	 */
	public static Double[] toDoubleArray(double[] inputs) {
		Double[] returnVal = new Double[inputs.length];
		for (int i = 0; i < inputs.length; i++)
			returnVal[i] = new Double(inputs[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive {@link float} to Object {@link Float}
	 * @param a float array.
	 * @return An Object Array of Float
	 */
	public static Float[] toFloatArray(float[] fs) {
		Float[] returnVal = new Float[fs.length];
		for (int i = 0; i < fs.length; i++)
			returnVal[i] = new Float(fs[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive {@link byte} to Object {@link Byte}
	 * @param a byte array.
	 * @return An Object Array of Byte
	 */
	public static Byte[] toByteArray(byte[] input) {
		Byte[] returnVal = new Byte[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Byte(input[i]);
		return returnVal;
	}


	/**
	 * Converts and array of Primitive {@link short} to Object {@link Short}
	 * @param a short array.
	 * @return An Object Array of Short
	 */
	public static Short[] toShortArray(short[] input) {
		Short[] returnVal = new Short[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Short(input[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive {@link boolean} to Object {@link Boolean}
	 * @param a boolean array.
	 * @return An Object Array of Boolean
	 */
	public static Boolean[] toBoolArray(boolean[] input) {
		Boolean[] returnVal = new Boolean[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Boolean(input[i]);
		return returnVal;
	}
}
