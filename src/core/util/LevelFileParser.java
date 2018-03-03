/**
 *
 */
package core.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import levels.Level;

/**
 * @author matthias
 *
 */
public class LevelFileParser {

	private static int tilesize;

	private static char[][] level_text;

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
		 * Now the parsing begins
		 * What do we do ?
		 * we read each line
		 * if it begins with #, we ignore it
		 * if it begins with tilesize: we add the tilesize
		 * the level is 'level:' and then enclosed in brackets
		 */
		// the size of the attribute tilesize
		int tilesizelength = "tilesize:".length();
		int levellength = "level:".length();

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
				System.out.println("-------------------");
				System.out.println(line);
				System.out.println("In level: " + inLevel);

				if (!inLevel && line.substring(0, Math.min(tilesizelength, line.length())).equals("tilesize:")) {
					/*
					 *  the end of the substring should be the end of the line
					 *  and after, we strip the blanks from it with trim()
					 *  And then, we convert it to an Integer !
					 *
					 *  As we can see, the last 'tilesize:' in the file wins !
					 */
					System.out.println("Adding tilesize...");
					tilesize = Integer.parseInt(line.substring(tilesizelength).trim());
				}
				//TODO Need to manage the case of 'level:'
				else if (!inLevel && line.substring(0, Math.min(levellength, line.length())).equals("level:")) {
					/*
					 *  now, we search for the '{'
					 *  which will be on the same line
					 */
					System.out.println("Level text found");
					if (line.substring(levellength).trim().equals("{")) {
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
						System.out.println("char " + line.charAt(i) + " (" +
					line_nbr + "," + i + ")");
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
		 * to level_text, a char[][]
		 * And the following doesn't work :
		 * level_text = (char[][]) tiles_list.toArray();
		 * So either I make it work
		 * Or I code a dedicated conversion function
		 *
		 * Create a char[][] of size tiles_list.size() × max(tiles_list[i].length)
		 */
		int max = 0;
		for (char[] line: tiles_list) {
			max = Math.max(line.length, max);
		}
		level_text = new char[tiles_list.size()][max];
		// then we fill level_text !
		int j;
		for (int i = 0; i < tiles_list.size(); i++) {
			j = 0;
			for (char c: tiles_list.get(i)) {
				level_text[i][j] = c;
				j++;
			}
		}
	}

	public Level level() {
		return new Level();
	}

	public String toString() {
		String level_parser_txt = "";
		String line;
		for (char[] charline: level_text) {
			line = "";
			for (char c: charline) {
				line = line + c;
			}
			level_parser_txt = level_parser_txt + "\n" + line;
		}
		return level_parser_txt;
	}

}
