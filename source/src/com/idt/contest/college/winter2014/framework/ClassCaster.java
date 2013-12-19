package com.idt.contest.college.winter2014.framework;

/**
 * Class used to cast String values to particular class types
 */
public class ClassCaster {

	/**
	 * Utility method used to cast a String value to a particular class type
	 * @param paramType - Class type to cast String value to
	 * @param value - String value to cast to specific class type
	 * @return - Object representation of String value after casting to a particular class type
	 */
	public Object cast(Class<?> paramType, String value) {
		if (paramType == String.class) {
			return value;
		} else if (paramType == Integer.class || paramType == int.class) {
			return Integer.parseInt(value);
		} else if (paramType == Long.class || paramType == long.class) {
			return Long.parseLong(value);
		} else if (paramType == Double.class || paramType == double.class) {
			return Double.parseDouble(value);
		} else if (paramType == Float.class || paramType == float.class) {
			return Float.parseFloat(value);
		} else if (paramType == Short.class || paramType == short.class) {
				return Short.parseShort(value);
		} else if (paramType == Byte.class || paramType == byte.class) {
			return Byte.parseByte(value);
		} else if (paramType == Character.class || paramType == char.class) {
			return value.charAt(0);
		} else if (paramType == Boolean.class || paramType == boolean.class) {
			return Boolean.parseBoolean(value);
		} else if (paramType == int[].class) {
			String[] splitString = value.split(FrameworkConstants.SEMICOLON_DELIMITER);
			int[] returnArray = new int[splitString.length];
			for(int i = 0; i < splitString.length; i++) {
				returnArray[i] = Integer.parseInt(splitString[i]);
			}
			return returnArray;
		} else {
			return null;
		}
	}
	
}
