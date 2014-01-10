package edu.gmu.team1.idt2014;
import java.util.HashMap;

public class ThreadTester {
	static HashMap<String,Integer> states = new HashMap<String,Integer>();

	public static synchronized void incrementState(String objectName){
		Integer currentState = states.get(objectName);
		if(currentState==null){
			states.put(objectName, (1));
		}
		else{
			states.put(objectName, (++currentState));
		}

	}
	public static synchronized int getState(String objectName){
		return states.get(objectName);
	}

	public static synchronized void createStateTracker(String objectName){
		if(states.containsKey(objectName)) return;
		states.put(objectName, 1);
	}

	public static synchronized void compareWithGlobalState(String objectName,int state, String note){
		if(!states.containsKey(objectName)) {
			System.out.println("THIS VARIABLE DOES NOT EXIST");
			return;
		}
		int globalState = states.get(objectName);
		if(globalState!=state){
			//Sriram-TODO: log it as Race Condition
			System.out.println(note+" **********RACE CONDITION********* because global state is "
			+globalState+" Thread state is "+state);
		}
	}

}
