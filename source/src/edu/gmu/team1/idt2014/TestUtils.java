package edu.gmu.team1.idt2014;

public class TestUtils {
	// stop instantiation
	private TestUtils() {

	}

	/**
	 * This function returns the method name at a given depth of the stack
	 * trace. This method automatically adds 1 to the depth to avoid seeing 
	 * this function call itself.
	 * 
	 * @param depth
	 * @return the method name
	 */
	public static String getMethodName(int depth) {
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();
		return stackTraceElements[depth + 1].getMethodName();
	}

	/**
	 * This function returns the class name at a given depth of the stack trace.
	 * This method automatically adds 1 to the depth to avoid seeing 
	 * this function call itself.
	 * @param depth
	 * @return the class name.
	 */
	public static String getClassName(int depth) {
		StackTraceElement[] stackTraceElements = Thread.currentThread()
				.getStackTrace();
		return stackTraceElements[depth + 1].getClassName();
	}

	/**
	 * Converts an object to an Object Array. This is useful when primitive
	 * arrays are passed as converting them to their object equivalents allows
	 * for easy manipulation. This allows us to use java.util.Arrays and
	 * simplifies a lot of type management issues;<br>
	 */
	public static Object[] toObjectArray(Object input) {
		// byte short int long float double boolean char String
		if (input instanceof byte[])
			return toByteArray((byte[]) input);

		if (input instanceof short[])
			return toShortArray((short[]) input);
		
		if (input instanceof int[])
			return toIntegerArray((int[]) input);
		
		if (input instanceof long[])
			return toLongArray((long[]) input);
		
		if (input instanceof float[])
			return toFloatArray((float[]) input);
		
		if (input instanceof double[])
			return toDoubleArray((double[]) input);
		
		if (input instanceof boolean[])
			return toBooleanArray((boolean[]) input);
		
		if (input instanceof char[])
			return toCharacterArray((char[])input);

		try {
			return (Object[]) input;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static Character[] toCharacterArray(char[] input) {
		Character[] returnVal = new Character[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Character(input[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive byte to Object {@link Byte}
	 * 
	 * @param input a byte array.
	 * @return An Object Array of Byte
	 */
	public static Byte[] toByteArray(byte[] input) {
		Byte[] returnVal = new Byte[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Byte(input[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive short to Object {@link Short}
	 * 
	 * @param input short array.
	 * @return An Object Array of Short
	 */
	public static Short[] toShortArray(short[] input) {
		Short[] returnVal = new Short[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Short(input[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive int to Object {@link Integer}
	 * 
	 * @param input int array.
	 * @return An Object Array of Integer
	 */
	public static Integer[] toIntegerArray(int[] input) {
		Integer[] returnVal = new Integer[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Integer(input[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive long to Object {@link Long}
	 * 
	 * @param input long array.
	 * @return An Object Array of Long
	 */
	public static Long[] toLongArray(long[] input) {
		Long[] returnVal = new Long[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Long(input[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive float to Object {@link Float}
	 * 
	 * @param input float array.
	 * @return An Object Array of Float
	 */
	public static Float[] toFloatArray(float[] fs) {
		Float[] returnVal = new Float[fs.length];
		for (int i = 0; i < fs.length; i++)
			returnVal[i] = new Float(fs[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive double to Object {@link Double}
	 * 
	 * @param inputs double array.
	 * @return An Object Array of Doubles
	 */
	public static Double[] toDoubleArray(double[] inputs) {
		Double[] returnVal = new Double[inputs.length];
		for (int i = 0; i < inputs.length; i++)
			returnVal[i] = new Double(inputs[i]);
		return returnVal;
	}

	/**
	 * Converts and array of Primitive boolean to Object {@link Boolean}
	 * 
	 * @param input boolean array.
	 * @return An Object Array of Boolean
	 */
	public static Boolean[] toBooleanArray(boolean[] input) {
		Boolean[] returnVal = new Boolean[input.length];
		for (int i = 0; i < input.length; i++)
			returnVal[i] = new Boolean(input[i]);
		return returnVal;
	}
}
