package edu.gmu.team1.idt2014.test.tests;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.predicates.Equals;
import edu.gmu.team1.idt2014.reportwriters.StringReportWriter;

public class TypeTest_All extends TypeTest {

	@Override
	public void execute() {
		note("TypeTest_All tests the underlying testing system");
		note("TypeTest_<test> test the predicates for each individual type.");
		note("The underlying logic is if the system works, responsibility for type handling passes to the predicates");
		typeTestAll((short) 5);
		typeTestAll((int) 5);
		typeTestAll((long) 5);
		typeTestAll((float) 5);
		typeTestAll((double) 5);
		typeTestAll((boolean) true);
		typeTestAll((byte) 0x5);
		typeTestAll('c');
		typeTestAll("s");

		String[] actualReport = ((StringReportWriter) GMUT.getReportWriter()).report
				.split("\r\n");
		for (String reportLine : actualReport) {
			if (reportLine.contains("typetest"))
				log(true, reportLine);
		}
	}

	public String typeTestAll(Object input) {
		GMUT.addTest()
				.testNote("short typetest", new Equals((short) 5),
						new Equals("5"))
				.testNote("integer typetest", new Equals((int) 5),
						new Equals("5"))
				.testNote("long typetest", new Equals((long) 5),
						new Equals("5"))
				.testNote("float typetest", new Equals((float) 5),
						new Equals("5.0"))
				.testNote("double typetest", new Equals((double) 5),
						new Equals("5.0"))
				.testNote("boolean typetest", new Equals(true),
						new Equals("true"))
				.testNote("byte typetest", new Equals((byte) 5),
						new Equals("5"))
				.testNote("char typetest", new Equals('c'), new Equals("c"))
				.testNote("String typetest", new Equals("s"), new Equals("s"))
				.build();

		GMUT.test(input.toString(), 1, input);
		return input.toString();
	}

}
