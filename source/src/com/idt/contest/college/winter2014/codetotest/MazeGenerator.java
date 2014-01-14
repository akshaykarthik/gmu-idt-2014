package com.idt.contest.college.winter2014.codetotest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.predicates.Equals;
import edu.gmu.team1.idt2014.predicates.MultiEquals;
import edu.gmu.team1.idt2014.predicates.ValidMaze;

/**
 * The Class MazeGenerator. This class generates a textual representation of a maze using depth-first search.
 * 
 * This algorithm was based on the description at: 
 * http://en.wikipedia.org/wiki/Maze_generation_algorithm#Depth-first_search
 * 
 * @author sclark
 */
public class MazeGenerator {

	/**
	 * The Enum MazeDirection.
	 */
	private enum MazeDirection { 
		/** Go up. */ UP, 
		/** Go down. */ DOWN, 
		/** Go left. */ LEFT, 
		/** Go right. */ RIGHT, 
		/** Go back to the previous cell. */ BACKTRACK 
	};

	/** Coordinate pair storing dimensions of the maze. */
	private CoordinatePair mMazeDimensions;

	/** The total number of cells the maze contains. */
	private int mTotalCells;

	/** HashMap containing the current state of the maze cells. */
	private HashMap<CoordinatePair, MazeCell> mMazeCells;

	/**
	 * The Class CoordinatePair.
	 */
	private class CoordinatePair {

		/** The X value. */
		private int mX;

		/** The Y value. */
		private int mY;

		/**
		 * Instantiates a new coordinate pair.
		 *
		 * @param x the X value
		 * @param y the X value
		 */
		public CoordinatePair(final int x, final int y) {
			this.mX = x;
			this.mY = y;
		}

		/**
		 * Gets the X value.
		 *
		 * @return the X value
		 */
		public int getX() {
			return this.mX;
		}

		/**
		 * Gets the Y value.
		 *
		 * @return the Y value
		 */
		public int getY() {
			return this.mY;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(final Object o) {
			if (o instanceof CoordinatePair) {
				if (((CoordinatePair) o).getX() == this.mX
						&& ((CoordinatePair) o).getY() == this.mY) {
					return true;
				}
			}
			return false;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			return MazeGenerator.this.mMazeDimensions.getX() * this.mY + this.mX;
		}


	}

	/**
	 * The Class MazeCell.
	 */
	private class MazeCell {

		/** The location of this cell. */
		private CoordinatePair mLocation;

		/** Is this the start cell? */
		private boolean mStartCell;

		/** Is this the finish cell? */
		private boolean mFinishCell;

		/** Is the bottom wall intact? */
		private boolean mBottomWallIntact;

		/** Is the top wall intact? */
		private boolean mTopWallIntact;

		/** Is the left wall intact? */
		private boolean mLeftWallIntact;

		/** Is the right wall intact? */
		private boolean mRightWallIntact;

		/**
		 * Instantiates a new maze cell.
		 *
		 * @param location the location of the cell
		 */
		public MazeCell(final CoordinatePair location) {
			this.mLocation = location;
			this.mBottomWallIntact = true;
			this.mTopWallIntact = true;
			this.mLeftWallIntact = true;
			this.mRightWallIntact = true;
			this.mStartCell = false;
			this.mFinishCell = false;
		}

		/**
		 * Break the bottom wall.
		 */
		public void breakBottomWall() {
			this.mBottomWallIntact = false;
		}

		/**
		 * Break the top wall.
		 */
		public void breakTopWall() {
			this.mTopWallIntact = false;
		}

		/**
		 * Break the left wall.
		 */
		public void breakLeftWall() {
			this.mLeftWallIntact = false;
		}

		/**
		 * Break the right wall.
		 */
		public void breakRightWall() {
			this.mRightWallIntact = false;
		}

		/**
		 * Set this as the start.
		 */
		public void setStart() {
			this.mStartCell = true;
		}

		/**
		 * Sets this as the finish.
		 */
		public void setFinish() {
			this.mFinishCell = true;
		}

		/**
		 * Are all walls intact? (Is this cell unvisited?)
		 *
		 * @return true, if successful
		 */
		public boolean areAllWallsIntact() {
			return this.mTopWallIntact
			&& this.mLeftWallIntact
			&& this.mRightWallIntact
			&& this.mBottomWallIntact;
		}

		/**
		 * Gets the location of this cell.
		 *
		 * @return the location of this cell
		 */
		public CoordinatePair getLocation() {
			return this.mLocation;
		}

