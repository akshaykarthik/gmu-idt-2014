package edu.gmu.team1.idt2014.reportwriters;

public class ConsoleReportWriter extends AbstractReportWriter {

	@Override
	public void logTest(String tclass, String tmethod, boolean passed,
			int currentBranch, int allBranches, Object output, Object[] inputs,
			String notes) {
		String format = "%s[%s][c:%s][m:%s][b:%s]%s[o:%s][n:%s]";
		String content = String.format(format,
				getDateTag(),
				(passed ? "pass" : "fail"),
				tclass,
				tmethod,
				currentBranch + "/" + allBranches,
				getInputTag(inputs),
				getOutputTag(output).replaceAll("\n", "\\n").replaceAll("\r", "\\r"),
				(notes == null) ? "" : notes);
		System.out.println(content);

	}

}
