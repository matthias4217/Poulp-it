package levels;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import core.util.Annex.Direction;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import levels.Tile.TileType;

/**
 * This class represents a level with a matrix of Tiles:
 * 	each case of the grid stores the type of tile it contains
 *
 * @author matthias
 *
 */
public class Level {

	/**
	 * The HashMap used to link the characters in the txt file to the game
	 * representation
	 */
	private static HashMap<Character, TileType> ASSOCIATIONS;

	private static void initializeASSOCIATIONS() {
		ASSOCIATIONS = new HashMap<Character, TileType>();
		ASSOCIATIONS.put(' ', TileType.EMPTY);
		ASSOCIATIONS.put('x', TileType.SQUARE);
		ASSOCIATIONS.put('/', TileType.TRIANGLE_DOWN_RIGHT);
		ASSOCIATIONS.put('\\', TileType.TRIANGLE_DOWN_LEFT);
		ASSOCIATIONS.put('u', TileType.TRIANGLE_TOP_RIGHT);
		ASSOCIATIONS.put('v', TileType.TRIANGLE_TOP_LEFT);
	}

	/**
	 *  The texts that identifies the different informations in the txt file
	 */
	public static final String LEVEL = "level:";
	public static final String THEME = "theme:";


	/**
	 * The grid of Tiles,
	 * for the physics of the world
	 */
	private TileType[][] tileMatrix;

	/**
	 * List of Tiles, used for rendering only
	 */
	public Tile[] tileList;

	private String theme;


	/**
	 * Return the type of the tile (x, y) in the tile matrix; null if the index are out of bounds
	 * 
	 * @param i
	 * @param j
	 * @return	the tile of coordinates (i, j), if (i,j) is outside tileMatrix, returns EMPTY
	 */
	public TileType getTile(int i, int j) {
		// FIXME tileMatrix seems to invert x and y
		// Check if i and j < lengths of tileMatrix
		if (0 < i && i < tileMatrix.length && 0 < j && j < tileMatrix[0].length) {
			return tileMatrix[i][j];
		}
		else {
			return TileType.EMPTY;
		}
	}
	
	
	
	
	/**
	 * Constructor
	 *
	 * @param file
	 * @throws IOException
	 */
	public Level(String file) throws IOException {
		initializeASSOCIATIONS();

		/*
		 * Open the file
		 * https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java#4716623
		 */
		Path filePath = FileSystems.getDefault().getPath(file);
		System.out.println("Path to the level: " + filePath);

		/*
		 * Now the parsing begins (dramatic music)
		 * What do we do ?
		 * we read each line
		 * if it begins with #, we ignore it
		 * if it begins with tilesize: we add the tilesize
		 * the level is 'level:' and then enclosed in brackets
		 */

		boolean inLevel = false;		// false outside the brackets, true inside
		int lineNbr = 0;
		List<char[]> tilesList = new ArrayList<char[]>();

		System.out.println("Beginning level parsing...");
		for (String line: Files.readAllLines(filePath)) {
			if (line.trim().charAt(0) != '#') {
				if (!inLevel && line.substring(0, Math.min(THEME.length(), line.length())).equals(THEME)) {
					/*
					 *  the end of the substring should be the end of the line
					 *  and after, we strip the blanks from it with trim()
					 *
					 *  As we can see, the last 'theme:' in the file wins !
					 */
					System.out.println("Adding theme...");		// TODO write the name of the theme read
					theme = line.substring(THEME.length()).trim();
					// TODO We need to check whether the theme is 'right' (=it exists)
				}
				else if (!inLevel && line.substring(0, Math.min(LEVEL.length(), line.length())).equals(LEVEL)) {
					/*
					 *  now, we search for the '{'
					 *  which will be on the same line
					 */
					System.out.println("Level text found");
					if (line.substring(LEVEL.length()).trim().equals("{")) {
						inLevel = true;
					}

				}
				else if (inLevel && line.trim().equals("}")) {
					System.out.println("Finished treating level");
					inLevel = false;
				}
				else if (inLevel) {
					char[] lineChars = new char[line.length()];
					for (int i = 0; i < line.length(); i++) {
						lineChars[i] = line.charAt(i);
					}

					tilesList.add(lineNbr, lineChars);
					lineNbr++;
				}
				else {
					System.out.println("Line not treated: \"" + line + "\"");
				}
			}
		}

		/*
		 * Now, we have to convert tilesList, an ArrayList<char[]>
		 * to levelGrid, a char[][]
		 * And the following doesn't work :
		 * levelGrid = (char[][]) tiles_list.toArray();
		 * So either I make it work
		 * Or I code a dedicated conversion function
		 *
		 * Create a char[][] of size tiles_list.size() × max(tiles_list[i].length)
		 */
		int max = 0;
		for (char[] line: tilesList) {
			max = Math.max(line.length, max);
		}
		tileMatrix = new TileType[max][tilesList.size()];
		// then we fill TileMatrix !

		int j;

		for (int i = 0; i < tilesList.size(); i++) {
			j = 0;
			for (char c: tilesList.get(i)) {
				tileMatrix[j][i] = ASSOCIATIONS.get(c);
				j++;
			}
		}
		toLevel();
	}

