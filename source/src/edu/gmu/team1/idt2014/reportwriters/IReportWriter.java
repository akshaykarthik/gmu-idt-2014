package edu.gmu.team1.idt2014.reportwriters;


/**
 * This Interface documents all that is necessary to create your own
 * Report writer. After implementing this interface. You can simply
 * use <code>GMUT.tester().setReportWriter(<Your Writer>);</code>
 * <p>
 * This allows for many different report writers and formats to be utilized.
 * Be warned however that LogViewer expects the Default ReportWriter format. 
 */
public interface IReportWriter {

	void logTest(String tclass, String tmethod, boolean passed,int currentBranch, int allBranches, Object output,
			Object[] inputs, String string);

}
