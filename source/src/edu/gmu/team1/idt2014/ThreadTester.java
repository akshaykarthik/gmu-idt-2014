package edu.gmu.team1.idt2014;

public class ThreadTester {

	static boolean sync = false;
	static int count = 0;

	// Sriram-TODO: make this work for more than two threads
	// Sriram-TODO: remember to make sure that
	public static boolean getTrap() {
		return sync;
	}

	public static void setTrap(boolean x) {
		try {
			// Thread.sleep(10000);
			count++;
			if (count % 2 == 0) {
				if (x == true) {
					count = 0;
					sync = false;
					System.out
							.println("THIS METHOD IS NOT SYNCH AND A RACING CONDITION HAS OCCURED");
				} else {
					System.out.println("~RACING CONDITION DOES NOT OCCUR");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
