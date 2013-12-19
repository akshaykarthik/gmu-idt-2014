package com.idt.contest.college.winter2014.framework;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Abstract class representing an execution mode for the 2014 winter contest sample application framework
 */
public abstract class Mode {

	
	/** 
	 * map used to hold class names
	 **/ 
	protected Map <Integer, String> classNameMap = new TreeMap<Integer, String>();
	
	/**
	 * map used to hold methods related to a selected class
	 */
	protected Map <Integer, Method> methodMap = new TreeMap<Integer, Method>();
	
	/**
	 * scanner used to accept input from user on command line
	 */
	protected Scanner scanner;
	
	/**
	 * object used to handle the dynamic casting of parameter values
	 */
	protected ClassCaster classCaster;
	
	
	/**
	 * Protected constructor for abstract Mode class, called by constructors of derived types
	 */
	protected Mode() {
		buildClassNameMap();
		classCaster = new ClassCaster();
	}
	
	
	/**
	 * Abstract method to be implemented by derived classes - triggers execution of particular mode
	 */
	public abstract void execute();
	
	
	/**
	 * Method used to assemble a map of option values to class names used by the menu
	 */
	protected void buildClassNameMap() {
		for (int i=0; i < FrameworkConstants.CLASSES.length; i++) {
			classNameMap.put(i+1, FrameworkConstants.CLASSES[i]);
		}
	}
	
	
	/**
	 * Method used to assemble a map of option values to method names used by the menu
	 * @param clazz
	 */
	protected void buildMethodMap(Class<?> clazz) {
		methodMap.clear();
		Method[] allMethods = clazz.getDeclaredMethods();
		int i = 1;
		for (Method m : allMethods) {
			// only add public methods to the method map
			if (Modifier.isPublic(m.getModifiers())) {
				methodMap.put(i++, m);
			}
		}
	}
	
	
	/**
	 * Method to determine whether or not a class is an Array
	 * @param potentialArray - class that might or might not be an array
	 * @return - true if the class is an array, false if the class is not an array
	 */
	protected boolean isArray(Class<?> potentialArray) {
		
		if (potentialArray == int[].class) {
			return true;
		} else if  (potentialArray == double[].class || potentialArray == float[].class || 
				   potentialArray == byte[].class || potentialArray == short[].class || 
				   potentialArray == long[].class || potentialArray == boolean[].class ||  
				   potentialArray == Integer[].class || potentialArray == Long[].class ||
				   potentialArray == Byte[].class || potentialArray == Short[].class ||
				   potentialArray == Double[].class || potentialArray == Float[].class ||
				   potentialArray == Character[].class || potentialArray == Boolean[].class ||
				   potentialArray == char[].class || potentialArray == String[].class) {

			return true;
		} else {
			return false;
		}
	}
	

