package content.chess.pieces;

import content.GameObject;
import content.Layer;
import content.Tag;
import core.util.Collider;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * The superclass for all chess pieces.
 * 
 * @author Raph
 *
 */
public abstract class ChessPiece extends GameObject {

	public int x;
	public int y;


	public ChessPiece(int x, int y, Image sprite) {
		super(null,
				sprite,
				Layer.DEFAULT,
				Tag.DEFAULT,
				Collider.NO_COLLIDER());

		this.x = x;
		this.y = y;
	}



	
	
	/**
	 * Move this piece to the new coordinates (x, y)
	 * 
	 * @param x
	 * @param y
	 */
	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}




	@Override
	public void render(GraphicsContext gc, double windowWidth, double windowHeight) {




	}



}
