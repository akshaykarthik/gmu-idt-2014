package edu.gmu.team1.idt2014.reportwriters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This implementation of AbstractReportWriter writes the log to a file. By
 * default, it writes to a "log.txt" file in the directory of the application
 * however by changing the variables FILENAME, one can easily change the
 * location of the log file.
 */
public class FileReportWriter extends AbstractReportWriter {
	private static final String DEFAULT_FILENAME = "log.txt";
	private static String FILENAME = DEFAULT_FILENAME;

	public FileReportWriter() {

	}

	public FileReportWriter(String fileName) {
		FILENAME = fileName;
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
				String format = "%s[%s][c:%s][m:%s][b:%s]%s[o:%s][n:%s]";
				String content = String.format(format, getDateTag(),
						(passed ? "pass" : "fail"), tclass, tmethod,
						currentBranch + "/" + allBranches, getInputTag(inputs),
						getOutputTag(output).replaceAll("\n", "\\n")
						.replaceAll("\r", "\\r"), (notes == null) ? ""
								: notes);

				BufferedWriter oWriter = new BufferedWriter(new FileWriter(
						FILENAME, true));
				oWriter.write(content + "\r\n");
				oWriter.close();
			}

		} catch (IOException oException) {
			throw new IllegalArgumentException(
					"Error appending/File cannot be written: \n" + FILENAME);
		}
	}

	@Override
	public void log(String message) {
		try {

			File oFile = new File(FILENAME);
			if (!oFile.exists()) {
				oFile.createNewFile();
			}
			if (oFile.canWrite()) {
				BufferedWriter oWriter = new BufferedWriter(new FileWriter(
						FILENAME, true));
				oWriter.write(message + "\r\n");
				oWriter.close();
			}
		}catch (IOException oException) {
			throw new IllegalArgumentException(
					"Error appending/File cannot be written: \n" + FILENAME);
		}

	}

}


