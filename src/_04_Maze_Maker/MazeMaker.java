package _04_Maze_Maker;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class MazeMaker{
	
	private static int width;
	private static int height;
	
	private static Maze maze;
	
	private static Random randGen = new Random();
	private static Stack<Cell> uncheckedCells = new Stack<Cell>();
	
	
	public static Maze generateMaze(int w, int h){
		width = w;
		height = h;
		maze = new Maze(width, height);
		
		//4. select a random cell to start
		Random randy = new Random();
		Cell randomCell = maze.getCell(randy.nextInt(maze.getWidth()),randy.nextInt(maze.getHeight()));
		//5. call selectNextPath method with the randomly selected cell
		selectNextPath(randomCell);
		
		return maze;
	}

	//6. Complete the selectNextPathMethod
	private static void selectNextPath(Cell currentCell) {
		//A. mark cell as visited
		currentCell.setBeenVisited(true);
		//B. Get an ArrayList of unvisited neighbors using the current cell and the method below
		Random randy = new Random();
		ArrayList<Cell> neighbors = getUnvisitedNeighbors(currentCell);
		//C. if has unvisited neighbors,
		if(neighbors.size()>0) {
			//C1. select one at random.
			Cell randomCell = neighbors.get(randy.nextInt(neighbors.size()));
			//C2. push it to the stack
			uncheckedCells.push(randomCell);
			//C3. remove the wall between the two cells
			removeWalls(randomCell, currentCell);
			//C4. make the new cell the current cell and mark it as visited
			currentCell=randomCell;
			currentCell.setBeenVisited(true);
			//C5. call the selectNextPath method with the current cell
			selectNextPath(currentCell);
		}	
		//D. if all neighbors are visited
		ArrayList<Cell> check = getUnvisitedNeighbors(currentCell);
		if(check.size()==0) {
			//D1. if the stack is not empty
			if(uncheckedCells.empty()) {
				// D1a. pop a cell from the stack
				// D1b. make that the current cell
				// D1c. call the selectNextPath method with the current cell
				currentCell = uncheckedCells.pop();
				selectNextPath(currentCell);
			}
		}	
	}

	//7. Complete the remove walls method.
	//   This method will check if c1 and c2 are adjacent.
	//   If they are, the walls between them are removed.
	private static void removeWalls(Cell c1, Cell c2) {
		int x1=c1.getX();
		int y1=c1.getY();
		int x2=c2.getX();
		int y2=c2.getY();
		if(x1+1==x2 && y1==y2) {
			c1.setEastWall(false);
			c2.setWestWall(false);
		}
		if(x1==x2 && y1+1==y2) {
			c1.setSouthWall(false);
			c2.setNorthWall(false);
		}
		if(x1-1==x2 && y1==y2) {
			c1.setWestWall(false);
			c2.setEastWall(false);
		}
		if(x1==x2 && y1-1==y2) {
			c1.setNorthWall(false);
			c2.setSouthWall(false);
		}
	}
	
	//8. Complete the getUnvisitedNeighbors method
	//   Any unvisited neighbor of the passed in cell gets added
	//   to the ArrayList
	private static ArrayList<Cell> getUnvisitedNeighbors(Cell c) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		int x=c.getX();
		int y=c.getY();
		if(x<width-1 && !maze.getCell(x+1, y).hasBeenVisited()) {
			neighbors.add(maze.getCell(x+1, y));
		}
		if(y<width-1 && !maze.getCell(x, y+1).hasBeenVisited()) {
			neighbors.add(maze.getCell(x, y+1));
		}
		if(x>0 && !maze.getCell(x-1, y).hasBeenVisited()) {
			neighbors.add(maze.getCell(x-1, y));
		}
		if(y>0 && !maze.getCell(x, y-1).hasBeenVisited()) {
			neighbors.add(maze.getCell(x, y-1));
		}
		return neighbors;
	}
}
