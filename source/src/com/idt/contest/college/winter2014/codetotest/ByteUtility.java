package com.idt.contest.college.winter2014.codetotest;

import com.idt.contest.college.winter2014.framework.FrameworkConstants;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.predicates.Equals;

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
		
		GMUT.addTest()
			.branches(2)
			.test(new Equals((byte)127), new Equals("1111111"))
			.test(new Equals((byte)-128), new Equals("10000000"))
			.test(new Equals((byte)57), new Equals("111001"))
			.test(new Equals((byte)0), new Equals(FrameworkConstants.ZERO_STRING))
			.test(new Equals((byte)-57), new Equals("11000111"))
			.build();
		byte orig = b;
		
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
			
			GMUT.test(FrameworkConstants.ONE_STRING, 1, b);
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
		
		GMUT.test(binaryRepresentation, 2, orig);
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
