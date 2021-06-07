import biuoop.DrawSurface;

/**
 * The Interface Animation.
 *
 * @author Jonathan Fibush
 */
public interface Animation {

    /**
     * .
     * The function is in charge of the logic
     *
     * @param d            the surface
     * @param dt the dt
     */
    void doOneFrame(DrawSurface d, double dt);

    /**.
     * The function is in charge of the stopping condition
     *
     * @return true if should be stopped
     */
    boolean shouldStop();

}