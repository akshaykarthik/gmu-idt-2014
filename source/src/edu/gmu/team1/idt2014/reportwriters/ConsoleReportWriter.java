package edu.gmu.team1.idt2014.reportwriters;

/**
 * This implementation of AbstractReportWriter writes the log to the console. It
 * utilizes the exact same formatting as FileReportWriter and
 * StringReportWriter;
 */
public class ConsoleReportWriter extends AbstractReportWriter {

	@Override
	public void logTest(String tclass, String tmethod, boolean passed,
			int currentBranch, int allBranches, Object output, Object[] inputs,
			String notes) {
		String format = "%s[%s][c:%s][m:%s][b:%s]%s[o:%s][n:%s]";
		String ioutput = getOutputTag(output);
		ioutput = ioutput.replace("\r","{R}");
		ioutput = ioutput.replace("\n","{N}");
		String content = String.format(format, getDateTag(), (passed ? "pass"
				: "fail"), tclass, tmethod, currentBranch + "/" + allBranches,
				getInputTag(inputs), ioutput, (notes == null) ? "" : notes);
		System.out.println(content);

	}

	public void log(String message) {
		System.out.println("~" + message);
	}

}
