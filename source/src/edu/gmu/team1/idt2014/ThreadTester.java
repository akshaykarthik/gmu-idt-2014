package edu.gmu.team1.idt2014;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Used to track the state of an object.
 * */
public class ThreadTester {
	private ConcurrentHashMap<String, Integer> states;
	private static ThreadTester tester;


	private ThreadTester(){
		this.states = new ConcurrentHashMap<String, Integer>();
	}

	private static ThreadTester getInstance(){
		if(tester==null) tester = new ThreadTester();
		return tester;
	}

	/**
	 * Gets the object name and increases the state. If name is not in the
	 * HashMap, then entry is created.
	 * 
	 * @param objectName - the tracking id/name of the object.
	 * */
	private static synchronized void incrementState(String objectName) {
		ThreadTester test = getInstance();
		Integer currentState = test.states.get(objectName);
		++currentState;
		test.states.put(objectName, (currentState));
	}

	/**
	 * Gets the object's state
	 * 
	 * @return the state the object is in.
	 * */
	public static synchronized int getState(String objectName) {
		return getInstance().states.get(objectName);
	}

	/**
	 * Creates object to track
	 * 
	 * @param objectName
	 *            - the tracking id/name of the object.
	 * */
	public static synchronized void createStateTracker(String objectName) {
		ThreadTester test = getInstance();
		if (test.states.containsKey(objectName))
			return;
		test.states.put(objectName, 1);
	}
	/**
	 * Creates object to track with given state. It overrides the previous entry
	 * 
	 * @param objectName- the tracking id/name of the object.
	 * @param state - the state the object should start with
	 * */
	public static synchronized void createStateTrackerOverride(String objectName, int state) {
		ThreadTester test = getInstance();
		test.states.put(objectName, state);
	}

	/**
	 * Compares the local state (Thread's) with the global state (actual state)
	 * 
	 * @param objectName- the tracking id/name of the object.
	 * @param state- the state the thread thinks the object is in
	 * @param note- note
	 * */
	public static synchronized void compareWithGlobalState(String objectName, int state, String note) {
		int globalState = getState(objectName);
		if (globalState != state) {
			log(note+" Race Condition Has Occurred");
		}
		incrementState(objectName);
	}

	/**
	 * Compares the two states.
	 * 
	 * @param state- a state
	 * @param state2- a state
	 * @param note- note
	 * */
	public static synchronized void compareStates(int state, int state2, String note){
		if(state!=state2){
			log(note+" Race Condition Has Occurred");
		}
	}
	
	private static synchronized void log(String message){
		GMUT.getReportWriter().log(message);
	}

}
