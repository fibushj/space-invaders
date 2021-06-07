
import java.awt.Color;

import biuoop.DrawSurface;

/**
 * The CountdownAnimation will display the given gameScreen, for numOfSeconds
 * seconds, and on top of them it will show a countdown from countFrom back to
 * 1, where each number will is replaced with the next one.
 *
 * @author Jonathan Fibush
 *
 */

public class CountdownAnimation implements Animation {

    /** The number from which the count starts. */
    private int countFrom;

    /** The sprite collection. */
    private SpriteCollection gameScreen;

    /** The duration of the appearance of each number in the countdown. */
    private double duration;

    /** Stop indicator. */
    private boolean stop = false;

    /** Indicator for the first time that a number is printed in the countdown. */
    private boolean firstTime;

    /** The start time of a number in the countdown in milliseconds. */
    private long startTime;

    /**
     * Instantiates a new countdown animation.
     *
     * @param numOfSeconds
     *            the duration of the animation in seconds
     * @param countFrom
     *            the number from which the count starts
     * @param gameScreen
     *            the sprite collection
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.duration = numOfSeconds / countFrom;
        this.firstTime = true;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // updating the stop variable in case the countdown has finished
        if (countFrom < 1) {
            this.stop = true;
        }
        gameScreen.drawAllOn(d);
        d.setColor(Color.WHITE);
        // printing the count
        d.drawText(d.getWidth() / 2, d.getHeight() / 2 + 100, Integer.toString(countFrom), 50);
        if (this.firstTime) {
            this.startTime = System.currentTimeMillis();
            this.firstTime = false;
        } else if (1000 * this.duration < System.currentTimeMillis() - this.startTime) {
            // updating the count
            this.countFrom--;
            // updating the start time
            this.startTime = System.currentTimeMillis();
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}