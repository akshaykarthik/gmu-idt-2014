package edu.gmu.team1.idt2014.predicates;


/**
 */
public class ArraySorted<T extends Comparable<T>> extends Predicate {
	//XXX: Broken
	@Override
	public boolean evaluate(Object... inputs) {
		try {
			@SuppressWarnings("unchecked")
			T[] arrayVal = (T[]) inputs[0];
			int n = arrayVal.length;

			for (int i = 0; i < n - 1; ++i)
				if (arrayVal[i].compareTo(arrayVal[i + 1]) > 0)
					return false;

		} catch (Exception ex) {
			return false;
		}
		return true;

	}

}
