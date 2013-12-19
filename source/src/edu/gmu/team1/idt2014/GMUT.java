package edu.gmu.team1.idt2014;

public class GMUT extends Thread{
	private ReportWriter _report;
	
	private GMUT() {
		_report = new ReportWriter();
		this.start();
	}
	
	private static class GMUTHolder {
		private static final GMUT INSTANCE = new GMUT();
	}

	public static GMUT tester(){
		return GMUTHolder.INSTANCE;
	}
	
	public static GMUT getInstance() {
		return GMUTHolder.INSTANCE;
	}
	
	
}
