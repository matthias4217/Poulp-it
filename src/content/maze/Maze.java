/*
 * Maze
 * 
 * @version 1.0
 *
 * 01/01/2018
 * 
 * @author Raphaël Tran
 */
package content.maze;

import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * This class represents a rectangular maze.
 * It is characterized by two matrix : horizontalWalls and verticalWalls.
 * 
 * 
 * TODO: 	explosion!, void removeWalls()..., improved evaluation function (countBranch + length of them)
 * 				play, implementation 2), 3)
 */
public class Maze { 

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
	private static final float SCALE = 0.91f;		// Used to determine the window size

	private static final int TIME_PLAYER_MOVE = 1;			// The time the player uses to make one step (ms)


	// ATTRIBUTES

	private int width;
	private int height;
	private boolean[][] horizontalWalls;		// | false means there is a wall; true means there isn't one
	private boolean[][] verticalWalls;			// |

	private boolean[][] visited;		// Used by intern methods: true means visited


	public Maze() {
		this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public Maze(int width, int height) {
		/* Constructor */
		this.width = width;
		this.height = height;
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


	@Override
	public String toString() {
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


	public void show() {
		int[] dimensions = optimalDimensions();
		show(dimensions[0], dimensions[1]);
	}

	public void show(int windowWidth, int windowHeight) {
		show(windowWidth, windowHeight, false, false);
	}

	public void showFantastic() {
		int[] dimensions = optimalDimensions();
		show(dimensions[0], dimensions[1], false, true);
	}

	public void showFantastic(int windowWidth, int windowHeight) {
		show(windowWidth, windowHeight, false, true);
	}

	private void show(int windowWidth, int windowHeight, boolean play, boolean fantastic) {
		/* Show a window displaying the maze. */
		JFrame window = new JFrame();
		window.setTitle((fantastic) ? "BEAUTIFUL!" : "Maze (" + width + "x" + height + ")");
		window.setSize(windowWidth, windowHeight + 36);		// We consider the size of the title bar
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		@SuppressWarnings("serial")
		class Panel extends JPanel {
			/**
			 * 
			 */
			private int playerPositionX = width / 2;
			private int playerPositionY = height / 2;
			private int playerXWindow;
			private int playerYWindow;


			public int getPlayerPositionX() {
				return playerPositionX;
			}

			public void setPlayerPositionX(int playerPositionX) {
				this.playerPositionX = playerPositionX;
			}

			public int getPlayerPositionY() {
				return playerPositionY;
			}

			public void setPlayerPositionY(int playerPositionY) {
				this.playerPositionY = playerPositionY;
			}

			@SuppressWarnings("unused")
			public int getPlayerXWindow() {
				return playerXWindow;
			}

			public void setPlayerXWindow(double x0, double wallSize) {
				playerXWindow = (int)(x0 + (playerPositionX + 0.25)*wallSize);
			}

			@SuppressWarnings("unused")
			public int getPlayerYWindow() {
				return playerYWindow;
			}

			public void setPlayerYWindow(double y0, double wallSize) {
				playerYWindow = (int)(y0 + (playerPositionY + 0.25)*wallSize);
			}


			@Override
			public void paintComponent(Graphics g) {
				// Toggle comment on the next line to show the window's dimensions.
				//System.out.println("Window width: " + getWidth() + " ; window height: " + getHeight());

				g.setColor(BACKGROUND_COLOR);
				g.fillRect(0, 0, getWidth(), getHeight());				

				// Determining parameters
				double x0;
				double y0;
				double wallSize;
				if (getWidth() * height < getHeight() * width) {		// Is the maze large or long?
					x0 = getWidth() * MARGIN;
					wallSize = (getWidth() - 2*x0) / width;
					y0 = (getHeight() - height*wallSize) / 2;
				} else {
					y0 = getHeight() * MARGIN;
					wallSize = (getHeight() - 2*y0) / height;
					x0 = (getWidth() - width*wallSize) / 2;
				}

				// Player representation
				if (play) {
					setPlayerXWindow(x0, wallSize);
					setPlayerYWindow(y0, wallSize);
					g.setColor(PLAYER_COLOR);
					g.fillOval(playerXWindow, playerYWindow, (int)(wallSize/2), (int)(wallSize/2));
				}

				// Maze representation
				g.setColor(WALL_COLOR);					
				for (int j = 0; j < height; j++) {
					for (int i = 0; i < width; i++) {
						if (!horizontalWalls[i][j]) {
							if (fantastic) {
								g.setColor(AnnexMaze.randomColor());
							}
							double x = x0 + i*wallSize;
							double y = y0 + j*wallSize;
							g.drawLine((int)x, (int)y, (int)(x+wallSize), (int)y);
						}
					}
					for (int i = 0; i < width; i++) {
						if (!verticalWalls[i][j]) {
							if (fantastic) {
								g.setColor(AnnexMaze.randomColor());
							}
							double x = x0 + i*wallSize;
							double y = y0 + j*wallSize;
							g.drawLine((int)x, (int)y, (int)x, (int)(y+wallSize));
						}
					}
				}
				g.setColor(WALL_COLOR);
				int w = (int)(x0 + width*wallSize) - (int)x0;		// | Might seems weird but actually
				int h = (int)(y0 + height*wallSize) - (int)y0;		// | enable pixel perfection
				g.drawRect((int)x0, (int)y0, w, h);		// Contour




			}
		};
		Panel panel = new Panel();
		window.setContentPane(panel);

		window.setVisible(true);

		while (fantastic) {			// Can probably be improved with multithreading @@@
			panel.repaint();
			// Sleep
			try {
				Thread.sleep(SPARKLING_PERIOD);
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}

		while (play) {		// Moving player
			try {
				Thread.sleep(TIME_PLAYER_MOVE);
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}

			int x = panel.getPlayerPositionX();		// | Absolute coordinates
			int y = panel.getPlayerPositionY();		// |
			List<Integer> neighbors = neighbors1(x, y);
			int index = AnnexMaze.randInt(0, neighbors.size());
			int[] next = toCoordinates(neighbors.get(index), x, y);

			panel.setPlayerPositionX(next[0]);
			panel.setPlayerPositionY(next[1]);

			panel.repaint();
		}




	}



	private int[] optimalDimensions() {		// @@@ not good when the maze has a high ratio
		/* Return good windowWidth and windowHeight to show this Maze. */

		int[] result = new int[2];

		// Scaling
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double w = SCALE * screenSize.getWidth();
		double h = SCALE * screenSize.getHeight();
		double screenRatio = screenSize.getWidth() / screenSize.getHeight(); 

		double mazeRatio = (double)width / (double)height;
		if (mazeRatio < 1.0) {
			result[0] = (int)(w * mazeRatio);
			result[1] = (int) h;
		} else if (mazeRatio <= screenRatio) {
			result[0] = (int) w;
			result[1] = (int) h;
		} else {
			System.out.println((int)(w));
			System.out.println((int)(w / mazeRatio));
			result[0] = (int) w;
			result[1] = (int)(w / mazeRatio);
		}
		return result;
	}


	public void play() {
		play(0);
	}

	public void play(int mode) {
		/* Render the maze and enable to move inside it.
		 * 
		 * mode 0 -> normal
		 * mode 1 -> realistic vision
		 * mode 2 -> keep track of visited tiles
		 */
		int[] dimensions = optimalDimensions();
		show(dimensions[0], dimensions[1], true, false);
	}

	public void playFantastic() {		// @@@ Need multithreading to work
		int[] dimensions = optimalDimensions();
		show(dimensions[0], dimensions[1], true, true);
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



	public int countBranches() {
		/* Return the number of branching path in the Maze. */

		// Reseting the visited matrix
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				visited[i][j] = false;
			}
		}
		// Need to compense the fact that the (0, 0) tile might be incorrectly considered a branching point.
		int compensation = ((neighbors2(0, 0).size() > 1) ? 1 : 0);
		return countBranches(0, 0) - compensation;
	}

	private int countBranches(int x, int y) {
		/* Auxiliary function for public int countBranches(). */
		visited[x][y] = true;
		List<Integer> neighbors = neighbors2(x, y);
		if (neighbors.isEmpty()) {
			return 0;
		}
		int nb = 0;
		for (int neigh: neighbors) {
			int[] next = toCoordinates(neigh, x, y);
			nb += countBranches(next[0], next[1]);
		}
		return nb + ((neighbors.size() > 1) ? 1 : 0);
	}



	// ARCHIVE

	public String toString0() {
		/* First version of toString: quite ugly. */
		String result = new String();

		for (int j = 0; j < height; j++) {

			for (int i = 0; i < width; i++) {
				if (horizontalWalls[i][j]) {
					result += "┼   ";
				} else {
					result += "┼───";
				}
			}
			result += "┼";		// Last column
			result += System.lineSeparator();

			for (int i = 0; i < width; i++) {
				if (verticalWalls[i][j]) {
					result += "    ";
				} else {
					result += "│   ";
				}
			}
			result += "│";		// Last column
			result += System.lineSeparator();
		}
		// Last line
		for (int k = 0; k < width; k++) {
			result += "┼───";
		} result += "┼";
		return result;
	}



}


