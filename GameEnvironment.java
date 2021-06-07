import java.util.ArrayList;
import java.util.List;

/**
 * The Class GameEnvironment.
 *
 * @author Jonathan Fibush
 */
public class GameEnvironment {

    /** The collidables. */
    private List<Collidable> collidables = new ArrayList<Collidable>();

    /**
     * . Adds the given collidable to the environment
     *
     * @param c
     *            the collidable
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * Assume an object moving from line.start() to line.end(). If this object will
     * not collide with any of the collidables in this collection, return null.
     * Else, return the information about the closest collision that is going to
     * occur.
     *
     * @param trajectory
     *            the trajectory
     * @return the closest collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        double minDistance = trajectory.length();
        Point closestPoint = null, intersection, start = trajectory.start();
        Collidable closestCollidable = null;
        // going through all the collidables in the list in order to find the closest
        // intersection point, if exists
        for (int i = 0; i < this.collidables.size(); i++) {
            intersection = trajectory.closestIntersectionToStartOfLine(this.collidables.get(i).getCollisionRectangle());
            // if such found, updating the information about it
            if (intersection != null && intersection.distance(start) <= minDistance) {
                minDistance = intersection.distance(start);
                closestPoint = intersection;
                closestCollidable = this.collidables.get(i);
            }
        }
        if (closestPoint == null) {
            return null;
        }
        return new CollisionInfo(closestPoint, closestCollidable);
    }

    /**
     * Removes the collidable from the list.
     *
     * @param c the collidable
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

}