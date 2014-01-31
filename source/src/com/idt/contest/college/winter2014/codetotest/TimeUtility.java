package com.idt.contest.college.winter2014.codetotest;

import com.idt.contest.college.winter2014.framework.FrameworkConstants;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.predicates.Equals;
import edu.gmu.team1.idt2014.predicates.EstimateEquals;

/**
 * Class containing time related utility methods 
 */
public class TimeUtility {


	/**
	 * integer array to hold days within each month conversion
	 */
	static int[] calendar = {0,31,28,31,30,31,30,31,31,30,31,30,31};

	/**
	 * double representation of a posix day in milliseconds
	 */
	static double posixDay = 86400.0f;


	/**
	 * Method that takes a time in H:M:S.s format and converts it to milliseconds posix time
	 * @param HMSString - String representation of time in H:M:S.s (fractional seconds are optional)
	 * @return - double representation of number of milliseconds in HMS time (posix time), -1 if the time value is invalid
	 */
	public double HMSStringTimeToPosix(String HMS) {
		double fraction = 0.0;
		int hours, minutes, seconds;
		StringUtility su = new StringUtility();

		//I am saying that there are three branches - null/0 case; everything works case; everything fails case
		GMUT.addTest()
		.branches(3)
		.testNote("null case", new Equals(null), new Equals(0.0))
		.testNote("zero case", new Equals("0.0"), new Equals(0.0))
		.testNote("Number format exception", new Equals("A:15:25"), new Equals(FrameworkConstants.INVALID_VALUE))
		.test(new Equals("5:15:25"),new Equals(18925000.0))
		.test(new Equals("0:00:01.1"),new EstimateEquals(1100,0))
		.test(new Equals("23:59:59.999"),new EstimateEquals(86399999,2))
		.build();

		// handle null and zero time
		if (HMS == null ||  HMS.equals("0.0")) {
			GMUT.test(0.0, 1, HMS);
			return 0.0;
		}

		// parse the input string into H M S . SS
		try{ 
			// parse hours
			hours = Integer.parseInt(HMS.substring(0, su.indexOfFirstSpecificChar(HMS,':')));

			// parse minutes
			minutes = Integer.parseInt(HMS.substring(su.indexOfFirstSpecificChar(HMS,':')+1, su.indexOfLastSpecificChar(HMS,':')));

			// parse seconds
			if (HMS.contains(".")) {
				seconds = Integer.parseInt(HMS.substring(su.indexOfLastSpecificChar(HMS, ':')+1, su.indexOfLastSpecificChar(HMS, '.')));
			} else {
				seconds = Integer.parseInt(HMS.substring(su.indexOfLastSpecificChar(HMS,':')+1));
			}

			// parse fraction of seconds if necessary
			if (HMS.contains(".")) {
				// no longer requiring milliseconds although they are allowed
				String dec = "0." + HMS.substring(su.indexOfFirstSpecificChar(HMS, '.')+1);
				fraction   = Float.parseFloat(dec);
			}
			
			GMUT.test((((hours * 3600.0) + (minutes * 60.0) + seconds + fraction) * 1000), 2, HMS);
			// convert to posix time, which is total milliseconds of this time
			return (((hours * 3600.0) + (minutes * 60.0) + seconds + fraction) * 1000);

		} catch (NumberFormatException e) {
			// instead of throwing an error, this method will return -1 for any non valid date
			GMUT.test(-1, 3, HMS);
			return FrameworkConstants.INVALID_VALUE;
		}
	}

	/**
	 * Method that takes a date in yyyy/mm/dd format and converts it to milliseconds posix time
	 * @param input_date - String representation of date in yyyy/mm/dd format
	 * @return - double millisecond posix representation of date, -1 if the date value is invalid
	 */
	public double dateToPosix(String input_date) {
		int    year, month, day;
		int    total_days, num_leap_year;
		double seconds = 0;
		
		GMUT.addTest()
		.branches(1)
		.test(new Equals("2013/12/16"),new Equals(1387152000.0))
		.test(new Equals("1970/01/01"),new Equals(0.0))
		.test(new Equals("1970/02/02"),new Equals(2764800.0))
		.test(new Equals("1970/AB/02"), new Equals(-1.0))
		.build();
		
		try {
			// parse year, month, and day from input_date
			year  = Integer.parseInt(input_date.substring(0,4));
			month = Integer.parseInt(input_date.substring(5,7));
			day   = Integer.parseInt(input_date.substring(8,10));

			// compute number of days in the current year
			if (month == 1)
			{
				total_days = day;
			}
			else if (month == 2)
			{
				//
				//
				//
				//
				//
				//
				// BUG below... the wrong month has been selected in the calendar array for 
				// February. Instead of calendar[2], it should be calendar[1]. If you are
				// in the month of February, the entire month of January has occurred, so
				// the total days should include January's length, not February's length.
				//
				//
				//
				//
				//
				//
				total_days = day + calendar[2];
			}
			else
			{
				if (year % 4 == 0)
				{
					total_days = day + calendar[1] + calendar[2] + 1;
				}
				else
				{
					total_days = day + calendar[1] + calendar[2];
				}

				int month_cnt = 3;

				while (month > month_cnt)
				{
					total_days += calendar[month_cnt];
					month_cnt++;
				}
			}

			// convert total number of days to seconds since epoch
			num_leap_year = (year - 1969)/4;
			total_days = total_days - 1 + (year -1970)*365 + num_leap_year;
			seconds = ((double)(total_days)) * posixDay;
//			GMUT.test(seconds, 2, input_date);
		} catch (NumberFormatException e) {
			// instead of throwing an error, this method will return -1 for any non valid date
			seconds = -1.0;
		}
		
		GMUT.test(seconds, 1, input_date);
		return seconds;
	}

}
