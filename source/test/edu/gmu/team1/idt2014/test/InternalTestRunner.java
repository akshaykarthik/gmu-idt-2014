package edu.gmu.team1.idt2014.test;

import java.util.ArrayList;
import java.util.List;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.reportwriters.StringReportWriter;
import edu.gmu.team1.idt2014.test.tests.APITest_Integration;
import edu.gmu.team1.idt2014.test.tests.TypeTest_Boolean;
import edu.gmu.team1.idt2014.test.tests.TypeTest_Byte;
import edu.gmu.team1.idt2014.test.tests.TypeTest_Char;
import edu.gmu.team1.idt2014.test.tests.TypeTest_Double;
import edu.gmu.team1.idt2014.test.tests.TypeTest_Float;
import edu.gmu.team1.idt2014.test.tests.TypeTest_Int;
import edu.gmu.team1.idt2014.test.tests.TypeTest_IntArr;
import edu.gmu.team1.idt2014.test.tests.TypeTest_Long;
import edu.gmu.team1.idt2014.test.tests.TypeTest_Short;
import edu.gmu.team1.idt2014.test.tests.TypeTest_String;

public class InternalTestRunner {
	public static void main(String[] args){
		StringReportWriter writer = new StringReportWriter();
		GMUT.setReportWriter(writer);
		
		InternalTest[] tests = new InternalTest[]{
			new APITest_Integration(),
			new TypeTest_Byte(),
			new TypeTest_Short(),
			new TypeTest_Int(),
			new TypeTest_Long(),
			new TypeTest_Float(),
			new TypeTest_Double(),
			new TypeTest_Boolean(),
			new TypeTest_Char(),
			new TypeTest_String(),
			new TypeTest_IntArr()
		};
		
		for (InternalTest intt : tests) {
			intt.run();
		}
		System.out.println("Test Log");
		System.out.println(writer.report);
		System.out.println("End Log");
	}
}
