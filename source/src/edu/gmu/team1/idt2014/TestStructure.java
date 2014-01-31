package edu.gmu.team1.idt2014;

import java.util.HashMap;

import edu.gmu.team1.idt2014.predicates.Predicate;

/**
 * A testStructure is a key object that stores a given test.
 * A test is defined as a {@link String} name, <br>
 * The number of branches {@link Integer} Branches <br>
 * A map relating a given input predicate to an output predicate testMap; <br>
 * A map relating a given input predicate to a note about that test notesMap; <br>
 * 
 */
public class TestStructure {
	private String name;
	private int branches = 1;
	private HashMap<Predicate, Predicate> testMap;
	private HashMap<Predicate, String> notesMap;
	public TestStructure(String tName){
		this.name = tName;
		this.testMap = new HashMap<Predicate, Predicate>();
		this.notesMap = new HashMap<Predicate, String>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the branches
	 */
	public int getBranches() {
		return branches;
	}

	/**
	 * @param branches the branches to set
	 */
	public void setBranches(int branches) {
		this.branches = branches;
	}

	/**
	 * @return the testMap
	 */
	public HashMap<Predicate, Predicate> getTestMap() {
		return testMap;
	}

	/**
	 * @param testMap the testMap to set
	 */
	public void setTestMap(HashMap<Predicate, Predicate> testMap) {
		this.testMap = testMap;
	}


	/**
	 * @return the notesMap
	 */
	public HashMap<Predicate, String> getNotesMap() {
		return notesMap;
	}

	/**
	 * @param notesMap the notesMap to set
	 */
	public void setNotesMap(HashMap<Predicate, String> notesMap) {
		this.notesMap = notesMap;
	}
}
