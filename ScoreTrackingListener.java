/**
 * The listener interface for updating the score counter when enemies are being
 * hit and removed.
 *
 * @author Jonathan Fibush
 */
public class ScoreTrackingListener implements HitListener {

    /** The current score. */
    private Counter currentScore;

    /**
     * Instantiates a new score tracking listener.
     *
     * @param scoreCounter
     *            the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(HitNotifier beingHit, Shot hitter) {
        this.currentScore.increase(100);

    }
}