/**
 * The Interface listener.
 *
 * @author Jonathan Fibush
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit. The hitter
     * parameter is the Shot that's doing the hitting.
     *
     * @param beingHit
     *            the object that is being hit
     * @param hitter
     *            the hitting ball
     */
    void hitEvent(HitNotifier beingHit, Shot hitter);
}