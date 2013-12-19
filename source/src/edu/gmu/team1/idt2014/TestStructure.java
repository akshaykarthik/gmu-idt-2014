package edu.gmu.team1.idt2014;

import java.util.concurrent.ConcurrentHashMap;

import edu.gmu.team1.idt2014.predicates.Predicate;

public class TestStructure {
	public TestStructure(){
		testMap = new ConcurrentHashMap<Predicate, Predicate>();
	}
	public int branches = 1;
	public ConcurrentHashMap<Predicate, Predicate> testMap;

}
