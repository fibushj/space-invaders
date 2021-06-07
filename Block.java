import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * The Class Block.
 *
 * @author Jonathan Fibush
 */

public class Block implements Collidable, Sprite, HitNotifier {

    /** The rect. */
    private Rectangle rect;

    /** The hit listeners list. */
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * Instantiates a new block.
     *
     * @param rect
     *            the rectangle
     */
    public Block(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return rect;
    }

    @Override
    public void hit(Shot hitter) {
        this.notifyHit(hitter);
    }

    @Override
    public void drawOn(DrawSurface d) {
        int upLeftX = (int) this.rect.getUpperLeft().getX();
        int upLeftY = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        d.setColor(Color.cyan);
        d.fillRectangle(upLeftX, upLeftY, width, height);
        d.drawRectangle(upLeftX, upLeftY, width, height);
    }

    @Override
    public void timePassed(double dt) {
    }

    /**
     * Adds the block to the game.
     *
     * @param g
     *            the game
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    @Override
    public void removeFromGame(Game g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        if (this.hitListeners.contains(hl)) {
            this.hitListeners.remove(hl);
        }
    }

    /**
     * Notifies all of the registered HitListener objects about the hit.
     *
     * @param hitter
     *            the hitting ball
     */
    private void notifyHit(Shot hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
