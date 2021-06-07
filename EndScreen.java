import java.awt.Color;

import biuoop.DrawSurface;

/**
 * The Class EndScreen.
 *
 * @author Jonathan Fibush
 */
public class EndScreen implements Animation {

    /** The message. */
    private int score;

    /**
     * Instantiates a new pause screen.
     *
     * @param score
     *            the message
     */
    public EndScreen(int score) {
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.lightGray);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.BLACK);
        // printing the appropriate messages
        d.drawText(d.getWidth() / 2 - 120, d.getHeight() / 4, "You Lost", 50);
        d.drawText(d.getWidth() / 2 - 170, 3 * d.getHeight() / 4, "Press space to continue", 30);
        d.drawText(10, 590, "Final score: " + this.score, 15);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}