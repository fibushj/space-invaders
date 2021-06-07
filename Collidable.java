
/**
 * The Interface Collidable. .
 *
 * @author Jonathan Fibush
 */
public interface Collidable {

    /**
     * Returns the "collision shape" of the object.
     *
     * @return the collision rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it the hitter velocity.
     *
     * @param hitter
     *            the hitting shot
     */
    void hit(Shot hitter);
}
