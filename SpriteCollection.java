import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * The Class SpriteCollection.
 *
 * @author Jonathan Fibush
 */
public class SpriteCollection {

    /** The sprites. */
    private List<Sprite> sprites = new ArrayList<Sprite>();

    /**
     * Adds the sprite to the collection.
     *
     * @param s
     *            the s
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Callss timePassed() on all sprites.
     *
     * @param dt
     *            the dt
     */
    public void notifyAllTimePassed(double dt) {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).timePassed(dt);
        }
    }

    /**
     * Draws all sprites to the screen.
     *
     * @param d
     *            the d
     */
    public void drawAllOn(DrawSurface d) {
        for (int i = 0; i < this.sprites.size(); i++) {
            this.sprites.get(i).drawOn(d);
        }
    }

    /**
     * Removes the sprite from the list.
     *
     * @param s
     *            the sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}