/**
 * . A point
 *
 * @author Jonathan Fibush
 */

public class Point {

    /** The x. */
    private double x;

    /** The y. */
    private double y;

    /**
     * . Constructor
     *
     * @param x
     *            value
     * @param y
     *            value
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * . Returns the distance of this point to the other point
     *
     * @param other
     *            point
     * @return the distance
     */
    public double distance(Point other) {
        return Math.sqrt(
                (this.x - other.getX()) * (this.x - other.getX()) + (this.y - other.getY()) * (this.y - other.getY()));
    }

    /**
     * Returns true if the points are equal, false otherwise.
     *
     * @param other
     *            point
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        return (this.x == other.getX() && this.y == other.getY());
    }

    /**
     * . Returns the x value of this point
     *
     * @return x
     */
    public double getX() {
        return this.x;
    }

    /**
     * . Returns the y value of this point
     *
     * @return y
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets the x.
     *
     * @param newX
     *            the new x
     */
    public void setX(double newX) {
        this.x = newX;
    }

    /**
     * Sets the y.
     *
     * @param newY
     *            the new y
     */
    public void setY(double newY) {
        this.y = newY;
    }

}
