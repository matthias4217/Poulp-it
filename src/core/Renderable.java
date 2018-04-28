package core;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface implemented by all elements that can be rendered on screen
 * 
 * @author Raph
 *
 */
public interface Renderable {

	public void render(GraphicsContext gc, double windowWidth, double windowHeight);

}
