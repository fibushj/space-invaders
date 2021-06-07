import java.awt.Color;

import biuoop.DrawSurface;

/**
 * The Class PauseScreen.
 *
 * @author Jonathan Fibush
 */
public class PauseScreen implements Animation {

    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        d.setColor(Color.GRAY);
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(Color.BLACK);
        // printing the message
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}