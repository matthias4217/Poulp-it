/**
 *
 */
package core.util;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
					inLevel = false;
					}
				else if (inLevel) {
					for (int i = 0; i < line.length(); i++) {
						System.out.println("char " + line.charAt(i) + " (" +
					line_nbr + "," + i + ")");
						// Problem : we *must* define level_text beforehand
						level_text[line_nbr][i] = line.charAt(i);
						// case
					}
					line_nbr ++;
				}
				else {
					System.out.println("Line not treated :" + line);
				}
			}
		}
	}

	public Level level() {
		return new Level();
	}

	public String toString() {
		return level_text.toString();
	}

}
