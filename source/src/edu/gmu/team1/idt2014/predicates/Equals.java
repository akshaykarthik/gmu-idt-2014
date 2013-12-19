package edu.gmu.team1.idt2014.predicates;

public class Equals extends Predicate {

	private Object initial;
	
	public Equals(Object input){
		initial = input;
	}
	
	@Override
	public boolean evaluate(Object ... inputs) {
		return initial.equals(inputs[0]);
	}

}
