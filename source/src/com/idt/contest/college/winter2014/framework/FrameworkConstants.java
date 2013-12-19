package com.idt.contest.college.winter2014.framework;

/**
 * Class containing constants used throughout the application
 */
public class FrameworkConstants {
	
	/**
	 * Array of classes that have been contributed to this application
	 */
	final public static String[] CLASSES = {"com.idt.contest.college.winter2014.codetotest.MathUtility",
											"com.idt.contest.college.winter2014.codetotest.StringUtility",
											"com.idt.contest.college.winter2014.codetotest.ByteUtility",
											"com.idt.contest.college.winter2014.codetotest.TimeUtility",
											"com.idt.contest.college.winter2014.codetotest.MergeSort",
											"com.idt.contest.college.winter2014.codetotest.MazeGenerator",
											"com.idt.contest.college.winter2014.codetotest.DiningPhilosophers"};
	
	/**
	 * Array of proper titles regarding order for requesting parameters from user
	 */
	final public static String[] ORDER = {"first",
										  "second",
										  "third",
										  "fourth",
										  "fifth",
										  "sixth",
										  "seventh",
										  "eighth",
										  "ninth",
										  "tenth"};
	/**
	 * Delimiter used in breaking a single line apart based on commas
	 */
	final public static String COMMA_DELIMITER = ",";
	
	/**
	 * Delimiter used in breaking a single argument apart into components based on semicolons
	 */
	final public static String SEMICOLON_DELIMITER = ";";
	
	/**
	 * Length of the ORDER array, limit is defined so we do not exceed options
	 */
	final public static int ORDER_LIMIT = ORDER.length;
	
	/**
	 * text for menu input failure due to not entering a valid integer that corresponds with a menu option
	 */
	final public static String METHOD_SELECTION_ERROR = "ERROR: Selected method does not exist";
	
	/**
	 * text for menu input failure due to not entering a valid integer that corresponds with a menu option
	 */
	final public static String INTEGER_INPUT_ERROR = "ERROR: Must enter an integer corresponding to a menu option";
	
	/**
	 * text for menu option failure due to class problem error
	 */
	final public static String MENU_OPTION_FAILED = "ERROR: Could not process menu option - class not found";
	
	/**
	 * text for parameter entry failure error
	 */
	final public static String PARAMETER_ENTRY_FAILED = "ERROR: Expected parameter type did not match actual parameter type";
	
	/**
	 * text for batch processing line failure
	 */
	final public static String BATCH_LINE_FAILED = "ERROR: Line during batch processing did not have at least class name and method name";
	
	/**
	 * text for class not found exception
	 */
	final public static String CLASS_NOT_FOUND_EXCEPTION = "class not found exception";
	
	/**
	 * text for class not found exception
	 */
	final public static String FILE_NOT_FOUND_EXCEPTION = "file not found exception";
	
	/**
	 * text for instantiation exception
	 */
	final public static String INSTANTIATION_EXCEPTION = "instantiation exception";
	
	/**
	 * text for illegal access exception
	 */
	final public static String ILLEGAL_ACCESS_EXCEPTION = "illegal access exception";
	
	/**
	 * text for class cast exception 
	 */
	final public static String CLASS_CAST_EXCEPTION = "class cast exception";
	
	/**
	 * Text for illegal argument exception
	 */
	final public static String ILLEGAL_ARGUMENT_EXCEPTION = "illegal argument exception";
	
	/**
	 * Text for innvocation target exception
	 */
	final public static String INVOCATION_TARGET_EXCEPTION = "invocation target exception";
	
	/**
	 * Text for null pointer exception
	 */
	final public static String NULL_POINTER_EXCEPTION = "value was unexpectedly null";
	
	/**
	 * Text error for binary string containing non 1 and 0 characters
	 */
	final public static String BINARY_REPRESENTATION_ERROR = "binary string passed in contained characters other than 1s and 0s";
	
	/**
	 * Value defined as 'exit' menu value
	 */
	final public static int BITS_IN_BYTE = 8;
	
	/**
	 * Time to wait for threaded scenarios in milliseconds
	 */
	final public static int TIME_TO_WAIT = 10;
	
	/**
	 * Minimum number of attempts to make in any situation before giving up
	 */
	final public static int MINIMUM_NUMBER_OF_ATTEMPTS = 128;
	
	/**
	 * Return value for invalid values, e.g. searching for index of a specific character within a string and it is never found
	 */
	final public static int INVALID_VALUE = -1;
	
	/**
	 * Value defined as 'exit' menu value
	 */
	final public static int EXIT_CODE = 0;
	
	/**
	 * Index in a single line of batch file to find the class
	 */
	final public static int CLASS_INDEX = 0;

	/**
	 * Index in a single line of batch file to find the class
	 */
	final public static int METHOD_INDEX = 1;
	
	/**
	 * Index in a single line of batch file to find the class
	 */
	final public static int PARAMETER_INDEX = 2;
	
	/**
	 * String representation of 0
	 */
	final public static String ZERO_STRING = "0";
	
	/**
	 * String representation of 1
	 */
	final public static String ONE_STRING = "1";
	
	/**
	 * String representation of negative sign
	 */
	final public static String NEGATIVE_SIGN = "-";
	
	/**
	 * String representation of positive sign
	 */
	final public static String POSITIVE_SIGN = "+";
	
	/**
	 * String representation of int[] instructions
	 */
	final public static String INT_ARRAY_INSTRUCTIONS = "int[] - separate int array entries by semicolon";
}
