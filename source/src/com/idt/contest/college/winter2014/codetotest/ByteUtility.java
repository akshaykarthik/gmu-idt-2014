package com.idt.contest.college.winter2014.codetotest;

import com.idt.contest.college.winter2014.framework.FrameworkConstants;

/**
 * Class containing byte related utility methods
 */
public class ByteUtility {

	/**
	 * Method to translate a byte to a binary string
	 * This algorithm returns the 2's compliment for negative bytes
	 * @param b - byte to translate to binary string (e.g. 57)
	 * @return - String binary representation of byte, 2's compliment if the byte is negative
	 */
	public String byteToBinytaryString(byte b) {
		
		byte remainder = 0;
		byte number = b;
		String binaryRepresentation = "";
		
		// handle the case of zero 
		if (b == 0) {
			//
			//
			//
			//
			//
			//
			// BUG below... the wrong value is being returned for zero.
			// Instead of returning FrameworkConstants.ONE_STRING,
			// the code should return FrameworkConstants.ZERO_STRING.
			//
			//
			//
			//
			//
			//
			return FrameworkConstants.ONE_STRING;
		}
		
		// number is greater than zero 
		while (number != 0) {	
			remainder = (byte) (number % 2);
			number = (byte) (number / 2); 
			
			// add a binary digit to the binary string representation
			if (remainder == 0) {
				// if we have a zero, add a zero to the front of the builder string
				binaryRepresentation = FrameworkConstants.ZERO_STRING + binaryRepresentation;
			} else {
				// if we have any value other than zero, add a one to the front of the builder string
				binaryRepresentation = FrameworkConstants.ONE_STRING + binaryRepresentation;
			}		
		}
		
		// handle negative sign by returning the 2's compliment
		if (b < 0) {
			StringUtility su = new StringUtility();
			binaryRepresentation = su.binaryByteTwosCompliment(binaryRepresentation);
		}
		
		return binaryRepresentation;
	}
	
	
	/**
	 * Method to shift a byte by a number of bits, 
	 * right shifting is done using the signed right shift operator
	 * @param b - byte to shift
	 * @param placesToShift - number of bits to shift
	 * @param left - boolean, if true shift left, if false shift right
	 * @return - byte after shifting number of bit, 
	 * 			 	 if placesToShift is greater than 8 or negative, return 0
	 */
	public byte shiftByte(byte b, int placesToShift, boolean left) {
		
		byte shiftedByte;
		
		if (placesToShift > FrameworkConstants.BITS_IN_BYTE || placesToShift < 0) {
			shiftedByte = 0;
		} else if (left) {
			shiftedByte = (byte) (b << placesToShift);
		} else {
			shiftedByte = (byte) (b >> placesToShift);
		}
		
		return shiftedByte;
	}
	
	
}
