package com.idt.contest.college.winter2014.codetotest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.idt.contest.college.winter2014.framework.FrameworkConstants;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.ThreadTester;
import edu.gmu.team1.idt2014.predicates.Equals;
import edu.gmu.team1.idt2014.predicates.MultiEquals;

/**
 * This problem is based on the description at: 
 * http://en.wikipedia.org/wiki/Dining_philosophers_problem
 * 
 * The solution is based on the resource hierarchy solution
 * http://en.wikipedia.org/wiki/Dining_philosophers_problem#Resource_hierarchy_solution
 * 
 * *** BUG IN THIS CLASS ***
 * This class experiences a race condition because there is no synchronization between threads
 * when accessing the forkStates array. Two Philosophers will check a forks availability, see that
 * it is available, and on the next processor iteration they will both pick up the same fork. This 
 * race condition issue is timing based and is not guaranteed to occur in the same order, or even at all.
 * 
 * 
 */
public class DiningPhilosophers {
	enum State { THINKING, 
		EATING, 
		WAITING_FOR_LOWER_FORK,
		WAITING_FOR_HIGHER_FORK, 
		RELEASING_FORKS };

		/**
		 * integer array representing which philosopher has which fork (index represent fork, value represents philosopher)
		 */
		private  static int[] forkStates;

		
		/**
		 * map to digest result and keep track of number of times eaten (key) and number of philosophers (value)
		 */
		private  Map <Integer, Integer> numOfTimesEatenMap;
		private ThreadTester threadTester;

		/**
		 * public constructor for DiningPhilosphers class
		 */
		public DiningPhilosophers(){
			numOfTimesEatenMap = new HashMap<Integer, Integer>();
			 threadTester = new ThreadTester();
			threadTester.createStateTracker("forks");
		}


		/**
		 * Method used to trigger a Philosopher dinner party
		 * @param numberOfPhilosphers - number of philosophers at the party
		 * @param numberOfTimesToEat - number of times they are expected to eat at the party
		 */
		public String execute(int _numberOfPhilosophers, int _numberOfTimesToEat) {

			// put the forks on the table
			forkStates = new int[_numberOfPhilosophers];
			Arrays.fill(forkStates, FrameworkConstants.INVALID_VALUE);




			// invite the philosophers to dinner
			Philosopher[] philosophers = new Philosopher[_numberOfPhilosophers];
			for (int i = 0; i < _numberOfPhilosophers; i++) {
				philosophers[i] = new Philosopher(i, _numberOfTimesToEat);

			}

			// tell the philosophers that they can start thinking and eating
			for (Philosopher p: philosophers) {
				p.start();
			}

			// wait for all of the philosophers to finish dinner 
			try {
				Thread.sleep(FrameworkConstants.TIME_TO_WAIT * _numberOfPhilosophers * _numberOfTimesToEat);
			} catch (InterruptedException e) {}

			for (Philosopher p: philosophers) {
				try {
					p.join();
				} catch (InterruptedException e) {}
			}

			// ask the philosophers how many times they ate
			Integer value;
			for (Philosopher p: philosophers) {
				value = numOfTimesEatenMap.get(p.getNumberOfTimesEaten()); 
				if (value == null) {
					numOfTimesEatenMap.put(p.getNumberOfTimesEaten(), 1); 
				} else {
					numOfTimesEatenMap.put(p.getNumberOfTimesEaten(), (value + 1));
				}
			}

			// assemble result string and return results
			String resultString = "\n";
			for (Integer i: numOfTimesEatenMap.keySet()) {
				resultString += numOfTimesEatenMap.get(i) + " philosophers ate " + i + " times successfully\n";
			}
			return resultString;
		}


		/**
		 * class representing a single philosopher that will come to dinner, think and eat
		 * Since each philosopher is on their own thread, they can think and eat simultaneously
		 */
		private  class Philosopher extends Thread {

			int identity;
			int leftFork;
			int rightFork;
			int numberOfTimesToEat;
			int numberOfTimesEaten;
			int attemptsBeforeQuitting;
			int attempts;
			boolean holdingForks;
			DiningPhilosophers.State state;


			/**
			 * Constructor of Philosopher
			 * @param _identity - identity of this philosopher
			 * @param _numberOfTimesToEat - number of times they are expected to eat
			 * @param _numberOfForks - number of forks on the table
			 */
			Philosopher(int _identity, int _numberOfTimesToEat) {
				this.identity = _identity;
				this.numberOfTimesToEat = _numberOfTimesToEat;
				this.numberOfTimesEaten = 0;
				this.attemptsBeforeQuitting = _numberOfTimesToEat * _numberOfTimesToEat;
				if (this.attemptsBeforeQuitting < FrameworkConstants.MINIMUM_NUMBER_OF_ATTEMPTS) {
					this.attemptsBeforeQuitting = FrameworkConstants.MINIMUM_NUMBER_OF_ATTEMPTS;
				}
				this.attempts = 0;
				this.rightFork = _identity;
				this.leftFork = determineLeftForkValue(_identity, DiningPhilosophers.forkStates.length);
				holdingForks = false;
				this.state = DiningPhilosophers.State.THINKING;
			}