		/**
		 * Stringify top row.
		 *
		 * @return the string
		 */
		public String stringifyTopRow() {
			String cell = "";
			cell += "#";
			cell += (this.mTopWallIntact) ? "#" : " ";
			cell += "#";
			return cell;
		}

		/**
		 * Stringify middle row.
		 *
		 * @return the string
		 */
		public String stringifyMidRow() {
			String cell = "";
			cell += (this.mLeftWallIntact) ? "#" : " ";
			cell += (this.mStartCell) ? "S" : (this.mFinishCell) ? "F" : " ";
			cell += (this.mRightWallIntact) ? "#" : " ";
			return cell;
		}

		/**
		 * Stringify bottom row.
		 *
		 * @return the string
		 */
		public String stringifyBotRow() {
			String cell = "";
			cell += "#";
			cell += (this.mBottomWallIntact) ? "#" : " ";
			cell += "#";
			return cell;
		}
	}

	/**
	 * Generate maze. (This is the entry point into this class)
	 *
	 * @param initialX the X value of where the algorithm will start generation
	 * @param initialY the the Y value of where the algorithm will start generation
	 * @param xDimension the X dimension of the maze
	 * @param yDimension the Y dimension of the maze
	 * @param randomSeed the random seed of the maze
	 * @return the maze displayed as a string
	 */
	public final String generateMaze(
			final int initialX, 
			final int initialY, 
			final int xDimension, 
			final int yDimension, 
			final long randomSeed) 
	{
		//1,1,20,20,1
		GMUT.addTest()
			.branches(4)
			.testNote("invalid x, y", new MultiEquals(3, 3, 2, 2, 1), new Equals("Invalid X and Y dimensions"))
			.testNote("invalid x", new MultiEquals(3, 1, 2, 2, 1), new Equals("Invalid X dimension"))
			.testNote("invalid y", new MultiEquals(1,3,2,2,1), new Equals("Invalid Y dimension"))
			.testNote("is valid maze", new MultiEquals(1, 1, 20, 20, 1), new ValidMaze())
			.test(new MultiEquals(1, 1, 5, 5, 1), new ValidMaze())
			.build();
		// simple error checking
		if (initialX > xDimension && initialY > yDimension) {
			return "Invalid X and Y dimensions";
		}
		else if (initialX > xDimension) {
			return "Invalid X dimension";
		}
		else if (initialY > yDimension) {
			return "Invalid Y dimension";
		}

		// set up private variables
		this.mTotalCells = xDimension * yDimension;
		this.mMazeDimensions = new CoordinatePair(xDimension, yDimension);
		this.mMazeCells = new HashMap<CoordinatePair, MazeCell>();

		// generate a blank maze by storing newly created MazeCells in the HashMap
		for (int i = 1; i <= xDimension; i++) {
			for (int j = 1; j <= yDimension; j++) {
				CoordinatePair cellLocation = new CoordinatePair(i, j);
				this.mMazeCells.put(cellLocation, new MazeCell(cellLocation));
			}
		}

		// Set the start and finish cells; start at upper left and finish at lower right. This is not ideal,
		// it is possible to get into a situation where these cells are not always connected.
		this.mMazeCells.get(new CoordinatePair(1, 1)).setStart();
		this.mMazeCells.get(new CoordinatePair(xDimension, yDimension)).setFinish();

		// get (visit) the current cell and set the number of visited cells to 1
		MazeCell currentCell = this.mMazeCells.get(new CoordinatePair(initialX, initialY));
		int visitedCells = 1;

		// create a stack (using the LinkedList data structure in this case) and a random generator
		LinkedList<CoordinatePair> cellLocationStack = new LinkedList<CoordinatePair>();
		Random random = new Random(randomSeed);

		// loop until all the cells are visited		
		while (visitedCells < this.mTotalCells) {
			MazeDirection nextDirection = null;
			MazeCell nextCell = null;

			// generate a random number between 0 and 99
			int randomValue = random.nextInt(100);

			// Think of this loop as a slot machine. We loop through it until we find a cell that is not null and has all 4 walls
			// still standing. We do this by by finding 'randomValue mod 4 plus 1' and setting it to maxCount. This means maxCount
			// will always between 1 and 4. Each time we go through the loop, we increment this maxCount value by 1 so we hit the
			// next if statement. If we find a valid cell, we set the nextDirection enum to either DOWN, UP, LEFT, or RIGHT. If we 
			// go through the loop 4 times without finding a valid cell we need to backtrack, so we set the nextDirection enum to 
			// BACKTRACK and break out of the while loop.
			int numLoops = 0;
			while (null == nextCell || !nextCell.areAllWallsIntact()) {
				int currentCount = 0;
				int maxCount = (++randomValue % 4) + 1;

				if (++currentCount <= maxCount) {
					nextCell = this.mMazeCells.get(new CoordinatePair(currentCell.getLocation().getX(), currentCell.getLocation().getY() + 1));
					nextDirection = MazeDirection.DOWN;
				}
				if (++currentCount <= maxCount) {
					nextCell = this.mMazeCells.get(new CoordinatePair(currentCell.getLocation().getX(), currentCell.getLocation().getY() - 1));
					nextDirection = MazeDirection.UP;
				}
				if (++currentCount <= maxCount) {
					nextCell = this.mMazeCells.get(new CoordinatePair(currentCell.getLocation().getX() - 1, currentCell.getLocation().getY()));
					nextDirection = MazeDirection.LEFT;
				}
				if (++currentCount <= maxCount) {
					nextCell = this.mMazeCells.get(new CoordinatePair(currentCell.getLocation().getX() + 1, currentCell.getLocation().getY()));
					nextDirection = MazeDirection.RIGHT;
				}
				if (++numLoops >= 4) {
					nextDirection = MazeDirection.BACKTRACK;
					break;
				}
			}

			// Perform an action based on the enum value set in the loop above. For values where we are moving to a new cell, we break 
			// through the adjoining wall of the current cell, push the currentCell onto the stack, set the currentCell to the nextCell,
			// and break through the adjoining wall of the nextCell (which is now the currentCell). We then increment visitedCells.
			if (nextDirection == MazeDirection.DOWN) {
				currentCell.breakBottomWall();
				cellLocationStack.push(currentCell.getLocation());
				currentCell = nextCell;
				currentCell.breakTopWall();
				++visitedCells;
			} else if (nextDirection == MazeDirection.UP) {
				currentCell.breakTopWall();
				cellLocationStack.push(currentCell.getLocation());
				currentCell = nextCell;
				currentCell.breakBottomWall();
				++visitedCells;
			} else if (nextDirection == MazeDirection.LEFT) {
				currentCell.breakLeftWall();
				cellLocationStack.push(currentCell.getLocation());
				currentCell = nextCell;
				currentCell.breakRightWall();
				++visitedCells;
			} else if (nextDirection == MazeDirection.RIGHT) {
				currentCell.breakRightWall();
				cellLocationStack.push(currentCell.getLocation());
				currentCell = nextCell;
				currentCell.breakLeftWall();
				++visitedCells;
			} else if (nextDirection == MazeDirection.BACKTRACK) {
				// In the case where BACKTRACK is set, we pop the top value of the stack if the stack has at least 1 element. This 
				// element is then set to the currentCell. If the stack is empty, we look for a cell with all walls intact (not 
				// visited) and set that as the currentCell, then increment visitedCells.
				if (cellLocationStack.size() > 0) {
					currentCell = this.mMazeCells.get(cellLocationStack.pop());
				} else {
					for (CoordinatePair coord : this.mMazeCells.keySet()) {
						if (this.mMazeCells.get(coord).areAllWallsIntact()) {
							currentCell = this.mMazeCells.get(coord);
							++visitedCells;
						}
					}
				}
			}
		}


		// At this point our maze has already been created. The code below appends it line by line to
		// the returned mazeString.
		String mazeString = "";
		mazeString += System.getProperty("line.separator");

		for (int i = 1; i <= xDimension; i++) {
			for (int r = 1; r <= 3; r++) {
				for (int j = 1; j <= yDimension; j++) {
					CoordinatePair cellLocation = new CoordinatePair(j, i);
					if (r == 1) {
						mazeString += this.mMazeCells.get(cellLocation).stringifyTopRow();
					} else if (r == 2) {
						mazeString += this.mMazeCells.get(cellLocation).stringifyMidRow();
					} else if (r == 3) {
						mazeString += this.mMazeCells.get(cellLocation).stringifyBotRow();
					}
				}
				mazeString += System.getProperty("line.separator");
			}
		}

		GMUT.test(mazeString, 4, initialX, initialY, xDimension, yDimension, randomSeed);
		return mazeString;
	}


}