	/**
	 * @@@ TODO Documentation required
	 *
	 * @return
	 */
	public void toLevel() {
		// Variables used
		int max_i = tileMatrix.length - 1; // width of the char[][]
		int max_j = tileMatrix[0].length - 1; // height of the char[][]

		/*
		 * Now, the objects...
		 */
		// Images :
		/*
		 *  We've got the theme
		 *  So we load the corresponding images and make some imageViews
		 */
		// We get the TILE_SIZE
		float tileSize = Tile.TILE_SIZE;
		//System.out.println(theme);

		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAA resources/tiles/ +\n" + 
								theme + "/tile-plain.png");
		
		//SQUARE
		ImageView tilePlain = new ImageView(new Image("resources/tiles/" +
				theme + "/tile-plain.png", tileSize, tileSize, false, false));
		// surface-simple
		ImageView tileSurfaceTop = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-top.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceDown= new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-down.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceLeft = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-left.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceRight = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-right.png", tileSize, tileSize, false, false));
		// surface-double
		ImageView tileSurfaceDoubleDownLeft = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-double-down-left.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceDoubleDownRight = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-double-down-right.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceDoubleLeftRight = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-double-left-right.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceDoubleTopDown = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-double-top-down.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceDoubleTopLeft = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-double-top-left.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceDoubleTopRight = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-double-top-right.png", tileSize, tileSize, false, false));
		// surface-triple
		ImageView tileSurfaceTripleDown = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-triple-down.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceTripleTop = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-triple-top.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceTripleLeft = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-triple-left.png", tileSize, tileSize, false, false));
		ImageView tileSurfaceTripleRight = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-triple-right.png", tileSize, tileSize, false, false));
		// surface-quadruple
		ImageView tileSurfaceQuadruple = new ImageView(new Image("resources/tiles/"
				+ theme + "/tile-surface-quadruple.png", tileSize, tileSize, false, false));

		// TRIANGLE
		ImageView tileTriangleDownRight = new ImageView(new Image("resources/tiles/"
				+ theme + "/triangle-down-right.png", tileSize, tileSize, false, false));
		ImageView tileTriangleDownLeft= new ImageView(new Image("resources/tiles/"
				+ theme + "/triangle-down-left.png", tileSize, tileSize, false, false));
		ImageView tileTriangleTopLeft = new ImageView(new Image("resources/tiles/"
				+ theme + "/triangle-top-left.png", tileSize, tileSize, false, false));
		ImageView tileTriangleTopRight = new ImageView(new Image("resources/tiles/"
				+ theme + "/triangle-top-right.png", tileSize, tileSize, false, false));


		List<Tile> listTiles = new ArrayList<Tile>();
		// the following tile won't be used, it's just so that the IDE won't yell
		Tile tile = new Tile(0, 0, tileSurfaceTop, TileType.SQUARE);

