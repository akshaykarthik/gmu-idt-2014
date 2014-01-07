package edu.gmu.team1.idt2014;
import java.util.HashMap;

//Sriram-TODO: make this work for more than two threads
//Sriram-TODO: remember to make sure that

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


}
