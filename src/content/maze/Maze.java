/*
 * Maze
 * 
 * @version JavaFX
 *
 * 25/05/2018
 * 
 * @author Raphaël Tran
 */
package content.maze;

import java.util.*;

import content.GameObject;
import core.Renderable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class represents a rectangular maze.
 * It is characterized by two matrix : horizontalWalls and verticalWalls.
 * 
 */
public class Maze implements Renderable {

	// STATIC VARIABLES

	// Construction related variables
	private static final int DEFAULT_WIDTH = 38;		// | (38, 17) optimal for Eclipse console
	private static final int DEFAULT_HEIGHT = 17;		// |
	// Representation related variables
	private static final Color WALL_COLOR = Color.MAGENTA;
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	private static final Color PLAYER_COLOR = Color.WHITE;
	private static final int SPARKLING_PERIOD = 110;		// Time between color changes in fantastic mode (ms)
	// Other (please do not touch, they're fine like that)
	private static final boolean ZEROZERO = true;		// Does the recursion start at (0, 0)?
	private static final float MARGIN = 0.02f;		// Used for window representation	@@@

	// ATTRIBUTES

	public boolean fantastic;

	private int width;
	private int height;
	private boolean[][] horizontalWalls;		// | false means there is a wall; true means there isn't one
	private boolean[][] verticalWalls;			// |

	private boolean[][] visited;		// Used by intern methods: true means visited



