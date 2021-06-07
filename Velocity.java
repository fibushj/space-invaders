/**
 * The Class Velocity.
 *
 * @author Jonathan Fibush
 */
public class Velocity {

    /** The dx. */
    private double dx;

    /** The dy. */
    private double dy;

    /**
     * . Constructor
     *
     * @param dx
     *            value
     * @param dy
     *            value
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * . Return delta x
     *
     * @return dx
     */
    public double getdx() {
        return this.dx;
    }

    /**
     * . Return delta y
     *
     * @return dy
     */
    public double getdy() {
        return this.dy;
    }

    /**
     * Sets the dx.
     *
     * @param newDx
     *            the new dx
     */
    public void setdx(double newDx) {
        this.dx = newDx;
    }

    /**
     * Sets the dy.
     *
     * @param newDy
     *            the new dy
     */
    public void setdy(double newDy) {
        this.dy = newDy;
    }

    /**
     * . Receiving the angle and the speed (basically the characteristics of a
     * vector) and returning the corresponding velocity (using the above
     * constructor)
     *
     * @param angle
     *            value
     * @param speed
     *            value
     * @return velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        double dx = speed * Math.sin(angle);
        double dy = -speed * Math.cos(angle);
        return new Velocity(dx, dy);
    }

    /**
     * . Moving the received point in the plane one unit (according to the velocity)
     *
     * @param p
     *            point
     * @return new point
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Clones the velocity.
     *
     * @return the velocity
     */
    public Velocity cloneVelocity() {
        return new Velocity(this.dx, this.dy);
    }

}
