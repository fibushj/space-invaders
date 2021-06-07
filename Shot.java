import java.awt.Color;

import biuoop.DrawSurface;

/**
 * The Class Shot.
 *
 * @author Jonathan Fibush
 */
public class Shot implements Sprite {

    /** The center point of the shot. */
    private Point center;

    /** The size. */
    private int size;

    /** The color. */
    private java.awt.Color color;

    /** The velocity. */
    private Velocity velocity;

    /** The environment. */
    private GameEnvironment environment;

    /**
     * The variable indicates by whom the shot was shot - if by the spaceship, the
     * value is true; if by an alien, the value is false.
     */
    private boolean isShotBySpaceship;

    /**
     * Instantiates a new shot.
     *
     * @param center
     *            point
     * @param environment
     *            the environment
     * @param isShotBySpaceship
     *            the is shot by spaceship
     */
    public Shot(Point center, GameEnvironment environment, boolean isShotBySpaceship) {
        this.center = center;
        this.environment = environment;
        this.isShotBySpaceship = isShotBySpaceship;
        // differentiating between the 2 cases in terms of color, size and velocity
        if (isShotBySpaceship) {
            this.color = Color.white;
            this.size = 3;
            this.velocity = new Velocity(0, -550);
        } else {
            this.color = Color.red;
            this.size = 5;
            this.velocity = new Velocity(0, 350);
        }
    }

    /**
     * Returns the x value of the center.
     *
     * @return x
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Returns the y value of the center.
     *
     * @return y
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Returns the size of the shot.
     *
     * @return size
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the color of the shot.
     *
     * @return color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Checks if the shot was shot by the spaceship.
     *
     * @return true, if is shot by the spaceship
     */
    public boolean isShotBySpaceship() {
        return isShotBySpaceship;
    }

    /**
     * Draws the shot on the received board.
     *
     * @param d
     *            the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle(this.getX(), this.getY(), this.size);
        d.setColor(Color.BLACK);
        d.drawCircle(this.getX(), this.getY(), this.size);
    }

    /**
     * Returns the velocity of the shot.
     *
     * @return velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Moving the shot one step in his current velocity.
     *
     * @param dt
     *            the dt
     */
    public void moveOneStep(double dt) {
        Velocity v = new Velocity(dt * this.velocity.getdx(), dt * this.velocity.getdy());
        Point start = this.center, end = v.applyToPoint(this.center);
        Line trajectory = new Line(start, end);
        CollisionInfo info = this.environment.getClosestCollision(trajectory);
        // if collision isn't going to occur
        if (info == null) {
            this.center = v.applyToPoint(this.center);
            // if collision is going to occur
        } else {
            info.collisionObject().hit(this);
        }

    }

    @Override
    public void timePassed(double dt) {
        this.moveOneStep(dt);
    }

    /**
     * Adds the shot to the game.
     *
     * @param g
     *            the ball
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    /**
     * Sets the y coordinate of the center.
     *
     * @param y
     *            the new y center
     */
    public void setYCenter(int y) {
        this.center.setY(y);
    }

    /**
     * Removes the shot from the game.
     *
     * @param g
     *            the game
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
        g.removeFromShotList(this);
    }

}