	public Maze() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT, false);
	}

	public Maze(int width, int height) {
		this(width, height, false);
	}
	
	public Maze(int width, int height, boolean fantastic) {
		this.width = width;
		this.height = height;
		this.fantastic = fantastic;
		this.horizontalWalls = new boolean[width][height+1];
		this.verticalWalls = new boolean[width+1][height];
		this.visited = new boolean[width][height];

		if (ZEROZERO) {
			dig(0, 0);
		} else {
			dig(width/2, height/2);
		}
	}


	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}






	/**
	 * Calculate the length of a wall according to the size of the rendering window  
	 * 
	 * @param windowWidth	- the width of the window the maze will be rendered on
	 * @param windowHeight	- the height of the window the maze will be rendered on
	 */
	public double calculateWallSize(double windowWidth, double windowHeight) {
		return (1 - 2*MARGIN) * 
				((windowWidth * height < windowHeight * width) ? windowWidth/width : windowHeight/height);
	}


	@Override
	public void render(GraphicsContext gc, double windowWidth, double windowHeight) {
		//System.out.println("Window width: " + getWidth() + " ; window height: " + getHeight());

		gc.setFill(BACKGROUND_COLOR);
		gc.fillRect(0, 0, windowWidth, windowHeight);				

		// Determining parameters
		double x0;
		double y0;
		double wallSize;
		if (windowWidth * height < windowHeight * width) {		// Is the maze large or long?
			x0 = windowWidth * MARGIN;
			wallSize = (windowWidth - 2*x0) / width;
			y0 = (windowHeight - height*wallSize) / 2;
		} else {
			y0 = windowHeight * MARGIN;
			wallSize = (windowHeight - 2*y0) / height;
			x0 = (windowWidth - width*wallSize) / 2;
		}
		
		// Maze representation
		gc.setStroke(WALL_COLOR);
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				if (!horizontalWalls[i][j]) {
					if (fantastic) {
						gc.setStroke(AnnexMaze.randomColor());
					}
					double x = x0 + i*wallSize;
					double y = y0 + j*wallSize;
					gc.strokeLine(x, y, x+wallSize, y);
				}
			}
			for (int i = 0; i < width; i++) {
				if (!verticalWalls[i][j]) {
					if (fantastic) {
						gc.setStroke(AnnexMaze.randomColor());
					}
					double x = x0 + i*wallSize;
					double y = y0 + j*wallSize;
					gc.strokeLine(x, y, x, y+wallSize);
				}
			}
		}
		gc.setStroke(WALL_COLOR);
		double w = width*wallSize;
		double h = height*wallSize;
		gc.strokeRect(x0, y0, w, h);		// Contour
	}








	private List<Integer> neighbors0(int x, int y) {
		/* 
		 * Return a ListArray indicating the adjacent tiles accessible; uses visited matrix.
		 * Used for generation: DOES NOT TAKE WALLS INTO ACCOUNT
		 * 
		 * 0 -> tile above
		 * 1 -> tile to the right
		 * 2 -> tile below
		 * 3 -> tile to the left 
		 */
		List<Integer> result = new ArrayList<Integer>();		// 
		if (y != 0 && !visited[x][y-1]) {		// The tile above
			result.add(0);
		}
		if (x != width-1 && !visited[x+1][y]) {		// The tile on the right
			result.add(1);
		}
		if (y != height-1 && !visited[x][y+1]) {		// The tile below
			result.add(2);
		}
		if (x != 0 && !visited[x-1][y]) {		// The tile on the left
			result.add(3);
		}
		return result;
	}

	private List<Integer> neighbors1(int x, int y) {
		/* Return the adjacent tiles to this one, considering walls.
		 *
		 * 0 -> tile above
		 * 1 -> tile to the right
		 * 2 -> tile below
		 * 3 -> tile to the left 
		 */
		List<Integer> result = new ArrayList<Integer>();		//
		if (horizontalWalls[x][y]) {		// The tile above
			result.add(0);
		}
		if (verticalWalls[x+1][y]) {		// The tile on the right
			result.add(1);
		}
		if (horizontalWalls[x][y+1]) {		// The tile below
			result.add(2);
		}
		if (verticalWalls[x][y]) {		// The tile on the left
			result.add(3);
		}			
		return result;
	}

	private List<Integer> neighbors2(int x, int y) {
		/* Return the adjacent tiles to this one, considering walls and visited tiles.
		 *
		 * 0 -> tile above
		 * 1 -> tile to the right
		 * 2 -> tile below
		 * 3 -> tile to the left 
		 */
		List<Integer> result = new ArrayList<Integer>();		//
		if (horizontalWalls[x][y] && !visited[x][y-1]) {		// The tile above
			result.add(0);
		}
		if (verticalWalls[x+1][y] && !visited[x+1][y]) {		// The tile on the right
			result.add(1);
		}
		if (horizontalWalls[x][y+1] && !visited[x][y+1]) {		// The tile below
			result.add(2);
		}
		if (verticalWalls[x][y] && !visited[x-1][y]) {		// The tile on the left
			result.add(3);
		}			
		return result;
	}

	private int[] toCoordinates(int n, int x, int y) {
		/* Convenient method which translate the "neighbor code" n to actual coordinates.
		 * Example: 2 -> {x, y+1}) 
		 */
		switch (n) {
		case 0:
			return new int[] {x, y-1};
		case 1:
			return new int[] {x+1, y};
		case 2:
			return new int[] {x, y+1};
		case 3:
			return new int[] {x-1, y};
		default:
			throw new Error("Should not happen");
		}
	}



	private void dig(int x, int y) {
		/* Dig the initialy walled maze (depth-first). */
		visited[x][y] = true;
		List<Integer> neighbors = neighbors0(x, y);

		while (!neighbors.isEmpty()) {

			int index = AnnexMaze.randInt(0, neighbors.size());
			int newX = x;
			int newY = y;

			switch (neighbors.get(index)) {
			case 0:		// We go up
				newY--;
				horizontalWalls[x][y] = true;
				break;
			case 1:		// We go right
				newX++;
				verticalWalls[x+1][y] = true;
				break;
			case 2:		// We go down
				newY++;
				horizontalWalls[x][y+1] = true;
				break;
			case 3:		// We go left
				newX--;
				verticalWalls[x][y] = true;
				break;
			default:
			}
			dig(newX, newY);
			// It's important to refresh neighbors as the previous line changed the maze structure
			neighbors = neighbors0(x, y);
		}
	}



	@Override public String toString() {
		/* PFIOUUU! */

		// First two lines
		String result = "┌───";
		for (int i = 1; i < width; i++) {
			if (verticalWalls[i][0]) {
				result += "─";
			} else {
				result += "┬";
			}
			result += "───";
		}
		result += "┐";
		result += System.lineSeparator();
		result += "│   ";
		for (int i = 1; i < width; i++) {
			if (verticalWalls[i][0]) {
				result += " ";
			} else {
				result += "│";
			}
			result += "   ";
		}
		result += "│";
		result += System.lineSeparator();

		// Middle lines
		for (int j = 1; j < height; j++) {
			if (horizontalWalls[0][j]) {
				result += "│   ";
			} else {
				result += "├───";
			}
			for (int i = 1; i < width; i++) {
				result += intersection(verticalWalls[i][j-1], horizontalWalls[i][j],
						verticalWalls[i][j], horizontalWalls[i-1][j]);
				if (horizontalWalls[i][j]) {
					result += "   ";
				} else {
					result += "───";
				}
			}
			if (horizontalWalls[width-1][j]) {
				result += "│";
			} else {
				result += "┤";
			}
			result += System.lineSeparator();

			result += "│   ";
			for (int i = 1; i < width; i++) {
				if (verticalWalls[i][j]) {
					result += " ";
				} else {
					result += "│";
				}	
				result += "   ";
			}
			result += "│";
			result += System.lineSeparator();
		}

		// Last line
		result += "└───";
		for (int i = 1; i < width; i++) {
			if (verticalWalls[i][height-1]) {
				result += "─";
			} else {
				result += "┴";
			}
			result += "───";
		}
		result += "┘";

		return result;
	}

	private String intersection(boolean up, boolean right, boolean down, boolean left) {
		/* Return the correct character according to the nearby walls; used in toString. */
		int n = (up ? 8:0) + (right ? 4:0) + (down ? 2:0) + (left ? 1:0);
		switch (n) {
		case 0:
			return "┼";		
		case 1:
			return "├";
		case 2:
			return "┴";
		case 3:
			return "└";
		case 4:
			return "┤";
		case 5:
			return "│";
		case 6:
			return "┘";
		case 7:
			return "╵";
		case 8:
			return "┬";
		case 9:
			return "┌";
		case 10:
			return "─";
		case 11:
			return "╶";
		case 12:
			return "┐";
		case 13:
			return "╷";
		case 14:
			return "╴";
		case 15:
			return "·";
		default:
		}
		throw new Error("Should not happen");
	}

}
