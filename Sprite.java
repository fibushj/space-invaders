import biuoop.DrawSurface;

/**
 * The Interface Sprite.
 *
 * @author Jonathan Fibush
 */
public interface Sprite {

    /**
     * Draws the sprite to the screen.
     *
     * @param d
     *            the surface
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that time has passed.
     *
     * @param dt
     *            the dt
     */
    void timePassed(double dt);
}
