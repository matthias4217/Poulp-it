package core.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import content.GameObject;
import content.Tile;
import content.Tile.TileType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import levels.Level;

/**
 * Parser object designed to transform a level as a txt file into a Level object
 * 
 * @author matthias
 * 
 */
public class LevelFileParser {

	private static String theme;

	public static InfoTile[][] levelGrid;
	
	/**
	 * The HashMap used to link the txt file to the game representation
	 */
	private static HashMap<Character, TileType> association = new HashMap<Character, TileType>();

	
	
	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	public LevelFileParser(String file) throws IOException {
		/*
		 * open the file
		 * https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java#4716623
		 */
		Path file_path = FileSystems.getDefault().getPath(file);
		System.out.println("Path to the level: " + file_path);
		List<String> stringArray;
		stringArray = Files.readAllLines(file_path);
		System.out.println(stringArray);

		/*
		 * Now the parsing begins (dramatic music)
		 * What do we do ?
		 * we read each line
		 * if it begins with #, we ignore it
		 * if it begins with tilesize: we add the tilesize
		 * the level is 'level:' and then enclosed in brackets
		 */
		// The size of the attributes recognized
		int levelLength = "level:".length();
		int themeLength = "theme:".length();

		/*
		 * false if not between the brackets,
		 * true if in
		 */
		boolean inLevel = false;
		int line_nbr = 0;
		List<char[]> tiles_list = new ArrayList<char[]>();

		System.out.println("Beginning level parsing...");
		for (String line: stringArray) {
			if (line.trim().charAt(0) != '#') {
				/*
				 * System.out.println("-------------------");
				 * System.out.println(line);
				 * System.out.println("In level: " + inLevel);
				 */

				if (!inLevel && line.substring(0, Math.min(themeLength, line.length())).equals("theme:")) {
					/*
					 *  the end of the substring should be the end of the line
					 *  and after, we strip the blanks from it with trim()
					 *
					 *  As we can see, the last 'theme:' in the file wins !
					 */
					System.out.println("Adding theme...");
					theme = line.substring(themeLength).trim();
					//TODO We need to check whether the theme is 'right' (=it exists)
				}
				else if (!inLevel && line.substring(0, Math.min(levelLength, line.length())).equals("level:")) {
					/*
					 *  now, we search for the '{'
					 *  which will be on the same line
					 */
					System.out.println("Level text found");
					if (line.substring(levelLength).trim().equals("{")) {
						inLevel = true;
					}

				}
				else if (inLevel && line.trim().equals("}")) { // we are in the level
					System.out.println("Finished treating level");
					inLevel = false;
					}
				else if (inLevel) {
					char[] line_chars = new char[line.length()];
					for (int i = 0; i < line.length(); i++) {
						/* System.out.println("char " + line.charAt(i) + " (" +
					* line_nbr + "," + i + ")");
					*/
						// Problem : we *must* define level_text beforehand
						// I guess we should make a list of array, and then convert it ?
						line_chars[i] = line.charAt(i);
						// case
					}

					tiles_list.add(line_nbr, line_chars);
					line_nbr ++;
				}
				else {
					System.out.println("Line not treated :" + line);
				}
			}
		}
		/*
		 * Now, we have to convert tiles_list, an ArrayList<char[]>
		 * to levelGrid, a char[][]
		 * And the following doesn't work :
		 * levelGrid = (char[][]) tiles_list.toArray();
		 * So either I make it work
		 * Or I code a dedicated conversion function
		 *
		 * Create a char[][] of size tiles_list.size() × max(tiles_list[i].length)
		 */
		int max = 0;
		for (char[] line: tiles_list) {
			max = Math.max(line.length, max);
		}
		levelGrid = new InfoTile[tiles_list.size()][max];
		// then we fill levelGrid !
		
		int j;
		
		// Raph: ça m'a l'air moche
		for (int i = 0; i < tiles_list.size(); i++) {
			j = 0;
			for (char c: tiles_list.get(i)) {
				// levelGrid[i][j] = new InfoTile(association.getValue(c));
				switch(c) {
					case 'x':
						levelGrid[i][j] = new InfoTile(TileType.SQUARE);
						break;
					case '/':
						levelGrid[i][j] = new InfoTile(TileType.TRIANGLE_DOWN_RIGHT);
						break;
					case '\\':
						levelGrid[i][j] = new InfoTile(TileType.TRIANGLE_DOWN_LEFT);
						break;
					case 'u':
						levelGrid[i][j] = new InfoTile(TileType.TRIANGLE_TOP_RIGHT);
						break;
					case 'v':
						levelGrid[i][j] = new InfoTile(TileType.TRIANGLE_TOP_LEFT);
						break;					
					default:
						levelGrid[i][j] = new InfoTile(TileType.SQUARE); /// !!!!!!!!!!!!!!!!
						//System.out.println(level_text[i][j]);
				}
				j++;
			}
		}
	}

