import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The Class Spaceship.
 *
 * @author Jonathan Fibush
 */

public class Spaceship implements Sprite, Collidable, HitNotifier {

    /** The rectangle. */
    private Rectangle rect;

    /** The left border. */
    private double leftBorder;

    /** The right border. */
    private double rightBorder;

    /** The keyboardSensor. */
    private KeyboardSensor keyboardSensor;

    /** The speed. */
    private int speed;

    /** The hit listeners list. */
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /** Indicates if the spaceship is hit by a shot. */
    private boolean isSpaceshipHit;

    /**
     * Instantiates a new spaceship.
     *
     * @param rect
     *            the rectangle
     * @param rightBorder
     *            the right border
     * @param keyboardSensor
     *            the keyboard sensor
     * @param speed
     *            the speed
     */
    public Spaceship(Rectangle rect, double rightBorder, KeyboardSensor keyboardSensor, int speed) {
        this.rect = rect;
        this.leftBorder = 0;
        this.rightBorder = rightBorder;
        this.keyboardSensor = keyboardSensor;
        this.speed = speed;
        this.isSpaceshipHit = false;
    }

    /**
     * Moves the spaceship left.
     *
     * @param dt
     *            the dt
     */
    public void moveLeft(double dt) {
        this.rect
                .setUpperLeft(new Point(this.rect.getUpperLeft().getX() - dt * speed, this.rect.getUpperLeft().getY()));
    }

    /**
     * Moves the spaceship right.
     *
     * @param dt
     *            the dt
     */
    public void moveRight(double dt) {
        this.rect
                .setUpperLeft(new Point(this.rect.getUpperLeft().getX() + dt * speed, this.rect.getUpperLeft().getY()));
    }

    /**
     * Checks the state of the arrow keys and moves the spaceship accordingly.
     *
     * @param dt
     *            the dt
     */
    @Override
    public void timePassed(double dt) {
        if (this.keyboardSensor.isPressed("left")) {
            // checking if there's space for the spaceship to move left
            if (this.rect.getUpperLeft().getX() >= this.leftBorder + 7) {
                moveLeft(dt);
            } else {
                this.rect.setUpperLeft(new Point(this.leftBorder, this.rect.getUpperLeft().getY()));
            }
        }
        if (this.keyboardSensor.isPressed("right")) {
            // checking if there's space for the spaceship to move right
            if (this.rect.getUpperLeft().getX() + this.rect.getWidth() <= this.rightBorder - 7) {
                moveRight(dt);
            } else {
                this.rect.setUpperLeft(
                        new Point(this.rightBorder - this.rect.getWidth(), this.rect.getUpperLeft().getY()));
            }

        }
    }

    /**
     * Draws the spaceship on the surface.
     *
     * @param d
     *            the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        int upLeftX = (int) this.rect.getUpperLeft().getX();
        int upLeftY = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int height = (int) this.rect.getHeight();
        d.setColor(java.awt.Color.ORANGE);
        d.fillRectangle(upLeftX, upLeftY, width, height);
        d.setColor(java.awt.Color.BLACK);
        d.drawRectangle(upLeftX, upLeftY, width, height);
    }

    /**
     * Returns the collision rectangle of the spaceship.
     *
     * @return the collision rectangle
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Adds the spaceship to the game.
     *
     * @param g
     *            the game
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl
     *            the listener
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl
     *            the listener
     */
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
    public void removeFromGame(Game game) {
    }

    @Override
    public void hit(Shot hitter) {
        this.notifyHit(hitter);
        this.isSpaceshipHit = true;
    }

    /**
     * Checks if the spaceship is hit.
     *
     * @return true, if is spaceship hit
     */
    public boolean isSpaceshipHit() {
        if (this.isSpaceshipHit) {
            this.isSpaceshipHit = false;
            return true;
        }
        return false;
    }

}