			/**
			 * This is the code body that represents a philosophers dinner party experience
			 * The philosopher will loop between states until they have eaten enough or quit from frustration
			 * THINKING -> WAITING FOR LOWER FORK -> WAITING FOR HIGHER FORK -> EATING -> RELEASING FORKS 
			 */
			@Override
			public void run(){
				// continue to think and eat until the philosopher is full, 
				// but give him the opportunity to put down his forks
				while ((this.numberOfTimesEaten < this.numberOfTimesToEat || holdingForks) 
						&& this.attempts < this.attemptsBeforeQuitting) {

					// represents the philosopher thinking state
					if (state == DiningPhilosophers.State.THINKING)  {
						System.out.println(formatActionStatement(this.identity, "thinking"));
						sleep(FrameworkConstants.TIME_TO_WAIT * 3);
						state = DiningPhilosophers.State.WAITING_FOR_LOWER_FORK;

						// represents the philosopher eating state
					} else if (state == DiningPhilosophers.State.EATING) {
						System.out.println(formatActionStatement(this.identity, "eating"));
						sleep(FrameworkConstants.TIME_TO_WAIT);
						this.numberOfTimesEaten++;
						state = DiningPhilosophers.State.RELEASING_FORKS;

						// represents the philosopher waiting for lower value fork state
					} else if (state == DiningPhilosophers.State.WAITING_FOR_LOWER_FORK) {
						// if the lower fork is free, take it
						if (isForkAvailable(determineLowestValue(this.leftFork, this.rightFork), DiningPhilosophers.forkStates)) {
							System.out.println(formatActionStatement(this.identity, "picking up lower fork #" + determineLowestValue(this.leftFork, this.rightFork)));
							pickUpFork(this.identity, determineLowestValue(this.leftFork, this.rightFork), DiningPhilosophers.forkStates);
							holdingForks = true;
							state = DiningPhilosophers.State.WAITING_FOR_HIGHER_FORK;
							// the lower fork is not available, wait for it
						} else {
							sleep(FrameworkConstants.TIME_TO_WAIT);
							this.attempts++;
						}

						// representing the philosopher waiting for higher value fork state
					} else if (state == DiningPhilosophers.State.WAITING_FOR_HIGHER_FORK) {
						// if the higher fork is free, take it
						if (isForkAvailable(determineHighestValue(this.leftFork, this.rightFork), DiningPhilosophers.forkStates)) {
							System.out.println(formatActionStatement(this.identity, "picking up higher fork #" + determineHighestValue(this.leftFork, this.rightFork)));
							pickUpFork(this.identity, determineHighestValue(this.leftFork, this.rightFork), DiningPhilosophers.forkStates);
							state = DiningPhilosophers.State.EATING;
							// the higher fork is not available, wait for it
						} else {
							sleep(FrameworkConstants.TIME_TO_WAIT);
							this.attempts++;
						}

						// representing the philosopher releasing forks state
					} else if (state == DiningPhilosophers.State.RELEASING_FORKS) {
						System.out.println(formatActionStatement(this.identity, "releasing fork #" + determineLowestValue(this.leftFork, this.rightFork) +
								" and fork #" + determineHighestValue(this.leftFork, this.rightFork)));
						//releasing the forks
						releaseForks(this.leftFork, this.rightFork, DiningPhilosophers.forkStates);
						holdingForks = false;
						state = DiningPhilosophers.State.THINKING;

					} 
				}

				// in case the Philosopher gets up and tries to walk away with a fork out of frustration, 
				// remind him to put the forks down before he leaves
				if (holdingForks){
					releaseForks(this.leftFork, this.rightFork, DiningPhilosophers.forkStates);
				}

				System.out.println(formatActionStatement(this.identity, "done eating"));
			}

			/**
			 * Method that determines the smaller value between two ints
			 * @param _left - first int to consider
			 * @param _right - second int to consider
			 * @return - smaller number of the two parameters
			 */
			private  int determineLowestValue(int _left, int _right) {

				GMUT.addTest()
				.branches(3)
				.test(new MultiEquals(10,12), new Equals(10))
				.test(new MultiEquals(12,12), new Equals(12))
				.test(new MultiEquals(40,0), new Equals(0))
				.build();

				int lowestValue;
				if (_left < _right) {
					GMUT.test(_left, 1, _left,_right);
					lowestValue = _left;
				} else if (_left == _right) {
					GMUT.test(_left, 2, _left,_right);
					lowestValue = _left;
				} else {
					GMUT.test(_right, 3, _left,_right);
					lowestValue = _right;
				}
				return lowestValue;
			}


			/**
			 * Method that determines the larger value between two ints
			 * @param _left - first int to consider
			 * @param _right - second int to consider
			 * @return - larger number of the two parameters
			 */
			private  int determineHighestValue(int _left, int _right) {


				GMUT.addTest()
				.branches(3)
				.test(new MultiEquals(16,12), new Equals(16))
				.test(new MultiEquals(12,12), new Equals(12))
				.test(new MultiEquals(10,12), new Equals(12))
				.build();

				int highestValue;
				if (_left > _right) {
					GMUT.test(_left, 1, _left,_right);
					highestValue = _left;
				} else if (_left == _right) {
					GMUT.test(_left, 2, _left,_right);
					highestValue = _left;
				} else {
					GMUT.test(_right, 3, _left,_right);
					highestValue = _right;
				}
				return highestValue;
			}


