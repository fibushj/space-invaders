import java.awt.Color;

import biuoop.DrawSurface;

/**
 * The Class ScoreIndicator.
 *
 * @author Jonathan Fibush
 */
public class ScoreIndicator implements Sprite {

    /** The score. */
    private Counter score;

    /**
     * Instantiates a new score indicator.
     *
     * @param score the score
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }

    /**
     * Draws the current score.
     *
     * @param d
     *            the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 20);
        d.setColor(Color.WHITE);
        d.drawRectangle(0, 0, 800, 20);
        d.setColor(Color.BLACK);
        //printing the score
        d.drawText(300, 17, "Score: " + this.score.getValue(), 15);
    }

    @Override
    public void timePassed(double dt) {
    }
}
