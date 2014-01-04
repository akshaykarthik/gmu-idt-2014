package com.idt.contest.college.winter2014.codetotest;

import com.idt.contest.college.winter2014.framework.FrameworkConstants;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.predicates.Equals;

/**
 * Class containing String related utility methods
 */
public class StringUtility {
	
	/**
	 * Method that counts the number of vowels in a String
	 * @param stringToCheck - String to count vowels in
	 * @return - int number of vowels in supplied String
	 */
	public int countVowels(String stringToCheck) {

		GMUT.addTest()
			.branches(2)
			.test(new Equals("aeiou"), new Equals(5))
			.test(new Equals("bcdfg"), new Equals(0))
			.test(new Equals("bcd fgh jkl mnp qrs tvw xyz"), new Equals(0))
			.test(new Equals("programming"), new Equals(3))
			.test(new Equals("This is a test of the emergency broadcast system"), new Equals(13))
			.test(new Equals("1234567890"), new Equals(0))
			.test(new Equals(""), new Equals(0))
			.test(new Equals(null), new Equals(0))
			.test(new Equals("hi"), new Equals(1))
			.build();
		
		int vowelCount = 0;
		char currentChar;
		//
		//
		//
		//
		//
		//
		//BUG below... the vowelArray contains zero instead of capital O.
		//
		//
		//
		//
		//
		//
		//
		char[] vowelArray = {'a','A','e','E','i','I','o','0','u','U'};
		
		// must check string for null before processing 
		if (stringToCheck == null) {
			GMUT.test(vowelCount, 1, stringToCheck);
			return vowelCount;
		}
		
		// iterate through each character of the string
		for (int i = 0; i < stringToCheck.length(); i++) {
			currentChar = stringToCheck.charAt(i);
			
			// check if the current character is in the vowelArray
			for (char vowel: vowelArray) {
				if (currentChar == vowel) {
					vowelCount++;
				}
			}
		}
		
		GMUT.test(vowelCount, 2, stringToCheck);
		return vowelCount;
	}
	
	
	/* 
	 * TODO: Should be done, but if someone could look over this one for me, I'd appreciate it.
	 */
	/**
	 * Method to return the 2's compliment of a binary String
	 * @param binaryString - binary string to convert to 2's compliment
	 * 						 binary string must be positive and only contain 1s and 0s
	 * @return - String representation 2's compliment of binary string
	 */
	public String binaryByteTwosCompliment(String binaryByteString) {
		
		GMUT.addTest()
			.branches(3)
			.test(new Equals("0"), new Equals("00000000"))
			.test(new Equals("1"), new Equals("11111111"))
			.test(new Equals("1111"), new Equals("11110001"))
			.test(new Equals("0200"), new Equals(FrameworkConstants.BINARY_REPRESENTATION_ERROR))
			.test(new Equals("0101 anything else 010101"), new Equals(FrameworkConstants.BINARY_REPRESENTATION_ERROR))
			.test(new Equals("00110011"), new Equals("11001101"))
			.test(new Equals("00000000"), new Equals("00000000"))
			.test(new Equals("01111111"), new Equals("10000001"))
			.build();
		
		String orig = binaryByteString;
	
		String binaryRepresentation = "";
		char currentChar;
		char tempDigit;
		int carryOver = 0;
		
		// if the binary byte string is null or empty, return an error string
		if (binaryByteString == null || binaryByteString.isEmpty()) {
			
			GMUT.test(FrameworkConstants.BINARY_REPRESENTATION_ERROR, 1, binaryByteString);
			return FrameworkConstants.BINARY_REPRESENTATION_ERROR;
		}
		
		// pad the binary string to be 8 characters long
		while (binaryByteString.length() < FrameworkConstants.BITS_IN_BYTE) {
			binaryByteString = FrameworkConstants.ZERO_STRING + binaryByteString;
		}
		
		// process binary string from back to front
		for (int i = binaryByteString.length()-1; i >= 0; i--) {
			currentChar = binaryByteString.charAt(i);
			
			// only handle strings that contain 1s or 0s
			if (currentChar == '0' || currentChar == '1') {
				
				// flip the digit
				if (currentChar == '0') {
					tempDigit = '1';
				} else {
					tempDigit = '0';
				}
				
				// if this is the first iteration or if carry over exists, add 1
				if (i == binaryByteString.length() - 1 || carryOver == 1) {
					if (tempDigit == '0') {
						tempDigit = '1';
						carryOver = 0;
					} else {
						tempDigit = '0';
						carryOver = 1;
					}
				}
				
				// add the determined bit to the binary representation of 2's compliment
				binaryRepresentation = tempDigit + binaryRepresentation;
				
			} else {
				// binary byte string contained a non 1 or 0 character, return an error string
				GMUT.test(FrameworkConstants.BINARY_REPRESENTATION_ERROR, 2, binaryByteString);
				return FrameworkConstants.BINARY_REPRESENTATION_ERROR;
			}
		}
		
		GMUT.test(formatBinaryByteString(binaryRepresentation), 3, orig);
		return formatBinaryByteString(binaryRepresentation);
	}
	
	
	/**
	 * Method to format a binary byte string to 8 characters
	 * @param binaryByteString - binary byte string to format
	 * @return - String version of binary byte string with 8 characters
	 */
	public String formatBinaryByteString(String binaryByteString) {
		
		//  handle null pointers by returning an error string
		if (binaryByteString == null) {
			return FrameworkConstants.BINARY_REPRESENTATION_ERROR;
		}
		
		if (binaryByteString != null && binaryByteString.length() < FrameworkConstants.BITS_IN_BYTE) {
			// pad the binary string to be 8 characters long
			while (binaryByteString.length() < FrameworkConstants.BITS_IN_BYTE) {
				binaryByteString = FrameworkConstants.ZERO_STRING + binaryByteString;
			}
		} else {
			// cut the binary string to be 8 characters long
			binaryByteString = binaryByteString.substring(binaryByteString.length() - FrameworkConstants.BITS_IN_BYTE);
		}
		
		return binaryByteString;
	}
	
	
	/**
	 * Method to find the index of the first location a specific character appears in a String
	 * @param stringToCheck - String to check for specific character
	 * @param charToLookFor - Specific character to look for inside of stringToCheck
	 * @return - int index of first instance of char found in string, -1 if char is never found
	 */
	public int indexOfFirstSpecificChar(String stringToCheck, char charToLookFor) {
		
		if (stringToCheck == null) {
			return FrameworkConstants.INVALID_VALUE;
		}
		
		for (int i = 0; i < stringToCheck.length(); i++) {
			if (stringToCheck.charAt(i) == charToLookFor) {
				return i;
			}
		}
		
		return FrameworkConstants.INVALID_VALUE;
	}
	
	
	/**
	 * Method to find the index of the last location a specific character appears in a String
	 * @param stringToCheck - String to check for specific character
	 * @param charToLookFor - Specific character to look for inside of stringToCheck
	 * @return - int index of last instance of char found in string, -1 if char is never found
	 */
	public int indexOfLastSpecificChar(String stringToCheck, char charToLookFor) {
		
		if (stringToCheck == null) {
			return FrameworkConstants.INVALID_VALUE;
		}
		
		for (int i = stringToCheck.length() - 1; i >= 0; i--) {
			if (stringToCheck.charAt(i) == charToLookFor) {
				return i;
			}
		}
		
		return FrameworkConstants.INVALID_VALUE;
	}
}
