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

	private static String level_text;

	LevelFileParser(String file) throws IOException {
		/*
		 * open the file
		 * https://stackoverflow.com/questions/4716503/reading-a-plain-text-file-in-java#4716623
		 */
		Path file_path = FileSystems.getDefault().getPath(file);
		List<String> stringArray;
		stringArray = Files.readAllLines(file_path);

		/*
		 * Now the parsing begins
		 * What do we do ?
		 * we read each line
		 * if it begins with #, we ignore it
		 * if it begins with tilesize: we add the tilesize
		 * the level is 'level:' and then enclosed in brackets
		 */
		int tilesizelength = "tilesize:".length();
		for (String line: stringArray) {
			if (line.substring(0,1) != "#") {
				if (line.substring(0, tilesizelength) == "tilesize:") {
					/*
					 *  the end of the substring should be the end of the line
					 *  and after, we strip the blanks from it with trim()
					 *  And then, we convert it to an Integer !
					 *
					 *  As we can see, the last 'tilesize:' in the file wins !
					 */
					tilesize = Integer.parseInt(line.substring(tilesizelength).trim());
				}
				//TODO Need to manage the case of 'level:'
			}
		}
	}

	public Level level() {
		return new Level();
	}

}
