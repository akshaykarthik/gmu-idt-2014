package edu.gmu.team1.idt2014.test.tests;

import java.io.File;
import java.util.Scanner;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.reportwriters.ConsoleReportWriter;
import edu.gmu.team1.idt2014.reportwriters.FileReportWriter;
import edu.gmu.team1.idt2014.reportwriters.StringReportWriter;

public class APITest_Config extends TypeTest {
	@Override
	public void execute() {		
		System.out.println("Disable/Enable Test\n");

		System.out.println("Disabling GMUT");
		GMUT.disable();
		System.out.println("GMUT enabled? "+GMUT.isEnabled());

		System.out.println("Enabling GMUT");
		GMUT.enable();
		System.out.println("GMUT enabled? "+GMUT.isEnabled());

		System.out.println("\nReportWriter Configs");
		System.out.println("\nFileReportWriter Test");
		//Delete any File.txt (if it exists)
		File f = new File("File.txt");
		f.delete();
		
		System.out.println("Setting ReportWriter to FileReportWriter");
		GMUT.setReportWriter(new FileReportWriter("File.txt"));
		System.out.println("Using FileReportWriter to log \"Log Message\" to File.txt");
		GMUT.getReportWriter().log("Log Message");
		
		System.out.println("Printing Logs From File.txt");
		f = new File("File.txt");
		if(f.exists()){
			try{
				Scanner s  = new Scanner(f);
				while(s.hasNextLine()){
					System.out.println(s.nextLine());
				}
				s.close();
			}catch(Exception e){};
		}
		
		System.out.println("\nConsoleReporterWriter Test");
		System.out.println("Setting ReportWriter to ConsoleReportWriter");
		GMUT.setReportWriter(new ConsoleReportWriter());
		GMUT.getReportWriter().log("Log Message using the ConsoleReportWriter");
		
		System.out.println("\nStringReportWriter Test");
		System.out.println("Setting ReportWriter to StringReportWriter");
		GMUT.setReportWriter(new StringReportWriter());
		((StringReportWriter)GMUT.getReportWriter()).reset();
		GMUT.getReportWriter().log("Log Message using the StringReportWriter");
		System.out.println("The value of String in StringReportWriter is:");
		System.out.println(((StringReportWriter)GMUT.getReportWriter()).report);
		
		
		
	}	
}