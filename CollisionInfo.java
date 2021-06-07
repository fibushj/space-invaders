/**
 * The Class CollisionInfo.
 *.
 * @author Jonathan Fibush
 */
public class CollisionInfo {

    /** The point. */
    private Point point;

    /** The collidable. */
    private Collidable collidable;

    /**
     * Instantiates a new collision info.
     *
     * @param point
     *            the point
     * @param collidable
     *            the collidable
     */
    public CollisionInfo(Point point, Collidable collidable) {
        this.point = point;
        this.collidable = collidable;
    }

    /**
     * Returns the collision point.
     *
     * @return the point
     */
    public Point collisionPoint() {
        return this.point;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return the collidable
     */

    public Collidable collisionObject() {
        return this.collidable;
    }
}