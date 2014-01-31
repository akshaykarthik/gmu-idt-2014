package edu.gmu.team1.idt2014.predicates;

/**
 * This predicate evaluates fuzzy equality (i.e, two numbers equal within a
 * given number of decimals (5.60322 == 5.62 at 1 decimal precision)
 */
public class EstimateEquals implements Predicate {
	private double initial;
	private int rounding;

	/**
	 * @param input
	 *            - The value of predicate
	 * @param demicalRounding
	 *            - The number of decimals to round to.
	 */
	public EstimateEquals(double input, int decimalRounding) {
		initial = input;
		rounding = decimalRounding;
	}

	@Override
	public boolean evaluate(Object... inputs) {
		double input = ((Double) inputs[0]).doubleValue();
		double factor = Math.pow(10, rounding);
		double result = Math.round(input * factor) / factor;
		return result == initial;
	}
}
