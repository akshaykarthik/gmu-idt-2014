package edu.gmu.team1.idt2014.test;

public abstract class InternalTest {
	public abstract void execute();
	
	public void log(boolean passed, String name){
		System.out.println("\t" + (passed?"pass":"fail") + " |\t" + name);
	}
	
	public void note(String inote){
		System.out.println("\t~ " + inote);
	}

	public void run() {
		announce();
		execute();
		end();
		
	}

	private void announce() {
		System.out.println("====================================");
		System.out.println("start:\t" + getClass().getSimpleName());

	}
	
	private void end() {

		System.out.println("  end:\t" + getClass().getSimpleName());
		System.out.println("------------------------------------");
	}


}