	/**
	 * @@@ Documentation required
	 * 
	 * @return
	 */
	public Level toLevel() {
		// Variables used
		int max_i = levelGrid.length - 1; // height of the char[][]
		int max_j = levelGrid[0].length - 1; // width of the char[][]

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


		Level level = new Level();
		List<Tile> list_tiles = new ArrayList<Tile>();
		// the following tile won't be used, it's just so that the IDE won't yell
		Tile tile = new Tile(0, 0, tileSurfaceTop, TileType.SQUARE);

		for (int i = 0; i < max_i + 1; i++) {
			for (int j = 0; j < max_j + 1; j++) {
				TileType type;
				try {
					type = levelGrid[i][j].tileType;
				} catch (NullPointerException exception) {
					type = TileType.TRIANGLE_TOP_RIGHT;
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
					List<neighbourPosition> neighbours = new ArrayList<neighbourPosition>();
					/*
					 *  if we assume the tile not to be on an edge
					 *  Solutions :
					 *  https://stackoverflow.com/questions/4120609/more-efficient-way-to-check-neighbours-in-a-two-dimensional-array-in-java#5802694
					 */
					int rowStart  = Math.max( i - 1, 0   );
					int rowFinish = Math.min( i + 1, max_i);
					int colStart  = Math.max( j - 1, 0   );
					int colFinish = Math.min( j + 1, max_j);

					for (int curRow = rowStart; curRow <= rowFinish; curRow++ ) {
					    for (int curCol = colStart; curCol <= colFinish; curCol++ ) {

					    	/*
					    	 * The if condition is a bit messy
					    	 * First, we check that we are on a direct neighbour
					    	 * then, we check that the neighbour is not empty
					    	 */
					        if (((curRow == i && curCol != j) ||
					        		(curRow != i && curCol == j))
					        		&& (levelGrid[curRow][curCol] != null)) {
					        	nbrDirectNeighbours ++;
					        	// then we detect where the neighbours are
					        	if (curRow > i) {
					        		neighbours.add(neighbourPosition.DOWN);
					        	}
					        	else if (curRow < i) {
					        		neighbours.add(neighbourPosition.UP);
					        	}
					        	else if (curCol < j) {
					        		neighbours.add(neighbourPosition.LEFT);
					        	}
					        	else if (curCol > j) {
					        		neighbours.add(neighbourPosition.RIGHT);
					        	}
					        }
					    }
					}
					switch (nbrDirectNeighbours) {
					case 0:
						tile = new Tile(j, i, tileSurfaceQuadruple, TileType.SQUARE);
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
						neighbourPosition n = neighbours.get(0);
						switch (n) {
						case UP:
							tile = new Tile(j, i, tileSurfaceTripleTop, TileType.SQUARE);
							break;
						case DOWN:
							tile = new Tile(j, i, tileSurfaceTripleDown, TileType.SQUARE);
							break;
						case LEFT:
							tile = new Tile(j, i, tileSurfaceTripleLeft, TileType.SQUARE);
							break;
						case RIGHT:
							tile = new Tile(j, i, tileSurfaceTripleRight, TileType.SQUARE);
							break;
						}
						break;
					case 2:
						/*
						 * Not a hard problem for the textures, we just need to make 6 cases
						 */
						tile = new Tile(j, i, tileSurfaceDoubleDownRight, TileType.SQUARE);
						List<neighbourPosition> temp2 = new ArrayList<neighbourPosition>(4);
						temp2.add(neighbourPosition.UP);
						temp2.add(neighbourPosition.DOWN);
						temp2.add(neighbourPosition.LEFT);
						temp2.add(neighbourPosition.RIGHT);
						for (int nIndex = 0; nIndex < 2; nIndex++) {
							temp2.remove(neighbours.get(nIndex));
						}
						if ((temp2.get(0) == neighbourPosition.UP
								&& temp2.get(1) == neighbourPosition.DOWN)
								|| (temp2.get(0) == neighbourPosition.DOWN
										&& temp2.get(1) == neighbourPosition.UP)) {

							tile = new Tile(j, i, tileSurfaceDoubleTopDown, TileType.SQUARE);
						}
						else if ((temp2.get(0) == neighbourPosition.UP
								&& temp2.get(1) == neighbourPosition.RIGHT)
								|| (temp2.get(0) == neighbourPosition.RIGHT
										&& temp2.get(1) == neighbourPosition.UP)) {

							tile = new Tile(j, i, tileSurfaceDoubleTopRight, TileType.SQUARE);
						}
						else if ((temp2.get(0) == neighbourPosition.UP
								&& temp2.get(1) == neighbourPosition.LEFT)
								|| (temp2.get(0) == neighbourPosition.LEFT
										&& temp2.get(1) == neighbourPosition.UP)) {

							tile = new Tile(j, i, tileSurfaceDoubleTopLeft, TileType.SQUARE);
						}
						else if ((temp2.get(0) == neighbourPosition.DOWN
								&& temp2.get(1) == neighbourPosition.RIGHT)
								|| (temp2.get(0) == neighbourPosition.RIGHT
										&& temp2.get(1) == neighbourPosition.DOWN)) {

							tile = new Tile(j, i, tileSurfaceDoubleDownRight, TileType.SQUARE);
						}
						else if ((temp2.get(0) == neighbourPosition.DOWN
								&& temp2.get(1) == neighbourPosition.LEFT)
								|| (temp2.get(0) == neighbourPosition.LEFT
										&& temp2.get(1) == neighbourPosition.DOWN)) {

							tile = new Tile(j, i, tileSurfaceDoubleDownLeft, TileType.SQUARE);
						}
						if ((temp2.get(0) == neighbourPosition.RIGHT
								&& temp2.get(1) == neighbourPosition.LEFT)
								|| (temp2.get(0) == neighbourPosition.LEFT
										&& temp2.get(1) == neighbourPosition.RIGHT)) {

							tile = new Tile(j, i, tileSurfaceDoubleLeftRight, TileType.SQUARE);
						}
						break;
					case 3:
						//List<neighbourPosition> temp3 = new ArrayList<neighbourPosition>(4);
						EnumSet<neighbourPosition> temp3 = EnumSet.of(
								neighbourPosition.UP, neighbourPosition.DOWN,
								neighbourPosition.LEFT, neighbourPosition.RIGHT);
						for (int nIndex = 0; nIndex < 3; nIndex++) {
							temp3.remove(neighbours.get(nIndex));
						}
						if (temp3.contains(neighbourPosition.UP)) {
							tile = new Tile(j, i, tileSurfaceTop, TileType.SQUARE);
						}
						else if (temp3.contains(neighbourPosition.DOWN)) {
							tile = new Tile(j, i, tileSurfaceDown, TileType.SQUARE);
						}
						else if (temp3.contains(neighbourPosition.LEFT)) {
							tile = new Tile(j, i, tileSurfaceLeft, TileType.SQUARE);
						}
						else if (temp3.contains(neighbourPosition.RIGHT)) {
							tile = new Tile(j, i, tileSurfaceRight, TileType.SQUARE);
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
						tile = new Tile(j, i, tilePlain, TileType.SQUARE);
						// and we do not need to add a collider
						break;
					}
					tilefound = true;
				}
				else if (type == TileType.TRIANGLE_DOWN_RIGHT) {
					tile = new Tile(j, i, tileTriangleDownRight, TileType.TRIANGLE_DOWN_RIGHT);
					tilefound = true;
				}
				else if (type == TileType.TRIANGLE_DOWN_LEFT) {
					tile = new Tile(j, i, tileTriangleDownLeft, TileType.TRIANGLE_DOWN_LEFT);
					tilefound = true;
				}
				else if (type == TileType.TRIANGLE_TOP_LEFT) {
					tile = new Tile(j, i, tileTriangleTopLeft, TileType.TRIANGLE_TOP_LEFT);
					tilefound = true;
				}
				else if (type == TileType.TRIANGLE_TOP_RIGHT) {
					tile = new Tile(j, i, tileTriangleTopRight, TileType.TRIANGLE_TOP_RIGHT);
					tilefound = true;
				}
				else {
					//System.out.println("Char '" + c + "' ignored");
				}

				if (tilefound) {
					//System.out.println("Char '" + c + "' not ignored");
					list_tiles.add(tile);
				}
			}
		}
		System.out.println(list_tiles.getClass().getName());
		System.out.println(list_tiles.toArray().getClass().getName());

		/*
		 * Ce serait bien de faire
		 * level.tiles = (Tile[]) list_tiles.toArray();
		 */

		level.tiles = new Tile[list_tiles.size()];
		for (int i = 0; i < list_tiles.size(); i++) {
			level.tiles[i] = list_tiles.get(i);
		}
		return level;
	}

	
	
	@Override
	public String toString() {
		String level_parser_txt = "";
		String line;
		for (InfoTile[] typeline: levelGrid) {
			line = "";
			for (InfoTile type: typeline) {
				line = line + type.tileType;
			}
			level_parser_txt = level_parser_txt + "\n" + line;
		}
		return level_parser_txt;
	}

	
	
	// For now, no indirect (on a diagonal) neighbour
	/**
	 * @@@ doc
	 * 
	 * @author matthias
	 *
	 */
	private enum neighbourPosition {
		UP, DOWN, RIGHT, LEFT
	}
}