	/**
	 * Method to print the contents of an array instead of the address 
	 * @param potentialArray - Object representing a potential array
	 */
	protected void printArray(Object potentialArray) {
		
		if (potentialArray == null) {
			System.out.println("\nRESULT: " + FrameworkConstants.NULL_POINTER_EXCEPTION);
		} else if (potentialArray.getClass() == int[].class) {
			printIntArrayContents(potentialArray);
		} else if  (potentialArray == double[].class) {
			printDoubleArrayContents(potentialArray);
		} else if  (potentialArray == float[].class) {
			printFloatArrayContents(potentialArray);
		} else if  (potentialArray == byte[].class) {
			printByteArrayContents(potentialArray);
		} else if  (potentialArray == short[].class) {
			printShortArrayContents(potentialArray);
		} else if  (potentialArray == long[].class) {
			printLongArrayContents(potentialArray);
		} else if  (potentialArray == boolean[].class) {
			printBooleanArrayContents(potentialArray);
		} else if  (potentialArray == char[].class) {
			printCharacterArrayContents(potentialArray);
		} else if (potentialArray == Integer[].class || potentialArray == Long[].class ||
				   potentialArray == Byte[].class || potentialArray == Short[].class ||
				   potentialArray == Double[].class || potentialArray == Float[].class ||
				   potentialArray == Character[].class || potentialArray == Boolean[].class ||
				   potentialArray == String[].class) {
			printObjectArrayContents(potentialArray);
		}
	}
	
	
	/**
	 * Method used to print contents of an int array instead of the array address
	 * @param intArrayToPrint - int array to print the contents of 
	 */
	protected void printIntArrayContents(Object intArrayToPrint) {		
		int[] castArrayToPrint = (int[]) intArrayToPrint;
		List<Integer> integerList = new ArrayList<Integer>();
		for (int i: castArrayToPrint) {
			integerList.add(i);
		}
		printObjectArrayContents(integerList.toArray());
	}
	
	
	/**
	 * Method used to print contents of an long array instead of the array address
	 * @param intArrayToPrint - long array to print the contents of 
	 */
	protected void printLongArrayContents(Object longArrayToPrint) {		
		long[] castArrayToPrint = (long[]) longArrayToPrint;
		List<Long> longList = new ArrayList<Long>();
		for (long l: castArrayToPrint) {
			longList.add(l);
		}
		printObjectArrayContents(longList.toArray());
	}
	
	
	/**
	 * Method used to print contents of an double array instead of the array address
	 * @param doubleArrayToPrdouble - double array to print the contents of 
	 */
	protected void printDoubleArrayContents(Object doubleArrayToPrint) {		
		double[] castArrayToPrint = (double[]) doubleArrayToPrint;
		List<Double> doubleList = new ArrayList<Double>();
		for (double d: castArrayToPrint) {
			doubleList.add(d);
		}
		printObjectArrayContents(doubleList.toArray());
	}
	
	
	/**
	 * Method used to print contents of an short array instead of the array address
	 * @param shortArrayToPrshort - short array to print the contents of 
	 */
	protected void printShortArrayContents(Object shortArrayToPrint) {		
		short[] castArrayToPrint = (short[]) shortArrayToPrint;
		List<Short> shortList = new ArrayList<Short>();
		for (short s: castArrayToPrint) {
			shortList.add(s);
		}
		printObjectArrayContents(shortList.toArray());
	}
	
	
	/**
	 * Method used to print contents of an byte array instead of the array address
	 * @param byteArrayToPrbyte - byte array to print the contents of 
	 */
	protected void printByteArrayContents(Object byteArrayToPrint) {		
		byte[] castArrayToPrint = (byte[]) byteArrayToPrint;
		List<Byte> byteList = new ArrayList<Byte>();
		for (byte b: castArrayToPrint) {
			byteList.add(b);
		}
		printObjectArrayContents(byteList.toArray());
	}
	
	
	/**
	 * Method used to print contents of an float array instead of the array address
	 * @param floatArrayToPrfloat - float array to print the contents of 
	 */
	protected void printFloatArrayContents(Object floatArrayToPrint) {		
		float[] castArrayToPrint = (float[]) floatArrayToPrint;
		List<Float> floatList = new ArrayList<Float>();
		for (float b: castArrayToPrint) {
			floatList.add(b);
		}
		printObjectArrayContents(floatList.toArray());
	}
	
	
	/**
	 * Method used to print contents of an char array instead of the array address
	 * @param charArrayToPrchar - char array to print the contents of 
	 */
	protected void printCharacterArrayContents(Object charArrayToPrint) {		
		char[] castArrayToPrint = (char[]) charArrayToPrint;
		List<Character> charList = new ArrayList<Character>();
		for (char b: castArrayToPrint) {
			charList.add(b);
		}
		printObjectArrayContents(charList.toArray());
	}
	
	
	/**
	 * Method used to print contents of an boolean array instead of the array address
	 * @param booleanArrayToPrboolean - boolean array to print the contents of 
	 */
	protected void printBooleanArrayContents(Object booleanArrayToPrint) {		
		boolean[] castArrayToPrint = (boolean[]) booleanArrayToPrint;
		List<Boolean> booleanList = new ArrayList<Boolean>();
		for (boolean b: castArrayToPrint) {
			booleanList.add(b);
		}
		printObjectArrayContents(booleanList.toArray());
	}
	
	
	/**
	 * Method used to print contents of an object array instead of the array address
	 * @param objectArrayToPrboolean - object array to print the contents of 
	 */
	protected void printObjectArrayContents(Object objectArrayToPrint) {
		Object[] arrayToPrint = (Object[]) objectArrayToPrint;
		
		System.out.print("\nRESULT: ");
		for (int i = 0; i < arrayToPrint.length; i++) {
			if (i > 0) {
				System.out.print(", ");
			}
			System.out.print(arrayToPrint[i]);
		}
		System.out.println("\n");
	}
}
