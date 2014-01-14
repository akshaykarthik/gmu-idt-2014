package edu.gmu.team1.idt2014.reportwriters;

/**
 * This implementation of AbstractReportWriter writes the log to a String object report.
 * It utilizes the exact same formatting as FileReportWriter and StringReportWriter; 
 *
 */
public class StringReportWriter extends AbstractReportWriter {

	public String report = "";
	
	
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
		report += content + "\r\n";
	}
	
	@Override
	public void reset(){
		report = "";
	}

	@Override
	public void log(String message) {
		report+=message +"\r\n";	
	}

}
