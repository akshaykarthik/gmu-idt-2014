package edu.gmu.team1.idt2014.reportwriters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


/**
 * This Interface documents all that is necessary to create your own
 * Report writer. After implementing this interface. You can simply
 * use <code>GMUT.tester().setReportWriter(<Your Writer>);</code>
 * <p>
 * This allows for many different report writers and formats to be utilized.
 * Be warned however that LogViewer expects the Default ReportWriter format. 
 */
public abstract class AbstractReportWriter {

	public abstract void logTest(String tclass, String tmethod, boolean passed,int currentBranch, int allBranches, Object output,
			Object[] inputs, String notes);

	protected String getDateTag() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		return "[" + dateString.substring(0, 10) + "]["
				+ dateString.substring(11) + "]";
	}

	protected String getInputTag(Object[] inputs) {
		if (inputs == null)
			return "[i:" + null + "]";
		String inputTag = "[i:";
		
		String arrayString = null;
		if (inputs.length > 1)
			arrayString = Arrays.deepToString(inputs);
		else if (inputs.length > 0)
			arrayString = inputs[0].toString();
		else
			arrayString = "";
		
		inputTag += formatArrayString(arrayString);

		inputTag += "]";
		return inputTag;
	}

	protected static String formatArrayString(String array) {
		return array.replace("]", "").replace(",", ";").replace("[", "");
	}
}