		for (int i = 0; i < max_i + 1; i++) {
			for (int j = 0; j < max_j + 1; j++) {
				TileType type;
				try {
					type = tileMatrix[i][j];
				} catch (NullPointerException exception) {
					type = TileType.EMPTY;
				}

				boolean tilefound = false;
				if (type == TileType.SQUARE) { // square
					/*
					 * we need to check the surroundings, to see how it connects
					 * So we will consider all its nine surroundings :
					 * ...
					 * .x.
					 * ...
					 * And if we only use ifs, it will be a lot of them.
					 * So we need a more clever solution
					 * 1. We could generate a file in which every solution would be written
					 * 2. But if we can generate the file, then we can just not use a file !
					 *
					 * Method :
					 * first, count number of direct neighbours (not in diagonal)
					 * case 0: quadruple, easy
					 * case 1: triple, depends on the neighbour
					 * case 2: double
					 * case 3: surface
					 */

					int nbrDirectNeighbours = 0;
					// So, we make a list/array of neighbours
					List<Direction> neighbours = new ArrayList<Direction>();
					/*
					 *  if we assume the tile not to be on an edge
					 *  Solutions :
					 *  https://stackoverflow.com/questions/4120609/more-efficient-way-to-check-neighbours-in-a-two-dimensional-array-in-java#5802694
					 */
					int colStart  = Math.max( i - 1, 0    );
					int colFinish = Math.min( i + 1, max_i);
					int rowStart  = Math.max( j - 1, 0    );
					int rowFinish = Math.min( j + 1, max_j);

					for (int curRow = rowStart; curRow <= rowFinish; curRow++ ) {
						for (int curCol = colStart; curCol <= colFinish; curCol++ ) {

							/*
							 * The if condition is a bit messy
							 * First, we check that we are on a direct neighbour
							 * then, we check that the neighbour is not empty
							 */
							if (((curRow == j && curCol != i) ||
									(curRow != j && curCol == i))
									&& (tileMatrix[curCol][curRow] != TileType.EMPTY)) {
								nbrDirectNeighbours ++;
								// then we detect where the neighbours are
								if (curRow > j) {
									neighbours.add(Direction.DOWN);
								}
								else if (curRow < j) {
									neighbours.add(Direction.UP);
								}
								else if (curCol < i) {
									neighbours.add(Direction.LEFT);
								}
								else if (curCol > i) {
									neighbours.add(Direction.RIGHT);
								}
							}
						}
					}
					switch (nbrDirectNeighbours) {
					case 0:
						tile = new Tile(i, j, tileSurfaceQuadruple, TileType.SQUARE);
						break;
					case 1:
						/*
						 * Supposing X is the char we're working onto :
						 * x
						 * xX
						 *
						 * We've got to add a collider
						 * line to the side of the tile
						 * opposed to the one it's attached
						 * to
						 * And one to the side with which
						 * there is an angle
						 * And extend the last one
						 */
						Direction n = neighbours.get(0);
						switch (n) {
						case UP:
							tile = new Tile(i, j, tileSurfaceTripleTop, TileType.SQUARE);
							break;
						case DOWN:
							tile = new Tile(i, j, tileSurfaceTripleDown, TileType.SQUARE);
							break;
						case LEFT:
							tile = new Tile(i, j, tileSurfaceTripleLeft, TileType.SQUARE);
							break;
						case RIGHT:
							tile = new Tile(i, j, tileSurfaceTripleRight, TileType.SQUARE);
							break;
						}
						break;
					case 2:
						/*
						 * Not a hard problem for the textures, we just need to make 6 cases
						 */
						tile = new Tile(i, j, tileSurfaceDoubleDownRight, TileType.SQUARE);
						List<Direction> temp2 = new ArrayList<Direction>(4);
						temp2.add(Direction.UP);
						temp2.add(Direction.DOWN);
						temp2.add(Direction.LEFT);
						temp2.add(Direction.RIGHT);
						for (int nIndex = 0; nIndex < 2; nIndex++) {
							temp2.remove(neighbours.get(nIndex));
						}
						if ((temp2.get(0) == Direction.UP
								&& temp2.get(1) == Direction.DOWN)
								|| (temp2.get(0) == Direction.DOWN
								&& temp2.get(1) == Direction.UP)) {

							tile = new Tile(i, j, tileSurfaceDoubleTopDown, TileType.SQUARE);
						}
						else if ((temp2.get(0) == Direction.UP
								&& temp2.get(1) == Direction.RIGHT)
								|| (temp2.get(0) == Direction.RIGHT
								&& temp2.get(1) == Direction.UP)) {

							tile = new Tile(i, j, tileSurfaceDoubleTopRight, TileType.SQUARE);
						}
						else if ((temp2.get(0) == Direction.UP
								&& temp2.get(1) == Direction.LEFT)
								|| (temp2.get(0) == Direction.LEFT
								&& temp2.get(1) == Direction.UP)) {

							tile = new Tile(i, j, tileSurfaceDoubleTopLeft, TileType.SQUARE);
						}
						else if ((temp2.get(0) == Direction.DOWN
								&& temp2.get(1) == Direction.RIGHT)
								|| (temp2.get(0) == Direction.RIGHT
								&& temp2.get(1) == Direction.DOWN)) {

							tile = new Tile(i, j, tileSurfaceDoubleDownRight, TileType.SQUARE);
						}
						else if ((temp2.get(0) == Direction.DOWN
								&& temp2.get(1) == Direction.LEFT)
								|| (temp2.get(0) == Direction.LEFT
								&& temp2.get(1) == Direction.DOWN)) {

							tile = new Tile(i, j, tileSurfaceDoubleDownLeft, TileType.SQUARE);
						}
						if ((temp2.get(0) == Direction.RIGHT
								&& temp2.get(1) == Direction.LEFT)
								|| (temp2.get(0) == Direction.LEFT
								&& temp2.get(1) == Direction.RIGHT)) {

							tile = new Tile(i, j, tileSurfaceDoubleLeftRight, TileType.SQUARE);
						}
						break;
					case 3:
						//List<Direction> temp3 = new ArrayList<Direction>(4);
						EnumSet<Direction> temp3 = EnumSet.of(
								Direction.UP, Direction.DOWN,
								Direction.LEFT, Direction.RIGHT);
						for (int nIndex = 0; nIndex < 3; nIndex++) {
							temp3.remove(neighbours.get(nIndex));
						}
						if (temp3.contains(Direction.UP)) {
							tile = new Tile(i, j, tileSurfaceTop, TileType.SQUARE);
						}
						else if (temp3.contains(Direction.DOWN)) {
							tile = new Tile(i, j, tileSurfaceDown, TileType.SQUARE);
						}
						else if (temp3.contains(Direction.LEFT)) {
							tile = new Tile(i, j, tileSurfaceLeft, TileType.SQUARE);
						}
						else if (temp3.contains(Direction.RIGHT)) {
							tile = new Tile(i, j, tileSurfaceRight, TileType.SQUARE);
						}
						/*
						 * xxx
						 * xX
						 * xx
						 *
						 * We need to add/extend one collider
						 */
						break;
					case 4:
						tile = new Tile(i, j, tilePlain, TileType.SQUARE);
						// and we do not need to add a collider
						break;
					}
					tilefound = true;
				}
				else if (type == TileType.TRIANGLE_DOWN_RIGHT) {
					tile = new Tile(i, j, tileTriangleDownRight, TileType.TRIANGLE_DOWN_RIGHT);
					tilefound = true;
				}
				else if (type == TileType.TRIANGLE_DOWN_LEFT) {
					tile = new Tile(i, j, tileTriangleDownLeft, TileType.TRIANGLE_DOWN_LEFT);
					tilefound = true;
				}
				else if (type == TileType.TRIANGLE_TOP_LEFT) {
					tile = new Tile(i, j, tileTriangleTopLeft, TileType.TRIANGLE_TOP_LEFT);
					tilefound = true;
				}
				else if (type == TileType.TRIANGLE_TOP_RIGHT) {
					tile = new Tile(i, j, tileTriangleTopRight, TileType.TRIANGLE_TOP_RIGHT);
					tilefound = true;
				}
				else {
					//System.out.println("Char '" + c + "' ignored");
				}

				if (tilefound) {
					//System.out.println("Char '" + c + "' not ignored");
					listTiles.add(tile);
				}
			}
		}
		System.out.println(listTiles.getClass().getName());
		System.out.println(listTiles.toArray().getClass().getName());

		/*
		 * Ce serait bien de faire
		 * level.tiles = (Tile[]) list_tiles.toArray();
		 */

		tileList = new Tile[listTiles.size()];
		for (int i = 0; i < listTiles.size(); i++) {
			tileList[i] = listTiles.get(i);
		}
	}



	@Override public String toString() {		// Illisible...
		String result = "";
		for (TileType[] typeLine: tileMatrix) {
			for (TileType type: typeLine) {
				result += type;
			}
			result += System.lineSeparator();
		}
		return result;
	}



}
