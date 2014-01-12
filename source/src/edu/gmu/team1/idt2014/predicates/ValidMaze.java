package edu.gmu.team1.idt2014.predicates;

import java.util.Arrays;

/**
 * Valid Maze is a multi-pronged approach to validating a given maze.<br>
 * First it attempts to parse the string for a maze. <br>
 * Then it attempts to find a valid solution for the maze <br>
 * If the maze is both valid and solvable, then the maze is returned as valid.
 */
public class ValidMaze extends Predicate {

	public static final int CLOSED = 0;
	public static final int TOP_OPEN = 1 << 0;
	public static final int BOT_OPEN = 1 << 1;
	public static final int LEFT_OPEN = 1 << 2;
	public static final int RIGHT_OPEN = 1 << 3;
	public static final int STARTING_CELL = 1 << 4;
	public static final int ENDING_CELL = 1 << 5;
	public static final int ERROR_CELL = 1 << 6;
		
	@Override
	public boolean evaluate(Object... inputs) {
		try{
			String strMaze = (String) inputs[0];
			String[] mazeArray = strMaze.split(System.getProperty("line.separator"));
			// x, y dimensions of the maze
			int lenx = (mazeArray.length) / 3;
			int leny = mazeArray[1].length() / 3;
			
			if(mazeArray[0].length() == 0){
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
			
		} catch (Exception ex){
			return false;
		}
	}
	

	@SuppressWarnings("unused")
	private class Pair<T, U> {
		public Pair(T il, U ir){
			this.l = il;
			this.r = ir;
		}
		public T l;
		public U r;
		
	}
	
	public String dijkstra(int[][] valueGrid){
		
		return null;
	}
		
//	public String dijkstra(int[][] valueGrid){
//		int[][] distance = new int[valueGrid.length][valueGrid[0].length];
//		List<int[]> settled = new ArrayList<int[]>();
//		List<int[]> unsettled = new ArrayList<int[]>();
//		for(int i = 0; i < valueGrid.length; i++){
//			for(int j = 0; j < valueGrid[0].length; j++){
//				distance[i][j] = Integer.MAX_VALUE;
//				if((valueGrid[i][j] & this.STARTING_CELL) == STARTING_CELL){
//					unsettled.add(new int[]{i, j});
//					distance[i][j] = 0;
//				}
//			}
//		}
//		
//		while(unsettled.size() > 0){
//			int[] nextNode = lowestDistance(unsettled, distance);
//			settled.remove(o)
//		}
//		return null;
//	}
//	
//	public int[] lowestDistance(List<int[]> test, int[][] distances){
//		int nx = -1;
//		int ny = -1;
//		int lowest = Integer.MAX_VALUE;
//		for (int[] is : test) {
//			int i = is[0];
//			int j = is[1];
//			if(distances[i][j] < lowest){
//				lowest = distances[i][j];
//				nx = i;
//				ny = j;
//			}
//		}
//		return new int[]{nx, ny};
//	}

	
	public int[][] extractValueGrid(int lenx, int leny, String[] mazeArray){
		int[][] valueGrid = new int[lenx][leny];
		for(int i = 0; i < lenx; i++){
			for(int j = 0; j < leny; j++){
				valueGrid[i][j] = 0;
				int bx = i * 3;
				int by = j * 3;			
				String top = mazeArray[bx].substring(by, by + 3);
				String mid = mazeArray[bx + 1].substring(by, by + 3);
				String bot = mazeArray[bx + 2].substring(by, by + 3);
				
				valueGrid[i][j] |= top.equals("###")? CLOSED :
								   top.equals("# #")? TOP_OPEN:
													ERROR_CELL;
				char[] midA = mid.toCharArray();
				valueGrid[i][j] |= (midA[0] == '#') ? CLOSED : LEFT_OPEN;
				valueGrid[i][j] |= (midA[1] == 'S') ? STARTING_CELL :
								   (midA[1] == 'F') ? ENDING_CELL:
									   				CLOSED;
				valueGrid[i][j] |= (midA[2] == '#') ? CLOSED : RIGHT_OPEN;
				
				
				valueGrid[i][j] |= bot.equals("###")? CLOSED :
					   			   bot.equals("# #")? BOT_OPEN:
					   				   				  ERROR_CELL;

				
			}
		}
		return valueGrid;
	}
	
	public String valueToBox(int q){
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
