package com.idt.contest.college.winter2014.codetotest;

import java.util.ArrayList;
import java.util.List;

import com.idt.contest.college.winter2014.framework.FrameworkConstants;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.predicates.ArrayEquals;
import edu.gmu.team1.idt2014.predicates.Equals;
import edu.gmu.team1.idt2014.predicates.MultiEquals;


/**
 * Class containing math related utility methods 
 */
public class MathUtility {
	
	
	/**
	 * Method that checks if a number is even
	 * @param numToCheck - the number to check 
	 * @return boolean - true if the number is even, false if the number is odd
	 */
	public boolean isEven(int numToCheck) {
		GMUT.addTest()
			.branches(2)
			.testNote("test evens", new Equals(10), new Equals(true))
			.testNote("test odds", new Equals(11), new Equals(false))
			.build();
		// divide the number by 2 and no remainder exists, the number is even
		if (numToCheck % 2 == 0) {
			GMUT.test(true, 1, numToCheck);
			return true;
		} else {
			GMUT.test(false, 2,  numToCheck);
			return false;
		}
	}
	
	
	/**
	 * Method to compute the hypotenus of a triangle given two sides
	 * @param a - length of first side of triangle
	 * @param b - length of second side of triangle
	 * @return - integer value of the hypotenus of the triangle
	 */
	public double hypotenus(double a, double b) {
		GMUT.addTest()
			.branches(1)
			.test(new MultiEquals(3.0, 4.0), new Equals(5.0))
			.test(new MultiEquals(4.0, 3.0), new Equals(5.0))
			.test(new MultiEquals(5.0, 12.0), new Equals(13.0))
			.test(new MultiEquals(12.0, 5.0), new Equals(13.0))
			.build();
		double aSquared = a * a;
		double bSquared = b * b;
		double c = Math.sqrt(aSquared + bSquared);
		GMUT.test(c, 1, a, b);
		return c;
		
	}
	
	
	/**
	 * Method that will return the prime factors of a given number
	 * @param n - number you wish to derive the prime factors of
	 * @return - array of ints that represent the prime factors
	 */
	public int[] primeFactor(int number) {
		GMUT.addTest()
			.branches(1)
			.test(new Equals(2), new ArrayEquals(2))
			.test(new Equals(4), new ArrayEquals(2, 2))
			.test(new Equals(5), new ArrayEquals(5))
			.test(new Equals(12), new ArrayEquals(2, 2, 3))
			.build();
		int initialNumber = number;
		
		List<Integer> factors = new ArrayList<Integer>();
		int divider = 2;
		
		// determine the prime factors of the given number
		while (divider * divider <= number) {
			if (number % divider == 0) {
				factors.add(divider);
				number /= divider;
			} else {
				divider++;
			}
		}
		
		if (number > 1) {
			factors.add(number);
		}
		
		// convert Integer List to int array
		int[] returnArray = new int[factors.size()];
		for (int i = 0; i < factors.size(); i++) {
			returnArray[i] = factors.get(i);
		}
		
		// return int array of prime factors
		GMUT.test(returnArray, 1, initialNumber);
		return returnArray;
	}
	
	
	/**
	 * Method to multiple to simple binomials using the FOIL technique
	 * similar to (2x + 6) * (7x - 10)    
	 * @param x1, the multiplier of the first x (2 in the example above)
	 * @param ones1, the first ones value (6 in the example above)
	 * @param x2, the multiplier of the second x (7 in the example above)
	 * @param ones2, the second ones value (-10 in the example above)
	 * @return - String representation of binomial product
	 */
	public String multiplySimpleBinomials(short x1, short ones1, short x2, short ones2) {
		GMUT.addTest()
			.branches(1)
				.test(new MultiEquals((short) 1, (short) 3, (short) 1,
						(short) 2), new Equals("x^2 + 5x + 6"))
				.test(new MultiEquals((short) 1, (short) -4, (short) 1,
						(short) -3), new Equals("x^2 - 7x + 12"))
				.test(new MultiEquals((short) 2, (short) 2, (short) -2,
						(short) 2), new Equals("-4x^2 + 4"))
				.test(new MultiEquals((short) -4, (short) 1, (short) 3,
						(short) -1), new Equals("-12x^2 + 7x - 1"))
				.test(new MultiEquals((short) -11, (short) -2, (short) -8,
						(short) -3), new Equals("88x^2 + 49x + 6"))
			.build();
		boolean notTheFirst = false;
		String binomialResult = "";
		
		// follow foil procedure and multiply all combinations
		short first, outer, inner, last;
		first = (short) (x1 * x2);
		outer = (short) (x1 * ones2);
		inner = (short) (ones1 * x2);
		last = (short) (ones1 * ones2);
		
		// assemble binomial string
		if (first != 0) {
			if (Math.abs(first) != 1) {
				binomialResult += first;
			}
			binomialResult += "x^2 ";
			notTheFirst = true;
		}
		
		if (outer + inner != 0) {
			if (outer + inner > 0 && notTheFirst) {
				binomialResult += FrameworkConstants.POSITIVE_SIGN + " ";
			} else if (notTheFirst){
				binomialResult += FrameworkConstants.NEGATIVE_SIGN + " ";
			}
			
			if (Math.abs(outer + inner) != 1) {
				binomialResult += Math.abs(outer + inner);
			}
			binomialResult += "x ";
			notTheFirst = true;
		}
		
		if (last !=0) {
			//
			//
			//
			//
			//
			//
			// BUG below... the less than sign in the if statement should be a 
			// greater than sign. Because of this switch, a positive sign will 
			// show up in front of the ones place where a negative sign should 
			// show up, and vice versa. 
			//
			//
			//
			//
			//
			//
			if (last < 0 && notTheFirst) {
				binomialResult += FrameworkConstants.POSITIVE_SIGN + " ";
			} else if (notTheFirst){
				binomialResult += FrameworkConstants.NEGATIVE_SIGN + " ";
			}
			binomialResult += Math.abs(last);
		}
		
		GMUT.test(binomialResult, 1, x1, ones1, x2, ones2);
		return binomialResult;
	}
	
	
	/**
	 * Method used to do simple currency conversions
	 * @param amount - float amount of money you start with
	 * @param rate - float exchange rate from start currency to finish currency
	 * @return - float amount of currency you finish with
	 */
	public float convertCurrency(float amount, float rate) {
		GMUT.addTest()
			.branches(3)
			.test(new MultiEquals(1.0f, 0.61f), new Equals(0.61f))
			.test(new MultiEquals(100.0f, 0.73f), new Equals(73f))
			.build();
		// if the amount is zero, the result will be zero
		// if the rate is zero, the result will be zero
		if (amount == 0 || rate == 0) {
			GMUT.test(0, 1, amount, rate);
			return 0;
		// if the rate is one, the amount will not be transformed
		} else if (rate == 1) {
			GMUT.test(amount, 2, amount, rate);
			return amount;
		// otherwise we multiply the amount by the exchange rate
		} else {
			GMUT.test(amount * rate, 3, amount, rate);
			return amount * rate;
		}
	}
	
}

