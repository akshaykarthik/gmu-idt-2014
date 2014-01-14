package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		System.out.println("valid maze running");
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

			int[][] valueGrid = extractValueGrid(lenx, leny, mazeArray);

			String solution = dijkstra(valueGrid);

			for (int[] p : valueGrid) {
				for (int q : p) {
					System.out.print(valueToBox(q));
				}
				System.out.println();
			}

			return (solution != null) && (solution.length() >= (lenx + leny));

		} catch (Exception ex) {
			throw ex;
			// return false;
		}
	}

	class Coordinates {
		public int x;
		public int y;

		public Coordinates(int ix, int iy) {
			x = ix;
			y = iy;
		}

		public String toString() {
			return "[ " + x + " | " + y + " ]";
		}

		@Override
		public boolean equals(Object obj) {
			return ((Coordinates) obj).x == x && ((Coordinates) obj).y == y;
		}

		@Override
		public int hashCode() {
			return y * 10000000 + x;
		}

	}

	public String dijkstra(int[][] valueGrid) {
		Map<Coordinates, Integer> distances = new HashMap<Coordinates, Integer>();
		Map<Coordinates, Coordinates> backtrack = new HashMap<Coordinates, Coordinates>();
		Set<Coordinates> allCoords = new HashSet<Coordinates>();
		for (int i = 0; i < valueGrid.length; i++) {
			for (int j = 0; j < valueGrid[i].length; j++) {
				distances.put(new Coordinates(i, j), Integer.MAX_VALUE);
				backtrack.put(new Coordinates(i, j), null);
				allCoords.add(new Coordinates(i, j));
			}
		}

		distances.put(new Coordinates(0, 0), 0);
		while (allCoords.size() > 0) {
			int minDist = Integer.MAX_VALUE;
			Coordinates u = null;
			for (Coordinates coordinates : allCoords) {
				if (distances.get(coordinates) < minDist) {
					u = coordinates;
					minDist = distances.get(coordinates);
				}
			}
			allCoords.remove(u);
			if (u == null
					|| (distances.containsKey(u) && distances.get(u) == Integer.MAX_VALUE)) {
				break;
			}

			int alt = distances.get(u) + 1;
			if ((valueGrid[u.x][u.y] & LEFT_OPEN) == LEFT_OPEN) {
				Coordinates v = new Coordinates(u.x - 1, u.y);
				if (distances.containsKey(v) && alt < distances.get(v)) {
					distances.put(v, alt);
					backtrack.put(v, u);
				}
			}
			if ((valueGrid[u.x][u.y] & RIGHT_OPEN) == RIGHT_OPEN) {
				Coordinates v = new Coordinates(u.x + 1, u.y);
				if (distances.containsKey(v) && alt < distances.get(v)) {
					distances.put(v, alt);
					// System.out.println(v + " () " + distances.get(v));
					backtrack.put(v, u);
//					System.out.println(v + " < " + u);
				}
			}

			if ((valueGrid[u.x][u.y] & TOP_OPEN) == TOP_OPEN) {
				Coordinates v = new Coordinates(u.x, u.y - 1);
				if (distances.containsKey(v) && alt < distances.get(v)) {
					distances.put(v, alt);
					backtrack.put(v, u);
				}
			}

			if ((valueGrid[u.x][u.y] & BOT_OPEN) == BOT_OPEN) {
				Coordinates v = new Coordinates(u.x, u.y + 1);
				if (distances.containsKey(v) && alt < distances.get(v)) {
					distances.put(v, alt);
					backtrack.put(v, u);
				}
			}

		}
		for (Coordinates stack : distances.keySet()) {
			System.out.println(stack + " " + distances.get(stack));
		}
		for (Coordinates stack : backtrack.keySet())
			System.out.println(backtrack.get(stack) + " " + (stack));
		return null;
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
