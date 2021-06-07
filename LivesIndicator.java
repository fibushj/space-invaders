import java.awt.Color;

import biuoop.DrawSurface;

/**
 * The Class LivesIndicator.
 *
 * @author Jonathan Fibush
 */
public class LivesIndicator implements Sprite {

    /** The number of lives. */
    private Counter numberOfLives;

    /**
     * Instantiates a new lives indicator.
     *
     * @param numberOfLives
     *            the number of lives
     */
    public LivesIndicator(Counter numberOfLives) {
        this.numberOfLives = numberOfLives;
    }

    /**
     * Draws the number of lives left.
     *
     * @param d
     *            the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(100, 17, "Lives: " + this.numberOfLives.getValue(), 15);
    }

    @Override
    public void timePassed(double dt) {
    }

}
