package com.idt.contest.college.winter2014.codetotest;

import com.idt.contest.college.winter2014.framework.FrameworkConstants;

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

		// handle null and zero time
		if (HMS == null ||  HMS.equals("0.0")) {
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
	
			// convert to posix time, which is total milliseconds of this time
			return (((hours * 3600.0) + (minutes * 60.0) + seconds + fraction) * 1000);
			
		} catch (NumberFormatException e) {
			// instead of throwing an error, this method will return -1 for any non valid date
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
		
		} catch (NumberFormatException e) {
			// instead of throwing an error, this method will return -1 for any non valid date
			seconds = -1.0;
		}

		return seconds;
	}
	
}
