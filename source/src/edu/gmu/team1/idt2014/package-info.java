/**
 * In math, a predicate is a function P such that for a given input X,
 * P(X) evaluates to true or false. Simple examples are equality or comparison
 * operators.
 * <p>
 * A test in this library is defined as two predicates.
 * The first evaluates the input, the second evaluates the output.
 * <p>
 * The first determines whether the input constitutes a valid test and
 * the second validates the test to determine whether it passed or failed.
 * <p>
 * For example : Using the Example (isEven):
 * <p>
 * BuiltInTest.expecting(inputValue, 0, "return false") is made of two predicates  <p>
 * <code>-> The Input Predicate (inputValue == 0) </code><p>
 * <code>-> The Output Predicate (output == "return false")</code>
 */
package edu.gmu.team1.idt2014;