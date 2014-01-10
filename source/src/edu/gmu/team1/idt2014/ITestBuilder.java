package edu.gmu.team1.idt2014;

import edu.gmu.team1.idt2014.predicates.Predicate;

/**
 * An {@link ITestBuilder} is a utility class that makes it very easy to create
 * a build test structures. By default, test builder assumes <code>1</code>
 * branch per method and no note per test. <br>
 * 
 * 
 * Test builder returns itself in each method except build as this allows calls
 * to be chained simply. for example <br>
 * <br>
 * <code>
 * TestBuilder<br>
 * .test(...)<br>
 * .test(...)<br>
 * .test(...)<br>
 * .build();<br>
 * </code>
 * 
 * @see MainTestBuilder
 * @see NullTestBuilder
 */
public interface ITestBuilder {

	/**
	 * This sets the number of branches that that method should test. <br>
	 * This code overwrites the number of branches previously set so it should
	 * only really be called once.
	 * 
	 * @param branches
	 * @return
	 */
	public ITestBuilder branches(int br);

	/**
	 * This creates a test case that matches predicate b for input and c for
	 * output. <br>
	 * <br>
	 * <code>
	 * untested = !b<br>
	 * pass = b && c<br>
	 * fail = b && !c<br>
	 * </code>
	 * 
	 * @param b
	 * @param c
	 * @return
	 */
	public ITestBuilder test(Predicate b, Predicate c);

	/**
	 * This is the same as {@link #test(Predicate, Predicate)} except that it
	 * associates a note with the input predicate.
	 * 
	 * @param note
	 * @param b
	 * @param c
	 * @return
	 */
	public ITestBuilder testNote(String note, Predicate b, Predicate c);

	/**
	 * This builds the test structure and inserts the structure into the GMUT
	 * singleton instance.
	 * 
	 */
	public void build();

	public String getName();

}