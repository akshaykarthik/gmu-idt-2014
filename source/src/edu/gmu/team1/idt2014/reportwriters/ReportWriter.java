package edu.gmu.team1.idt2014.reportwriters;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ReportWriter implements IReportWriter {
	private static String FILENAME = "log.txt";

	public ReportWriter() {

	}

	/*
	 * ~ Report File Format ~ Lines starting with tilde should be ignored ~
	 * [date] - expect mm/dd/yyyy ~ [time] - expect hh:mm or hh:mm AM/PM ~
	 * [passed/failed] - expect passed/pass/fail/failed ~ [c:<CLASS NAME>] -
	 * expect a c: followed by the class name ~ [m:<METHOD NAME>] - expect a m:
	 * followed by the method name ~ [b:<BRANCH>/<BRANCHES>] - expect two
	 * integers, branch tested, number of branches ~ [i:<INPUTS>] - expect null
	 * or inputs, delimited by vertical bar ("|") ~ [o:<OUTPUT>] - expect null
	 * or output
	 * [12/19/2013][12:31][passed][c:edu.gmu.TestUtils][m:getClassName]
	 * [b:1/2][i:null][o:null]
	 * [12/19/2013][12:32][passed][c:edu.gmu.TestUtils][m
	 * :getClassName][b:1/2][i:null][o:null]
	 * [12/19/2013][12:33][failed][c:edu.gmu
	 * .TestUtils][m:getClassName][b:2/2][i:null][o:null]
	 * [12/19/2013][12:34][passed
	 * ][c:edu.gmu.TestUtils][m:getClassName][b:1/2][i:null][o:null]
	 */

	@Override
	public synchronized void logTest(String tclass, String tmethod,
			boolean passed, int currentBranch, int allBranches, Object output,
			Object[] inputs, String notes) {

		try {

			File oFile = new File(FILENAME);
			if (!oFile.exists()) {
				oFile.createNewFile();
			}
			if (oFile.canWrite()) {
				String content = getDateTag() + "["
						+ (passed ? "pass" : "fail") + "]" + "[c:" + tclass
						+ "]" + "[m:" + tmethod + "]" + "[b:" + currentBranch
						+ "/" + allBranches + "]" + getInputTag(inputs) + "[o:"
						+ output + "]" + "[n:" + (notes == null ? "" : notes)
						+ "]\r1\n";

				BufferedWriter oWriter = new BufferedWriter(new FileWriter(
						FILENAME, true));
				oWriter.write(content);
				oWriter.close();
			}

		} catch (IOException oException) {
			throw new IllegalArgumentException(
					"Error appending/File cannot be written: \n" + FILENAME);
		}
	}

	private String getDateTag() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		return "[" + dateString.substring(0, 10) + "]["
				+ dateString.substring(11) + "]";
	}

	private String getInputTag(Object[] inputs) {
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

		// for(int x= 0; x<inputs.length;x++){
		// if(inputs[x] instanceof String[]){
		// String arrayList = Arrays.toString((String[]) inputs[x]);
		// inputTag+=formatArrayString(arrayList);
		// }
		// else if(inputs[x] instanceof int[]){
		// String arrayList = Arrays.toString((int[]) inputs[x]);
		// inputTag+=formatArrayString(arrayList);
		// }
		// else if(inputs[x] instanceof double[]){
		// String arrayList = Arrays.toString((double[]) inputs[x]);
		// inputTag+=formatArrayString(arrayList);
		// }
		// else if(inputs[x] instanceof float[]){
		// String arrayList = Arrays.toString((float[]) inputs[x]);
		// inputTag+=formatArrayString(arrayList);
		// }
		// else if(inputs[x] instanceof byte[]){
		// String arrayList = Arrays.toString((byte[]) inputs[x]);
		// inputTag+=formatArrayString(arrayList);
		// }
		// else if(inputs[x] instanceof short[]){
		// String arrayList = Arrays.toString((short[]) inputs[x]);
		// inputTag+=formatArrayString(arrayList);
		// }
		// else if(inputs[x] instanceof boolean[]){
		// String arrayList = Arrays.toString((boolean[]) inputs[x]);
		// inputTag+=formatArrayString(arrayList);
		// }
		// else if(inputs[x] instanceof Object[]){
		// String arrayList = Arrays.toString((Object[]) inputs[x]);
		// inputTag+=formatArrayString(arrayList);
		// }
		// else{
		// inputTag+=(inputs[x]+";");
		// }
		// }
		inputTag += "]";
		return inputTag;
	}

	private static String formatArrayString(String array) {
		return array.replace("]", "").replace(",", ";").replace("[", "");
	}

}