			/**
			 * Method to determine the left fork value based on philosophers index
			 * Generally, this is the index minus 1, but must handle wrapping if index is 0
			 * @param _id - int representing current philosophers index
			 * @param _numOfForks - int representing total number of forks on table
			 * @return - index for the left fork for the philosopher
			 */
			private  int determineLeftForkValue(int _id, int _numOfForks) {

				int left;
				GMUT.addTest()
				.branches(2)
				.test(new MultiEquals(0), new Equals(3))
				.test(new MultiEquals(11,12), new Equals(10))
				.build();

				if (_id == 0) {
					GMUT.test((_numOfForks-1), 1, _id,_numOfForks);
					left = _numOfForks - 1;
				} else {
					GMUT.test((_id-1), 2, _id,_numOfForks);
					left =  _id - 1;
				}
				return left;
			}


			/**
			 * Method used to format the philosophers id and an action into an action statement
			 * @param _id - id of this philosopher
			 * @param _verb - action to add to action statement
			 * @return - String representation of philosopher and the action they performed
			 */
			private  String formatActionStatement(int _id, String _verb) {
				String actionStatement =  "Philosopher #" + this.identity + " is " + _verb + "...";
				return actionStatement;
			}


			/**
			 * Method to quietly handle exceptions incurred during sleeping
			 * @param t - amount of time to sleep in milliseconds
			 */
			private  void sleep(int t){
				try {
					Thread.sleep(t);
				} catch (InterruptedException ex) {}
			}


			/**
			 * Method that allows DiningPhilosophers to retrieve number of 
			 * times that this philosopher ate at the end of the dinner party
			 * @return int number of times that this philosopher at at the dinner party
			 */
			int getNumberOfTimesEaten() {
				return this.numberOfTimesEaten;
			}


			/**
			 * Method to check if a fork is available
			 * @param _forkIndex - identity of fork to check availability
			 * @param _forks - int array representing fork states
			 * @return - true if fork is available, false if fork is unavailable
			 */
			private  boolean isForkAvailable(int _forkIndex, int[] _forks) {
				int currentThreadState = threadTester.getState("forks");



				if (_forks[_forkIndex] == FrameworkConstants.INVALID_VALUE) {
					threadTester.compareWithGlobalState("forks", currentThreadState, "IsForkAvailable");
					
					return true;
				} else {
					threadTester.compareWithGlobalState("forks", currentThreadState, "IsForkAvailable");
					GMUT.test(false, 2, _forkIndex,_forks);
					
					return false;
				}
			}


			/**
			 * Method to pick up a fork
			 * @param _identity - identity of philosopher
			 * @param _forkIndex - index of fork to pick up
			 * @param _forks - int array representing fork states
			 * @return - true if fork pick up was successful, false if unsuccessful
			 */
			private   boolean pickUpFork(int _identity, int _forkIndex, int[] _forks) {
				int currentThreadState;

				
				currentThreadState = threadTester.getState("forks");
				

				if (_forks.length >= _forkIndex + 1) {
					GMUT.test(true, 1,  _identity,  _forkIndex, _forks);

					System.out.println("  Thread "+Thread.currentThread().getName()+" Current thread state "+currentThreadState
							+" Global state" +threadTester.getState("forks"));


					threadTester.compareWithGlobalState("forks", currentThreadState, "pickUpFork");
					threadTester.incrementState("forks");

					_forks[_forkIndex] = _identity;

					currentThreadState++;



					return true;

				} else {

					return false;

				}
			}



			/**
			 * Method to release forks from a philosopher
			 * @param _primaryForkIndex - primary index of fork to release
			 * @param _secondaryForkIndex - secondary index of a fork to release
			 * @param forks - int array of fork states
			 * @return - true if releasing forks was successful, false if unsuccessful
			 */
			private  boolean releaseForks(int _primaryForkIndex, int _secondaryForkIndex, int[] _forks) {
				int currentThreadState;
				//synchronized(forkStates){
				currentThreadState = threadTester.getState("forks");
				//}

				if (_forks.length >= _primaryForkIndex + 1 && _forks.length >= _secondaryForkIndex + 1) {


					threadTester.compareWithGlobalState("forks", currentThreadState, "releaseForks");
					threadTester.incrementState("forks");
					_forks[_primaryForkIndex] = FrameworkConstants.INVALID_VALUE;
					currentThreadState++;

					threadTester.compareWithGlobalState("forks", currentThreadState, "releaseForks-2");	
					threadTester.incrementState("forks");
					_forks[_secondaryForkIndex] = FrameworkConstants.INVALID_VALUE;
					currentThreadState++;

					return true;
				}

				//XXX: Returns true no matter what.

				return true;
			}
		}
}
