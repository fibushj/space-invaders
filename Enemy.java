import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * The Class Enemy.
 *
 * @author Jonathan Fibush
 */
public class Enemy implements Collidable, Sprite, HitNotifier {

    /** The upper left point of the enemy. */
    private Point upperLeft;

    /** The image of the enemy. */
    private Image image;

    /** The velocity of the enemy. */
    private Velocity velocity;

    /** The rectangle that comprises the enemy. */
    private Rectangle rectangle;

    /** The hit listeners list. */
    private List<HitListener> hitListeners;

    /** The enemy width. */
    private final int enemyWidth = 40;

    /** The enemy height. */
    private final int enemyHeight = 30;

    /**
     * Instantiates a new enemy.
     *
     * @param upperLeft
     *            the upper left point
     * @param velocity
     *            the velocity
     */
    public Enemy(Point upperLeft, Velocity velocity) {
        this.upperLeft = upperLeft;
        this.rectangle = new Rectangle(this.upperLeft, enemyWidth, enemyHeight);
        this.velocity = velocity;
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Sets the velocity.
     *
     * @param newVelocity
     *            the new velocity
     */
    public void setVelocity(Velocity newVelocity) {
        this.velocity = newVelocity;
    }

    /**
     * Sets the position of the upper left point.
     *
     * @param newUpperLeft
     *            the new position
     */
    public void setPosition(Point newUpperLeft) {
        this.upperLeft = newUpperLeft;
        this.rectangle.setUpperLeft(newUpperLeft);
    }

    /**
     * Sets the image.
     *
     * @param newImage
     *            the image
     */
    public void setImage(Image newImage) {
        this.image = newImage;
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

    @Override
    public void drawOn(DrawSurface d) {
        d.drawImage((int) this.upperLeft.getX(), (int) this.upperLeft.getY(), this.image);
    }

    @Override
    public void timePassed(double dt) {
        Velocity v = new Velocity(dt * this.velocity.getdx(), 0);
        this.upperLeft = v.applyToPoint(this.upperLeft);
        this.rectangle.setUpperLeft(this.upperLeft);
    }

    /**
     * . Adds the enemy to the game
     *
     * @param g
     *            the game
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Removes the enemy from the game.
     *
     * @param g
     *            the game
     */
    @Override
    public void removeFromGame(Game g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    @Override
    public void hit(Shot hitter) {
        this.notifyHit(hitter);
    }

}
