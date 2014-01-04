package edu.gmu.team1.idt2014;

import java.util.concurrent.ConcurrentHashMap;

import edu.gmu.team1.idt2014.predicates.Predicate;

public class TestStructure {
	public String name;
	public int branches = 1;
	public ConcurrentHashMap<Predicate, Predicate> testMap;
	public ConcurrentHashMap<Predicate, String> notesMap;
	
	public TestStructure(String tName){
		this.name = tName;
		this.testMap = new ConcurrentHashMap<Predicate, Predicate>();
		this.notesMap = new ConcurrentHashMap<Predicate, String>();
	}
}
