
/**
 * The Class HitRemover, which is in charge of removing objects from the game,
 * as well as keeping count of them.
 *
 * @author Jonathan Fibush
 */
public class HitRemover implements HitListener {

    /** The game. */
    private Game game;

    /** The remaining objects of the current type. */
    private Counter remaining;

    /** The object type. */
    private String objectType;

    /**
     * Instantiates a new hit remover.
     *
     * @param game            the game
     * @param remaining            the remaining objects
     * @param objectType the object type
     */
    public HitRemover(Game game, Counter remaining, String objectType) {
        this.game = game;
        this.remaining = remaining;
        this.objectType = objectType;
    }


    @Override
    public void hitEvent(HitNotifier beingHit, Shot hitter) {
        // if a block is hit
        if (this.objectType.equals("block")) {
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(this.game);
            // updating the counter of the remaining blocks
            this.remaining.decrease(1);
            // if an enemy is hit
        } else if (this.objectType.equals("enemy")) {
            if (hitter.isShotBySpaceship()) {
                // beingHit.removeHitListener(this);
                beingHit.removeFromGame(this.game);
                this.game.removeEnemy((Enemy) beingHit);
                // updating the counter of the remaining enemies
                this.remaining.decrease(1);
            }
        }
        // removing the hitting shot in any case
        hitter.removeFromGame(this.game);
    }

    /**
     * Gets the remaining amount of objects of this type.
     *
     * @return the remaining
     */
    public int getRemaining() {
        return this.remaining.getValue();
    }
}