/**
 * The Interface HitNotifier.
 *
 * @author Jonathan Fibush
 */
public interface HitNotifier {

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl
     *            the listener
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl
     *            the listener
     */
    void removeHitListener(HitListener hl);

    /**
     * Removes the object from the game.
     *
     * @param game
     *            the game
     */
    void removeFromGame(Game game);
}