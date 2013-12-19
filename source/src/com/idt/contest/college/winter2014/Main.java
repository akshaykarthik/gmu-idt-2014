package com.idt.contest.college.winter2014;

import com.idt.contest.college.winter2014.framework.BatchMode;
import com.idt.contest.college.winter2014.framework.MenuMode;
import com.idt.contest.college.winter2014.framework.Mode;

/**
 * Main class containing main method for com.idt.contest.winter2014 framework
 */
public class Main {

	/**
	 * Entry point for the com.idt.contest.winter2014 framework  
	 * @param args - String arguments supplied as command line arguments
	 * 				 This application takes a file path to a batch script as single optional argument
	 */
	public static void main(String[] args) {
			
		Mode mode;
		
		// set up mode depending on command line argument
		if (args != null && args.length > 0) {
			// a file to process has been supplied, start the application in batch mode
			mode = new BatchMode(args[0]);
		} else {
			// a file to process has not been supplied, start the application in menu mode
			mode = new MenuMode();
		}
		
		mode.execute();
	}
}
