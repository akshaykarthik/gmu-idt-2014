package edu.gmu.team1.idt2014.predicates;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Valid Maze is a multi-pronged approach to validating a given maze.<br>
 * First it attempts to parse the string for a maze. <br>
 * Then it attempts to find a valid solution for the maze <br>
 * If the maze is both valid and solvable, then the maze is returned as valid.
 */
public class ValidMaze implements Predicate {

	public static final int CLOSED = 0;
	public static final int TOP_OPEN = 1 << 0;
	public static final int BOT_OPEN = 1 << 1;
	public static final int LEFT_OPEN = 1 << 2;
	public static final int RIGHT_OPEN = 1 << 3;
	public static final int STARTING_CELL = 1 << 4;
	public static final int ENDING_CELL = 1 << 5;
	public static final int ERROR_CELL = 1 << 6;

	public boolean evaluate(Object... inputs) {
//		System.out.println("valid maze running");
		try {
			String strMaze = (String) inputs[0];
			String[] mazeArray = strMaze.split(System
					.getProperty("line.separator"));
			// x, y dimensions of the maze
			int lenx = (mazeArray.length) / 3;
			int leny = mazeArray[1].length() / 3;

			if (mazeArray[0].length() == 0) {
				mazeArray = Arrays.copyOfRange(mazeArray, 1, mazeArray.length);
			}
			boolean valid = false;
			int[][] valueGrid = extractValueGrid(lenx, leny, mazeArray);
			int[][] floodFill = new int[lenx][leny];
			Coordinate end = null;
			ArrayList<Coordinate> stack = new ArrayList<Coordinate>();
			for (int i = 0; i < valueGrid.length; i++) {
				for (int j = 0; j < valueGrid[i].length; j++) {
					floodFill[i][j] = 0;
					if((valueGrid[i][j] & STARTING_CELL) == STARTING_CELL)
						stack.add(new Coordinate(i, j));
					if((valueGrid[i][j] & ENDING_CELL) == ENDING_CELL)
						end = new Coordinate(i, j);
				}
			}

//			for (int i = 0; i < valueGrid.length; i++) {
//				for (int j = 0; j < valueGrid[i].length; j++) {
//					System.out.print(valueToBox(valueGrid[i][j]));
//				}
//				System.out.println();
//			}
			
			while(stack.size() > 0 && end != null){
				Coordinate c = stack.remove(0);
					
				int vgv = valueGrid[c.y][c.x];
				floodFill[c.x][c.y] = 1; 
				boolean left = (vgv & LEFT_OPEN) == LEFT_OPEN;
				boolean right = (vgv & RIGHT_OPEN) == RIGHT_OPEN;
				boolean top = (vgv & TOP_OPEN) == TOP_OPEN;
				boolean bot = (vgv & BOT_OPEN) == BOT_OPEN;
								
				if( left && floodFill[c.x - 1][c.y] == 0)
					stack.add(new Coordinate(c.x - 1, c.y));
				
				if( right && floodFill[c.x + 1][c.y] == 0)
					stack.add(new Coordinate(c.x + 1, c.y));
				
				if( top && floodFill[c.x][c.y - 1] == 0)
					stack.add(new Coordinate(c.x, c.y - 1));
				
				if( bot && floodFill[c.x][c.y + 1] == 0)
					stack.add(new Coordinate(c.x, c.y + 1));
				
				if(c.equals(end)){
					valid = true;
					break;
				}
			}
			
//			for (int i = 0; i < floodFill.length; i++) {
//				System.out.println(Arrays.toString(floodFill[i]));
//			}

			return valid;

		} catch (Exception ex) {
			throw ex;
			// return false;
		}
	}

	class Coordinate {
		public int x;
		public int y;

		public Coordinate(int ix, int iy) {
			x = ix;
			y = iy;
		}

		public String toString() {
			return "[ " + x + " | " + y + " ]";
		}

		@Override
		public boolean equals(Object obj) {
			return ((Coordinate) obj).x == x && ((Coordinate) obj).y == y;
		}

		@Override
		public int hashCode() {
			return y * 10000000 + x;
		}

	}


	public int[][] extractValueGrid(int lenx, int leny, String[] mazeArray) {
		int[][] valueGrid = new int[lenx][leny];
		for (int i = 0; i < lenx; i++) {
			for (int j = 0; j < leny; j++) {
				valueGrid[i][j] = 0;
				int bx = i * 3;
				int by = j * 3;
				String top = mazeArray[bx].substring(by, by + 3);
				String mid = mazeArray[bx + 1].substring(by, by + 3);
				String bot = mazeArray[bx + 2].substring(by, by + 3);

				valueGrid[i][j] |= top.equals("###") ? CLOSED : top
						.equals("# #") ? TOP_OPEN : ERROR_CELL;
				char[] midA = mid.toCharArray();
				valueGrid[i][j] |= (midA[0] == '#') ? CLOSED : LEFT_OPEN;
				valueGrid[i][j] |= (midA[1] == 'S') ? STARTING_CELL
						: (midA[1] == 'F') ? ENDING_CELL : CLOSED;
				valueGrid[i][j] |= (midA[2] == '#') ? CLOSED : RIGHT_OPEN;

				valueGrid[i][j] |= bot.equals("###") ? CLOSED : bot
						.equals("# #") ? BOT_OPEN : ERROR_CELL;

			}
		}
		return valueGrid;
	}

	public String valueToBox(int q) {
		boolean top = (q & TOP_OPEN) == TOP_OPEN;
		boolean bot = (q & BOT_OPEN) == BOT_OPEN;
		boolean left = (q & LEFT_OPEN) == LEFT_OPEN;
		boolean right = (q & RIGHT_OPEN) == RIGHT_OPEN;

		String ret = " ";

		if (top) {
			if (bot) {
				if (left && right)
					ret = "╋";
				else if (left)
					ret = "┫";
				else if (right)
					ret = "┣";
				else
					ret = "┃";
			} else {
				if (left && right)
					ret = "┻";
				else if (left)
					ret = "┛";
				else if (right)
					ret = "┗";
				else
					ret = "╹";
			}
		} else {
			if (bot) {
				if (left && right)
					ret = "┰";
				else if (left)
					ret = "┓";
				else if (right)
					ret = "┏";
				else
					ret = "╻";
			} else {
				if (left && right)
					ret = "━";
				else if (left)
					ret = "╸";
				else if (right)
					ret = "╺";
				else
					ret = "·";
			}
		}

		if ((q & STARTING_CELL) == STARTING_CELL)
			ret = "╬";
		if ((q & ENDING_CELL) == ENDING_CELL)
			ret = "╬";

		return ret + " ";
	}

}
