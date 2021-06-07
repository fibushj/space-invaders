import java.awt.Color;

import biuoop.DrawSurface;

/**
 * The Class LevelIndicator.
 *
 * @author Jonathan Fibush
 */
public class LevelIndicator implements Sprite {

    /** The name of the level. */
    private String levelName;

    /**
     * Instantiates a new level indicator.
     *
     * @param levelName
     *            the name of the level
     */
    public LevelIndicator(String levelName) {
        this.levelName = levelName;
    }

    /**
     * Draws the name of the level.
     *
     * @param d
     *            the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(500, 17, "Level Name: " + this.levelName, 15);
    }

    @Override
    public void timePassed(double dt) {
    }

}
