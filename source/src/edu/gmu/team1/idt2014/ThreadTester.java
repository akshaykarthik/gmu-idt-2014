package edu.gmu.team1.idt2014;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Used to track the state of an object.
 * */
public class ThreadTester {
	private static ConcurrentHashMap<String,Integer> states = new ConcurrentHashMap<String,Integer>();

	/**
	 * Gets the object name and increases the state. 
	 * If name is not in the HashMap, then entry is created.
	 * @param objectName - the tracking id/name of the object.
	 * */
	public  synchronized void incrementState(String objectName){
		Integer currentState = states.get(objectName);
		if(currentState==null){
			states.put(objectName, (1));
		}
		else{
			++currentState;
			System.out.println("Object Name "+objectName+" Current State "+ currentState);
			states.put(objectName, (currentState));
		}
	}

	/**
	 * Gets the object's state
	 * @return the state the object is in.
	 * */
	public  int getState(String objectName){
		return states.get(objectName);
	}

	/**
	 * Creates object to track
	 * @param objectName - the tracking id/name of the object.
	 * */
	public  synchronized void createStateTracker(String objectName){
		if(states.containsKey(objectName)) return;
		states.put(objectName, 1);
	}
	/**
	 * Compares the local state (Thread's) with the global state (actual state)
	 * @param objectName - the tracking id/name of the object.
	 * @param state - the state the thread thinks the object is in
	 * @param note - note
	 * */
	public synchronized void compareWithGlobalState(String objectName,int state, String note){
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
