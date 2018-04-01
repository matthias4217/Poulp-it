package levels;

import content.Tile;

/**
 * @author matthias
 *
 * Obsolete, won't be used
 *
 */
public class Level {
	public Tile[] tiles;
	public String theme;
	/**
	 * matrix of TileType
	 * raycast tests only on the cases in which the player is
	 * (we know the coords)
	 * in GameEngine : dict of what GameObject is in which case
	 */
}
